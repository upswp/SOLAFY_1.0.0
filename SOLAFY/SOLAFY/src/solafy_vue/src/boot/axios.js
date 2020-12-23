import Vue from "vue";
import axios from "axios";
axios.defaults.baseURL = "http://localhost:9999/solafy";
Vue.prototype.$axios = axios;
