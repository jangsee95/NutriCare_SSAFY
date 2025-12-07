<template>
    <div>
        <h3>BoardView.vue</h3>
        <Hearder />
        <main>
            <RouterView />
        </main>
        <Footer /> 
    </div>
</template>

<script setup>
import Footer from '@/components/common/Footer.vue';
import Hearder from '@/components/common/Header.vue'
</script>

<style scoped>

</style>