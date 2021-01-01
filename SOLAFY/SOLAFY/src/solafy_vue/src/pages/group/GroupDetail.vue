<template>
<div style="margin-top:20%;">
    <div class="fit row justify-center content-center">
      <!-- todo : 디자인 변경 해야함 -->
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
</div>
    <div class="fit row justify-center content-center" v-if="status == 0">
      <q-btn color="primary" @click="groupRegister">가입신청</q-btn>
    </div>
    <div class="fit row justify-center content-center" v-else-if="status == 99">
      <q-btn color="orange" @click="groupRegiCancel">가입 취소</q-btn>
    </div>
    <div class="fit row justify-center content-center" v-else-if="status == 1 || 2 || 3">
      <q-btn color="green" @click="groupHome">그룹 홈</q-btn>
    </div>
  </div>
</template>

<script>
import { firebaseAuth } from "boot/firebase";
import {createApplyGroupSignUp, deleteGroupMemberself, selectGroupByNo} from "src/api/Group/group.js";
import {notify} from "src/api/common.js";
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
          createApplyGroupSignUp({
              uid: firebaseAuth.currentUser.uid,
              groupNo: this.item.groupNo,
              grade: 99,
              regiMessage: data
          },
          (Response) => {
            notify("green", "white", "cloud_done", "가입신청 완료");
            this.$router.go(-1);
          },
          (error) => {
            notify("red-6", "white", "warning", "가입신청 실패");
          });
        })
        .onCancel(() => {})
        .onDismiss(() => {});
    },
    // 가입 신청 취소
    groupRegiCancel() {
      deleteGroupMemberself(
        { 
        uid: firebaseAuth.currentUser.uid,
        groupNo: this.item.groupNo
        },
        (Response) => {
          notify("green", "white", "cloud_done", "신청취소");
           this.$router.go(-1);
        },
        (error) => {
          notify("red-6", "white", "warning", "신청취소 실패");
           this.$router.go(-1);
        }
      );
    },
    groupHome() {
      //선택한 그룹의 홈
      this.$router.push(
        {
          name: "groupHome",
          params: {
            groupNo: this.item.groupNo
          }
        }
      );
    }
  },
  //TODO : 사용자가 고의로 URL을 수정했을 때, 막아야 하는 이슈
  created() {
    this.status = this.$route.params.grade;
   
   //선택한 그룹 받아오기
    selectGroupByNo(
      this.$route.params.groupNo,
      (Response)=>{
        this.item = Response.data;
      },
      (error)=>{
        notify("red-6", "white", "warning", "그룹정보 받기 실패");
        this.$router.go(-1);
      }
    );
  }
};
</script>

<style></style>
