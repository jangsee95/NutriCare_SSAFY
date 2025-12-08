<template>
  <section class="page-describe">
    <div class="hero">
      <button class="nav-btn" type="button" aria-label="이전" @click="prevSlide">‹</button>
      <div class="hero-visual">
        <div class="hero-image">
          <p>{{ currentSlide.title }}</p>
        </div>
        <div class="hero-dots" role="tablist" aria-label="슬라이드 선택">
          <button
            v-for="(dot, idx) in slides"
            :key="dot.id"
            class="dot"
            :class="{ active: idx === currentIndex }"
            type="button"
            :aria-label="`슬라이드 ${idx + 1}`"
            @click="goToSlide(idx)"
          />
        </div>
      </div>
      <button class="nav-btn" type="button" aria-label="다음" @click="nextSlide">›</button>
    </div>

    <div class="bullet-list">
      <ol>
        <li>당신의 피부와 건강을, 한 번의 분석으로 더 똑똑하게.</li>
        <li>나를 이해하는 건강 케어, NutriCare가 시작합니다.</li>
        <li>내가 처방하는 맞춤 영양·피부 솔루션, 지금 경험해 보세요.</li>
      </ol>
      <div class="cta">
        <button type="button" @click="goToAnalysis">분석 하기</button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const slides = ref([
  { id: 1, title: '건강한 인물 이미지 1' },
  { id: 2, title: '건강한 인물 이미지 2' },
  { id: 3, title: '건강한 인물 이미지 3' },
])

const currentIndex = ref(0)
const currentSlide = computed(() => slides.value[currentIndex.value])

function nextSlide() {
  currentIndex.value = (currentIndex.value + 1) % slides.value.length
}

function prevSlide() {
  currentIndex.value = (currentIndex.value - 1 + slides.value.length) % slides.value.length
}

function goToSlide(idx) {
  if (idx < 0 || idx >= slides.value.length) return
  currentIndex.value = idx
}

function goToAnalysis() {
  // TODO: 분석 페이지 라우트 이름 확정 시 name 변경
  router.push({ name: 'analysisUpload' }).catch(() => {})
}
</script>

<style scoped>
.page-describe {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 32px;
  padding: 32px 16px 48px;
  background: #f8f5eb;
  box-sizing: border-box;
}

.hero {
  width: 100%;
  max-width: 900px;
  display: grid;
  grid-template-columns: auto 1fr auto;
  align-items: center;
  gap: 12px;
}

.nav-btn {
  width: 36px;
  height: 36px;
  border: 1px solid #d4d4d4;
  border-radius: 6px;
  background: #fafafa;
  color: #555;
  font-size: 20px;
  cursor: pointer;
}

.hero-visual {
  border: 1px solid #d4d4d4;
  background: #fdfdfd;
  padding: 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.hero-image {
  width: 100%;
  min-height: 320px;
  background: #c8c8c8;
  display: grid;
  place-items: center;
  color: #3b3b3b;
  font-size: 16px;
  font-weight: 600;
  text-align: center;
}

.hero-dots {
  display: flex;
  gap: 8px;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  border: 1px solid #bdbdbd;
  background: #efefef;
  cursor: pointer;
}

.dot.active {
  background: #777;
  border-color: #777;
}

.bullet-list {
  width: 100%;
  max-width: 720px;
  background: #fff;
  border: 1px solid #e0e0e0;
  padding: 20px 24px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

.bullet-list ol {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding-left: 20px;
  margin: 0 0 16px;
  color: #333;
  line-height: 1.5;
}

.cta {
  display: flex;
  justify-content: center;
}

.cta button {
  padding: 10px 20px;
  background: #ece4ff;
  color: #5a45b0;
  border: 1px solid #d7cfff;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
}

.cta button:hover {
  background: #e2d8ff;
}

@media (max-width: 768px) {
  .hero {
    grid-template-columns: 1fr;
  }

  .nav-btn {
    justify-self: center;
    width: 44px;
    height: 44px;
  }
}
</style>
