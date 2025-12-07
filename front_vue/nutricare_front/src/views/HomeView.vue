<template>
  <div>
    <h3>HomeView.vue</h3>
    <Header />
    <main>
      <RouterView />
    </main>
    <Footer />
  </div>
</template>

<script setup>
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'
</script>

<style scoped>

</style>