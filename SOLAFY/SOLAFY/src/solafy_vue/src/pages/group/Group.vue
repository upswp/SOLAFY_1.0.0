<template
  ><div>
    <!-- tab bar 시작 -->
    <q-tabs v-model="tab" class="text-teal">
      <q-tab name="mails" icon="mail" label="Mails" />
      <q-tab name="alarms" icon="alarm" label="Alarms" />
      <q-tab name="movies" icon="movie" label="Movies" />
    </q-tabs>
    <!-- tab bar 끝 -->
  </div>
</template>

<script>
export default {};
</script>

<style></style>
