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
          >
            <template v-slot:append>
              <q-btn label="중복 확인" @click="clickDupbtn"></q-btn>
            </template>
          </q-input>
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
import { notify } from "src/api/common.js";
import { firebaseAuth, firebaseSt } from "boot/firebase";

export default {
  name: "UserCreate",
  data() {
    return {
      // 회원 가입 단계
      step: 1,
      //* 회원 정보
      email: "",
      password: "",
      // 비밀번호 확인
      pwdcnf: "",
      nickname: "",
      // 닉네임 중복 여부를 확인한 닉네임 저장
      nicknamecnf: "",
      statusMsg: "",
      // 프로필 사진
      profileimg: { name: "" },
      // 명찰 사진
      nametagimg: { name: "" }
    };
  },
  methods: {
    /**
     * @Method설명 : 중복체크 버튼 클릭 시 호출
     * @변경이력 :
     */
    clickDupbtn() {
      // 입력된 닉네임을 DB에서 검색
      axios
        .get("user/searchnickname/" + this.nickname)
        .then(response => {
          // 사용 중인 닉네임일 경우
          if (response.data == "success") {
            this.nicknamecnf = "";
            this.nickname = "";
            notify("red-6", "white", "warning", "사용할 수 없는 별명입니다");
          } else {
            this.nicknamecnf = this.nickname;
            notify("green", "white", "check", "사용 가능한 별명입니다");
          }
        })
        .catch(error => {
          console.log(error);
          notify("red", "white", "warning", "중복 검사 중 오류 발생");
        });
    },
    /**
     * @Method설명 : 각 단계에서 continue or finish 버튼 클릭 시 호출
     * @변경이력 :
     */
    onSubmit() {
      // 1, 2, 3 단계일 경우 dataVal을 호출하여 데이터의 유효성 검사
      if (this.step < 4) {
        this.verifyData();
        return;
      }

      // 4단계에서 데이터 유효성 검사
      if (this.nickname == null || this.nickname == "") return;

      // 입력창의 닉네임과 중복 검사 시 저장한 닉네임이 다르면
      // 현재 입력창의 닉네임은 중복검사를 안한 것
      if (this.nickname != this.nicknamecnf) {
        notify("red", "white", "warning", "닉네임 중복을 확인해주세요");
        return;
      }

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
          if (error.code == "auth/email-already-in-use")
            notify("red", "white", "warning", "이미 사용 중인 이메일입니다.");
          else if (error.code == "auth/invalid-email")
            notify("red", "white", "warning", "사용할 수 없는 이메일입니다.");
          else if (error.code == "auth/weak-password")
            notify("red", "white", "warning", "사용할 수 없는 비밀번호입니다.");
          else notify("red", "white", "warning", "회원 가입에 실패했습니다.");
        });
    },
    /**
     * @Method설명 : continue, finish 버튼이 눌릴 때마다 data의 유효성 확인
     * 필수 정보가 입력이 되지 않았을 경우에는 페이지가 넘어가지 않음
     * @변경이력 :
     */
    verifyData() {
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
    /**
     * @Method설명 : DB에 회원 정보 전송
     * @변경이력 :
     */
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
    /**
     * @Method설명 : firebase에 이미지 전송
     *  true: 명찰 사진, false: 프로필 사진
     * @변경이력 :
     */
    uploadImg_FB(curuid, flag) {
      var storageRef = firebaseSt.ref();
      var metadata = {
        contentType: flag ? this.nametagimg.type : this.profileimg.type
      };
      var uploadTask = storageRef
        .child((flag ? "nametagimg/" : "profileimg/") + curuid)
        .put(flag ? this.nametagimg : this.profileimg, metadata);
    },
    /**
     * @Method설명 : 이메일 주소 인증 메일 전송
     * @변경이력 :
     */
    sendEmail() {
      firebaseAuth.currentUser
        .sendEmailVerification()
        .then(function() {
          console.log("이메일이 전송됨");
        })
        .catch("email not sent");
    },
    /**
     * @Method설명 : 사진 등록에 실패했을 경우 알림
     * @변경이력 :
     */
    onRejected() {
      notify("red", "white", "warning", "사진 등록 실패");
    }
  }
};
</script>
