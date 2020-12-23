<template
  ><div>
    <table>
      <tr>
        <td>번호</td>
        <td v-text="item.groupNo"></td>
      </tr>
      <tr>
        <td>타이틀</td>
        <td v-text="item.title"></td>
      </tr>
      <tr>
        <td>타입</td>
        <td v-text="item.type"></td>
      </tr>
      <tr>
        <td>설명</td>
        <td v-text="item.description"></td>
      </tr>
    </table>

    <q-btn @click="groupRegister">가입신청</q-btn>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      item: null
    };
  },
  methods: {
    groupRegister() {
      axios.get("");
    }
  },
  mounted() {
    console.log(this.$route.params.groupNo);

    axios
      .get("group/selectGroupByNo/" + this.$route.params.groupNo)
      .then(Response => {
        this.item = Response.data;
      })
      .catch(error => {});
  }
};
</script>

<style></style>
