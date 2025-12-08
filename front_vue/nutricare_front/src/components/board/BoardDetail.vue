<template>
  <section class="board-detail">
    <header class="title-row">
      <div>
        <h2 class="title">{{ post.title }}</h2>
        <div class="meta">
          <span class="category">{{ post.category || '카테고리 없음' }}</span>
          <span class="dot">·</span>
          <span class="author">작성자: {{ post.author }}</span>
          <span class="dot">·</span>
          <span class="views">조회수 {{ post.views }}</span>
          <span class="dot">·</span>
          <span class="date">{{ post.created_at }}</span>
        </div>
      </div>
    </header>

    <div class="content" v-text="post.content"></div>

    <div class="images" v-if="post.images.length">
      <div
        v-for="img in post.images"
        :key="img.image_id"
        class="image-item"
      >
        <span>🖼️</span>
        <a href="#" @click.prevent="viewImage(img.image_url)">{{ img.image_url }}</a>
      </div>
    </div>

    <div class="attachment" v-if="post.attachment">
      <span>📁</span>
      <a href="#" @click.prevent="download">{{ post.attachment }}</a>
    </div>

    <section class="comments">
      <h3 class="comment-title">댓글</h3>
      <article class="comment" v-for="comment in comments" :key="comment.id">
        <div class="meta">
          <span class="author">{{ comment.author }}</span>
          <span class="date">{{ comment.date }}</span>
        </div>
        <p class="body">{{ comment.body }}</p>
      </article>
    </section>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const post = ref({
  id: route.params.id,
  user_id: 1,
  author: 'user_id',
  title: '게시글 제목 예시',
  category: '카테고리',
  content: '내용 예시입니다.',
  view_count: 123,
  views: 123,
  created_at: '2025-01-10 12:00',
  attachment: '첨부파일.file',
  images: [
    { image_id: 1, image_url: 'image1.png' },
    { image_id: 2, image_url: 'image2.png' },
  ],
})

const comments = ref([
  { id: 1, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
  { id: 2, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
  { id: 3, author: 'user_id', date: '2025-12-05 17:36', body: '댓글 내용' },
])

function download() {
  // TODO: 파일 다운로드 구현
  alert('파일 다운로드')
}

function viewImage(url) {
  // TODO: 이미지 뷰어/다운로드 구현
  alert(`이미지 보기: ${url}`)
}
</script>

<style scoped>
.board-detail {
  max-width: 900px;
  margin: 0 auto;
  padding: 24px 16px 40px;
  background: #f8f5eb;
  box-sizing: border-box;
}

.title-row {
  border-bottom: 1px solid #aaa;
  padding-bottom: 10px;
}

.title {
  margin: 0;
  font-size: 18px;
  font-weight: 700;
}

.meta {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-top: 4px;
  color: #777;
  font-size: 13px;
}

.dot {
  color: #aaa;
}

.content {
  margin: 18px 0 24px;
  min-height: 200px;
  color: #444;
}

.images {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 12px;
}

.image-item {
  display: flex;
  align-items: center;
  gap: 6px;
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

.comment-title {
  margin: 0 0 6px;
  font-size: 15px;
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
  margin: 6px 0 0;
  color: #333;
}
</style>
