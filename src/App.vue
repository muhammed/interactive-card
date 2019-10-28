<template>
  <div class="wrapper" id="app">
    <CardForm
      :cardNumber.sync="cardNumber"
      :cardName.sync="cardName"
      :cardMonth.sync="cardMonth"
      :cardYear.sync="cardYear"
      :cardCvv.sync="cardCvv"
      @update:cardNumber="updateCardNumber"
      @update:cardName="updateCardName"
      @update:cardMonth="updateCardMonth"
      @update:cardYear="updateCardYear"
      @update:cardCvv="updateCardCvv"
    />
  </div>
</template>

<script>
import CardForm from '@/components/CardForm'
export default {
  name: 'app',
  components: {
    CardForm
  },
  data () {
    return {
      cardName: '',
      cardNumber: '',
      cardMonth: '',
      cardYear: '',
      cardCvv: ''
    }
  },
  methods: {
    updateCardNumber (val) {
    },
    updateCardName (val) {
    },
    updateCardMonth (val) {
    },
    updateCardYear (val) {
    },
    updateCardCvv (val) {
    }
  }
}
</script>

<style lang="scss">
@import '../src/assets/style.scss';
</style>
