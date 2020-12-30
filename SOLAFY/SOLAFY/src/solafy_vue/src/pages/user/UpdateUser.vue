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
export default {
  data() {
    return {
      beforenickname: {},
      modifydata: {},
      nicknameDup: false
    };
  },
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
    clickDupbtn() {
      if (this.modifydata.nickname == this.beforenickname) {
        return;
      }

      axios
        .get("user/searchnickname/" + this.modifydata.nickname)
        .then(response => {
          console.log(response);
          console.log(response.data);
          if (response.data == "success") {
            this.nicknameDup = false;
            this.modifydata.nickname = "";
            this.$q.notify({
              color: "red",
              textColor: "white",
              icon: "warning",
              message: "사용할 수 없는 별명입니다"
            });
          } else {
            this.nicknameDup = true;
            this.$q.notify({
              color: "green",
              textColor: "white",
              icon: "check",
              message: "사용 가능한 별명입니다"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    onSubmit() {
      if (!this.nicknameDup) return;
      axios
        .put("/user/update", this.modifydata)
        .then(response => {
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "cloud",
            message: "수정되었습니다"
          });
          this.$router.push("/mypage");
        })
        .catch(error => {
          this.$q.notify({
            color: "red",
            textColor: "white",
            icon: "warning",
            message: "수정 실패"
          });
        });
    },
    goMypage() {
      this.$router.push("/mypage");
    }
  }
};
</script>
