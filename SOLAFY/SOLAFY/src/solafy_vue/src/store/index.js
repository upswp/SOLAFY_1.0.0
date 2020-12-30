import Vue from "vue";
import Vuex from "vuex";

// BOARD쪽 사용을 위한 요소 추가
import { SETBOARDCOLUMN, SETBOARDSEARCHKEYWORDS } from "./mutation-type";

// import example from './module-example'

Vue.use(Vuex);

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default function(/* { ssrContext } */) {
  const Store = new Vuex.Store({
    state: {
      boardColumns: [],
      boardSearchKeywords: []
    },
    // 데이터인 state를 바로 반환할 수 있도록 getters 이용
    getters: {
      getBoardColumns: state => {
        return state.boardColumns;
      },
      getBoardSearchKeywords: state => {
        return state.boardSearchKeywords;
      }
    },
    // state를 변경할 수 있는 유일한 수단인 mutations
    mutations: {
      [SETBOARDCOLUMN](state, payload) {
        state.boardColumns = payload;
      },
      [SETBOARDSEARCHKEYWORDS](state, payload) {
        state.boardSearchKeywords = payload;
      }
    },
    // 비동기나 순차적 call이 필요할 때 사용
    actions: {
      [SETBOARDCOLUMN](state, payload) {
        state.boardColumns = payload;
      },
      [SETBOARDSEARCHKEYWORDS](state, payload) {
        state.boardSearchKeywords = payload;
      }
    },

    modules: {
      // example
    },

    // enable strict mode (adds overhead!)
    // for dev mode only
    strict: process.env.DEBUGGING
  });

  return Store;
}
