<template>
    <CModal :visible="visibleModal" @close="closeFunction()">
      <CModalHeader>
        <CModalTitle>Devi accedere prima</CModalTitle>
      </CModalHeader>
      <CModalBody>Questa funzionalità è riservata ai clienti, accedi per proseguire</CModalBody>
      <CModalFooter>
        <CButton color="secondary" @click="closeFunction()">
          Close
        </CButton>
        <CButton color="primary" @click="this.$router.push('pages/login')">Vai al login</CButton>
      </CModalFooter>
    </CModal>
  </template>
  <script>

    import $ from 'jquery';
    export default {
      props:['visibleModal'],
      data: () => ({
        visible:false,
      }),
      methods:{
        closeFunction(){
          window.alert(JSON.stringify(this.$parent.$props));
          this.$parent.errNotLogged=false;
        },
      }
    }
  </script>