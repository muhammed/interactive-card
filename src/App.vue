<!--
 * @Author: your name
 * @Date: 2021-05-09 17:12:11
 * @LastEditTime: 2021-08-15 20:42:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-interactive-paycard-master/src/App.vue
-->
<style scoped lang="sass">
.input_container_item
  display: inline-flex
  align-content: center
  height: 50px

</style>
<template>
  <div class="wrapper" id="app">
    <CardForm
      ref="card"
      :form-data="formData"
      :initIsCardNumberMasked="true"
      :showCardMask="true"
      :isPC="isPC"
      @input-card-number="updateCardNumber"
      @input-card-name="updateCardName"
      @input-card-month="updateCardMonth"
      @input-card-year="updateCardYear"
      @input-card-cvv="updateCardCvv"
    />
    <div class="input_container">
        <div class="input_container_item">
          <label>v-card-cardName</label>
          <input
            id="v-card-name"
            v-card-focus="handleCardFocus"
            v-card-blur="handleCardBlur"
            :value="formData.cardName"
            :maxlength="cardNumberMaxLength"
            @input="formData.cardName = $event.target.value"
          >
        </div>
        <div class="input_container_item">
          <label>v-card-cardNumber</label>
          <input
            v-card-focus="handleCardFocus"
            v-card-blur="handleCardBlur"
            @input="changeNumber"
            id="v-card-number"
            :maxlength="cardNumberMaxLength"
            :value="formData.cardNumber"
          >
        </div>
        <div class="input_container_item">
          <label>v-card-cardMonth</label>
          <input
            id="v-card-month"
            v-card-focus="handleCardFocus"
            v-card-blur="handleCardBlur"
            :value="formData.cardMonth"
            :maxlength="cardNumberMaxLength"
            @input="formData.cardMonth = $event.target.value"
          >
        </div>
        <div class="input_container_item">
          <label>v-card-cardYear</label>
          <input
            id="v-card-year"
            v-card-focus="handleCardFocus"
            v-card-blur="handleCardBlur"
            :value="formData.cardYear"
            :maxlength="cardNumberMaxLength"
            @input="formData.cardYear = $event.target.value"
          >
        </div>
        <div class="input_container_item">
          <label>v-card-cardCvv</label>
          <input
            id="v-card-ccv"
            v-card-flipped-focus="handleCardFlipped"
            v-card-flipped-blur="handleCardFlipped"
            :value="formData.cardCvv"
            :maxlength="cardNumberMaxLength"
            @input="formData.cardCvv = $event.target.value"
          >
        </div>
    </div>
  </div>
</template>

<script>
import CardForm from '@/components/CardForm'
import {
  cardFocus,
  cardBlur,
  cardFlippedBlur,
  cardFlippedFocus
} from '@/utils/directive'

export default {
  name: 'app',
  components: {
    CardForm
  },
  data () {
    return {
      cardNumberMaxLength: 16,
      isPC: false,
      formData: {
        cardName: '',
        cardNumber: '',
        cardMonth: '',
        cardYear: '',
        cardCvv: ''
      }
    }
  },
  directives: {
    cardFocus,
    cardBlur,
    cardFlippedFocus,
    cardFlippedBlur
  },
  methods: {
    handleCardFocus (id) {
      this.$refs.card._setCardPropValue('currentFocus', id)
    },
    handleCardBlur () {
      this.$refs.card._setCardPropValue('currentFocus', null)
    },
    handleCardFlipped (id, value) {
      this.$refs.card._setCardPropValue('currentFocus', id)
      this.$refs.card._setCardPropValue('isCardFlipped', value)
    },
    updateCardNumber (val) {
      this.$refs.card._setCardPropValue('currentFocus', null)
    },
    updateCardName (val) {
    },
    updateCardMonth (val) {
    },
    updateCardYear (val) {
    },
    updateCardCvv (val) {
    },
    foucus (e) {
      // 自定义指令
      const id = e.target.attributes.id.value
      this.$refs.card._setCardPropValue('currentFocus', id)
    },
    changeNumber (e) {
      this.formData.cardNumber = e.target.value
      let value = this.formData.cardNumber.replace(/\D/g, '')
      // american express, 15 digits
      if ((/^3[47]\d{0,13}$/).test(value)) {
        this.formData.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 17
      } else if ((/^3(?:0[0-5]|[68]\d)\d{0,11}$/).test(value)) { // diner's club, 14 digits
        this.formData.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 16
      } else if ((/^\d{0,16}$/).test(value)) { // regular cc number, 16 digits
        this.formData.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{4})/, '$1 $2 ').replace(/(\d{4}) (\d{4}) (\d{4})/, '$1 $2 $3 ')
        this.cardNumberMaxLength = 19
      }
      // eslint-disable-next-line eqeqeq
      if (e.inputType == 'deleteContentBackward') {
        let lastChar = this.formData.cardNumber.substring(this.formData.cardNumber.length, this.formData.cardNumber.length - 1)
        // eslint-disable-next-line eqeqeq
        if (lastChar == ' ') { this.formData.cardNumber = this.formData.cardNumber.substring(0, this.formData.cardNumber.length - 1) }
      }
      this.$emit('input-card-number', this.formData.cardNumber)
    }
  },
  mounted () {
    this.$i18n.locale = navigator.language
  }
}
</script>

<style lang="scss">
@import '../src/assets/style.scss';
</style>
