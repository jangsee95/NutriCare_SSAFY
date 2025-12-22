<template>
  <section class="board-list container">
    <div class="list-header">
      <h2>게시판</h2>
      <p>자유롭게 의견을 나누고 정보를 공유해보세요.</p>
    </div>

    <div v-if="isLoading" class="loading-spinner">
      <div class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <div v-else-if="hasError" class="alert alert-danger" role="alert">
      {{ hasError }}
    </div>

    <template v-else>
      <div class="controls">
        <button class="btn-write" @click="goToCreate">
          <i class="bi bi-pencil-fill"></i> 글쓰기
        </button>
        <div class="filters">
          <select id="category" v-model="categoryFilter" class="custom-select" aria-label="카테고리 필터">
            <option value="">전체 카테고리</option>
            <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
          </select>
          <select id="sort" v-model="sortKey" class="custom-select" aria-label="정렬 순서">
            <option value="created_at_desc">최신순</option>
            <option value="created_at_asc">오래된순</option>
            <option value="views_desc">조회수 높은순</option>
            <option value="views_asc">조회수 낮은순</option>
          </select>
        </div>
      </div>

      <div class="table-container">
        <table class="custom-table">
          <thead>
            <tr>
              <th class="col-id">No.</th>
              <th class="col-cat">카테고리</th>
              <th class="col-title">제목</th>
              <th class="col-author">작성자</th>
              <th class="col-views">조회수</th>
              <th class="col-date">작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="pagedPosts.length === 0" class="empty-row">
              <td colspan="6">게시글이 없습니다.</td>
            </tr>
            <tr v-for="post in pagedPosts" :key="post.boardId" @click="goDetail(post.boardId)">
              <td class="col-id">{{ post.boardId }}</td>
              <td class="col-cat"><span class="badge-cat">{{ post.category }}</span></td>
              <td class="col-title">{{ post.title }}</td>
              <td class="col-author">{{ post.userName }}</td>
              <td class="col-views">{{ post.viewCount }}</td>
              <td class="col-date">{{ formatDate(post.createdAt) }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="pagination-controls">
        <button class="page-btn prev" :disabled="page === 1" @click="page--">
          &lt;
        </button>
        <div class="page-numbers">
          <button
            v-for="p in totalPages"
            :key="p"
            class="page-btn number"
            :class="{ active: p === page }"
            @click="page = p"
          >
            {{ p }}
          </button>
        </div>
        <button class="page-btn next" :disabled="page === totalPages" @click="page++">
          &gt;
        </button>
      </div>
    </template>
  </section>
</template>

<script setup>
import { computed, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useBoardStore } from '@/stores/board';
import { storeToRefs } from 'pinia';

const router = useRouter();
const boardStore = useBoardStore();

const { boards, isLoading, hasError } = storeToRefs(boardStore);

onMounted(() => {
  boardStore.fetchBoards();
});

const categories = computed(() => [...new Set(boards.value.map((p) => p.category).filter(Boolean))]);
const categoryFilter = ref('');
const sortKey = ref('created_at_desc');

const page = ref(1);
const pageSize = 10;

const filteredSortedPosts = computed(() => {
  let arr = [...boards.value];
  if (categoryFilter.value) {
    arr = arr.filter((p) => p.category === categoryFilter.value);
  }
  switch (sortKey.value) {
    case 'views_desc':
      arr.sort((a, b) => b.viewCount - a.viewCount);
      break;
    case 'views_asc':
      arr.sort((a, b) => a.viewCount - b.viewCount);
      break;
    case 'created_at_asc':
      arr.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
      break;
    case 'created_at_desc':
    default:
      arr.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
      break;
  }
  return arr;
});

const totalPages = computed(() => Math.ceil(filteredSortedPosts.value.length / pageSize) || 1);

const pagedPosts = computed(() => {
  const start = (page.value - 1) * pageSize;
  return filteredSortedPosts.value.slice(start, start + pageSize);
});

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  });
};

function goDetail(id) {
  router.push({ name: 'boardDetail', params: { id } }).catch(() => {});
}

function goToCreate() {
  router.push({ name: 'boardCreate' }).catch(() => {});
}
</script>

<style scoped>
.board-list {
  max-width: 1000px;
  margin: 0 auto;
  padding: 60px 20px;
}

.list-header {
  text-align: center;
  margin-bottom: 40px;
}

.list-header h2 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #333;
  margin-bottom: 10px;
}

.list-header p {
  color: #666;
  font-size: 1.1rem;
}

/* Controls */
.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 15px;
}

.btn-write {
  background-color: #5a45b0;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.btn-write:hover {
  background-color: #483696;
}

.filters {
  display: flex;
  gap: 10px;
}

.custom-select {
  padding: 8px 32px 8px 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: white;
  color: #555;
  font-size: 0.95rem;
  cursor: pointer;
  outline: none;
  appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%235a45b0' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 8px center;
  background-size: 16px;
}

.custom-select:focus {
  border-color: #5a45b0;
}

/* Table */
.table-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin-bottom: 30px;
}

.custom-table {
  width: 100%;
  border-collapse: collapse;
}

.custom-table th,
.custom-table td {
  padding: 16px;
  text-align: center;
  border-bottom: 1px solid #f0f0f0;
}

.custom-table th {
  background-color: #f9f9fc;
  font-weight: 600;
  color: #444;
  font-size: 0.95rem;
  border-bottom: 2px solid #eaeaea;
}

.custom-table tbody tr {
  transition: background-color 0.2s;
  cursor: pointer;
}

.custom-table tbody tr:hover {
  background-color: #f8f5ff;
}

.custom-table tbody tr:last-child td {
  border-bottom: none;
}

/* Column Specifics */
.col-id { width: 80px; color: #888; }
.col-cat { width: 120px; }
.col-title { text-align: left; font-weight: 500; color: #333; }
.col-author { width: 120px; color: #555; }
.col-views { width: 100px; color: #666; font-size: 0.9rem; }
.col-date { width: 120px; color: #888; font-size: 0.9rem; }

.badge-cat {
  display: inline-block;
  padding: 4px 10px;
  background-color: #e8eaf6;
  color: #3f51b5;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
}

.empty-row td {
  padding: 40px;
  color: #999;
  font-style: italic;
}

/* Pagination */
.pagination-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.page-btn {
  background: white;
  border: 1px solid #ddd;
  color: #555;
  min-width: 36px;
  height: 36px;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  border-color: #5a45b0;
  color: #5a45b0;
}

.page-btn.active {
  background-color: #5a45b0;
  color: white;
  border-color: #5a45b0;
}

.page-btn:disabled {
  background-color: #f5f5f5;
  color: #ccc;
  cursor: not-allowed;
  border-color: #eee;
}

.loading-spinner {
  display: flex;
  justify-content: center;
  padding: 60px;
}

@media (max-width: 768px) {
  .col-id, .col-views, .col-date {
    display: none;
  }
  .col-cat {
    width: auto;
  }
}
</style>
