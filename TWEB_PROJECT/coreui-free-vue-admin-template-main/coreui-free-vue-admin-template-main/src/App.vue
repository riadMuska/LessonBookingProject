<template>
  <router-view />
</template>

<style lang="scss">
// Import Main styles for this application
@import 'styles/style';

</style>  
<script>
import $ from 'jquery'
import Footer from '@/components/AppFooter.vue';
export default {
  name: 'App',
  components:{
    Footer,
  }
}
</script>
