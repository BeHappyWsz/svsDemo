import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    user: null
  },
  mutations: {
    UPDATE_SET_USER (state, user) {
      state.user = user
    }
  },
  actions: {

  }
})
export default store
