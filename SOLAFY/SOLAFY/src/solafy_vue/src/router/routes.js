import { firebaseAuth, firebaseSt, firebase } from "boot/firebase";

const requireAuth = () => (to, from, next) => {
  if (firebaseAuth.currentUser == null)
    return next('/');
  else if (!firebaseAuth.currentUser.emailVerified)
    return next('/VerifyEmailWarn');
  next();
};

const requireEmailVerified = () => (to, from, next) => {
 if (firebaseAuth.currentUser != null &&!firebaseAuth.currentUser.emailVerified)
    return next('/VerifyEmailWarn');
  next();
};

const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/Index.vue"),
        beforeEnter: (to, from, next) => {
          if (firebaseAuth.currentUser == null)
            return next();
          next('/main');
        }
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
        beforeEnter: (to, from, next) => {
          if (firebaseAuth.currentUser == null)
            return next();
          next('/main');
        }
      },
      {
        path: "/verifyEmailWarn",
        component: () => import("pages/user/VerifyEmailWarn.vue"),
        beforeEnter: (to, from, next) => {
          if (firebaseAuth.currentUser.emailVerified)
          return next('/main');
        next();
        }
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
