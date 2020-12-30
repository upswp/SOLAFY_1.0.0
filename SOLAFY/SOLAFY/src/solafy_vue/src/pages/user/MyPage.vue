<template>
  <div>
    <div class="q-pa-md">
      <div class="q-gutter-y-md">
        <q-tabs v-model="tab" dense class="bg-grey-2 text-teal">
          <q-tab name="mypage" icon="mail" label="MyPage" />
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
      email: firebaseAuth.currentUser.email
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
    }
  }
};
</script>
