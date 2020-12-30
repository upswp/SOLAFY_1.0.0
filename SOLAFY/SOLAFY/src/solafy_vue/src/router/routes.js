import { firebaseAuth, firebaseSt, firebase } from "boot/firebase";

// 로그인 완료 + 이메일 인증 완료
// ex) 문제, 게시판, 그룹 등등 모든 조건이 모두 필요한 경우
const requireAuth = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    if (user && user.emailVerified) {
      return next();
    } else if (user) { 
      return next('/VerifyEmailWarn');
    }else {
      return next('/');
    }
  });
};

// 이메일 인증만 필요한 경우 (로그인 여부는 상관 없는 경우) 
// ex) 메인 페이지는 로그인 여부에 관계 없이 누구나 볼 수 있음 
//     하지만 이메일 인증이 안 되어 있는 겅우엔 아무데도 못감!@
const requireEmailVerified = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    if (!user && !user.emailVerified) {
      return next('/VerifyEmailWarn');
    }else {
      return next();
    }
  });
};

// 로그인 안한 상태가 필요한 경우 (로그인 한 상태에서는 갈 수 없음)
// ex) 회원 가입, 로그인 페이지 
const requireNullAuth = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function (user) {
    if (user) {
      return next('/main');
    } else {
      return next();
    }
  });
};

// 로그인 되어 있지만 이메일 인증이 완료되지 않은 경우
// ex) email warning 페이지 
const requireEmailNotVerified = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function (user) {
    if (user.emailVerified) {
      return next('/main');
    } else {
      return next();
    }
  });
};

const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/Index.vue"),
        beforeEnter: requireNullAuth()
      },
      {
        path: "/main",
        component: () => import("pages/Main.vue"),
        beforeEnter: requireEmailVerified()
      },
      {
        path: "/about",
        component: () => import("pages/About.vue"),
        beforeEnter: requireEmailVerified()
      },

      // problem
      {
        path: "/problem",
        name: "Problem",
        component: () => import("pages/problem/ProblemList.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemdetail/:problemNo",
        name: "ProblemDetail",
        component: () => import("pages/problem/ProblemDetail.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemcreate",
        name: "ProblemCreate",
        component: () => import("pages/problem/ProblemCreate.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemresult/:problemNo/:result",
        name: "ProblemResult",
        component: () => import("pages/problem/ProblemResult.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemset",
        name: "ProblemSet",
        component: () => import("pages/problemSet/ProblemSetList.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemsetdetail/problemSetListByProblem/:problemSetNo",
        name: "ProblemDetailByProblem",
        component: () =>
          import("pages/problemSet/problemSetDetail/ProblemDetailByProblem.vue"),
          beforeEnter: requireAuth()
      },
      {
        path: "/problemsetdetail/problemSetInfo/:problemSetNo",
        name: "ProblemDetailByProblemSetInfo",
        component: () =>
          import(
            "pages/problemSet/problemSetDetail/ProblemDetailByProblemSetInfo.vue"
          ),
          beforeEnter: requireAuth()
      },
      {
        path: "/problemsetcreate",
        name: "ProblemSetCreate",
        component: () => import("pages/problemSet/ProblemSetCreate.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemsetupdate/:problemSetNo",
        name: "ProblemSetUpdate",
        component: () => import("pages/problemSet/ProblemSetUpdate.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemsetresult/:problemSetNo/:result",
        name: "ProblemSetResult",
        component: () => import("pages/problemSet/ProblemSetResult.vue"),
        beforeEnter: requireAuth()
      },

      // board
      {
        path: "/freeboard",
        component: () => import("pages/board/FreeBoard.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/qnaboard",
        component: () => import("pages/board/FreeBoard.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/answermodifyboard",
        component: () => import("pages/board/AnswerModifyBoard.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/practiceboard",
        component: () => import("pages/board/PracticeBoard.vue"),
        beforeEnter: requireAuth()
      },

      // 그룹 Part
      {
        path: "/group/:groupNo",
        name: "groupHome",
        component: () => import("pages/group/Group.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/creategroup",
        component: () => import("pages/group/CreateGroup.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/groupmain",
        component: () => import("pages/group/GroupMain.vue"),
        beforeEnter: requireAuth()
      },
      ,
      {
        path: "/groupdetail/:groupNo/:grade",
        name: "GroupDetail",
        component: () => import("pages/group/GroupDetail.vue"),
        beforeEnter: requireAuth()
      },

      // user
      {
        path: "/userRegi",
        name: "UserRegi",
        component: () => import("pages/user/CreateUser.vue"),
        beforeEnter: requireNullAuth()
      },
      {
        path: "/verifyEmailWarn",
        component: () => import("pages/user/VerifyEmailWarn.vue"),
        beforeEnter: requireEmailNotVerified()
      },
      {
        path: "/mypage",
        name: "MyPage",
        component: () => import("pages/user/MyPage.vue"),
        beforeEnter: requireAuth()
      },
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "*",
    component: () => import("pages/Error404.vue")
  }
];

export default routes;
