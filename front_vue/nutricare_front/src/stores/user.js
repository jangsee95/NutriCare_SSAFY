import { defineStore } from 'pinia'
import axios from '@/api/axios' // [변경] 우리가 만든 Axios 인스턴스 import

export const useUserStore = defineStore('user', {
  // 1. 상태 (State)
  state: () => ({
    token: localStorage.getItem('accessToken') || null,
    userId: localStorage.getItem('userId') || null,
    isLoggedIn: !!localStorage.getItem('accessToken'),
    profile: {},
  }),

  // 2. 게터 (Getters)
  getters: {

  },

  // 3. 액션 (Actions)
  actions: {
    async login(email, password) {
      try {
        // [api/axios.js]의 baseURL 설정 덕분에 '/api' 생략 가능 (설정에 따라 다름)
        // 백엔드가 @RequestParam을 받으므로 params 사용 유지
        const response = await axios.post('/users/login', null, {
          params: {
            email: email,
            password: password
          }
        });

        const { token, userId } = response.data;

        if (token) {
          // (1) 상태 업데이트
          this.token = token;
          this.userId = userId;
          this.isLoggedIn = true;

          // (2) LocalStorage 저장
          // *중요*: 인터셉터는 요청 보낼 때 이곳(localStorage)을 확인해서 헤더를 붙입니다.
          localStorage.setItem('accessToken', token);
          localStorage.setItem('userId', userId);


          console.log('로그인 성공:', this.userId);
          return true;
        }
      } catch (error) {
        console.error('로그인 요청 실패:', error);
        throw error;
      }
    },

    logout() {
      // (1) 상태 초기화
      this.token = null;
      this.userId = null;
      this.isLoggedIn = false;
      this.profile = {};

      // (2) LocalStorage 비우기
      localStorage.removeItem('accessToken');
      localStorage.removeItem('userId');

      // [삭제됨] 헤더 삭제 로직 불필요
      
      console.log('로그아웃 되었습니다.');
    },

    async fetchMe() {
      // 토큰이 없으면 요청 보내지 않음
      if (!this.token) return;

      try {
        // [변경] 헤더를 명시적으로 넣을 필요 없음 -> 인터셉터가 자동 처리
        const response = await axios.get('/users/me');
        
        this.profile = response.data; 
        // 응답 구조에 따라 response.data.user 등 경로 확인 필요
        // 예: UserRestController가 UserDetailResponse를 반환한다면:
        // this.profile = response.data.user; 
        
        // 내 정보 갱신 시 userId 재확인
        if (response.data.user && response.data.user.userId) {
             this.userId = response.data.user.userId;
        }
        
        this.isLoggedIn = true;
      } catch (error) {
        console.error('내 정보 조회 실패:', error);
        // 401 에러는 api/axios.js의 응답 인터셉터가 처리하겠지만,
        // 여기서 추가적인 UI 처리가 필요하다면 작성
      }
    },
  },
});