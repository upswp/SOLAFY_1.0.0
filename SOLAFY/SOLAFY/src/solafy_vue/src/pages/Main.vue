<template>
  <div class="fit row justify-center content-center">
    <div v-if="!showImg">
      <div class="fit row justify-center content-center">
        <img src="~src/assets/ggg2.png"/>
    </div>
        <form class="row justify-center content-center">
            <q-input label="print 결과는?" :v-model="answerdata"></q-input>
            <q-btn flat text-color="primary" label="제출" @click="isAnswer"></q-btn>
        </form>
        </div>

    <div v-else>
 <div class="description text-center rounded-borders">
      <q-flashcard :no-hover="hover" :style="style">
        <q-flashcard-section transition="spin-out" :active="active">
          <img src="~src/assets/ggg.png" width=600 height=400>
        </q-flashcard-section>
      </q-flashcard>
    </div>

    <div class="description text-center rounded-borders">
      <q-flashcard :no-hover="hover" :style="style">
        <q-flashcard-section transition="spin-in" :active="active">
          <img src="~src/assets/ggg3.jpg" width=600 height=400>
        </q-flashcard-section>
      </q-flashcard>
    </div>

    <div class="description text-center rounded-borders">
      <q-flashcard :no-hover="hover" :style="style">
        <q-flashcard-section transition="spin-in" :active="active">
          <img src="~src/assets/ggg4.jpg" width=600 height=400>
        </q-flashcard-section>
      </q-flashcard>
    </div>

    </div>

    </div>
</template>

<script>
export default {
    data() {
        return {
            answerdata : "",
            showImg : false,
            hover: false,
            active: false
        }
    },
    computed: {
    style () {
      return {
        width: '620px',
        height: '420px',
        padding: '10px',
        border: '10px solid #fff',
        textAlign: 'center',
        boxShadow: '1px 1px 2px #e6e6e6'
      }
    }
  },
    methods: {
        isAnswer(){
            if(this.answerdata.trim() =="Happy New 2021" || "Happy New2021"){
                this.showImg = true;
            }
        },
    },
};
</script>
<style lang="sass" scoped>
.description
  padding: 10px
  background-color: black
  color: white
  box-shadow: 1px 1px 2px #e6e6e6

.my-header
  width: 100%
  top: 0
  height: 45px
  color: white
  background-color: rgba(0,0,0,0.8)
  text-transform: uppercase
  text-align: center
  font-size: 17px
  margin: 20px 0 0 0
  padding: 10px

.my-text
  width: 100%
  top: 0
  height: 80px
  color: white
  text-align: center
  font-size: 12px
  margin: 60px 0 0 0
  padding: 20px
  line-height: normal
  font-family: Georgia, serif
  font-style: italic

.my-button-container
  width: 100%

.my-button
  text-decoration: none
  text-transform: uppercase
  margin: 0 0 20px 0
  text-align: center
  padding: 7px 14px
  background-color: #000
  color: #fff
  text-transform: uppercase
  box-shadow: 0 0 1px #000
  transition-delay: 0.2s

.my-button:hover
  box-shadow: 0 0 5px #000
</style>