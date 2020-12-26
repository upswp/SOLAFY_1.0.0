<template
  ><div>
    <table>
      <tr>
        <td>번호</td>
        <td v-text="item.groupNo"></td>
      </tr>
      <tr>
        <td>타이틀</td>
        <td v-text="item.title"></td>
      </tr>
      <tr>
        <td>타입</td>
        <td v-text="item.type"></td>
      </tr>
      <tr>
        <td>설명</td>
        <td v-text="item.description"></td>
      </tr>
    </table>

    <!-- TODO : 가입신청이 되어있는가? 확인 해야함. -->
    <div v-if="status == 0">
      <q-btn color="primary" @click="groupRegister">가입신청</q-btn>
    </div>
    <div v-else-if="status == 99">
      <q-btn color="orange" @click="groupRegiCancel">가입 취소</q-btn>
    </div>
    <div v-else>
      <q-btn color="green" @click="groupHome">그룹 홈</q-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { firebaseAuth, firebaseDb } from "boot/firebase";
export default {
  data() {
    return {
      status: -1,
      item: {
        groupNo: "",
        title: "",
        type: "",
        description: ""
      }
    };
  },
  methods: {
    // 가입 신청
    groupRegister() {
      // 가입 메세지 다이얼로그 띄우기
      this.$q
        .dialog({
          title: "가입신청 메세지",
          message: "가입 신청 메세지를 입력하세요.",
          prompt: {
            model: "",
            type: "text"
          },
          cancel: true,
          persistent: true
        })
        .onOk(data => {
          axios
            .post("group/createApplyGroupSignUp", {
              uid: firebaseAuth.currentUser.uid,
              groupNo: this.item.groupNo,
              grade: 99,
              statusMessage: "안뇽하세요?",
              regiMessage: data
            })
            .then(Response => {
              this.$q.notify({
                color: "green",
                textColor: "white",
                icon: "cloud_done",
                message: "신청 성공"
              });
              this.$router.go(-1);
            })
            .catch(error => {
              this.$q.notify({
                color: "green",
                textColor: "white",
                icon: "cloud_done",
                message: "신청 실패"
              });
            });
        })
        .onCancel(() => {
          // console.log('>>>> Cancel')
        })
        .onDismiss(() => {
          // console.log('I am triggered on both OK and Cancel')
        });
    },
    groupRegiCancel() {
      axios
        .delete("group/deleteGroupMemberself", {
          data: {
            uid: firebaseAuth.currentUser.uid,
            groupNo: this.item.groupNo
          }
        })
        .then(Response => {
          this.$router.go(-1);
        })
        .catch(error => {});
    },
    groupHome() {
      this.$router.push({
        name: "groupHome",
        params: {
          groupNo: this.item.groupNo
        }
      });
    }
  },
  created() {
    // 가입 상태
    this.status = this.$route.params.grade;
    axios
      .get("group/selectGroupByNo/" + this.$route.params.groupNo)
      .then(Response => {
        this.item = Response.data;
      })
      .catch(error => {});
  }
};
</script>

<style></style>
