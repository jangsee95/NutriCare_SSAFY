import { defineStore } from 'pinia'
import axios from '@/api/axios'
import router from '@/router'

// 사용자 상태 스토어
export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('accessToken') || null,
    userId: localStorage.getItem('userId') || null,
    isLoggedIn: !!localStorage.getItem('accessToken'),
    userInfo: {},
    healthProfile: {},
  }),

  getters: {
    userName: (state) => (state.userInfo ? state.userInfo.name : 'Guest'),
    isAdmin: (state) => state.userInfo?.role === 'ADMIN',
  },

  actions: {
    async fetchMe() {
      if (!this.token) return
      try {
        // 인터셉터가 헤더에 토큰을 자동으로 넣어줍니다.
        const response = await axios.get('/users/me')

        // 백엔드 UserDetailResponse 구조: { user: {...}, healthProfile: {...} }
        this.userInfo = response.data.user
        this.healthProfile = response.data.healthProfile || {}

        this.isLoggedIn = true // 정보 로드 성공 시 로그인 확인
      } catch (error) {
        console.error('내 정보 조회 실패 (토큰 만료 등):', error)
        // 토큰이 유효하지 않으면 로그아웃 처리
        this.logout()
      }
    },

    async login(email, password) {
      try {
        // 백엔드 Login API가 @RequestParam을 사용하므로 params로 전달
        const response = await axios.post('/users/login', null, {
          params: { email, password },
        })

        const { token, userId } = response.data

        if (token) {
          // Pinia 상태 업데이트
          this.token = token
          this.userId = userId
          this.isLoggedIn = true

          // LocalStorage에 저장
          localStorage.setItem('accessToken', token)
          localStorage.setItem('userId', userId)

          // 사용자 정보 가져오기
          await this.fetchMe()

          console.log('로그인 성공:', this.userId)
          return true
        }
      } catch (error) {
        console.error('로그인 요청 실패:', error)
        throw error
      }
    },

    // 로그아웃
    logout() {
      this.token = null
      this.userId = null
      this.isLoggedIn = false
      this.userInfo = null
      this.healthProfile = null

      localStorage.removeItem('accessToken')
      localStorage.removeItem('userId')

      router.push('/').catch(() => {})
    },

    async updateProfile(payload) {
      // TODO: axios.put('/api/user/profile', payload)
      this.healthProfile = { ...this.healthProfile, ...payload }
    },

    // 새로고침 시 세션 복구
    restoreSession() {
      const token = localStorage.getItem('accessToken')
      const userId = localStorage.getItem('userId')
      if (!token) return

      this.token = token
      this.userId = userId
      this.isLoggedIn = true

      // 사용자 정보 갱신
      this.fetchMe()
    },
  },
})
