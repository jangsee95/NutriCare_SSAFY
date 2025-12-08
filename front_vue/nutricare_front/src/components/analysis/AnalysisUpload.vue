<template>
  <section class="analysis-upload">
    <div class="dropzone" @click="onPickFile">
      <div class="icon">📄</div>
      <p class="hint">이미지를 업로드하세요</p>
      <input ref="fileInput" type="file" accept="image/*" class="hidden-input" @change="onFileChange" />
    </div>

    <div class="health-form">
      <div class="field">
        <label for="height">키 (cm)</label>
        <input id="height" v-model.number="health.height_cm" type="number" step="0.01" placeholder="예) 172.5" />
      </div>
      <div class="field">
        <label for="weight">몸무게 (kg)</label>
        <input id="weight" v-model.number="health.weight_kg" type="number" step="0.01" placeholder="예) 63.2" />
      </div>

      <div class="field radios" role="group" aria-label="활동도">
        <span class="label">활동도</span>
        <div class="options">
          <label><input v-model="health.activity_level" type="radio" value="LOW" /> 하(운동 없음)</label>
          <label><input v-model="health.activity_level" type="radio" value="MEDIUM" /> 중(주 2-3회)</label>
          <label><input v-model="health.activity_level" type="radio" value="HIGH" /> 상(주 4회 이상)</label>
        </div>
      </div>

      <div class="field radios" role="group" aria-label="목표">
        <span class="label">목표</span>
        <div class="options">
          <label><input v-model="health.goal_type" type="radio" value="LOSE" /> 감량</label>
          <label><input v-model="health.goal_type" type="radio" value="MAINTAIN" /> 유지</label>
          <label><input v-model="health.goal_type" type="radio" value="GAIN" /> 증량</label>
        </div>
      </div>
    </div>

    <div class="actions">
      <button class="primary" type="button" @click="analyze">분석하기</button>
      <span class="arrow">→</span>
      <span class="spinner" aria-live="polite">⏳</span>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const fileInput = ref(null)
const selectedFile = ref(null)
const health = ref({
  height_cm: null,
  weight_kg: null,
  activity_level: 'LOW',
  goal_type: 'MAINTAIN',
})

function onPickFile() {
  fileInput.value?.click()
}

function onFileChange(event) {
  const [file] = event.target.files || []
  selectedFile.value = file || null
}

function analyze() {
  // TODO: 업로드 + 분석 API 연동, 로딩 처리
  const fakeResultId = 'result-123'
  console.log('upload payload', {
    file: selectedFile.value,
    health_profile: {
      height_cm: health.value.height_cm,
      weight_kg: health.value.weight_kg,
      activity_level: health.value.activity_level,
      goal_type: health.value.goal_type,
    },
  })
  router.push({ name: 'analysisResult', params: { resultId: fakeResultId } }).catch(() => {})
}
</script>

<style scoped>
.analysis-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 32px 16px 48px;
  background: #f8f5eb;
}

.dropzone {
  width: min(420px, 90vw);
  aspect-ratio: 4 / 3;
  background: #e9e2f3;
  border-radius: 12px;
  display: grid;
  place-items: center;
  gap: 10px;
  color: #5b4b82;
  cursor: pointer;
}

.icon {
  font-size: 32px;
}

.hint {
  margin: 0;
  color: #6a6680;
}

.hidden-input {
  display: none;
}

.extra {
  width: min(420px, 90vw);
  display: flex;
  flex-direction: column;
  gap: 6px;
  color: #555;
}

.extra input {
  border: none;
  border-bottom: 1px solid #999;
  padding: 8px 4px;
  background: transparent;
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

.arrow {
  color: #555;
}

.spinner {
  color: #555;
}
</style>
