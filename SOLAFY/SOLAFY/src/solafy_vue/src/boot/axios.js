import Vue from "vue";
import axios from "axios";
axios.defaults.baseURL = process.env.API_URL;
Vue.prototype.$axios = axios;
