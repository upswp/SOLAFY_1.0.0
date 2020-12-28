const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      {
        path: "",
        component: () => import("pages/Index.vue")
      },
      {
        path: "/main",
        component: () => import("pages/Main.vue")
      },
      {
        path: "/about",
        component: () => import("pages/About.vue")
      },

      // problem
      {
        path: "/problem",
        name: "Problem",
        component: () => import("pages/problem/ProblemList.vue")
      },
      {
        path: "/problemdetail/:problemNo",
        name: "ProblemDetail",
        component: () => import("pages/problem/ProblemDetail.vue")
      },
      {
        path: "/problemcreate",
        name: "ProblemCreate",
        component: () => import("pages/problem/ProblemCreate.vue")
      },
      {
        path: "/problemresult/:problemNo/:result",
        name: "ProblemResult",
        component: () => import("pages/problem/ProblemResult.vue")
      },
      {
        path: "/problemset",
        name: "ProblemSet",
        component: () => import("pages/problemSet/ProblemSetList.vue")
      },

      // board
      {
        path: "/freeboard",
        component: () => import("pages/board/FreeBoard.vue")
      },
      {
        path: "/qnaboard",
        component: () => import("pages/board/FreeBoard.vue")
      },
      {
        path: "/answermodifyboard",
        component: () => import("pages/board/AnswerModifyBoard.vue")
      },
      {
        path: "/practiceboard",
        component: () => import("pages/board/PracticeBoard.vue")
      },

      // 그룹 Part
      {
        path: "/group/:groupNo",
        name: "groupHome",
        component: () => import("pages/group/Group.vue")
      },
      {
        path: "/creategroup",
        component: () => import("pages/group/CreateGroup.vue")
      },
      {
        path: "/groupmain",
        component: () => import("pages/group/GroupMain.vue")
      },
      ,
      {
        path: "/groupdetail/:groupNo/:grade",
        name: "GroupDetail",
        component: () => import("pages/group/GroupDetail.vue")
      },

      // member
      {
        path: "/memberRegi",
        name: "memberRegi",
        component: () => import("pages/member/CreateMember.vue")
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
