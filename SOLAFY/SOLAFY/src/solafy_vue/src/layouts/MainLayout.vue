<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title>
          <span @click="goMain">SOLAFY</span>
        </q-toolbar-title>

        <div class="cursor-pointer non-selectable">
          <span>{{ nickname }}</span>
          <q-btn flat dense round icon="account_circle" aria-label="Menu">
            <q-menu>
              <q-list dense style="min-width: 100px">
                <q-item
                  :style="{ display: admin }"
                  @click="goUserManage"
                  clickable
                  v-close-popup
                >
                  <q-item-section>회원관리</q-item-section>
                </q-item>
                <q-item
                  :style="{ display: mem }"
                  @click="goMypage"
                  clickable
                  v-close-popup
                >
                  <q-item-section>마이페이지</q-item-section>
                </q-item>
                <q-item
                  :style="{ display: non }"
                  @click="goRegi"
                  clickable
                  v-close-popup
                >
                  <q-item-section>회원가입</q-item-section>
                </q-item>
                <q-item
                  :style="{ display: non }"
                  @click="goLogin"
                  clickable
                  v-close-popup
                >
                  <q-item-section>로그인</q-item-section>
                </q-item>
                <q-item
                  :style="{ display: mem }"
                  @click="logout"
                  clickable
                  v-close-popup
                >
                  <q-item-section>로그아웃</q-item-section>
                </q-item>
                <q-separator />
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-grey-1"
    >
      <q-list>
        <q-item-label header class="text-grey-8">
          SOLAFY Menu
        </q-item-label>
        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view @updateLoginUser="checkUser" />
    </q-page-container>
  </q-layout>
</template>

<script>
import axios from "axios";
import EssentialLink from "components/EssentialLink.vue";
import { firebaseAuth } from "boot/firebase";
import { SessionStorage } from "quasar";

const linksData = [
  {
    title: "About",
    caption: "모두함께",
    icon: "groups",
    link: "/about"
  },
  {
    title: "Problem",
    caption: "아영&상우",
    icon: "pages",
    link: "/problem"
  },
  {
    title: "Problem Set",
    caption: "아영&상우",
    icon: "history_edu",
    link: "/problemset"
  },
  {
    title: "Practice",
    caption:
      "주희가 하는 중... 해도 되는 거 맞겠지... 범석 오빠 자나..? 오빠는 하는 중 아니죠....?",
    icon: "work",
    link: "/practicelist"
  },
  {
    title: "Free Board",
    caption: "범석&기현",
    icon: "dashboard",
    link: "/free"
  },
  {
    title: "Answer Modify Board",
    caption: "범석&기현",
    icon: "announcement",
    link: "/answermodify"
  },
  {
    title: "Question Board",
    caption: "범석&기현",
    icon: "api",
    link: "/question"
  },
  {
    title: "Group",
    caption: "기현",
    icon: "group",
    link: "/groupmain"
  },
  {
    title: "create Group",
    caption: "기현",
    icon: "group",
    link: "/creategroup"
  }
];

export default {
  name: "MainLayout",
  components: { EssentialLink },
  data() {
    return {
      leftDrawerOpen: false,
      essentialLinks: linksData,
      admin: "",
      mem: "",
      non: "",
      nickname: ""
    };
  },
  mounted: function() {
    // console.log("mounted");
    this.checkUser();
  },
  methods: {
    goMain() {
      this.$router.push("/main");
    },
    goMypage() {
      this.$router.push("MyPage");
    },
    goRegi() {
      this.$router.push("UserCreate");
    },
    goLogin() {
      this.$router.push("/");
    },
    logout() {
      firebaseAuth.signOut();
      SessionStorage.clear();
      this.nickname = "";
      if (this.$router.currentRoute.path == "/main") this.$router.push("/");
      else this.$router.push("/main");
    },
    goUserManage() {
      this.$router.push("UserManage");
    },
    checkUser() {
      firebaseAuth.onAuthStateChanged(user => {
        if (user) {
          this.mem = "";
          this.non = "none";
          if (!SessionStorage.has("loginUser")) {
            // console.log("FB ok, SS x");
            this.getLoginUserInfo(user.uid);
          } else {
            // console.log("FB ok, SS ok");
            this.nickname = SessionStorage.getItem("loginUser").nickname;
            if (SessionStorage.getItem("loginUser").admin == 0) this.admin = "";
            else this.admin = "none";
          }
        } else {
          // console.log("FB x");
          this.mem = "none";
          this.non = "";
          this.admin = "none";
        }
      });
    },
    getLoginUserInfo(uid) {
      // console.log("axios");
      axios
        .get("/user/selectbyuid/" + uid)
        .then(response => {
          SessionStorage.set("loginUser", response.data);
          this.nickname = response.data.nickname;
          // console.log(response.data);
          if (response.data.admin == 0) this.admin = "";
          else this.admin = "none";
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
