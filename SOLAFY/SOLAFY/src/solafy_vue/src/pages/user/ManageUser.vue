<template>
  <div class="q-pa-md">
    <div class="q-gutter-y-md">
      <q-tabs v-model="tab" class="bg-grey-2 text-teal shadow-2">
        <q-tab name="member" icon="people" label="Member" />
        <q-tab name="regi" icon="mail" label="Registration">
          <q-badge v-if="regis.length != 0" color="red-8" floating>{{
            regis.length
          }}</q-badge>
        </q-tab>
      </q-tabs>

      <q-tab-panels v-model="tab" animated>
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
                  color="teal"
                  icon="check"
                  @click="clickRegiConfirm(props.row)"
                />
              </q-td>
            </template>
          </q-table>
        </q-tab-panel>
      </q-tab-panels>
    </div>

    <q-dialog v-model="withdrawaldialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="people" color="primary" text-color="white" />
          <span class="q-ml-sm">정말로 탈퇴 시키겠습니까?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn
            flat
            label="확인"
            color="primary"
            @click="clickRealWithdrawal"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import { firebaseAuth, firebaseSt } from "boot/firebase";
import { notify } from "src/api/common.js";

export default {
  data() {
    return {
      tab: "regi",
      users: [],
      selected: [],
      regis: [],
      withdrawaldialog: false,
      picturedialog: false,
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
      visibleColums: ["nickname", "email", "statusMessage", "action"]
    };
  },
  mounted: function() {
    axios
      .get("/user/selectall")
      .then(response => {
        this.users = response.data;
      })
      .catch(error => {
        console.log(error.message);
        notify("red", "white", "warning", "목록 조회 실패");
      });
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
  methods: {
    getSelectedString() {
      return this.selected.length === 0
        ? ""
        : `${this.selected.length} record${
            this.selected.length > 1 ? "s" : ""
          } selected of ${this.users.length}`;
    },
    clickRealWithdrawal() {
      for (let idx = 0; idx < this.selected.length; idx++) {
        const user = selected[idx];
        this.deleteImg_FB(user);
        this.deleteUser_DB(user);
        this.deleteUser_FB(user);
      }
      this.withdrawaldialog = false;
    }
  },
  deleteImg_FB(user) {
    var nametagRef = firebaseSt.ref().child("nametagimg/" + user.uid);
    var profileRef = firebaseSt.ref().child("profileimg/" + user.uid);
    nametagRef.delete();
    profileRef.delete();
  },
  // DB의 회원 정보 삭제
  deleteUser_DB(user) {
    axios.delete("/user/delete/" + user.uid);
  },
  // FB의 인증 정보 삭제
  deleteUser_FB(user) {
    firebaseAuth.currentUser
      .delete()
      .then(function() {})
      .catch(function(error) {
        console.log(error);
      });
  },
  clickPicture() {
    console.log("hi");
    this.picturedialog = true;
  },
  clickRegiConfirm(item) {
    console.log("dd");
  }
};
</script>
