<template>
  <section class="page-describe container">
    <div class="header-section">
      <h2 class="title">NutriCare</h2>
      <p class="subtitle">당신의 건강을 위한 똑똑한 선택, AI 기반 맞춤형 식단 및 피부 관리 솔루션</p>
    </div>
    
    <div class="hero">
      <div class="hero-visual">
        <div ref="trackRef" class="hero-track" role="list" aria-label="슬라이드 목록" @scroll="handleScroll"
          @wheel.prevent="handleWheel">
          <div class="hero-slide" v-for="(slide, idx) in slides" :key="slide.id" role="listitem">
            <div class="hero-image">
              <img
                :src="slide.image"
                :alt="slide.title"
                class="hero-photo"
                :class="{ active: idx === currentIndex }"
              />
            </div>
          </div>
        </div>
        <div class="hero-dots" role="tablist" aria-label="슬라이드 선택">
          <button v-for="(slide, idx) in slides" :key="slide.id" class="dot" :class="{ active: idx === currentIndex }"
            type="button" :aria-label="`슬라이드 ${idx + 1}`" @click="goToSlide(idx)"></button>
        </div>
      </div>
    </div>

    <div class="features-section">
      <div class="feature-item">
        <h3>맞춤형 분석</h3>
        <p>사용자의 신체 정보와 생활 습관을 정밀하게 분석하여 개인에게 딱 맞는 건강 관리법을 찾아드립니다.</p>
      </div>
      <div class="feature-item">
        <h3>AI 솔루션</h3>
        <p>최신 AI 기술을 활용하여 과학적이고 체계적인 식단과 피부 관리 솔루션을 실시간으로 제공합니다.</p>
      </div>
      <div class="feature-item">
        <h3>지속적인 케어</h3>
        <p>단회성 분석에 그치지 않고, 꾸준한 기록과 피드백을 통해 건강한 라이프스타일을 유지하도록 돕습니다.</p>
      </div>
    </div>

    <div class="cta">
      <button type="button" @click="goToAnalysis" class="cta-button">지금 무료로 분석 받기</button>
    </div>

  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import IntroImg1 from '@/assets/IntroImg1.png'
import IntroImg2 from '@/assets/IntroImg2.png'
import IntroImg3 from '@/assets/IntroImg3.png'

const router = useRouter()

const slides = ref([
  { id: 1, title: '건강한 인물 이미지 1', image: IntroImg1 },
  { id: 2, title: '건강한 인물 이미지 2', image: IntroImg2 },
  { id: 3, title: '건강한 인물 이미지 3', image: IntroImg3 },
])

const currentIndex = ref(0)
const trackRef = ref(null)
const wheelLock = ref(false)

function goToSlide(idx) {
  if (!trackRef.value) return
  const slideWidth = trackRef.value.clientWidth
  trackRef.value.scrollTo({
    left: slideWidth * idx,
    behavior: 'smooth',
  })
  currentIndex.value = idx
}

function handleScroll() {
  if (!trackRef.value) return
  const slideWidth = trackRef.value.clientWidth || 1
  const nextIndex = Math.round(trackRef.value.scrollLeft / slideWidth)
  currentIndex.value = Math.min(Math.max(nextIndex, 0), slides.value.length - 1)
}

function handleWheel(event) {
  if (wheelLock.value || !trackRef.value) return
  const delta = Math.abs(event.deltaY) > Math.abs(event.deltaX) ? event.deltaY : event.deltaX
  if (delta === 0) return

  const next = delta > 0 ? currentIndex.value + 1 : currentIndex.value - 1
  const clamped = Math.min(Math.max(next, 0), slides.value.length - 1)
  if (clamped === currentIndex.value) return

  wheelLock.value = true
  goToSlide(clamped)
  setTimeout(() => {
    wheelLock.value = false
  }, 400)
}

function goToAnalysis() {
  router.push({ name: 'analysisUpload' }).catch(() => { })
}
</script>

<style scoped>
.page-describe {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 40px;
  padding: 48px 20px;
  background: #f8f5eb;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  max-width: 1000px;
  margin: 0 auto;
}

.header-section {
  text-align: center;
  margin-bottom: 10px;
}

.title {
  font-size: 2.5rem;
  font-weight: 800;
  color: #2c3e50;
  margin: 0 0 10px;
}

.subtitle {
  font-size: 1.1rem;
  color: #666;
  margin: 0;
  font-weight: 500;
}

.hero {
  width: 100%;
  max-width: 800px;
  display: flex;
  justify-content: center;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
  background: #fff;
}

.hero-visual {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 16px;
}

.hero-dots {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 15px;
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 2px solid #ddd;
  background: #fff;
  cursor: pointer;
  padding: 0;
  transition: all 0.3s ease;
  flex-shrink: 0; /* Prevent shrinking */
}

.dot.active {
  background: #5a45b0;
  border-color: #5a45b0;
  transform: scale(1.2);
}

.hero-track {
  display: flex;
  width: 100%;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  scroll-behavior: smooth;
  scrollbar-width: none; /* Firefox */
}

.hero-track::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.hero-slide {
  flex: 0 0 100%;
  scroll-snap-align: center;
}

.hero-image {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
}

.hero-photo {
  width: 100%;
  height: auto;
  display: block;
  object-fit: cover;
}

.features-section {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  width: 100%;
  max-width: 900px;
  text-align: center;
  padding: 20px 0;
}

.feature-item h3 {
  font-size: 1.25rem;
  color: #5a45b0;
  margin-bottom: 12px;
  font-weight: 700;
}

.feature-item p {
  font-size: 0.95rem;
  color: #555;
  line-height: 1.6;
}

.cta {
  display: flex;
  justify-content: center;
  width: 100%;
}

.cta-button {
  padding: 16px 40px;
  background: linear-gradient(135deg, #6e56cf, #5a45b0);
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  font-weight: 700;
  font-size: 1.1rem;
  transition: transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 4px 10px rgba(90, 69, 176, 0.3);
}

.cta-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 14px rgba(90, 69, 176, 0.4);
}

@media (max-width: 768px) {
  .title {
    font-size: 2rem;
  }
  
  .subtitle {
    font-size: 1rem;
  }

  .features-section {
    grid-template-columns: 1fr;
    gap: 24px;
  }
}
</style>
