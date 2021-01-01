import { firebaseAuth, firebaseSt, firebase } from "boot/firebase";

// 로그인 완료 + 이메일 인증 완료
// ex) 문제, 게시판, 그룹 등등 모든 조건이 모두 필요한 경우
const requireAuth = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    // 로그인 (O) 이메일 인증(O) : 라우팅 된 페이지로 이동
    if (user && user.emailVerified) {
      return next();
      // 로그인 (O) 이메일 인증(X) : 이메일 인증 경고 페이지로 이동
    } else if (user) {
      return next("/verifyemailwarn");
      // 로그인 (X) 이메일 인증(X) : 로그인 페이지로 이동
    } else {
      return next("/");
    }
  });
};

// 로그인 한 상태에서는 갈 수 없는 페이지
// ex) 회원 가입, 로그인 페이지
const requireNullAuth = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      return next("/main");
    } else {
      return next();
    }
  });
};

// 로그인 (O) 이메일 인증(X) 상태에서 갈 수 없는 페이지
// 로그인 (X) 상태에서는 갈 수 있음
// ex) main, about 페이지
const requireEmailVerified = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    if (user && !user.emailVerified) {
      return next("/verifyemailwarn");
    } else {
      return next();
    }
  });
};

// 로그인 (O) 이메일 인증(O) 상태에서 갈 수 없는 페이지
// 로그인 (X) 에서도 갈 수 없음
// ex) email warning 페이지
const requireEmailNotVerified = () => (to, from, next) => {
  firebase.auth().onAuthStateChanged(function(user) {
    if (!user || user.emailVerified) {
      return next("/main");
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
          import(
            "pages/problemSet/problemSetDetail/ProblemDetailByProblem.vue"
          ),
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
        path: "/problemsetcreate/problemSetListByProblem",
        name: "ProblemSetCreateByProblem",
        component: () =>
          import(
            "pages/problemSet/problemSetCreate/ProblemSetCreateByProblem.vue"
          ),
        beforeEnter: requireAuth()
      },
      {
        path: "/problemsetcreate/problemSetInfo",
        name: "ProblemSetCreateByProblemSetInfo",
        component: () =>
          import(
            "pages/problemSet/problemSetCreate/ProblemSetCreateByProblemSetInfo.vue"
          ),
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
        path: "/free",
        name: "free",
        component: () => import("pages/board/FreeBoard.vue"),
        children: [
          {
            path: "",
            name: "free-board-list",
            component: () => import("components/board/BoardList.vue"),
            beforeEnter: requireAuth()
          },
          {
            path: "write",
            name: "free-board-write",
            component: () => import("components/board/BoardWrite.vue"),
            beforeEnter: requireAuth()
          },
          {
            path: "detail/:articleNo",
            name: "free-board-detail",
            component: () => import("components/board/BoardDetail.vue"),
            beforeEnter: requireAuth()
          },
          {
            path: "update/:articleNo",
            name: "free-board-update",
            component: () => import("components/board/BoardUpdate.vue"),
            beforeEnter: requireAuth()
          }
        ]
      },
      {
        path: "/qna",
        component: () => import("pages/board/FreeBoard.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/answermodify",
        component: () => import("pages/board/AnswerModifyBoard.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/practice",
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
        path: "/userregi",
        name: "UserRegi",
        component: () => import("pages/user/CreateUser.vue"),
        beforeEnter: requireNullAuth()
      },
      {
        path: "/updateuser",
        name: "UpdateUser",
        component: () => import("pages/user/UpdateUser.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/manageUser",
        name: "ManageUser",
        component: () => import("pages/user/ManageUser.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/verifyemailwarn",
        component: () => import("pages/user/VerifyEmailWarn.vue"),
        beforeEnter: requireEmailNotVerified()
      },
      {
        path: "/mypage",
        name: "MyPage",
        component: () => import("pages/user/MyPage.vue"),
        beforeEnter: requireAuth()
      }
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
