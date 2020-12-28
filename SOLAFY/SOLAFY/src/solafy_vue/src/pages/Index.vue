<template>
  <div class="q-pa-md" style="max-width: 400px">
    <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
      <q-input
        filled
        dense
        type="email"
        v-model="formData.email"
        label="email *"
        lazy-rules
        :rules="[val => (val && val.length > 0) || '이메일을 입력해주세요']"
      />

      <q-input
        filled
        dense
        type="password"
        v-model="formData.password"
        label="password *"
        lazy-rules
        :rules="[
          val => (val !== null && val !== '') || '비밀번호를 입력해주세요'
        ]"
      />
      <q-toggle v-model="formData.accept" label="이메일 저장" />
      <div>
        <q-btn label="login" type="submit" color="positive" />
        <q-btn label="reset" type="reset" color="negative" flat class="q-ml" />
      </div>
    </q-form>
    <div>
      <q-btn label="회원가입" @click="goUserRegi" />
    </div>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { firebaseAuth, firebaseSt } from "boot/firebase";
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
      firebaseAuth
        .signInWithEmailAndPassword(this.formData.email, this.formData.password)
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
    },
    onReset() {
      this.formData.email = null;
      this.formData.password = null;
      this.accept = false;
    },
    goUserRegi() {
      this.$router.push("UserRegi");
    }
  }
};
</script>
