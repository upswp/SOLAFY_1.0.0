<template>
  <div>
    <div class="q-pa-md">
      <div class="q-gutter-y-md">
        <!-- 상단의 메뉴탭 -->
        <q-tabs v-model="tab" dense class="bg-grey-2 text-teal">
          <q-tab name="mypage" icon="mail" label="MyPage" />
          <q-tab name="withdrawal" icon="mail" label="Withdrawal" />
          <q-tab name="problem" icon="alarm" label="Problems" />
          <q-tab name="problemset" icon="movie" label="ProblemSets" />
        </q-tabs>

        <q-tab-panels v-model="tab" animated>
          <!-- 회원 정보 탭 -->
          <q-tab-panel name="mypage">
            <div class="text-h6">{{ userinfo.nickname }} 님 ({{ email }})</div>
            상태메시지: {{ userinfo.statusMessage }}
            <q-separator />
            <div>something ...</div>
            <div>
              <q-btn label="수정" @click="goUpdateUserInfo"></q-btn>
              <q-btn label="test" @click="deleteImg_FB"></q-btn>
            </div>
          </q-tab-panel>

          <!-- 탈퇴 탭 -->
          <q-tab-panel name="withdrawal">
            <div class="text-h6">비밀번호를 입력해주세요</div>
            <div>
              <q-input
                filled
                type="password"
                v-model="password"
                label="password *"
                lazy-rules
                :rules="[
                  val =>
                    (val !== null && val !== '') || '비밀번호를 입력해주세요'
                ]"
              />
              <q-btn label="탈퇴하기" @click="clickwithdrawalbtn"></q-btn>
            </div>
          </q-tab-panel>

          <!-- 문제 탭 -->
          <q-tab-panel name="problem">
            <q-table
              title="내가 출제한 문제"
              :data="problems"
              :columns="problemcols"
              row-key="name"
              :loading="loading"
              @row-click="problemDetail"
              no-data-label="검색결과가 없습니다"
            ></q-table>
          </q-tab-panel>

          <!-- 문제집 탭 -->
          <q-tab-panel name="problemset">
            <q-table
              title="내가 만든 문제집"
              :data="problemSets"
              :columns="problemsetcols"
              row-key="name"
              :loading="loading"
              @row-click="problemSetDetail"
              no-data-label="검색결과가 없습니다"
            ></q-table>
          </q-tab-panel>
        </q-tab-panels>
      </div>
    </div>

    <!-- 탈퇴 컨펌 다이얼로그 -->
    <q-dialog v-model="dialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="signal_wifi_off" color="primary" text-color="white" />
          <span class="q-ml-sm">정말로 탈퇴하시겠습니까?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn
            flat
            label="탈퇴하기"
            color="primary"
            @click="clickRealWithdrawalbtn"
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
import { SessionStorage } from "quasar";

export default {
  data() {
    return {
      loading: false,
      userinfo: {},
      tab: "mypage",
      email: firebaseAuth.currentUser.email,
      password: "",
      dialog: false,
      problems: [],
      problemSets: [],
      problemcols: [
        {
          name: "problemNo",
          required: true,
          label: "문제번호",
          align: "left",
          field: row => row.problemNo,
          format: val => `${val}`,
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: row => row.title,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: row => row.nickname,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: "regiTime",
          label: "작성일자",
          required: true,
          align: "left",
          field: row => row.regiTime,
          format: val => `${val}`,
          sortable: true
        }
      ],
      problemsetcols: [
        {
          name: "problemSetNo",
          required: true,
          label: "문제집 번호",
          align: "left",
          field: "problemSetNo",
          sortable: true,
          style: "width = 10px"
        },
        {
          name: "title",
          label: "제목",
          required: true,
          align: "left",
          field: "title",
          sortable: true
        },
        {
          name: "nickname",
          label: "작성자",
          required: true,
          align: "left",
          field: "nickname",
          sortable: true
        },
        {
          name: "regiTime",
          label: "작성일자",
          required: true,
          align: "left",
          field: "regiTime",
          sortable: true
        }
      ]
    };
  },
  // uid로 회원 정보 가져옴
  mounted: function() {
    axios
      .get("/user/selectbyuid/" + firebaseAuth.currentUser.uid)
      .then(response => {
        this.userinfo = response.data;
      })
      .catch(error => {
        console.log(error);
      });
    axios
      .get(
        "/problem/search/작성자/" + SessionStorage.getItem("loginUser").nickname
      )
      .then(response => {
        this.problems = response.data;
      })
      .catch(error => {
        this.$q.notify({
          color: "red-6",
          textColor: "white",
          icon: "warning",
          message: "조회 실패"
        });
      });
    axios
      .get(
        "problem/problemset/problemSetSelectByWriter/" +
          SessionStorage.getItem("loginUser").nickname
      )
      .then(response => {
        this.problemSets = response.data;
      })
      .catch(error => {
        this.$q.notify({
          color: "red-6",
          textColor: "white",
          icon: "warning",
          message: "조회 실패"
        });
      });
  },
  methods: {
    // 회원 정보 수정 버튼을 누르면 페이지 이동
    goUpdateUserInfo() {
      this.$router.push("/updateuser");
    },
    // 탈퇴 버튼 클릭 시 호출
    clickwithdrawalbtn() {
      // 비밀번호 입력하지 않으면
      if (this.password == "") return;
      // 입력된 비밀번호로 회원 인증
      firebaseAuth
        .signInWithEmailAndPassword(
          firebaseAuth.currentUser.email,
          this.password
        )
        // 입력된 비밀번호 인증이 되면 컨펌 다이얼로그 띄움
        .then(() => {
          this.dialog = true;
        })
        .catch(error => {
          // 입력된 비밀번호가 다르면 notify
          if (error.code == "auth/wrong-password") {
            this.password = "";
            notify("red-6", "white", "warning", "비밀번호를 확인해주세요");
          } else {
            console.log(error);
          }
        });
    },
    // 컨펌 다이얼로그에서 탈퇴하기 버튼 클릭 시 호출
    clickRealWithdrawalbtn() {
      //컨펌 다이얼로그를 끄고
      this.dialog = false;
      //관련 데이터 전부 삭제
      this.deleteImg_FB();
      this.deleteUser_DB();
      this.deleteUser_FB();

      notify("green", "white", "cloud_done", "계정이 삭제되었습니다");

      this.$router.push("/main");
    },
    // FB의 명찰 사진, 프로필 사진 삭제
    deleteImg_FB() {
      var nametagRef = firebaseSt
        .ref()
        .child("nametagimg/" + firebaseAuth.currentUser.uid);

      var profileRef = firebaseSt
        .ref()
        .child("profileimg/" + firebaseAuth.currentUser.uid);
      nametagRef.delete();
      profileRef.delete();
    },
    // DB의 회원 정보 삭제
    deleteUser_DB() {
      axios.delete("/user/delete/" + firebaseAuth.currentUser.uid);
    },
    // FB의 인증 정보 삭제
    deleteUser_FB() {
      firebaseAuth.currentUser
        .delete()
        .then(function() {})
        .catch(function(error) {
          console.log(error);
        });
    },
    problemDetail(evt, row) {
      this.loading = true;
      this.$router.push({
        name: "ProblemDetail",
        params: {
          problemNo: row.problemNo
        }
      });
    },
    problemSetDetail(evt, row) {
      this.loading = true;
      this.$router.push({
        name: "ProblemDetailByProblemSetInfo",
        params: {
          problemSetNo: row.problemSetNo
        }
      });
    }
  }
};
</script>
