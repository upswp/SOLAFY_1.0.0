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
          <div>{{ profileimg.name }}</div>
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
  name: "createMember",
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
          //this.uploadImg_FB(uid);
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "cloud_done",
            message: "가입 성공"
          });
          this.$router.push("");
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
      // Points to 'images/space.jpg'
      // Note that you can use variables to create child values
      //var fileName = 'space.jpg';
      //var spaceRef = imagesRef.child(fileName);

      // File path is 'images/space.jpg'
      //var path = spaceRef.fullPath

      // File name is 'space.jpg'
      //var name = spaceRef.name

      // Points to 'images'
      //var imagesRef = spaceRef.parent;
      var metadata = {
        contentType: this.profileimg.type
      };
      var uploadTask = storageRef
        .child("images/" + this.profileimg.name)
        .put(this.profileimg, metadata);
      // Listen for state changes, errors, and completion of the upload.
      uploadTask.on(
        firebaseSt.TaskEvent.state_changed, // or 'state_changed'
        function(snapshot) {
          // Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
          var progress =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
          console.log("Upload is " + progress + "% done");
          switch (snapshot.state) {
            case firebaseSt.TaskState.paused: // or 'paused'
              console.log("Upload is paused");
              break;
            case firebaseSt.TaskState.running: // or 'running'
              console.log("Upload is running");
              break;
          }
        },
        function(error) {},
        function() {
          // Upload completed successfully, now we can get the download URL
          uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
            console.log("File available at", downloadURL);
          });
        }
      );
    }
  }
};
</script>
