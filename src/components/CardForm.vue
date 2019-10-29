<template>
  <div class="card-form">
    <div class="card-list">
      <Card
        :amexCardMask="amexCardMask"
        :defaultCardMask="defaultCardMask"
        :cardNumber="cardNumberModel"
        :cardName="cardName"
        :cardMonth="cardMonth"
        :cardYear="cardYear"
        :cardCvv="cardCvv"
        :fields="fields"
        :isCardNumberMasked="isCardNumberMasked"
        :randomBackgrounds="randomBackgrounds"
        :backgroundImage="backgroundImage"
      />
    </div>
    <div class="card-form__inner">
      <div class="card-input">
        <label for="cardNumber" class="card-input__label">Card Number</label>
        <input
          type="tel"
          :id="fields.cardNumber"
          @input="changeNumber"
          @focus="focusCardNumber"
          @blur="blurCardNumber"
          class="card-input__input"
          :value="cardNumberModel"
          :maxlength="cardNumberMaxLength"
          data-card-field
          autocomplete="off"
        />
        <button
          class="card-input__eye"
          :class="{ '-active' : !isCardNumberMasked }"
          title="Show/Hide card number"
          :disabled="cardNumberModel === ''"
          @click="toggleMask"
        ></button>
      </div>
      <div class="card-input">
        <label for="cardName" class="card-input__label">Card Name</label>
        <input
          type="text"
          :id="fields.cardName"
          v-letter-only
          @input="changeName"
          class="card-input__input"
          :value="cardNameModel"
          data-card-field
          autocomplete="off"
        />
      </div>
      <div class="card-form__row">
        <div class="card-form__col">
          <div class="card-form__group">
            <label for="cardMonth" class="card-input__label">Expiration Date</label>
            <select
              class="card-input__input -select"
              :id="fields.cardMonth"
              v-model="cardMonthModel"
              @change="changeMonth"
              data-card-field
            >
              <option value disabled selected>Month</option>
              <option
                v-bind:value="n < 10 ? '0' + n : n"
                v-for="n in 12"
                v-bind:disabled="n < minCardMonth"
                v-bind:key="n"
              >{{generateMonthValue(n)}}</option>
            </select>
            <select
              class="card-input__input -select"
              :id="fields.cardYear"
              v-model="cardYearModel"
              @change="changeYear"
              data-card-field
            >
              <option value disabled selected>Year</option>
              <option
                v-bind:value="$index + minCardYear"
                v-for="(n, $index) in 12"
                v-bind:key="n"
              >{{$index + minCardYear}}</option>
            </select>
          </div>
        </div>
        <div class="card-form__col -cvv">
          <div class="card-input">
            <label for="cardCvv" class="card-input__label">CVV</label>
            <input
              type="tel"
              class="card-input__input"
              v-number-only
              :id="fields.cardCvv"
              maxlength="4"
              :value="cardCvvModel"
              @input="changeCvv"
              data-card-field
              autocomplete="off"
            />
          </div>
        </div>
      </div>

      <button class="card-form__button" v-on:click="invaildCard">Submit</button>
    </div>
  </div>
</template>

