<template>
  <section class="detail-form">
    <h2 class="sr-only">유저 상세</h2>
    <form @submit.prevent>
      <div class="field" v-for="field in fields" :key="field.key">
        <label class="icon-label" :for="field.key">{{ field.icon }}</label>
        <input
          :id="field.key"
          v-model="form[field.key]"
          :type="field.type"
          :placeholder="field.label"
          :readonly="field.readonly"
        />
      </div>
      <div class="actions">
        <button type="button" class="secondary" @click="onEditProfile">회원정보 수정</button>
        <button type="button" class="secondary" @click="onEditPassword">비밀번호 수정</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { reactive, watch, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const form = reactive({
  userId: route.params.userid || '',
  email: '',
  name: '',
  birthYear: '',
  gender: '',
  height: '',
  weight: '',
  activity: '',
  goal: '',
})

const fields = [
  { key: 'email', label: '이메일', icon: '📧', type: 'email', readonly: true },
  { key: 'name', label: '이름', icon: '😊', type: 'text', readonly: true },
  { key: 'birthYear', label: '출생연도', icon: '📅', type: 'text', readonly: true },
  { key: 'gender', label: '성별', icon: '👤', type: 'text', readonly: true },
  { key: 'height', label: '키(cm)', icon: '📏', type: 'text', readonly: true },
  { key: 'weight', label: '몸무게(kg)', icon: '⚖️', type: 'text', readonly: true },
  { key: 'activity', label: '활동 정도', icon: '🏃', type: 'text', readonly: true },
  { key: 'goal', label: '목표', icon: '🎯', type: 'text', readonly: true },
]

function syncFromStore() {
  const info = userStore.userInfo || {}
  const profile = userStore.profile || {}

  form.email = info.email || ''
  form.name = info.name || ''
  form.birthYear = info.birthYear ?? ''
  form.gender = info.gender ?? ''
  form.height = profile.heightCm ?? profile.height ?? ''
  form.weight = profile.weightKg ?? profile.weight ?? ''
  form.activity = profile.activityLevel ?? profile.activity ?? ''
  form.goal = profile.goalType ?? profile.goal ?? ''
}

onMounted(() => {
  syncFromStore()
})

watch(
  () => [userStore.userInfo, userStore.healthProfile, userStore.profile],
  () => syncFromStore(),
  { deep: true }
)

function onEditProfile() {
  router.push({ name: 'updateProfile', params: { userid: form.userId || 'me' } }).catch(() => {})
}

function onEditPassword() {
  router.push({ name: 'updatePassword', params: { userid: form.userId || 'me' } }).catch(() => {})
}
</script>

<style scoped>
.detail-form {
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
  background: transparent;
}

.actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 10px;
}

.secondary {
  padding: 10px 18px;
  background: #efefef;
  border: 1px solid #aeaeae;
  cursor: pointer;
  min-width: 140px;
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
