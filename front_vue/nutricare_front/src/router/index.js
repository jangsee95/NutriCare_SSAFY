import BoardCreate from '@/components/board/boardCreate.vue'
import BoardDetail from '@/components/board/boardDetail.vue'
import BoardList from '@/components/board/BoardList.vue'
import EngineeringDescribe from '@/components/main/EngineeringDescribe.vue'
import PageDescribe from '@/components/main/PageDescribe.vue'
import BoardView from '@/views/BoardView.vue'
import HomeView from '@/views/HomeView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView,
      children: [
        {
          path: '',
          name: 'pageDescribe',
          component: PageDescribe,
        },
        {
          path: 'engineeringDescribe',
          name: 'engineeringDescribe',
          component: EngineeringDescribe,
        },
      ],
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      children: [
        {
          path: 'create',
          name: 'boardCreate',
          component: BoardCreate,
        },
        {
          path: 'detail/:id',
          name: 'boardDetail',
          component: BoardDetail,
        },
        {
          path: '',
          name: 'boardList',
          component: BoardList,
        },
      ],
    }
  ],
})

export default router
