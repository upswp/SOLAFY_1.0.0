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
      {
        path: "/problem",
        component: () => import("pages/problem/Problem.vue")
      },
      {
        path: "/problemset",
        component: () => import("pages/problem/ProblemSet.vue")
      },
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
      {
        path: "/group",
        component: () => import("pages/group/group.vue")
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
