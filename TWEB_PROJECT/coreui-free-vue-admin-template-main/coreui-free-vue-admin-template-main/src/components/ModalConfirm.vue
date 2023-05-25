<template>
    <section>
    <CModal :visible="visibleModalDC" @close="closeFunction()">
      <CModalHeader>
        <CModalTitle>{{ testoModal }}</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <h1>è sicuro di voler procedere con {{testoModal}}</h1>
      </CModalBody>
      <CModalFooter>
        <div v-if="this.showLabel" class="alert alert-danger" role="alert" :visible="this.showLabel" id="label">
          Errore!
      </div>
        <CButton color="secondary" @click="closeFunction()">
          Chiudi
        </CButton>
        <CButton color="success" @click="confirm()">Associa!</CButton>
      </CModalFooter>
    </CModal>
    </section>
  </template>
  <script>
  import { mount } from '@vue/test-utils';
import $ from 'jquery';

export default {
    props: ['operation','testoModal','idLezione','idCourseToDelete'],
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
        confirm(){
            let self = this;
            switch(this.operation){
                case 'disdici':
                
                $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDisdiciLezione;jsessionid="+localStorage.getItem("token"),
                    {
                        idLezione:lezione.idLezione,
                    },
                    function(response){
                        if(response=="OK"){
                            self.$parent.getPrenotations();
                            self.$parent.getFreePrenotations();
                            self.closeFunction();
                        }
                        else if(response=="sessione_inesistente"){
                            localStorage.clear();
                        }
                    });
                break;

                case 'deleteDocente':
                $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
                {
                    method:"createDoc",
                    nome:self.newDocName,
                    cognome:self.newDocSurname,
                    attivita:self.newDocActivity,
                },
                function(response){
                if(response=="OK"){
                    self.getDocs();
                    self.nuovoDocente=false;
                    self.getAllLezioni();
                }
                else if(response=="sessione_inesistente"){
                    localStorage.clear();
                }
                });
                break;

                case 'deleteCorso':
                $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDeleteData;jsessionid="+localStorage.getItem("token"),
                    {
                        method:"deleteCourse",
                        id:self.idCourseToDelete,
                    },
                    function(response){
                    if(response=="sessione_inesistente"){
                        localStorage.clear();
                    }
                    if(response=="OK"){
                    self.$parent.getCourses();
                    self.$parent.getAllLezioni();
                    self.$parent.removeCourse=false;
                    }
                    }
                )
                        break;
            }
        }
    }
}
  </script>