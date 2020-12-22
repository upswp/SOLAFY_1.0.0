<template>
  <div>
    <!-- tab bar 시작 -->
    <q-tabs v-model="tab" class="text-teal">
      <q-tab name="mails" icon="mail" label="Mails" />
      <q-tab name="alarms" icon="alarm" label="Alarms" />
      <q-tab name="movies" icon="movie" label="Movies" />
    </q-tabs>
    <!-- tab bar 끝 -->

    <!-- itemlist - 자유게시판 시작-->
    <item-list></item-list>
    <!-- itemlist - 자유게시판 끝-->
    <h1 :v-text="path"></h1>
  </div>
</template>

<script>
import ItemList from "components/ItemList.vue";
export default {
  components: { ItemList },
  data() {
    return {
      tab: "mails",
      path: ""
    };
  },
  created() {
    this.path = process.env.API_URL;
    console.log(this.path);
  }
};
</script>

<style></style>
