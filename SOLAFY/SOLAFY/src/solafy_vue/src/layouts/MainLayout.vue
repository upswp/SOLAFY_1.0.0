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
          SOLAFY
        </q-toolbar-title>

        <div class="cursor-pointer non-selectable">
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
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from "components/EssentialLink.vue";
import { firebaseAuth } from "boot/firebase";

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
    title: "Free Board",
    caption: "범석&기현",
    icon: "dashboard",
    link: "/freeboard"
  },
  {
    title: "Practice Board",
    caption: "범석&기현",
    icon: "api",
    link: "/practiceboard"
  },
  {
    title: "Answer Modify Board",
    caption: "범석&기현",
    icon: "announcement",
    link: "/answermodifyboard"
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
      admin: "none",
      mem: "",
      non: ""
    };
  },
  mounted: function() {
    if (firebaseAuth.currentUser != null) {
      this.non = "none";
      this.mem = "";
    } else {
      this.mem = "none";
      this.non = "";
    }
  },
  updated: function() {
    if (firebaseAuth.currentUser != null) {
      this.non = "none";
      this.mem = "";
    } else {
      this.mem = "none";
      this.non = "";
    }
  },
  methods: {
    goMypage() {},
    goRegi() {
      this.$router.push("UserRegi");
    },
    goLogin() {
      this.$router.push("/");
    },
    logout() {
      firebaseAuth.signOut();
      this.$router.go(this.$router.currentRoute);
    },
    goUserManage() {}
  }
};
</script>
