<template>
  <div class="q-pa-md" style="max-width: 400px">
    <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
      <q-input
        filled
        dense
        type="email"
        v-model="formData.email"
        label="admin email *"
        lazy-rules
        :rules="[
          val => (val && val.length > 0) || '관리자 이메일을 입력해주세요.'
        ]"
      />

      <q-input
        filled
        dense
        type="password"
        v-model="formData.password"
        label="admin password *"
        lazy-rules
        :rules="[
          val => (val !== null && val !== '') || '비밀번호를 입력해주세요'
        ]"
      />
      <q-toggle
        v-model="formData.accept"
        label="false 회원가입 || true 로그인"
      />
      <div>
        <q-btn label="로그인" type="submit" color="positive" />
        <q-btn label="리셋" type="reset" color="negative" flat class="q-ml" />
      </div>
    </q-form>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { firebaseAuth, firebaseDb } from "boot/firebase";
export default {
  name: "PageIndex",
  data() {
    return {
      formData: {
        email: "",
        password: "",
        accept: false
      }
    };
  },
  methods: {
    ...mapActions("store", ["loginUser"]),
    onSubmit() {
      if (!this.formData.accept) {
        // 가입용 코드
        console.log(this.formData);
        firebaseAuth
          .createUserWithEmailAndPassword(
            this.formData.email,
            this.formData.password
          )
          .then(Response => {
            let userId = firebaseAuth.currentUser.uid;
            firebaseDb.ref("users/" + userId).set({
              email: this.formData.email,
<<<<<<< HEAD
              nickname: "test"
=======
              nickname:
                "견222222222222222222222222222222222222222222222222222222222"
>>>>>>> 0104d6086693067c6d3f0eb814a7712f8ffd48fb
            });
            this.$q.notify({
              color: "green",
              textColor: "white",
              icon: "cloud_done",
              message: "가입 성공"
            });
          })
          .catch(error => {
            console.log(error.message);
            this.$q.notify({
              color: "red",
              textColor: "white",
              icon: "warning",
              message: "가입 실패"
            });
          });
      } else {
        //로그인용 코드
        firebaseAuth
          .signInWithEmailAndPassword(
            this.formData.email,
            this.formData.password
          )
          .then(Response => {
            console.log(Response);
            this.$q.notify({
              color: "green",
              textColor: "white",
              icon: "cloud_done",
              message: "인증 성공"
            });
            this.$router.push("main");
          })
          .catch(error => {
            console.log(error);
            this.$q.notify({
              color: "red",
              textColor: "white",
              icon: "warning",
              message: "인증 실패"
            });
          });
      }
    },

    onReset() {
      this.formData.email = null;
      this.formData.password = null;
      this.accept = false;
    }
  }
};
</script>
