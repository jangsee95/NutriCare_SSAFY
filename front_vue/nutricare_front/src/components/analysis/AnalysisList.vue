<template>
  <section class="analysis-list">
    <table>
      <thead>
        <tr>
          <th>분석 날짜</th>
          <th>분석명</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="item in pagedItems"
          :key="item.id"
          @click="goDetail(item.id)"
        >
          <td class="with-icon">📅 {{ item.date }}</td>
          <td>{{ item.title }}</td>
        </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button type="button" :disabled="page===1" @click="page--">‹</button>
      <button
        v-for="p in totalPages"
        :key="p"
        :class="{ active: p === page }"
        type="button"
        @click="page = p"
      >
        {{ p }}
      </button>
      <button type="button" :disabled="page===totalPages" @click="page++">›</button>
    </div>
  </section>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const items = ref([
  { id: 'r1', date: '2025-01-01', title: '분석명' },
  { id: 'r2', date: '2025-01-02', title: '분석명' },
  { id: 'r3', date: '2025-01-03', title: '분석명' },
  { id: 'r4', date: '2025-01-04', title: '분석명' },
  { id: 'r5', date: '2025-01-05', title: '분석명' },
  { id: 'r6', date: '2025-01-06', title: '분석명' },
  { id: 'r7', date: '2025-01-07', title: '분석명' },
  { id: 'r8', date: '2025-01-08', title: '분석명' },
])

const page = ref(1)
const pageSize = 7
const totalPages = computed(() => Math.ceil(items.value.length / pageSize))
const pagedItems = computed(() => {
  const start = (page.value - 1) * pageSize
  return items.value.slice(start, start + pageSize)
})

function goDetail(id) {
  router.push({ name: 'analysisDetail', params: { resultId: id } }).catch(() => {})
}
</script>

<style scoped>
.analysis-list {
  width: 100%;
  max-width: 720px;
  margin: 0 auto;
  padding: 32px 16px 48px;
  background: #f8f5eb;
  box-sizing: border-box;
}

table {
  width: 100%;
  border-collapse: collapse;
  background: #fff;
}

th, td {
  border: 1px solid #9f9f9f;
  padding: 12px 14px;
  text-align: left;
}

tbody tr {
  cursor: pointer;
}

tbody tr:hover {
  background: #f1f1f1;
}

.with-icon {
  display: flex;
  align-items: center;
  gap: 8px;
}

.pagination {
  margin-top: 16px;
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
