<template>
  <div>
    <div class="q-pa-md">
      <div class="q-gutter-y-md">
        <q-tabs v-model="tab" dense class="bg-grey-2 text-teal">
          <q-tab name="mypage" icon="mail" label="MyPage" />
          <q-tab name="withdrawal" icon="mail" label="Withdrawal" />
          <q-tab name="problem" icon="alarm" label="Problems" />
          <q-tab name="problemset" icon="movie" label="ProblemSets" />
        </q-tabs>

        <q-tab-panels v-model="tab" animated>
          <q-tab-panel name="mypage">
            <div class="text-h6">{{ userinfo.nickname }} 님 ({{ email }})</div>
            상태메시지: {{ userinfo.statusMessage }}
            <q-separator />
            <div>something ...</div>
            <div><q-btn label="수정" @click="goUpdateUserInfo"></q-btn></div>
          </q-tab-panel>

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

          <q-tab-panel name="problem">
            <div class="text-h6">Alarms</div>
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
          </q-tab-panel>

          <q-tab-panel name="problemset">
            <div class="text-h6">Movies</div>
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
          </q-tab-panel>
        </q-tab-panels>
      </div>
    </div>
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
import { firebaseAuth } from "boot/firebase";
export default {
  data() {
    return {
      userinfo: {},
      tab: "mypage",
      email: firebaseAuth.currentUser.email,
      password: "",
      dialog: false
    };
  },
  mounted: function() {
    axios
      .get("/user/selectbyuid/" + firebaseAuth.currentUser.uid)
      .then(response => {
        this.userinfo = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    goUpdateUserInfo() {
      this.$router.push("/updateuser");
    },
    clickwithdrawalbtn() {
      if (this.password == "") return;
      this.dialog = true;
    },
    clickRealWithdrawalbtn() {
      this.dialog = false;
      axios.delete("/user/delete/" + firebaseAuth.currentUser.uid);
      firebaseAuth
        .signInWithEmailAndPassword(
          firebaseAuth.currentUser.email,
          this.password
        )
        .then(() => {
          firebaseAuth.currentUser
            .delete()
            .then(function() {})
            .catch(function(error) {
              console.log(error);
            });
          this.$q.notify({
            color: "green",
            textColor: "white",
            icon: "check",
            message: "계정이 삭제되었습니다."
          });
          this.$router.push("/main");
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
