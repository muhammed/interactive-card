<template>
  <div class="card-form">
    <div class="card-list">
      <Card
        :cardType="getCardType"
        :amexCardMask="amexCardMask"
        :otherCardMask="otherCardMask"
        :cardNumber="cardNumber"
        :cardName="cardName"
        :cardMonth="cardMonth"
        :cardYear="cardYear"
        :cardCvv="cardCvv"
        :fields="fields"
      />
    </div>
    <div class="card-form__inner">
      <div class="card-input">
        <label for="cardNumber" class="card-input__label">Card Number</label>
        <input
          type="text"
          :id="fields.cardNumber"
          @focus="focusField(fields.cardNumber)"
          v-mask="generateCardNumberMask"
          @input="changeNumber"
          class="card-input__input"
          v-model="cardNumberModel"
          data-card-field
          autocomplete="off"
        />
      </div>
      <div class="card-input">
        <label for="cardName" class="card-input__label">Card Name</label>
        <input
          type="text"
          :id="fields.cardName"
          @focus="focusField(fields.cardName)"
          @input="changeName"
          class="card-input__input"
          v-model="cardNameModel"
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
              @focus="focusField(fields.cardMonth)"
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
              @focus="focusField(fields.cardYear)"
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
              type="text"
              class="card-input__input"
              @focus="focusField(fields.cardCvv)"
              v-mask="'####'"
              :id="fields.cardCvv"
              maxlength="4"
              v-model="cardCvvModel"
              @input="changeCvv"
              data-card-field
              autocomplete="off"
            />
          </div>
        </div>
      </div>

      <button class="card-form__button">Submit</button>
    </div>
  </div>
</template>

<script>
import { mask } from 'vue-the-mask'
import Card from '@/components/Card'
export default {
  name: 'CardForm',
  directives: { mask },
  props: {
    cardNumber: [String, Number],
    cardName: String,
    cardMonth: [String, Number],
    cardYear: [String, Number],
    cardCvv: [String, Number]
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
      otherCardMask: '#### #### #### ####',
      cardNumberTemp: '',
      fields: {
        cardNumber: 'cardNumber',
        cardName: 'cardName',
        cardMonth: 'cardMonth',
        cardYear: 'cardYear',
        cardCvv: 'cardCvv'
      }
    }
  },
  mounted () {
    this.cardNumberTemp = this.otherCardMask
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

      return 'visa' // default type
    },
    generateCardNumberMask () {
      return this.getCardType === 'amex' ? this.amexCardMask : this.otherCardMask
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
    changeName () {
      this.$emit('update:cardName', this.cardNameModel)
    },
    changeNumber () {
      this.$emit('update:cardNumber', this.cardNumberModel)
    },
    changeMonth () {
      this.$emit('update:cardMonth', this.cardMonthModel)
    },
    changeYear () {
      this.$emit('update:cardYear', this.cardYearModel)
    },
    changeCvv () {
      this.$emit('update:cardCvv', this.cardCvvModel)
    },
    focusField (field) {
      console.log(field)
    }
  }
}
</script>
