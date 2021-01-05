<template>
  <div class="q-pa-md">
    <div class="q-gutter-y-md">
      <!-- 상단 tab -->
      <q-tabs v-model="tab" class="bg-grey-2 text-teal shadow-2">
        <q-tab name="member" icon="people" label="Member" />
        <q-tab name="regi" icon="mail" label="Registration">
          <q-badge v-if="regis.length != 0" color="red-8" floating>{{
            regis.length
          }}</q-badge>
        </q-tab>
      </q-tabs>

      <q-tab-panels v-model="tab" animated>
        <!-- 회원 관리 tab -->
        <q-tab-panel name="member">
          <q-table
            :data="users"
            :columns="columns"
            :visible-columns="visibleColums"
            row-key="nickname"
            :selected-rows-label="getSelectedString"
            selection="multiple"
            :selected.sync="selected"
          >
            <template v-slot:top>
              <q-btn
                color="primary"
                label="삭제"
                @click="withdrawaldialog = true"
              />
            </template>
          </q-table>
        </q-tab-panel>

        <!-- 회원 가입 신청자 관리 tab -->
        <q-tab-panel name="regi">
          <q-table
            :data="regis"
            :columns="columns"
            :visible-columns="visibleColums"
            row-key="nickname"
          >
            <template v-slot:body-cell-action="props">
              <q-td :props="props">
                <q-btn
                  rounded
                  dense
                  color="teal"
                  icon="photo"
                  @click="clickPictureBtn(props.row)"
                />
                &nbsp;&nbsp;
                <q-btn
                  rounded
                  dense
                  color="teal"
                  icon="check"
                  @click="clickRegiConfirmBtn(props.row)"
                />
              </q-td>
            </template>
          </q-table>
        </q-tab-panel>
      </q-tab-panels>
    </div>

    <!-- 회원 탈퇴 다이얼로그 -->
    <q-dialog v-model="withdrawaldialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="people" color="primary" text-color="white" />
          <span class="q-ml-sm">정말로 탈퇴 시키겠습니까?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn flat label="확인" color="primary" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <!-- 가입 신청자 사진 조회 다이얼로그 -->
    <q-dialog v-model="imgdialog">
      <q-card>
        <q-card-section class="row items-center">
          <span class="q-ml-sm">
            <strong>{{ imgdialognickname }}</strong> 님의 프로필 사진
          </span>
          <img id="ntimg" src="" />
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="확인" color="primary" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <!-- 회원 가입 승인 컨펌 다이얼로그 -->
    <q-dialog v-model="confirmdialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="people" color="primary" text-color="white" />
          <span class="q-ml-sm">가입을 승인하시겠습니까?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn
            flat
            label="확인"
            color="primary"
            @click="clickRealRegiConfirm"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { notify } from "src/api/common.js";
import { firebaseAuth, firebaseSt } from "boot/firebase";

