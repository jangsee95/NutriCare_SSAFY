<template>
  <section class="login-form">
    <h2 class="sr-only">로그인</h2>
    <form @submit.prevent="onLogin">
      <div class="field">
        <label class="icon-label" for="loginEmail">😊</label>
        <input id="loginEmail" v-model="email" type="text" placeholder="이메일" required />
      </div>
      <div class="field">
        <label class="icon-label" for="loginPw">🔒</label>
        <input id="loginPw" v-model="password" type="password" placeholder="비밀번호" required />
      </div>
      <div class="actions">
        <button type="button" class="secondary" @click="goSignup">회원가입</button>
        <button type="submit" class="primary">로그인</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

const email = ref('');
const password = ref('');

const handleLogin = async () => {
  try {
    // 스토어의 login 액션 호출
    await userStore.login(email.value, password.value);
    
    // 로그인 성공 시 메인 페이지로 이동
    router.push('/');
  } catch (error) {
    alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
  }
};

async function onLogin() {
  // TODO: axios 연동/검증 추가
  await userStore.login(email.value, password.value)
  router.push({ name: 'Home' }).catch(() => {})
}

function goSignup() {
  router.push({ name: 'signup' }).catch(() => {})
}


</script>

<style scoped>
.login-form {
  width: 100%;
  max-width: 420px;
  margin: 0 auto;
  padding: 24px 0 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
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

.actions {
  display: flex;
  justify-content: center;
  gap: 14px;
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
