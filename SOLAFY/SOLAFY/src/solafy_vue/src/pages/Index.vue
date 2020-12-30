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
      <q-toggle
        false-value="false"
        true-value="true"
        v-model="idsave"
        label="이메일 저장"
      />
      <div>
        <q-btn label="login" type="submit" color="positive" />
        <q-btn label="reset" type="reset" color="negative" flat class="q-ml" />
      </div>
    </q-form>
    <div>
      <q-btn label="회원가입" @click="goUserRegi" />
      <q-btn label="비밀번호 재설정" @click="pwdprompt = true" />
    </div>

    <q-dialog v-model="pwdprompt" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">비밀번호를 재설정할 이메일을 입력해주세요</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input
            v-model="promptemail"
            autofocus
            @keyup.enter="sendPwdEmail"
          />
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="취소" v-close-popup />
          <q-btn flat label="메일 보내기" @click="sendPwdEmail" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { firebaseAuth, firebaseSt, firebase } from "boot/firebase";
export default {
  name: "PageIndex",
  data() {
    return {
      formData: {
        email: "",
        password: ""
      },
      idsave: null,
      pwdprompt: false,
      promptemail: ""
    };
  },
  mounted() {
    this.idsave = localStorage.idsave;
    this.formData.email = localStorage.email;
  },
  methods: {
    ...mapActions("store", ["loginUser"]),
    onSubmit() {
      this.checkidsave();

      firebaseAuth
        .setPersistence(firebase.auth.Auth.Persistence.SESSION)
        .then(() => {
          return firebaseAuth.signInWithEmailAndPassword(
            this.formData.email,
            this.formData.password
          );
        })
        .then(() => {
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "cloud_done",
            message: "로그인 성공"
          });
          this.$router.push("main");
        })
        .catch(error => {
          console.log(error);
          this.$q.notify({
            color: "red",
            textColor: "white",
            icon: "warning",
            message: "로그인 실패"
          });
        });
    },
    onReset() {
      this.formData.email = null;
      this.formData.password = null;
    },
    goUserRegi() {
      this.$router.push("UserRegi");
    },
    checkidsave() {
      if (this.idsave) {
        localStorage.idsave = true;
        localStorage.email = this.formData.email;
      } else {
        localStorage.idsave = false;
        localStorage.email = "";
      }
    },
    sendPwdEmail() {
      this.pwdprompt = false;
      firebaseAuth
        .sendPasswordResetEmail(this.promptemail)
        .then(() => {
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "cloud_done",
            message: "비밀번호 재설정 이메일을 전송했습니다"
          });
        })
        .catch(() => {
          this.$q.notify({
            color: "red",
            textColor: "white",
            icon: "warning",
            message: "이메일 전송에 실패했습니다"
          });
        });
    }
  }
};
</script>
