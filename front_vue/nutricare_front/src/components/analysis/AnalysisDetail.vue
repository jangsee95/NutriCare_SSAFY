<template>
  <section class="analysis-detail">
    <div class="grid">
      <div class="photo">사용자 얼굴 사진</div>

      <div class="stack">
        <div class="field">😊 피부 질환</div>
        <div class="field">👍 추천식단</div>
        <div class="field">📅 분석 날짜</div>
      </div>

      <div class="desc">
        <span class="icon">📖</span>
        <p>설명</p>
      </div>

      <div class="recipe-card">
        <div class="recipe-visual">추천 식단 조리법 유튜브 썸네일</div>
        <div class="recipe-actions">
          <button type="button">👁️ 조회수</button>
          <button type="button">👍 좋아요 수</button>
        </div>
      </div>

      <div class="video">
        <div class="meta">📅 비디오 제목</div>
        <div class="footer">
          <button type="button" @click="goList">목록</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

function goList() {
  router.push({ name: 'analysisList', params: { userId: 'me' } }).catch(() => {})
}

// TODO: onMounted로 route.params.resultId 로 데이터 조회 후 바인딩
</script>

<style scoped>
.analysis-detail {
  padding: 32px 16px 48px;
  background: #f8f5eb;
}

.grid {
  display: grid;
  grid-template-columns: 260px 1fr;
  grid-template-rows: auto auto auto;
  gap: 16px;
  max-width: 960px;
  margin: 0 auto;
}

.photo {
  grid-row: 1 / 3;
  background: #cfcfcf;
  display: grid;
  place-items: center;
  color: #3d3d3d;
  font-weight: 600;
}

.stack {
  display: grid;
  gap: 10px;
}

.field {
  border: 1px solid #9f9f9f;
  padding: 10px 12px;
  background: #fff;
}

.desc {
  grid-column: 1 / 3;
  border: 1px solid #9f9f9f;
  min-height: 140px;
  background: #fff;
  padding: 12px;
  display: flex;
  gap: 10px;
  align-items: flex-start;
}

.desc .icon {
  margin-top: 2px;
}

.recipe-card {
  background: #cfcfcf;
  min-height: 160px;
  display: grid;
  grid-template-rows: 1fr auto;
  color: #3d3d3d;
  font-weight: 600;
}

.recipe-visual {
  display: grid;
  place-items: center;
  padding: 10px;
}

.recipe-actions {
  display: flex;
  gap: 6px;
  padding: 8px;
  background: #e9e9e9;
  justify-content: flex-start;
}

.video {
  border: 1px solid #9f9f9f;
  display: grid;
  grid-template-rows: auto auto;
  background: #fff;
}

.video .meta {
  padding: 12px;
  border-bottom: 1px solid #e0e0e0;
}

.video .footer {
  display: flex;
  justify-content: flex-end;
  padding: 8px 12px;
}

.video button,
.recipe-actions button {
  border: 1px solid #aeaeae;
  background: #f0f0f0;
  padding: 6px 10px;
  cursor: pointer;
}

@media (max-width: 900px) {
  .grid {
    grid-template-columns: 1fr;
    grid-template-rows: auto;
  }

  .photo {
    grid-row: auto;
  }

  .desc {
    grid-column: 1;
  }
}
</style>
