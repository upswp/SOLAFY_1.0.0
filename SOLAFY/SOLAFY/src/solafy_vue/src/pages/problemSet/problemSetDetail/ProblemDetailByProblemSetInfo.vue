<template>
  <div class="q-pa-md">
    <div class="column">
      <!-- 상단 제목 및 돌아가기 버튼 -->
      <div class="col" id="header-title">
        <h3>문제집 제목</h3>
      </div>
      <div class="col" id="header-btn">
        <div class="row">
          <div class="col-10"></div>
          <div class="col-2">
            <q-btn
              color="primary"
              label="문제 List"
              onclick="location.reload()"
            />
          </div>
        </div>
      </div>
      <!-- contents -->
      <div class="col">
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            문제 테이블 위치
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            <div class="row">
              <div class="col-7"></div>
              <div class="col-5">
                총 문제 :00
              </div>
            </div>
          </div>
        </div>
        <div class="row justify-center">
          <div class="col-12 col-md-auto" id="contents">
            문제 리스트 위치
          </div>
        </div>
      </div>
      <!-- 하단 문제집 삭제,수정, 시작하기 버튼 -->
      <div class="col">
        <div class="row">
          <div class="col-7"></div>
          <div class="col-5">
            <q-btn color="primary" label="문제 List" />
            <q-btn color="primary" label="문제 List" />
            <q-btn color="primary" label="문제 List" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
.q-btn {
  margin-left: 10px;
}
#header-title {
  height: 100px;
}
#header-btn {
  height: 150px;
}
#contents {
  padding-bottom: 20px;
}
</style>