<script>
import Card from '@/components/Card'
export default {
  name: 'CardForm',
  directives: {
    'number-only': {
      bind (el) {
        function checkValue (event) {
          event.target.value = event.target.value.replace(/[^0-9]/g, '')
          if (event.charCode >= 48 && event.charCode <= 57) {
            return true
          }
          event.preventDefault()
        }
        el.addEventListener('keypress', checkValue)
      }
    },
    'letter-only': {
      bind (el) {
        function checkValue (event) {
          if (event.charCode >= 48 && event.charCode <= 57) {
            event.preventDefault()
          }
          return true
        }
        el.addEventListener('keypress', checkValue)
      }
    }
  },
  props: {
    cardNumber: [String, Number],
    cardName: String,
    cardMonth: [String, Number],
    cardYear: [String, Number],
    cardCvv: [String, Number],
    backgroundImage: [String, Object],
    randomBackgrounds: {
      type: Boolean,
      default: true
    }
  },
  components: {
    Card
  },
  data () {
    return {
      cardNumberModel: this.cardNumber,
      cardNameModel: this.cardName,
      cardMonthModel: this.cardMonth,
      cardYearModel: this.cardYear,
      cardCvvModel: this.cardCvv,
      minCardYear: new Date().getFullYear(),
      amexCardMask: '#### ###### #####',
      defaultCardMask: '#### #### #### ####',
      cardNumberTemp: '',
      fields: {
        cardNumber: 'cardNumber',
        cardName: 'cardName',
        cardMonth: 'cardMonth',
        cardYear: 'cardYear',
        cardCvv: 'cardCvv'
      },
      isCardNumberMasked: true,
      mainCardNumber: this.cardNumber,
      cardNumberMaxLength: 19
    }
  },
  mounted () {
    this.cardNumberTemp = this.defaultCardMask
  },
  computed: {
    getCardType () {
      let number = this.cardNumber
      let re = new RegExp('^4')
      if (number.match(re) != null) return 'visa'

      re = new RegExp('^(34|37)')
      if (number.match(re) != null) return 'amex'

      re = new RegExp('^5[1-5]')
      if (number.match(re) != null) return 'mastercard'

      re = new RegExp('^6011')
      if (number.match(re) != null) return 'discover'

      re = new RegExp('^62')
      if (number.match(re) != null) return 'unionpay'

      re = new RegExp('^9792')
      if (number.match(re) != null) return 'troy'

      return '' // default type
    },
    generateCardNumberMask () {
      return this.getCardType === 'amex' ? this.amexCardMask : this.defaultCardMask
    },
    minCardMonth () {
      if (this.cardYear === this.minCardYear) return new Date().getMonth() + 1
      return 1
    }
  },
  watch: {
    cardYear () {
      if (this.cardMonth < this.minCardMonth) {
        this.cardMonth = ''
      }
    }
  },
  methods: {
    generateMonthValue (n) {
      return n < 10 ? `0${n}` : n
    },
    changeName (e) {
      this.cardNameModel = e.target.value
      this.$emit('update:cardName', this.cardNameModel)
    },
    changeNumber (e) {
      this.cardNumberModel = e.target.value
      let value = this.cardNumberModel.replace(/\D/g, '')
      // american express, 15 digits
      if ((/^3[47]\d{0,13}$/).test(value)) {
        this.cardNumberModel = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 17
      } else if ((/^3(?:0[0-5]|[68]\d)\d{0,11}$/).test(value)) { // diner's club, 14 digits
        this.cardNumberModel = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 16
      } else if ((/^\d{0,16}$/).test(value)) { // regular cc number, 16 digits
        this.cardNumberModel = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{4})/, '$1 $2 ').replace(/(\d{4}) (\d{4}) (\d{4})/, '$1 $2 $3 ')
        this.cardNumberMaxLength = 19
      }
      this.$emit('update:cardNumber', this.cardNumberModel)
    },
    changeMonth () {
      this.$emit('update:cardMonth', this.cardMonthModel)
    },
    changeYear () {
      this.$emit('update:cardYear', this.cardYearModel)
    },
    changeCvv (e) {
      this.cardCvvModel = e.target.value
      this.$emit('update:cardCvv', this.cardCvvModel)
    },
    invaildCard () {
      let number = this.cardNumber
      let sum = 0
      let isOdd = true
      for (let i = number.length - 1; i >= 0; i--) {
        let num = number.charAt(i)
        if (isOdd) {
          sum += num
        } else {
          num = num * 2
          if (num > 9) {
            num = num.toString().split('').join('+')
          }
          sum += num
        }
        isOdd = !isOdd
      }
      if (sum % 10 !== 0) {
        alert('invaild card number')
      }
    },
    blurCardNumber () {
      if (this.isCardNumberMasked) {
        this.maskCardNumber()
      }
    },
    maskCardNumber () {
      this.mainCardNumber = this.cardNumberModel
      let arr = this.cardNumberModel.split('')
      arr.forEach((element, index) => {
        if (index > 4 && index < 14 && element.trim() !== '') {
          arr[index] = '*'
        }
      })
      this.cardNumberModel = arr.join('')
    },
    unMaskCardNumber () {
      this.cardNumberModel = this.mainCardNumber
    },
    focusCardNumber () {
      this.unMaskCardNumber()
    },
    toggleMask () {
      this.isCardNumberMasked = !this.isCardNumberMasked
      if (this.isCardNumberMasked) {
        this.maskCardNumber()
      } else {
        this.unMaskCardNumber()
      }
    }
  }
}
</script>
