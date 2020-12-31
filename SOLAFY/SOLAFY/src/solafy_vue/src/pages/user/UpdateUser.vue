<template>
  <div>
    <div class="q-pa-md">
      <q-form @submit="onSubmit" class="q-gutter-md">
        <q-input
          filled
          v-model="modifydata.nickname"
          label="별명 *"
          lazy-rules
          :rules="[
            val => (val && val.length > 0) || '별명을 입력해주세요',
            val =>
              beforenickname == modifydata.nickname ||
              nicknameDup ||
              '중복 확인을 해주세요'
          ]"
        >
          <template v-slot:append>
            <q-btn label="중복 확인" @click="clickDupbtn"></q-btn>
          </template>
        </q-input>

        <q-input filled v-model="modifydata.statusMessage" label="상태 메시지">
          <template v-slot:append>
            <q-icon
              name="close"
              @click="modifydata.statusMessage = ''"
              class="cursor-pointer"
            ></q-icon>
          </template>
        </q-input>

        <div>
          <q-btn label="수정" type="submit" color="primary" />
          <q-btn
            label="취소"
            @click="goMypage"
            color="primary"
            flat
            class="q-ml-sm"
          />
        </div>
      </q-form>
    </div>
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
      beforenickname: {},
      modifydata: {},
      nicknameDup: false
    };
  },
  // DB에서 회원 정보 가져옴
  mounted: function() {
    axios
      .get("/user/selectbyuid/" + firebaseAuth.currentUser.uid)
      .then(response => {
        this.beforenickname = response.data.nickname;
        this.modifydata = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    // 중복체크 버튼 클릭 시 호출
    clickDupbtn() {
      // input 태그에 입력된 닉네임과 DB에 등록되어 있는 닉네임이 같으면
      // == 닉네임이 변경되지 않았으면
      if (this.modifydata.nickname == this.beforenickname) {
        return;
      }

      // 입력된 닉네임을 DB에서 검색
      axios
        .get("user/searchnickname/" + this.modifydata.nickname)
        .then(response => {
          // 사용 중인 닉네임일 경우
          if (response.data == "success") {
            this.nicknameDup = false;
            this.modifydata.nickname = "";
            notify("red-6", "white", "warning", "사용할 수 없는 별명입니다");

            // 사용 중이지 않은 닉네임일 경우
          } else {
            this.nicknameDup = true;
            notify("green", "white", "check", "사용 가능한 별명입니다");
          }
        })
        .catch(error => {
          console.log(error);
          notify("red", "white", "warning", "중복 검사 중 오류 발생");
        });
    },

    // 수정 버튼 클릭 시 호출
    onSubmit() {
      // 닉네임이 변경되었는데 중복확인 버튼을 안 누른 경우
      if (!this.nicknameDup && this.beforenickname != this.modifydata.nickname)
        return;

      // 닉네임 수정 요청
      axios
        .put("/user/update", this.modifydata)
        .then(response => {
          notify("green", "white", "cloud", "사용 가능한 별명입니다");
          this.$router.push("/mypage");
        })
        .catch(error => {
          console.log(error);
          notify("red", "white", "warning", "수정 중 오류 발생");
        });
    },
    goMypage() {
      this.$router.push("/mypage");
    }
  }
};
</script>
