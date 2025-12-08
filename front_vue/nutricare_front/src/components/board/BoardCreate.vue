<template>
  <section class="board-create">
    <form @submit.prevent="onSubmit">
      <div class="file-row">
        <span>🖼️ 파일 첨부</span>
        <button type="button" @click="pickFile">파일 선택</button>
        <input ref="fileInput" type="file" class="hidden" @change="onFileChange" />
        <span class="file-name" v-if="fileName">{{ fileName }}</span>
      </div>

      <div class="category-row">
        <span>▼ 카테고리</span>
        <select v-model="category">
          <option value="">카테고리 선택</option>
          <option value="주사">주사</option>
          <option value="어두름">어두름</option>
          <option value="건선">건선</option>
          <option value="지루">지루</option>
        </select>
      </div>

      <input
        class="title"
        v-model="title"
        type="text"
        placeholder="제목을 입력하세요."
        required
      />

      <textarea
        class="content"
        v-model="content"
        placeholder="내용"
        rows="10"
        required
      ></textarea>

      <div class="actions">
        <button type="button" class="secondary" @click="goList">목록</button>
        <button type="submit" class="primary">등록</button>
      </div>
    </form>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const fileInput = ref(null)
const fileName = ref('')
const category = ref('')
const title = ref('')
const content = ref('')
const images = ref([]) // board_image

function pickFile() {
  fileInput.value?.click()
}

function onFileChange(event) {
  const files = Array.from(event.target.files || [])
  images.value = files
  fileName.value = files.map((f) => f.name).join(', ')
}

function goList() {
  router.push({ name: 'boardList' }).catch(() => {})
}

function onSubmit() {
  // TODO: 실제 업로드/등록 API 연동
  console.log('create', {
    category: category.value,
    title: title.value,
    content: content.value,
    images: images.value,
  })
  goList()
}
</script>

<style scoped>
.board-create {
  max-width: 700px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  background: #f8f5eb;
}

.file-row,
.category-row {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  color: #555;
}

.hidden {
  display: none;
}

.title {
  width: 100%;
  border: none;
  border-bottom: 1px solid #aaa;
  padding: 10px 4px;
  margin: 12px 0;
  font-size: 16px;
}

.content {
  width: 100%;
  border: none;
  border-bottom: 1px solid #aaa;
  padding: 10px 4px;
  margin: 12px 0 20px;
  font-size: 14px;
  resize: vertical;
  min-height: 200px;
  background: transparent;
}

.actions {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
}

.primary,
.secondary,
.file-row button {
  padding: 8px 14px;
  background: #d8d8d8;
  border: 1px solid #aeaeae;
  cursor: pointer;
}

.secondary {
  background: #efefef;
}

.file-name {
  color: #555;
  font-size: 13px;
}
</style>
