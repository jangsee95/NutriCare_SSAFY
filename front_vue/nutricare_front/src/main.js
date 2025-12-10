import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { useUserStore } from './stores/user'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

// 새로고침 시 세션 복구
const userStore = useUserStore(pinia)
userStore.restoreSession()

app.mount('#app')

