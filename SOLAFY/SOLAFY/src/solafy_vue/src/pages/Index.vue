<template>
  <div class="q-pa-md">
    <!-- logo -->
    <div class="fit row justify-center content-center">
      <img
        style="width : 350px; height : 350px;"
        src="~src/assets/mainLogo.png"
      />
    </div>
    <!-- 로그인 입력 폼 -->
    <div class="fit row justify-center content-center">
      <q-form
        @submit="onSubmit"
        @reset="onReset"
        class="col-5 self-center"
        style="max-width : 300px"
      >
        <q-input
          dense
          type="email"
          v-model="formData.email"
          label="email *"
          lazy-rules
          :rules="[val => (val && val.length > 0) || '이메일을 입력해주세요']"
        />

        <q-input
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
        <div class="fit row justify-center content-center">
          <q-btn label="login" type="submit" color="positive" />
          <q-btn label="reset" type="reset" color="negative" flat />
        </div>
      </q-form>
    </div>
    <!-- 버튼 -->
    <div class="fit row justify-center content-center">
      <q-btn flat label="회원가입" @click="goUserRegi" />
      <q-btn flat label="비밀번호 재설정" @click="pwdprompt = true" />
    </div>

    <!-- 비밀번호 재설정 다이얼로그 -->
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
import axios from "axios";
import { mapActions } from "vuex";
import { notify } from "src/api/common.js";
import { firebaseAuth, firebaseSt, firebase } from "boot/firebase";
import { SessionStorage } from "quasar";

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
  // 로컬 저장소에 저장되어 있는 이메일과 이메일 저장 여부(boolean) 가져옴
  mounted() {
    this.idsave = localStorage.idsave;
    this.formData.email = localStorage.email;
  },
  methods: {
    ...mapActions("store", ["loginUser"]),
    //로그인 버튼 클릭 시 호출
    onSubmit() {
      // 이메일과 이메일 저장 여부를 로컬 저장소에 저장
      this.checkidsave();
      // 로그인 결과를 세션에 저장
      firebaseAuth
        .setPersistence(firebase.auth.Auth.Persistence.SESSION)
        .then(() => {
          // 로그인
          return firebaseAuth
            .signInWithEmailAndPassword(
              this.formData.email,
              this.formData.password
            )
            .then(response => {
              //this.getLoginUserInfo(response.user.uid);
              console.log("emit");
              this.$emit("updateLoginUser");
            });
        })
        .then(() => {
          notify("green", "white", "cloud_done", "로그인 성공");
          this.$router.push("main");
        })
        .catch(error => {
          console.log(error);
          notify("red-6", "white", "warning", "로그인 실패");
        });
    },
    onReset() {
      this.formData.email = null;
      this.formData.password = null;
    },
    goUserRegi() {
      this.$router.push("UserCreate");
    },
    // 이메일과 이메일 저장 여부를 로컬 저장소에 저장
    checkidsave() {
      if (this.idsave) {
        localStorage.idsave = true;
        localStorage.email = this.formData.email;
      } else {
        localStorage.idsave = false;
        localStorage.email = "";
      }
    },
    // 비밀번호 재설정 다이얼로그의 메일보내기 버튼 클릭 시 호출
    sendPwdEmail() {
      // 다이얼로그 닫음
      this.pwdprompt = false;

      // 비밀번호 재설정 메일 보냄
      firebaseAuth
        .sendPasswordResetEmail(this.promptemail)
        .then(() => {
          notify(
            "green",
            "white",
            "cloud_done",
            "비밀번호 재설정 이메일을 전송했습니다"
          );
        })
        .catch(() => {
          notify("red-6", "white", "warning", "메일 전송에 실패했습니다");
        });
    },
    getLoginUserInfo(uid) {
      axios
        .get("/user/selectbyuid/" + uid)
        .then(response => {
          SessionStorage.set("loginUser", response.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
