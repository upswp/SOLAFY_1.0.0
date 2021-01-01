<template>
  <div>
    <q-form @submit="onSubmit" class="q-gutter-md">
      <q-stepper v-model="step" ref="stepper" color="primary" animated>
        <!-- 1. 이메일 입력 -->
        <q-step
          :name="1"
          title="이메일 입력"
          icon="assignment"
          :done="step > 1"
        >
          <q-input
            filled
            type="email"
            v-model="email"
            label="email *"
            hint="이메일 인증을 위해 유효한 이메일 주소를 작성해주세요"
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

        <!-- 2. 비밀번호 입력 -->
        <q-step
          :name="2"
          title="비밀번호 입력"
          icon="assignment"
          :done="step > 2"
        >
          <q-input
            filled
            type="password"
            v-model="password"
            label="password *"
            hint="6자 이상 입력해주세요"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || '비밀번호를 입력해주세요'
            ]"
          />
          <q-input
            style="margin-top: 20px"
            filled
            type="password"
            v-model="pwdcnf"
            label="confirm password *"
            lazy-rules
            :rules="[
              val => (val && val === password) || '비밀번호를 확인해주세요'
            ]"
          />
        </q-step>

        <!-- 명찰 사진 등록 -->
        <q-step
          :name="3"
          title="명찰 사진 등록"
          icon="create_new_folder"
          :done="step > 3"
        >
          <q-file
            rounded
            outlined
            bottom-slots
            v-model="nametagimg"
            label="명찰 사진 선택"
            hint="사진과 이름이 잘 보이도록 촬영해주세요"
            accept=".jpg, .png"
            counter
            max-files="1"
            @rejected="onRejected"
            lazy-rules
            :rules="[
              val =>
                (val.name != null && val.name != '') || '사진을 선택해주세요'
            ]"
          >
            <template v-slot:before>
              <q-icon name="attachment" />
            </template>

            <template v-slot:append>
              <q-icon
                v-if="nametagimg !== null"
                name="close"
                @click.stop="nametagimg = null"
                class="cursor-pointer"
              />
              <q-icon name="search" @click.stop />
            </template>
          </q-file>
        </q-step>

        <!-- 4. 별명, 상태메시지, 프로필 사진 입력 -->
        <q-step
          :name="4"
          title="추가 정보 입력"
          icon="assignment"
          :done="step > 4"
        >
          <q-input
            filled
            v-model="nickname"
            label="별명 *"
            lazy-rules
            :rules="[
              val => (val !== null && val !== '') || '별명을 입력해주세요'
            ]"
          />
          <q-input filled v-model="statusMsg" label="상태 메시지" />
          <q-file
            style="margin-top: 20px"
            rounded
            outlined
            bottom-slots
            v-model="profileimg"
            label="프로필 사진 선택"
            accept=".jpg, .png"
            counter
            max-files="1"
            @rejected="onRejected"
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

        <!-- 각 스텝 하단에 출력되는 버튼 두개 -->
        <template v-slot:navigation>
          <q-stepper-navigation>
            <q-btn
              color="primary"
              :label="step === 4 ? 'Finish' : 'Continue'"
              type="submit"
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
import { notify } from "src/api/common.js";
export default {
  name: "CreateMember",
  data() {
    return {
      step: 1,
      email: "",
      password: "",
      pwdcnf: "",
      nickname: "",
      statusMsg: "",
      profileimg: { name: "" },
      nametagimg: { name: "" }
    };
  },
  methods: {
    // continue, finish 버튼이 눌릴 때마다 data의 유효성 확인
    // 필수 정보가 입력이 되지 않았을 경우에는 페이지가 넘어가지 않음
    dataVal() {
      var flag = false;

      // 각 step에서 data 검사
      switch (this.step) {
        case 1:
          if (this.email != null && this.email != "") flag = true;
          break;
        case 2:
          if (
            this.password != null &&
            this.password != "" &&
            this.pwdcnf != null &&
            this.pwdcnf != "" &&
            this.password == this.pwdcnf
          )
            flag = true;
          break;
        case 3:
          if (this.nametagimg.name != "" && this.nametagimg.name != null)
            flag = true;
          break;
      }

      // data가 유효하여 flag가 true로 변경되었다면 다음 페이지로 넘어감
      if (flag) this.$refs.stepper.next();
    },
    // 각 단계에서 continue or finish 버튼 클릭 시 호출
    onSubmit() {
      // 1, 2, 3 단계일 경우 dataVal을 호출하여 데이터의 유효성 검사
      if (this.step < 4) {
        this.dataVal();
        return;
      }

      // 4단계에서 데이터 유효성 검사
      if (this.nickname == null || this.nickname == "") return;

      // firebase에 회원가입 요청
      firebaseAuth
        .createUserWithEmailAndPassword(this.email, this.password)
        .then(response => {
          let uid = firebaseAuth.currentUser.uid;
          // 회원 데이터 등록
          this.createUser_DB(uid);
          this.uploadImg_FB(uid, true);
          // 프로필 사진의 경우, 등록되어 있을 때만 이미지 등록
          if (this.profileimg.name != "" && this.profileimg.name != null)
            this.uploadImg_FB(uid, false);
          //email 인증 메일 전송
          this.sendEmail();
          notify(
            "green",
            "white",
            "cloud_done",
            "회원 가입 성공, 이메일 인증을 진행해주세요!"
          );
          //this.getLoginUserInfo(response.user.uid);
          this.$emit("updateLoginUser");
          this.$router.push("/");
        })
        .catch(error => {
          console.log(error.message);
          notify("red", "white", "warning", "회원 가입 실패");
        });
    },
    // DB에 회원 정보 전송
    createUser_DB(curuid) {
      axios
        .post("/user/create", {
          uid: curuid,
          nickname: this.nickname,
          statusMessage: this.statusMsg
        })
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    },
    // firebase에 이미지 전송, true: 명찰 사진, false: 프로필 사진
    uploadImg_FB(curuid, flag) {
      var storageRef = firebaseSt.ref();
      var metadata = {
        contentType: flag ? this.nametagimg.type : this.profileimg.type
      };
      var uploadTask = storageRef
        .child((flag ? "nametagimg/" : "profileimg/") + curuid)
        .put(flag ? this.nametagimg : this.profileimg, metadata);
    },
    // 이메일 주소 인증 메일 전송
    sendEmail() {
      firebaseAuth.currentUser
        .sendEmailVerification()
        .then(function() {
          console.log("이메일이 전송됨");
        })
        .catch("email not sent");
    },
    // 사진 등록에 실패했을 경우 알림
    onRejected() {
      notify("red", "white", "warning", "사진 등록 실패");
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
