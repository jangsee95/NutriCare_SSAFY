<template>
  <section class="join-form">
    <h2 class="sr-only">회원가입</h2>
    <form @submit.prevent="onSubmit">
      <div class="field">
        <label class="icon-label" for="userId">😊</label>
        <input id="userId" v-model="form.userId" type="text" placeholder="아이디" required />
      </div>
      <div class="field">
        <label class="icon-label" for="password">🔒</label>
        <input id="password" v-model="form.password" type="password" placeholder="비밀번호" required />
      </div>
      <div class="field">
        <label class="icon-label" for="email">✉️</label>
        <input id="email" v-model="form.email" type="email" placeholder="이메일" required />
      </div>
      <div class="field">
        <label class="icon-label" for="birth">📅</label>
        <input id="birth" v-model="form.birth" type="date" placeholder="생년" />
      </div>
      <div class="field gender-field" role="group" aria-label="성별">
        <button type="button" :class="['seg-btn', form.gender === 'M' && 'active']" @click="form.gender = 'M'">남</button>
        <button type="button" :class="['seg-btn', form.gender === 'F' && 'active']" @click="form.gender = 'F'">여</button>
      </div>
      <div class="actions">
        <button type="submit" class="primary">회원가입</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const form = reactive({
  userId: '',
  password: '',
  email: '',
  birth: '',
  gender: 'M',
})

async function onSubmit() {
  // TODO: axios 연동 및 검증 로직 추가
  await userStore.login({ userId: form.userId })
  router.push({ name: 'Home' }).catch(() => {})
}
</script>

<style scoped>
.join-form {
  width: 100%;
  max-width: 480px;
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
}

.gender-field {
  grid-template-columns: repeat(2, 1fr);
  padding: 0;
  border: none;
  gap: 10px;
}

.seg-btn {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #b7b7b7;
  background: #f5f5f5;
  cursor: pointer;
}

.seg-btn.active {
  background: #e4ddff;
  border-color: #c7bbff;
}

.actions {
  display: flex;
  justify-content: center;
  margin-top: 6px;
}

.primary {
  padding: 10px 20px;
  background: #d8d8d8;
  border: 1px solid #aeaeae;
  cursor: pointer;
  min-width: 120px;
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
