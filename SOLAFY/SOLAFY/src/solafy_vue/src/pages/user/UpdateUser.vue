<template>
  <div>
    <div class="q-pa-md">
      <div class="q-gutter-y-md">
        <q-table></q-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth } from "boot/firebase";
export default {
  data() {
    return {
      userinfo: {},
      tab: "mypage",
      email: firebaseAuth.currentUser.email
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
