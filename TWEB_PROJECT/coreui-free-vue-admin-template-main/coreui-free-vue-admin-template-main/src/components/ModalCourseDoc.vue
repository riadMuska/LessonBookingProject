<template>
    <section>
    <CModal :visible="visibleModalDC" @close="closeFunction()">
      <CModalHeader>
        <CModalTitle>Associa un docente a un corso!</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <select class="btn btn-primary dropdown-toggle" type="button" @change="selectedDoc($event)">
            <option value="" disabled selected>Seleziona il docente...</option>
            <option v-for="(doc, index) in docs" v-bind:key="index" v-bind:value="doc.idDocente">{{doc.nome+" "+doc.cognome}}</option>
        </select>
        <br/>
        <br/>
        <select class="btn btn-primary dropdown-toggle" type="button" id="selectCorso" @change="selectedCourse($event)">
            <option value="" disabled selected>Seleziona il corso...</option>
            <option v-for="(course, index) in courses" v-bind:key="index" v-bind:value="course.idCorso">{{course.titolo}}</option>
        </select>
      </CModalBody>
      <CModalFooter>
        <div v-if="this.showLabel" class="alert alert-danger" role="alert" :visible="this.showLabel" id="label">
          Associazione già esistente!
      </div>
        <CButton color="secondary" @click="closeFunction()">
          Chiudi
        </CButton>
        <CButton color="success" @click="newAssociation()">Associa!</CButton>
      </CModalFooter>
    </CModal>
    </section>
  </template>
  <script>
  import { mount } from '@vue/test-utils';
import $ from 'jquery';

export default {
    props: ['visibleModalDC','docs','courses'],
    data: () => ({
        isVisible:false,
        showLabel:false,
        idDoc:0,
        idCourse:0,
    }),
    methods:{
        closeFunction(){
          this.showLabel=false;
          this.$parent.visibleNewDC=false;
        },
        newAssociation(){
          let self=this;
          $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
            {
              method:"createAssociation",
              idDoc:self.idDoc,
              idCorso:self.idCourse,
            },
            function(response){
              if(response.toString()=="ERROR")
              {
                self.showLabel=true;
              }
              if(response.toString()=="sessione_inesistente")
              {
                localStorage.clear();
                self.$router.push("/pages/login");
              }
              else if(response=="OK"){
                self.showLabel=false;
                self.closeFunction();
                self.$parent.getCoursesForDoc();
              }
            }
          )
        },
        selectedCourse(idCorso){
            this.idCourse=idCorso.target.value;
        },
        selectedDoc(idDocente){
            this.idDoc=idDocente.target.value;
        },
    }
}
  </script>