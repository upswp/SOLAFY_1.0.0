import Vue from "vue";
import Vuex from "vuex";

// BOARD쪽 사용을 위한 요소 추가
import {
  SETBOARDCOLUMNS,
  SETBOARDSEARCHKEYWORDS,
  SETBOARDTYPE
} from "./mutation-type";

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
      boardSearchKeywords: [],
      boardType: ""
    },
    // 데이터인 state를 바로 반환할 수 있도록 getters 이용
    getters: {
      getBoardColumns: state => {
        return state.boardColumns;
      },
      getBoardSearchKeywords: state => {
        return state.boardSearchKeywords;
      },
      getBoardType: state => {
        return state.boardType;
      }
    },
    // state를 변경할 수 있는 유일한 수단인 mutations
    mutations: {
      [SETBOARDCOLUMNS](state, payload) {
        state.boardColumns = payload;
      },
      [SETBOARDSEARCHKEYWORDS](state, payload) {
        state.boardSearchKeywords = payload;
      },
      [SETBOARDTYPE](state, payload) {
        state.boardType = payload;
      }
    },
    actions: {},

    modules: {
      // example
    },

    // enable strict mode (adds overhead!)
    // for dev mode only
    strict: process.env.DEBUGGING
  });

  return Store;
}
