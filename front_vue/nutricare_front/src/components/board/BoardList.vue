<template>
  <section class="board-list">
    <div class="controls">
      <div class="filter">
        <label for="category">카테고리</label>
        <select id="category" v-model="categoryFilter">
          <option value="">전체</option>
          <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
        </select>
      </div>
      <div class="sort">
        <label for="sort">정렬</label>
        <select id="sort" v-model="sortKey">
          <option value="created_at_desc">최신순</option>
          <option value="created_at_asc">오래된순</option>
          <option value="views_desc">조회수 높은순</option>
          <option value="views_asc">조회수 낮은순</option>
        </select>
      </div>
    </div>

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>게시글ID</th>
            <th>카테고리</th>
            <th>게시글 제목 (댓글수)</th>
            <th>작성자</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="post in pagedPosts"
            :key="post.id"
            @click="goDetail(post.id)"
          >
            <td>{{ post.id }}</td>
            <td>{{ post.category }}</td>
            <td>{{ post.title }} ({{ post.commentCount }})</td>
            <td>{{ post.author }}</td>
            <td>{{ post.views }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="pagination">
      <button type="button" :disabled="page === 1" @click="page--">‹</button>
      <button
        v-for="p in totalPages"
        :key="p"
        :class="{ active: p === page }"
        type="button"
        @click="page = p"
      >
        {{ p }}
      </button>
      <button type="button" :disabled="page === totalPages" @click="page++">›</button>
    </div>
  </section>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 목데이터: 실제 API 호출 후 교체 (board_id, user_id, title, category, view_count, created_at)
const posts = ref([
  { id: 1, category: '주사', title: '주사 관련법 꿀팁', commentCount: 12, author: '김', views: 102, created_at: '2025-01-10' },
  { id: 2, category: '여드름', title: '여드름 관련법 꿀팁', commentCount: 5, author: '박', views: 32, created_at: '2025-01-09' },
  { id: 3, category: '건선', title: '건선 관련법 꿀팁', commentCount: 22, author: '이', views: 309, created_at: '2025-01-08' },
  { id: 4, category: '지루', title: '지루 관련법 꿀팁', commentCount: 17, author: '장', views: 235, created_at: '2025-01-07' },
  { id: 5, category: '주사', title: '주사 관련법 꿀팁', commentCount: 12, author: '김', views: 102, created_at: '2025-01-06' },
  { id: 6, category: '여드름', title: '여드름 관련법 꿀팁', commentCount: 5, author: '박', views: 32, created_at: '2025-01-05' },
  { id: 7, category: '건선', title: '건선 관련법 꿀팁', commentCount: 22, author: '이', views: 309, created_at: '2025-01-04' },
  { id: 8, category: '지루', title: '지루 관련법 꿀팁', commentCount: 17, author: '장', views: 235, created_at: '2025-01-03' },
  { id: 9, category: '건선', title: '건선 관련법 꿀팁', commentCount: 22, author: '이', views: 309, created_at: '2025-01-02' },
  { id: 10, category: '지루', title: '지루 관련법 꿀팁', commentCount: 17, author: '장', views: 235, created_at: '2025-01-01' },
])

const categories = computed(() => [...new Set(posts.value.map((p) => p.category).filter(Boolean))])
const categoryFilter = ref('')
const sortKey = ref('created_at_desc')

const page = ref(1)
const pageSize = 10
const filteredSortedPosts = computed(() => {
  let arr = [...posts.value]
  if (categoryFilter.value) {
    arr = arr.filter((p) => p.category === categoryFilter.value)
  }
  switch (sortKey.value) {
    case 'views_desc':
      arr.sort((a, b) => b.views - a.views)
      break
    case 'views_asc':
      arr.sort((a, b) => a.views - b.views)
      break
    case 'created_at_asc':
      arr.sort((a, b) => new Date(a.created_at) - new Date(b.created_at))
      break
    case 'created_at_desc':
    default:
      arr.sort((a, b) => new Date(b.created_at) - new Date(a.created_at))
      break
  }
  return arr
})

const totalPages = computed(() => Math.ceil(filteredSortedPosts.value.length / pageSize) || 1)
const pagedPosts = computed(() => {
  const start = (page.value - 1) * pageSize
  return filteredSortedPosts.value.slice(start, start + pageSize)
})

function goDetail(id) {
  router.push({ name: 'boardDetail', params: { id } }).catch(() => {})
}
</script>

<style scoped>
.board-list {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  background: #f8f5eb;
  box-sizing: border-box;
}

.table-wrap {
  border: 1px solid #c3c9d3;
  background: #fff;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #9f9f9f;
  padding: 12px 10px;
  text-align: left;
  color: #333;
}

thead th {
  background: #dce8ff;
  font-weight: 600;
}

tbody tr {
  cursor: pointer;
}

tbody tr:hover {
  background: #f1f1f1;
}

.pagination {
  margin-top: 12px;
  display: flex;
  justify-content: center;
  gap: 6px;
}

.pagination button {
  border: none;
  background: transparent;
  cursor: pointer;
  padding: 6px 8px;
}

.pagination button.active {
  font-weight: 700;
  text-decoration: underline;
}

.pagination button:disabled {
  color: #b0b0b0;
  cursor: not-allowed;
}
</style>
