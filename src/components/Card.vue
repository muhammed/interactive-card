<template>
  <div class="card-item" :class="{ '-active' : isCardFlipped }">
    <div class="card-item__side -front">
      <div
        class="card-item__focus"
        :class="{'-active' : focusElementStyle }"
        :style="focusElementStyle"
        ref="focusElement"
      ></div>
      <div class="card-item__cover">
        <img
          :src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + currentCardBackground + '.jpeg'"
          class="card-item__bg"
        />
      </div>
      <div class="card-item__wrapper">
        <div class="card-item__top">
          <img
            src="https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/chip.png"
            class="card-item__chip"
          />
          <div class="card-item__type">
            <transition name="slide-fade-up">
              <img
                :src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + cardType + '.png'"
                v-if="cardType"
                :key="cardType"
                alt
                class="card-item__typeImg"
              />
            </transition>
          </div>
        </div>
        <label :for="fields.cardNumber" class="card-item__number" :ref="fields.cardNumber">
          <template v-if="cardType === 'amex'">
            <span v-for="(n, $index) in amexCardMask" :key="$index">
              <transition name="slide-fade-up">
                <div
                  class="card-item__numberItem"
                  :class="{ '-active' : n.trim() === '' }"
                  v-if="cardNumber.length > $index"
                  key="1"
                >{{cardNumber[$index]}}</div>
                <div
                  class="card-item__numberItem"
                  :class="{ '-active' : n.trim() === '' }"
                  v-else
                  key="2"
                >{{n}}</div>
              </transition>
            </span>
          </template>

          <template v-else>
            <span v-for="(n, $index) in otherCardMask" :key="$index">
              <transition name="slide-fade-up">
                <div
                  class="card-item__numberItem"
                  v-if="$index > 4 && $index < 15 && cardNumber.length > $index && n.trim() !== ''"
                >*</div>
                <div class="card-item__numberItem"
                  :class="{ '-active' : n.trim() === '' }"
                  key="1" v-else-if="cardNumber.length > $index">
                  {{cardNumber[$index]}}
                </div>
                <div
                  class="card-item__numberItem"
                  :class="{ '-active' : n.trim() === '' }"
                  v-else
                  key="2"
                >{{n}}</div>
              </transition>
            </span>
          </template>
        </label>
        <div class="card-item__content">
          <label :for="fields.cardName" class="card-item__info" :ref="fields.cardName">
            <div class="card-item__holder">Card Holder</div>
            <transition name="slide-fade-up">
              <div class="card-item__name" v-if="cardName.length" key="1">
                <transition-group name="slide-fade-right">
                  <span
                    class="card-item__nameItem"
                    v-for="(n, $index) in cardName.replace(/\s\s+/g, ' ')"
                    :key="$index + 1"
                  >{{n}}</span>
                </transition-group>
              </div>
              <div class="card-item__name" v-else key="2">Ad Soyad</div>
            </transition>
          </label>
          <div class="card-item__date" ref="cardDate">
            <label :for="fields.cardMonth" class="card-item__dateTitle">Expires</label>
            <label :for="fields.cardMonth" class="card-item__dateItem">
              <transition name="slide-fade-up">
                <span v-if="cardMonth" :key="cardMonth">{{cardMonth}}</span>
                <span v-else key="2">MM</span>
              </transition>
            </label>
            /
            <label for="cardYear" class="card-item__dateItem">
              <transition name="slide-fade-up">
                <span v-if="cardYear" :key="cardYear">{{String(cardYear).slice(2,4)}}</span>
                <span v-else key="2">YY</span>
              </transition>
            </label>
          </div>
        </div>
      </div>
    </div>
    <div class="card-item__side -back">
      <div class="card-item__cover">
        <img
          :src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + currentCardBackground + '.jpeg'"
          class="card-item__bg"
        />
      </div>
      <div class="card-item__band"></div>
      <div class="card-item__cvv">
        <div class="card-item__cvvTitle">CVV</div>
        <div class="card-item__cvvBand">
          <span v-for="(n, $index) in cardCvv" :key="$index">
            *
          </span>
        </div>
        <div class="card-item__type">
          <img
            :src="'https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/' + cardType + '.png'"
            v-if="cardType"
            class="card-item__typeImg"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Card',
  props: {
    cardType: String,
    amexCardMask: String,
    otherCardMask: String,
    cardNumber: [String, Number],
    cardName: String,
    cardMonth: [String, Number],
    cardYear: [String, Number],
    cardCvv: [String, Number],
    fields: Object
  },
  data () {
    return {
      currentCardBackground: Math.floor(Math.random() * 25 + 1),
      focusElementStyle: null,
      currentFocus: null,
      isFocused: false,
      isCardFlipped: false
    }
  },
  watch: {
    currentFocus () {
      if (this.currentFocus) {
        this.changeFocus()
      } else {
        this.focusElementStyle = null
      }
    }
  },
  mounted () {
    let self = this
    let fields = document.querySelectorAll('[data-card-field]')
    fields.forEach(element => {
      element.addEventListener('focus', () => {
        this.isFocused = true
        if (element.id === this.fields.cardYear || element.id === this.fields.cardMonth) {
          this.currentFocus = 'cardDate'
        } else {
          this.currentFocus = element.id
        }
        this.isCardFlipped = element.id === this.fields.cardCvv
      })
      element.addEventListener('blur', () => {
        this.isCardFlipped = !element.id === this.fields.cardCvv
        setTimeout(() => {
          if (!self.isFocused) {
            self.currentFocus = null
          }
        }, 300)
        self.isFocused = false
      })
    })
  },
  methods: {
    changeFocus () {
      let target = this.$refs[this.currentFocus]
      this.focusElementStyle = target ? {
        width: `${target.offsetWidth}px`,
        height: `${target.offsetHeight}px`,
        transform: `translateX(${target.offsetLeft}px) translateY(${target.offsetTop}px)`
      } : null
    }
  }
}
</script>

<style scoped lang="scss">

</style>
