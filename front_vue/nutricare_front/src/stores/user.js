import { defineStore } from 'pinia'

// 간단한 사용자 상태 스토어 (TODO: API 연동)
export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    userId: '',
    profile: {},
  }),
  actions: {
    async fetchMe() {
      // TODO: axios.get('/api/user/me') 사용
      this.isLoggedIn = false
      this.userId = 'User_ID'
    },
    async login(payload) {
      // TODO: axios.post('/api/login', payload)
      this.isLoggedIn = true
      this.userId = payload?.userId || payload?.email || 'User_ID'
    },
    async logout() {
      // TODO: axios.post('/api/logout')
      this.isLoggedIn = false
      this.userId = ''
      this.profile = {}
    },
    async updateProfile(payload) {
      // TODO: axios.put('/api/user/profile', payload)
      this.profile = { ...this.profile, ...payload }
    },
  },
})

