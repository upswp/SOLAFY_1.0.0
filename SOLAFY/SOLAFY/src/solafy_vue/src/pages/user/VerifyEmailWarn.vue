<template>
  <div>
    <h6>
      이메일 인증이 완료되지 않았습니다. 인증 완료 후 페이지 새로 고침 하세요.
    </h6>
    <q-expansion-item v-model="expanded" icon="mail" label="이메일 변경하기">
      <q-card>
        <q-card-section>
          <q-form @submit="onSubmit" class="q-gutter-md">
            <q-input
              filled
              type="email"
              v-model="email"
              label="email *"
              lazy-rules
              :rules="[
                val => (val && val.length > 0) || '이메일을 입력해주세요'
              ]"
            />
            <div>
              <q-btn label="변경" type="submit" color="positive" />
            </div>
          </q-form>
        </q-card-section>
      </q-card>
    </q-expansion-item>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth } from "boot/firebase";
import { notify } from "src/api/common.js";

export default {
  data() {
    return {
      expanded: false,
      email: ""
    };
  },
  methods: {
    // 이메일 변경하기 버튼 클릭 시 호출
    onSubmit() {
      // 이메일 업데이트 요청
      firebaseAuth.currentUser
        .updateEmail(this.email)
        // 이메일이 업데이트 되었을 경우 확인 이메일 전송
        .then(Response => {
          this.sendEmail();
        })
        .catch(error => {
          console.log(error);
          notify("red", "white", "warning", "이메일 변경 실패");
        });
    },
    // 인증 메일 전송
    sendEmail() {
      firebaseAuth.currentUser
        .sendEmailVerification()
        .then(Response => {
          console.log("이메일이 전송됨");
          notify(
            "green",
            "white",
            "cloud_done",
            "메일 전송 완료, 이메일 인증을 완료해주세요!"
          );
          this.goindex();
        })
        .catch("email not sent");
    },
    goindex() {
      this.$router.push("/");
    }
  }
};
</script>
