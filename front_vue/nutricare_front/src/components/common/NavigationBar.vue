<template>
    <div>
        <nav>
            <div class="logo">
                <img src="@/assets/logo.png" alt="Logo" width="50px" >
            </div>
            <div>
                <ul class="nav-links">
                    <li><RouterLink to="/">홈</RouterLink></li>
                    <li><RouterLink to="/engineeringDescribe">기술소개</RouterLink></li>
                    <li><RouterLink to="/board">게시판</RouterLink></li>
                    <li><RouterLink to="/analysis">분석</RouterLink></li>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script setup>

</script>

<style scoped>
nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #333;
    padding: 10px;
}
.logo img {
    display: block;
}
.nav-links {
    list-style: none;
    display: flex;
    gap: 15px;
}
.nav-links li a {
    color: white;
    text-decoration: none;
    font-weight: bold;
}
</style>