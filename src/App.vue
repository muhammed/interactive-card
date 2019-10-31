<template>
  <div class="wrapper" id="app">
    <CardForm
      :form-data="formData"
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
      formData: {
        cardName: '',
        cardNumber: '',
        cardMonth: '',
        cardYear: '',
        cardCvv: ''
      }
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
