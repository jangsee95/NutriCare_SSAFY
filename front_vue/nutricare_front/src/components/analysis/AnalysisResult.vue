<template>
  <section class="analysis-result">
    <div class="result-visual">
      <div class="photo">업로드한 얼굴 사진</div>
    </div>
    <p class="diagnosis">진단 결과 0O% "아토피" 입니다.</p>
    <div class="actions">
      <button class="primary" type="button" @click="goDiet">식단 추천 받기</button>
      <span class="arrow">→</span>
      <span class="spinner">⏳</span>
    </div>
  </section>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

function goDiet() {
  // TODO: 실제 식단 추천 라우트/동작 연결
  const resultId = route.params.resultId || 'result-123'
  router.push({ name: 'analysisDetail', params: { resultId } }).catch(() => {})
}
</script>

<style scoped>
.analysis-result {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 18px;
  padding: 32px 16px 48px;
  background: #f8f5eb;
}

.result-visual {
  width: min(420px, 90vw);
  aspect-ratio: 1;
  background: #d8d8d8;
  display: grid;
  place-items: center;
  color: #4a4a4a;
}

.photo {
  text-align: center;
  font-weight: 600;
}

.diagnosis {
  margin: 0;
  color: #555;
}

.actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.primary {
  padding: 8px 14px;
  background: #d8d8d8;
  border: 1px solid #aeaeae;
  cursor: pointer;
}

.arrow,
.spinner {
  color: #555;
}
</style>
