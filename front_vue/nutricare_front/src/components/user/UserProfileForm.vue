<template>
  <section class="profile-form">
    <h2 class="sr-only">프로필 수정</h2>
    <form @submit.prevent="onSubmit">
      <div class="field" v-for="field in basicFields" :key="field.key">
        <label class="icon-label" :for="field.key">{{ field.icon }}</label>
        <input
          :id="field.key"
          v-model="form[field.key]"
          :type="field.type"
          :placeholder="field.label"
          :required="field.required"
        />
      </div>

      <div class="field">
        <label class="icon-label" for="gender">👤</label>
        <div class="radio-group" id="gender">
          <label v-for="option in genderOptions" :key="option.value" class="radio">
            <input v-model="form.gender" type="radio" name="gender" :value="option.value" />
            <span>{{ option.label }}</span>
          </label>
        </div>
      </div>

      <div class="field activity">
        <label class="icon-label" for="activity">🏃</label>
        <div class="radio-group" id="activity">
          <label v-for="option in activityOptions" :key="option.value" class="radio">
            <input v-model="form.activityLevel" type="radio" name="activity" :value="option.value" />
            <span>{{ option.label }}</span>
          </label>
        </div>
      </div>

      <div class="field goal">
        <label class="icon-label" for="goal">🎯</label>
        <div class="radio-group" id="goal">
          <label v-for="option in goalOptions" :key="option.value" class="radio">
            <input v-model="form.goalType" type="radio" name="goal" :value="option.value" />
            <span>{{ option.label }}</span>
          </label>
        </div>
      </div>

      <div class="actions">
        <button type="button" class="secondary" @click="onLater">나중에 하기</button>
        <button type="submit" class="primary">등록</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { reactive, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

// 백엔드 필드명에 맞춘 폼 구조
const form = reactive({
  name: '',
  birthYear: '',
  gender: '',
  heightCm: '',
  weightKg: '',
  activityLevel: '',
  goalType: '',
})

function syncFormFromStore() {
  const info = userStore.userInfo || {}
  const profile = userStore.profile || {}

  form.name = info.name ?? ''
  form.birthYear = info.birthYear  ?? ''
  form.gender = info.gender ?? 'MALE'
  form.heightCm = profile.heightCm ?? ''
  form.weightKg = profile.weightKg ?? ''
  form.activityLevel = profile.activityLevel ?? 'LOW'
  form.goalType = profile.goalType ?? 'MAINTAIN'
}

onMounted(() => {
  syncFormFromStore()
})

watch(
  () => [userStore.userInfo, userStore.healthProfile, userStore.profile],
  () => syncFormFromStore(),
  { deep: true }
)

const basicFields = [
  { key: 'name', label: '이름', icon: '😊', type: 'text', required: true },
  { key: 'birthYear', label: '출생연도', icon: '📅', type: 'number', required: false },
  { key: 'heightCm', label: '키(cm)', icon: '📏', type: 'number', required: false },
  { key: 'weightKg', label: '몸무게(kg)', icon: '⚖️', type: 'number', required: false },
]

const genderOptions = [
  { value: 'MALE', label: '남성' },
  { value: 'FEMALE', label: '여성' },
]

const activityOptions = [
  { value: 'LOW', label: '낮음 (운동 안함)' },
  { value: 'MEDIUM', label: '보통 (주 2-3회)' },
  { value: 'HIGH', label: '높음 (주 4회 이상)' },
]

const goalOptions = [
  { value: 'GAIN', label: '증량' },
  { value: 'MAINTAIN', label: '유지' },
  { value: 'LOSS', label: '감량' },
]

async function onSubmit() {
  // TODO: axios 연동 및 검증 추가
  const payload = {
    name: form.name,
    birthYear: form.birthYear,
    gender: form.gender,
    heightCm: form.heightCm,
    weightKg: form.weightKg,
    activityLevel: form.activityLevel,
    goalType: form.goalType,
  }
  await userStore.updateProfile(payload)
  router.push({ name: 'Home' }).catch(() => {})
}

function onLater() {
  router.push({ name: 'Home' }).catch(() => {})
}
</script>

<style scoped>
.profile-form {
  width: 100%;
  max-width: 520px;
  margin: 0 auto;
  padding: 24px 0 32px;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.field {
  display: grid;
  grid-template-columns: 44px 1fr;
  align-items: center;
  border: 1px solid #b7b7b7;
  padding: 8px 10px;
  gap: 8px;
}

.icon-label {
  text-align: center;
  font-size: 18px;
}

input {
  border: none;
  outline: none;
  font-size: 14px;
  padding: 6px 4px;
  width: 100%;
}

.activity,
.goal {
  grid-template-columns: 44px 1fr;
  align-items: flex-start;
}

.radio-group {
  display: flex;
  gap: 18px;
  flex-wrap: wrap;
}

.radio {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  white-space: nowrap;
}

.actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 10px;
}

.primary,
.secondary {
  padding: 10px 20px;
  background: #d8d8d8;
  border: 1px solid #aeaeae;
  cursor: pointer;
  min-width: 130px;
}

.secondary {
  background: #efefef;
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}
</style>
