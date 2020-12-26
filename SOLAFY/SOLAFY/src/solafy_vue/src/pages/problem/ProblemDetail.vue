<template>
  <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
      <div class="row">
        <q-table
          :data="data"
          :columns="columns"
          hide-header
          hide-bottom
          separator="cell"
        />
      </div>
      <div class="content">
        <div class="row">
          <q-markdown style="width:100%">{{ contents }}</q-markdown>
        </div>
        <div class="multipleChoice">이곳은 객관식
          <div
            class="row"
            v-for="(choice, index) in multipleChoice"
            :key="index"
          >
            <q-checkbox :val="index + 1" v-model="checklist">
              <q-markdown>{{ index + 1 }}. {{ choice }}</q-markdown>
            </q-checkbox>
          </div>
          <div>
            <strong>{{ checklist }}</strong>
          </div>
        </div>
        <div class="shortAnswer">이곳은 주관식</div>
        <div class="essay">이곳은 서술형</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProblemDetail",
  data() {
    return {
      columns: [
        { name: "title", align: "left", label: "title", field: "title" },
        {
          name: "content",
          align: "left",
          label: "content",
          field: "content",
          style: "width:100vw"
        }
      ],
      data: [
        {
          title: "문제 제목",
          content: "input 태그"
        },
        {
          title: "문제 카테고리",
          content: "Web>HTML>tag"
        },
        {
          title: "문제 작성자",
          content: "호랑돌이"
        }
      ],
      contents:
        "#### 다음 중 숫자만 입력 가능하게 하려면 빈칸에 무엇이 들어가야하는가? \n ![아이유](https://i.pinimg.com/736x/0b/2f/8a/0b2f8a51314ab1ebe0505aee843a33b1.jpg =300x300)",
      multipleChoice: ["button", "check", "text", "number"],
      checklist: []
    };
  }
};
</script>

<style scoped>
.content {
  margin-left: 5%;
}
</style>
