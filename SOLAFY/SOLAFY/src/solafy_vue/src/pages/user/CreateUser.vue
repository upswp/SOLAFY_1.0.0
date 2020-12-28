<template>
  <div>
    <q-form @submit="onSubmit" class="q-gutter-md">
      <q-stepper v-model="step" ref="stepper" color="primary" animated>
        <q-step
          :name="1"
          title="이메일 입력"
          icon="assignment"
          :done="step > 1"
        >
          <q-input
            filled
            dense
            type="email"
            v-model="email"
            label="email *"
            lazy-rules
            :rules="[val => (val && val.length > 0) || '이메일을 입력해주세요']"
          >
            <template v-slot:prepend>
              <q-icon name="perm_identity"></q-icon>
            </template>
            <template v-slot:append>
              <q-icon
                name="close"
                @click="email = ''"
                class="cursor-pointer"
              ></q-icon>
            </template>
          </q-input>
        </q-step>

        <q-step
          :name="2"
          title="비밀번호 입력"
          icon="assignment"
          :done="step > 2"
        >
          <q-input
            filled
            dense
            type="password"
            v-model="password"
            label="password *"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || '비밀번호를 입력해주세요'
            ]"
          />
          <q-input
            filled
            dense
            type="password"
            v-model="pwdcnf"
            label="confirm password *"
            lazy-rules
            :rules="[
              val => (val && val === password) || '비밀번호를 확인해주세요'
            ]"
          />
        </q-step>

        <q-step
          :name="3"
          title="프로필 이미지 등록"
          icon="create_new_folder"
          :done="step > 3"
        >
          <q-file
            rounded
            outlined
            bottom-slots
            v-model="profileimg"
            label="이미지 선택"
            counter
            max-files="12"
          >
            <template v-slot:before>
              <q-icon name="attachment" />
            </template>

            <template v-slot:append>
              <q-icon
                v-if="profileimg !== null"
                name="close"
                @click.stop="profileimg = null"
                class="cursor-pointer"
              />
              <q-icon name="search" @click.stop />
            </template>
          </q-file>
        </q-step>

        <q-step :name="4" title="별명 입력" icon="assignment" :done="step > 4">
          <q-input
            filled
            dense
            v-model="nickname"
            label="별명 *"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || '별명을 입력해주세요'
            ]"
          />
        </q-step>

        <template v-slot:navigation>
          <q-stepper-navigation>
            <q-btn
              @click="$refs.stepper.next()"
              color="primary"
              :label="step === 4 ? 'Finish' : 'Continue'"
              type="step === 4 ? 'submit' : ''"
            />
            <q-btn
              v-if="step > 1"
              flat
              color="primary"
              @click="$refs.stepper.previous()"
              label="Back"
              class="q-ml-sm"
            />
          </q-stepper-navigation>
        </template>
      </q-stepper>
    </q-form>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth, firebaseSt } from "boot/firebase";
export default {
  name: "CreateMember",
  data() {
    return {
      step: 1,
      email: "",
      password: "",
      nickname: "",
      profileimg: { name: "" }
    };
  },
  methods: {
    onSubmit() {
      firebaseAuth
        .createUserWithEmailAndPassword(this.email, this.password)
        .then(Response => {
          let uid = firebaseAuth.currentUser.uid;
          //createMember_DB(uid);
          this.sendEmail();
          this.uploadImg_FB(uid);
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "cloud_done",
            message: "가입 성공, 이메일 인증을 해주세요!"
          });
          this.$router.push("/");
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
    },
    createMember_DB(curuid) {
      axios
        .put("member/createMember", {
          uid: curuid,
          nickname: this.nickname
        })
        .then(Response => {})
        .catch(error => {});
    },
    uploadImg_FB(curuid) {
      var storageRef = firebaseSt.ref();
      var imagesRef = storageRef.child("images");
      var metadata = {
        contentType: this.profileimg.type
      };
      var uploadTask = storageRef
        .child("profileimg/" + curuid)
        .put(this.profileimg, metadata);
    },
    sendEmail() {
      firebaseAuth.currentUser
        .sendEmailVerification()
        .then(function() {
          console.log("이메일이 전송됨");
        })
        .catch("email not sent");
    }
  }
};
</script>
