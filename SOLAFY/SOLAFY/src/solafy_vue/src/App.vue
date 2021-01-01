<template>
  <div id="q-app">
    <router-view />
  </div>
</template>
<script>
import Vue from "vue";
import VueLodash from "vue-lodash";
import lodash from "lodash";
Vue.use(VueLodash, { lodash: lodash });

export default {
  name: "App"
};
</script>