export default {
  data() {
    return {
      // 현재 보여지는 tab 저장
      tab: "member",

      //* 회원 조회 tab
      // DB에서 받아온 모든 회원
      users: [],
      // 셀렉트 박스로 선택된 회원 목록
      selected: [],
      // 회원 탐퇴 다이얼로그 오픈 여부
      withdrawaldialog: false,
      // 회원조회 table의 column 정보
      columns: [
        {
          required: true,
          name: "nickname",
          label: "별명",
          field: "nickname",
          align: "left"
        },
        { name: "email", label: "이메일", field: "email", align: "left" },
        {
          name: "statusMessage",
          label: "상태메시지",
          field: "statusMessage",
          align: "left"
        },
        {
          name: "action",
          label: "",
          field: "action",
          align: "center"
        },
        {
          name: "uid",
          field: "uid"
        }
      ],
      visibleColums: ["nickname", "email", "statusMessage", "action"],

      //* 가입 신청자 조회 tab
      // 가입 신청한 모든회원
      regis: [],
      // 명찰 사진 확인 다이얼로그 오픈 여부
      imgdialog: false,
      // 명찰 사진 다이얼로그에 출력될 해당 회원의 닉네임
      imgdialognickname: "",
      // 가입 승인 컨펌 다이얼로그 오픈 여부
      confirmdialog: false,
      // 승인 시킬 회원의 uid
      regiconfirmUid: ""
    };
  },
  methods: {
    /**
     * @Method설명 : DB에서 모든 회원 정보를 받아옴
     * @변경이력 :
     */
    getAllUsers() {
      axios
        .get("/user/selectall")
        .then(response => {
          this.users = response.data;
        })
        .catch(error => {
          console.log(error.message);
          notify("red", "white", "warning", "목록 조회 실패");
        });
    },
    /**
     * @Method설명 : DB에서 회원가입 신청자들의 정보를 받아옴
     * @변경이력 :
     */
    getRegiUsers() {
      axios
        .get("/user/selectregirequest")
        .then(response => {
          this.regis = response.data;
        })
        .catch(error => {
          console.log(error.message);
          notify("red", "white", "warning", "목록 조회 실패");
        });
    },
    /**
     * @Method설명 : 셀렉트박스로 체크된 회원을 관리
     * @변경이력 :
     */
    getSelectedString() {
      return this.selected.length === 0
        ? ""
        : `${this.selected.length} record${
            this.selected.length > 1 ? "s" : ""
          } selected of ${this.users.length}`;
    },
    /**
     * @Method설명 : 탈퇴 컨펌 다이얼로그에서 탈퇴 버튼 클릭 시 호출
     * @변경이력 :
     */
    clickRealWithdrawal() {
      for (let idx = 0; idx < this.selected.length; idx++) {
        const user = selected[idx];
        this.deleteImg_FB(user);
        this.deleteUser_DB(user);
        this.deleteUser_FB(user);
      }
      this.withdrawaldialog = false;
    },
    /**
     * @Method설명 : firebase에 저장되어 있는 이미지 삭제
     * @변경이력 :
     */
    deleteImg_FB(user) {
      var nametagRef = firebaseSt.ref().child("nametagimg/" + user.uid);
      var profileRef = firebaseSt.ref().child("profileimg/" + user.uid);
      nametagRef.delete();
      profileRef.delete();
    },
    /**
     * @Method설명 : DB에 저장되어 있는 회원 정보 삭제
     * @변경이력 :
     */
    // DB의 회원 정보 삭제
    deleteUser_DB(user) {
      axios.delete("/user/delete/" + user.uid);
    },
    /**
     * @Method설명 : firebase에 저장된 인증 정보 삭제
     * @변경이력 :
     */
    // FB의 인증 정보 삭제
    deleteUser_FB(user) {
      firebaseAuth.currentUser
        .delete()
        .then(function() {})
        .catch(function(error) {
          console.log(error);
        });
    },
    /**
     * @Method설명 : 회원 table에서 사진 버튼 클릭 시 호출
     * 해당 행 회원의 명찰 사진을 firebase에서 받아와 사진 확인 가능
     * @변경이력 :
     */
    clickPictureBtn(user) {
      var storageRef = firebaseSt.ref();
      storageRef
        .child("profileimg/" + user.uid)
        .getDownloadURL()
        .then(function(url) {
          console.log(url);
          var img = document.getElementById("ntimg");
          img.src = url;
        })
        .catch(function(error) {});
      this.imgdialognickname = user.nickname;
      this.imgdialog = true;
    },
    /**
     * @Method설명 : 승인 버튼 클릭 시 호출
     * @변경이력 :
     */
    clickRegiConfirmBtn(user) {
      this.confirmdialog = true;
      this.regiconfirmUid = user.uid;
    },
    /**
     * @Method설명 : 가입 승인 다이얼로그에서 승인 버튼 클릭 시 호출
     * DB에 저장되어 있는 해당 회원의 등급을 일반회원으로 조정
     * @변경이력 :
     */
    clickRealRegiConfirm() {
      axios.put("/user/updateadmin/" + this.regiconfirmUid).then(response => {
        notify("green", "white", "cloud_done", "회원 가입 승인 성공");
      });
      this.confirmdialog = false;
      window.location.reload();
    }
  },
  created: function() {
    this.getAllUsers();
    this.getRegiUsers();
  }
};
</script>
