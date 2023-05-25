<template>
  <section>
  <CModal :visible="visibleModal" @close="closeFunction()">
    <CModalHeader>
      <CModalTitle v-if="type=='doc'">Sicuro di voler aggiungere il docente?</CModalTitle>
      <CModalTitle v-if="type=='course'">Sicuro di voler aggiungere il corso?</CModalTitle>
    </CModalHeader>
    <CModalBody>Conferma la scelta!</CModalBody>
    <CModalFooter>
      <CButton color="secondary" @click="this.$parent.newDoc=false;closeFunction()">
        Annulla
      </CButton>
      <CButton color="primary" @click="confirmFunction()">Conferma operazione</CButton>
    </CModalFooter>
  </CModal>
  </section>
</template>
<script>
import { mount } from '@vue/test-utils';
import $ from 'jquery'
  export default {
    props: ['visibleModal','type'],
    data: () => ({
    }),
    methods:{
      closeFunction(){
        this.$parent.visibleLiveDemo=false;
        //this.$router.push("/");
      },
      confirmFunction(){
        this.closeFunction();
        if(this.type=='doc')
        {
          this.$parent.createDoc();
        }
        else if(this.type=='course'){
          this.$parent.createCourse();
        }
      }
    }
  }
</script>