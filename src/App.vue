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
    <!-- backgroundImage="https://images.unsplash.com/photo-1572336183013-960c3e1a0b54?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2250&q=80" -->
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
