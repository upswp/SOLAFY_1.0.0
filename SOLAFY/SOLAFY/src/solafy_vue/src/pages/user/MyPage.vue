<template>
  <div>{{ userinfo.nickname }}의 마이페이지</div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth } from "boot/firebase";
export default {
  data() {
    return {
      userinfo: {}
    };
  },
  mounted: function() {
    axios
      .get("/user/selectbyuid/" + firebaseAuth.currentUser.uid)
      .then(response => {
        this.userinfo = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {}
};
</script>
