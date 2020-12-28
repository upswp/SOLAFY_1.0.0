<template>
  <div></div>
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
