/*
 * @Author: your name
 * @Date: 2021-08-15 19:48:00
 * @LastEditTime: 2021-08-15 20:30:45
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-interactive-paycard-master/src/utils/directive.js
 */

export function cardFocus (el, binding) {
  el.addEventListener('focus', () => {
    let id = el.attributes.id.value
    const list = ['v-card-month', 'v-card-year']
    if (list.find(item => item === id)) id = 'cardDate'
    binding.value(id)
  })
}

export function cardBlur (el, binding) {
  el.addEventListener('blur', () => {
    binding.value()
  })
}

export function cardFlippedFocus (el, binding) {
  el.addEventListener('focus', () => {
    const id = el.attributes.id.value
    binding.value(id, true)
  })
}

export function cardFlippedBlur (el, binding) {
  el.addEventListener('blur', () => {
    const id = el.attributes.id.value
    binding.value(id, false)
  })
}
