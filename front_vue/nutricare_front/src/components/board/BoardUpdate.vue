<template>
  <section class="board-update">
    <header class="title-row">
      <input class="title" v-model="title" placeholder="제목" />
      <div class="category">카테고리</div>
    </header>

    <textarea class="content" v-model="content" placeholder="내용" rows="10"></textarea>

    <div class="attachment" v-if="attachment">
      <span>📁</span>
      <a href="#" @click.prevent="download">{{ attachment }}</a>
    </div>

    <section class="comments">
      <article class="comment" v-for="comment in comments" :key="comment.id">
        <div class="meta">
          <span class="author">{{ comment.author }}</span>
          <span class="date">{{ comment.date }}</span>
        </div>
        <p class="body">{{ comment.body }}</p>
        <div class="comment-actions">
          <button type="button" class="pill edit">수정</button>
          <button type="button" class="pill delete">삭제</button>
        </div>
      </article>
    </section>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const title = ref('제목')
const content = ref('내용 텍스트를 여기에 입력하세요.')
const attachment = ref('첨부파일.file')
const images = ref([
  { image_id: 1, image_url: 'image1.png' },
])
const comments = ref([
  { id: 1, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
  { id: 2, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
  { id: 3, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
])

function download() {
  // TODO: 파일 다운로드 구현
  alert('파일 다운로드')
}

// TODO: 이미지 추가/삭제, board_image 업로드/삭제 연동 필요

// TODO: 저장/삭제 API 연동, 카테고리/파일 업로드 추가 가능
</script>

<style scoped>
.board-update {
  max-width: 900px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  background: #f8f5eb;
  box-sizing: border-box;
}

.title-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  border-bottom: 1px solid #aaa;
  padding-bottom: 10px;
}

.title {
  flex: 1;
  border: none;
  border-bottom: 1px solid #aaa;
  padding: 8px 4px;
  font-size: 16px;
  background: transparent;
}

.category {
  color: #777;
  white-space: nowrap;
}

.content {
  width: 100%;
  border: none;
  border-bottom: 1px solid #aaa;
  padding: 10px 4px;
  margin: 18px 0 24px;
  font-size: 14px;
  min-height: 220px;
  background: transparent;
}

.attachment {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 0;
  border-top: 1px solid #aaa;
  border-bottom: 1px solid #aaa;
}

.comments {
  margin-top: 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.comment {
  border-bottom: 1px solid #d0d0d0;
  padding-bottom: 8px;
}

.meta {
  display: flex;
  justify-content: space-between;
  color: #777;
  font-size: 13px;
}

.body {
  margin: 6px 0;
  color: #333;
}

.comment-actions {
  display: flex;
  gap: 6px;
}

.pill {
  border: 1px solid #d1d1d1;
  border-radius: 12px;
  padding: 2px 8px;
  font-size: 12px;
  cursor: pointer;
}

.pill.edit {
  background: #e7f2e7;
}

.pill.delete {
  background: #f8d9d9;
}
</style>
