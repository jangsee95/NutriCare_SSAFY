<template>
  <section class="password-form">
    <h2 class="sr-only">비밀번호 수정</h2>
    <form @submit.prevent="onSubmit">
      <div class="field">
        <label class="icon-label" for="currentPw">🔒</label>
        <input id="currentPw" v-model="form.current" type="password" placeholder="기존 비밀번호" required />
      </div>
      <div class="field">
        <label class="icon-label" for="newPw">🔒</label>
        <input id="newPw" v-model="form.newPw" type="password" placeholder="새로운 비밀번호" required />
      </div>
      <div class="field">
        <label class="icon-label" for="confirmPw">🔒</label>
        <input id="confirmPw" v-model="form.confirm" type="password" placeholder="새로운 비밀번호 다시 확인" required />
      </div>
      <div class="actions">
        <button type="button" class="secondary" @click="goDetail">취소</button>
        <button type="submit" class="primary">변경</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const form = reactive({
  current: '',
  newPw: '',
  confirm: '',
})

async function onSubmit() {
  if (form.newPw !== form.confirm) {
    alert('새 비밀번호가 일치하지 않습니다.')
    return
  }
  // TODO: axios 연동
  await userStore.updateProfile({})
  goDetail()
}

function goDetail() {
  router.push({ name: 'userDetail', params: { userid: route.params.userid || 'me' } }).catch(() => {})
}
</script>

<style scoped>
.password-form {
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

.actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 10px;
}

.primary,
.secondary {
  padding: 10px 20px;
  background: #d8d8d8;
  border: 1px solid #aeaeae;
  cursor: pointer;
  min-width: 120px;
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
