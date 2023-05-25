<template>
    <section id="docs">
    <CRow>
      <CCol :md="12" v-if="!nuovoDocente && !dont_show">
        <CCard class="mb-4">
          <CCardHeader> Docenti </CCardHeader>
          <CCardBody>
            <CTable>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell scope="col">Nome</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Cognome</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Stato di attività</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Opzioni</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="(docente, index) in docenti" v-bind:key="index">
                  <CTableDataCell>{{docente.nome}}</CTableDataCell>
                  <CTableDataCell>{{docente.cognome}}</CTableDataCell>
                  <CTableDataCell v-if="docente.attivo==1">in attività</CTableDataCell>
                  <CTableDataCell v-else>fuori servizio</CTableDataCell>
                  <td>
                  <input id="deleteDoc" class="btn btn-outline-danger btn-md" value="ELIMINA"
                         @click="deleteDoc(docente.idDocente)">
                </td>
                </CTableRow>
                <br/>
                  <CButton color="primary" @click="this.nuovoDocente=true">Nuovo docente!</CButton>
              </CTableBody>
            </CTable>
          </CCardBody>
          <CAlert v-if="this.deleteDocOk" class="alert alert-success" role="alert" :visible="this.deleteDocOk" id="label" dismissible>
                <h3>Docente eliminato con successo</h3>
          </CAlert>  
        </CCard>
      </CCol>
      
      <CForm v-else-if="!dont_show">
        <div class="mb-3">
          <CFormLabel for="newDocName">Nome</CFormLabel>
          <CFormInput type="text" id="newDocName" placeholder="inserisci il nome..." v-model="newDocName" v-if="invalid.invalidNewDocName" invalid/>
          <CFormInput type="text" id="newDocName" placeholder="inserisci il nome..." v-model="newDocName" v-else/>
        </div>
        <div class="mb-3">
          <CFormLabel for="newDocSurname">Cognome</CFormLabel>
          <CFormInput type="text" id="newDocSurname" placeholder="inserisci il cognome..." v-model="newDocSurname" v-if="invalid.invalidNewDocSurame" invalid/>
          <CFormInput type="text" id="newDocSurname" placeholder="inserisci il cognome..." v-model="newDocSurname" v-else/>
        </div>
        <div class="mb-3">
          <input type="radio" id="activity" name="stato di attività" value=1 v-model="newDocActivity" v-if="invalid.invalidNewDocActivity" invalid>
          <input type="radio" id="activity" name="stato di attività" value=1 v-model="newDocActivity" v-else>
          <label for="activity">In attività</label><br>
          
          <input type="radio" id="inactivity" name="stato di attività" value=0 v-model="newDocActivity">
          <label for="inactivity">Fuori servizio</label><br>
        </div>
        <div class="mb-3">
          <CButton color="success" @click="visibleLiveDemo=true;modalType='doc'" style="margin-right: 5px;">Inserisci ora!</CButton>

          <CButton color="danger" @click="annullaNewDoc()">Annulla</CButton>
        </div>
      </CForm>
    </CRow>

    <CRow v-if="!dont_show">
      <CCol :md="12">
        <CCard class="mb-4">
          <CCardHeader> Prenotazioni </CCardHeader>
          <CCardBody>
            <CDropdown>
                <select class="btn btn-primary dropdown-toggle" type="button" @change="filterCourses($event)">
                    <option class="bnt btn-secodary" href="#" v-for="(course, index) in corsi" v-bind:key="index" v-bind:value="course.idCorso" style="text-align: left;">{{course.titolo}}</option>
                    <option href="#" value="*" style="text-align: left;">Tutti i corsi</option>
                </select>
            </CDropdown>
            <CTable>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell scope="col">Docente</CTableHeaderCell>
                  <th scope="col">Corso</th>
                  <th scope="col">Utente</th>
                  <th scope="col">Inizio</th>
                  <th scope="col">Fine</th>
                  <th scope="col">Disdici</th>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                
                <CTableRow  v-for="(prenotazione, index) in totalLessons"  v-bind:key="index">
                <CTableDataCell>{{prenotazione.docente}}</CTableDataCell>
                <CTableDataCell>{{prenotazione.corso}}</CTableDataCell>
                <CTableDataCell v-if="prenotazione.utente!=null">{{prenotazione.utente}}</CTableDataCell>
                <CTableDataCell v-else>da prenotare</CTableDataCell>
                <CTableDataCell>{{prenotazione.data_ora_inizio}}</CTableDataCell>
                <CTableDataCell>{{prenotazione.data_ora_fine}}</CTableDataCell>
                <th scope="col" v-if="(prenotazione.effetuata)">
                  <input  class="btn btn-info btn-md"  value="SVOLTA" disabled/>
                </th>
                <th scope="col" v-else-if="(prenotazione.disdetta)">
                  <input  class="btn btn-danger btn-md"  value="DISDETTA" disabled/>
                </th>
                <th scope="col" v-else-if="(ctrlDate(prenotazione)&&!prenotazione.disdetta&&!prenotazione.effetuata&&prenotazione.utente!=null)">
                  <input  id="disdiciPrenotazione" class="btn btn-outline-danger btn-md" value="DISDICI" @click="disdiciLezione(prenotazione)"/>
                </th>
                <th scope="col" v-else>
                </th>
                
                </CTableRow>
                
                
                <CTableRow v-for="(prenotazioneLibera, index) in ripetizioniLibere" v-bind:key="index">
                  <CTableDataCell>{{prenotazioneLibera.docente}}</CTableDataCell>
                  <CTableDataCell>{{prenotazioneLibera.corso}}</CTableDataCell>
                  <CTableDataCell></CTableDataCell>
                  <th scope="col"></th>
                </CTableRow>
              </CTableBody>
            </CTable>
          </CCardBody>
          <CAlert v-if="this.disdictOk" class="alert alert-success" role="alert" :visible="this.disdictOk" id="label" dismissible>
                <h3>Leione disdetta con successo</h3>
                </CAlert>  
        </CCard>
      </CCol>
    </CRow>

    <CRow v-if="!nuovoCorso && !dont_show">
      <CCol :md="12">
        <CCard class="mb-4">
          <CCardHeader> Corsi </CCardHeader>
          <CCardBody>
            <CTable>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell scope="col">Corso</CTableHeaderCell>
                  <th scope="col">
                </th>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="(corso, index) in corsi" v-bind:key="index">
                  <CTableDataCell>{{corso.titolo}}</CTableDataCell>
                  <td>
                  <input id="deleteDoc" class="btn btn-outline-danger btn-md" value="ELIMINA"
                         @click="deleteCourse(corso.idCorso)">
                </td>
                </CTableRow>
                <br/>
                  <CButton color="primary" @click="nuovoCorso=true" >Nuovo Corso!</CButton>
              </CTableBody>
            </CTable>
          </CCardBody>
                <CAlert v-if="this.deleteCourseOk" class="alert alert-success" role="alert" :visible="this.deleteCourseOk" id="label" dismissible>
                  <h3>Corso eliminato con successo</h3>
                </CAlert>  
        </CCard>
      </CCol>
    </CRow>

    <CForm v-else-if="!dont_show">
        <div class="mb-3">
          <CFormLabel for="titoloCorso">Titolo del corso</CFormLabel>
          <CFormInput placeholder="Inserisci il titolo" type="text" v-model="newCourseTitle"  v-if="invalid.invalidNewTitle" invalid />
          <CFormInput placeholder="Inserisci il titolo" type="text" v-model="newCourseTitle"  v-else/>
        </div>
        <div class="mb-3">
          <CButton color="success" @click="visibleLiveDemo=true; modalType='course'" style="margin-right: 5px;">Inserisci ora!</CButton>
          <!--@click="(visibleLiveDemo=true)"-->
          <CButton color="danger" @click="nuovoCorso=false;newCourseTitle='';invalid.invalidNewTitle=false">Annulla</CButton>
        </div>
    </CForm>


    <section id="associazioni">
      <CRow v-if="!dont_show">
      <CCol :md="12">
        <CCard class="mb-4">
          <CCardHeader>Associazioni Corso Docente </CCardHeader>
          <CCardBody>
            <CTable>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell scope="col">Corso</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Docente</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="(dc, index) in docsCourses" v-bind:key="index">
                  <CTableDataCell>{{dc.titolo}}</CTableDataCell>
                  <CTableDataCell>{{dc.nome}} {{dc.cognome}}</CTableDataCell>
                  <td>
                  <input  class="btn btn-outline-danger btn-md" value="ELIMINA"
                         @click="deleteAssociation(dc.idDocente,dc.idCorso)">
                </td>
                </CTableRow>
              </CTableBody>
              <br/>
              <CButton color="primary" @click="(corsiDocenti=true,visibleNewDC=true)">Nuova associazione!</CButton>
            </CTable>
            <CAlert v-if="this.deleteAssociationOk" class="alert alert-success" role="alert" :visible="this.deleteAssociationOk" id="label" dismissible>
                <h3>Operazione effettuata con successo</h3>
                </CAlert>  
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </section>

    <section id="modal">
      <Modal :visible="visibleLiveDemo" :visibleModal="visibleLiveDemo" v-bind:type="this.modalType">
      </Modal>
    </section>
    <section id="modalNewDC">
        <ModalDC :visible="visibleNewDC" :visibleModalDC="visibleNewDC" v-bind:docs="this.docenti" v-bind:courses="this.corsi">
        </ModalDC>
    </section>

  </section>
  <ModalNotLogged :visible="errNotLogged"></ModalNotLogged>
  <Footer></Footer>
  
</template>

<script>
import Modal from '@/components/Modal.vue'
import ModalDC from '@/components/ModalCourseDoc.vue'
import Footer from '@/components/AppFooter.vue'
import SideBar from '@/components/AppSidebar.vue'
import ModalNotLogged from '@/components/ModalNotLogged.vue'

import $ from 'jquery'
import { CAlert, CButton, CTableDataCell } from '@coreui/vue';

export default {
  name: 'Dashboard',
  props: ['visibleModal','visibleModalNewDC'],

  data: () => ({
    modalType:"",
    newCourseTitle:"",
    newDocName:"",
    newDocSurname:"",
    newDocActivity:false,
    nuovoDocente:false,
    nuovoCorso:false,
    showModal:false,
    filteredLessons:[],
    docenti:[],
    corsi:[],
    totalLessons:[],
    originalLessons:[],
    ripetizioniPrenotate:[],
    ripetizioniLibere:[],
    visibleLiveDemo: false,
    visibleNewDC:false,
    docsCourses:[],
    corsiDocenti:false,
    errNotLogged:false,
    dont_show:false,
    removeCourse:false,
    course_to_delete:0,
    invalid:{
      invalidNewTitle:false,
      invalidNewDocName:false,
      invalidNewDocSurame:false,
      invalidNewDocActivity:false,
    },
    deleteDocOk:false,
    deleteAssociationOk:false,
    deleteCourseOk:false,
    disdictOk:false,
  }),
  
  components: {
    Modal,
    ModalDC,
    SideBar,
    ModalNotLogged,
    Footer,
    CAlert
},

  mounted(){
    //window.alert(localStorage.getItem("username"));
    //const modalActive=ref(true);
    /*if(localStorage.getItem("username")==null)
    {
      self.$router.push("/pages/login");
    }*/
    this.ctrlSession();
    this.options = {
      data: [
        { label: 'Android', value: 56.9 },
        { label: 'iOS', value: 22.5 },
        { label: 'BlackBerry', value: 6.8 },
        { label: 'Symbian', value: 8.5 },
        { label: 'Bada', value: 2.6 },
        { label: 'Windows', value: 1.9 },
      ],
      series: [
        {
          type: 'pie',
          angleKey: 'value',
          labelKey: 'label',
          strokeWidth: 3,
        },
      ],
      legend: {
        position: 'right',
      },
    };
  },
  methods:{
    ctrlSession(){
        let self=this;
        /*window.alert(localStorage.getItem("token"));
        window.alert(localStorage.getItem("username"));
        window.alert(localStorage.getItem("ruolo"));*/
        /*if(localStorage.getItem("token")==null){
          self.$router.push("/pages/login");
        }
        else{*/
          if(localStorage.getItem("ruolo")!="admin" ){
              self.errNotLogged=true;
              localStorage.clear();
              self.$router.push("/guest");
              self.dont_show=true;
          }
          else{
            $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletErrorNotLog;jsessionid="+localStorage.getItem("token"),
          {
            user:localStorage.getItem("username"),
            ruolo:localStorage.getItem("ruolo"),
          },
            function(response){
            //alert(response);
            if(response=="not_logged" || response=="sessione_inesistente"){
                self.errNotLogged=true;
                self.dont_show=true;
                //self.$router.push("pages/login");
            }
            else if(response=="logged_in"){
              self.dont_show=false;
              self.getDocs();
              self.getCourses();
              self.getCoursesForDoc();
              self.getAllLezioni();
            }
            else if(response=="no_permission")
            {
              self.errNotLogged=true;
              localStorage.clear();
              self.$router.push("/guest");
              self.dont_show=true;
            }
        });
          }
        //}
      },
    /*******************CRUD DOC*********************************** */
    createDoc(){
      let self=this;
      this.newDocName==""?this.invalid.invalidNewDocName=true:this.invalid.invalidNewDocName=false;
      this.newDocSurname==""?this.invalid.invalidNewDocSurame=true:this.invalid.invalidNewDocSurame=false;
      this.newDocActivity==""?this.invalid.invalidNewDocActivity=true:this.invalid.invalidNewDocActivity=false;
      if(!(this.invalid.invalidNewDocActivity||this.invalid.invalidNewDocName||this.invalid.invalidNewDocSurame))
      {
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
        }
      );
    }
  },
    //READ
    getDocs(){
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
          method:"getDocs"
        },
        function(response){
          self.docenti=response;
        }
      )
    },
    //DELETE
    deleteDoc(id){
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDeleteData;jsessionid="+localStorage.getItem("token"),
        {
          method:"deleteDoc",
          id:id,
        },
        function(response){
          if(response=="OK"){
            self.getDocs();
            self.getAllLezioni();
            self.deleteDocOk=true;
          }
          else if(response=="sessione_inesistente"){
            localStorage.clear();
          }
        }
      )
    },
    /******************************CRUD COURSES *********************************/
    createCourse(){
      let self=this;
      if(this.newCourseTitle=="")
      {
        this.invalid.invalidNewTitle=true;
      }
      else{
        this.invalid.invalidNewTitle=false;
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
        {
          method:"createCourse",
          titolo:this.newCourseTitle,
        },
        function(response){
          if(response!="sessione_inesistente"){
            self.getCourses();
            self.nuovoCorso=false;
            self.getAllLezioni();
          }
          else{
            localStorage.clear();
          }
        })
      }
    },
    //READ
    getCourses(){
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
          method:"getCourses"
        },
        function(response){
        self.corsi=response;
        });
    },
    //DELETE
    deleteCourse(id){
      
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDeleteData;jsessionid="+localStorage.getItem("token"),
        {
          method:"deleteCourse",
          id:id,
        },
        function(response){
        if(response=="sessione_inesistente"){
          localStorage.clear();
        }
        if(response=="OK"){
          self.getCourses();
          self.getAllLezioni();
          self.deleteCourseOk=true;
        }
        }
      )
    },
    /**************CRUD ASSOCIATION********************** */
    getCoursesForDoc(){
      let self=this;
      $.post('http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid='+localStorage.getItem("token"),
        {
          method:"getDocsCourse",
        },
        function(response){
          self.docsCourses=response;
        })
    },
    deleteAssociation(idDoc,idCorso)
    {
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDeleteData;jsessionid="+localStorage.getItem("token"),
        {
          method:"deleteAssociation",
          idDocente:idDoc,
          idCorso:idCorso,
        },
        function(response){
          if(response=="sessione_inesistente"){
          localStorage.clear();
          }
          if(response=="OK"){
            self.deleteAssociationOk=true;
          }
          self.getCoursesForDoc();  
        }
      )
    },
    
    annullaNewDoc(){
      this.invalid.invalidNewDocActivity=this.invalid.invalidNewDocName=this.invalid.invalidNewDocSurame=false;
      this.nuovoDocente=false;
    },
    ////////////////////////GET PRENOTATIONS
    getPrenotations(){
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
          method:"getPrenotatedLessons"
        },
        function(response){
          //window.alert(JSON.stringify(response));
          self.ripetizioniPrenotate=response;
          self.updateLezioneSetEffettuata();
      })
    },
    getFreePrenotations(){
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
          method:"getLezioniDisponibili"
        },
        function(response){
          //window.alert(JSON.stringify(response));
          self.ripetizioniLibere=response;
      }) 
    },
    ctrlDate(lesson){
        let d=new Date();
        let systemDate;
        let hour=d.getHours().toString();
        if(parseInt(hour)<10)
        {
            hour='0'+hour;
        }
        let minutes=d.getMinutes().toString();
        if(parseInt(minutes)<10)
        {
            minutes='0'+minutes;
        }
        let seconds=d.getSeconds().toString();
        if(parseInt(seconds)<10)
        {
            seconds='0'+seconds;
        }
        let mounth=(d.getMonth()+1).toString();
        if(parseInt(mounth)<10)
        {
            mounth='0'+mounth;
        }
        let day=(d.getDate()).toString();
        if(parseInt(day)<10)
        {
            day='0'+day;
        }
        systemDate=(d.getFullYear().toString()+'-'+mounth+'-'+day+' '+hour+':'+minutes+':'+seconds).toString();
        let userDate=JSON.stringify(lesson.data_ora_inizio).replaceAll("\"","");
        return userDate>systemDate;
    },
    disdiciLezione(lezione)
    {
      let self = this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletDisdiciLezione;jsessionid="+localStorage.getItem("token"),
          {
              idLezione:lezione.idLezione,
          },
          function(response){
            if(response=="OK"){
              self.getAllLezioni();
              self.disdictOk=true;
            }
            else if(response=="sessione_inesistente"){
              localStorage.clear();
            }
          })
    },
    updateLezioneSetEffettuata()
    {
      for(let i=0;i<this.ripetizioniPrenotate.length;i++)
      {
        if(this.ripetizioniPrenotate[i].disdetta==false&&this.ripetizioniPrenotate[i].effetuata==false&&!this.ctrlDate(this.ripetizioniPrenotate[i]))
        {
          $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
          {
            method:"setEffetuata",
            idLezione:this.ripetizioniPrenotate[i].idLezione
          },
          function(response){
            //window.alert(response);
          }
          )
        }
      } 
    },
    filterCourses(id)
    {
      if(id.target.value!="*")
        {
            let filteredLessons=[];
            let j=0;
            for(let i=0;i<this.originalLessons.length;i++)
            {
                if(this.originalLessons[i].idCorso==id.target.value)
                {
                    filteredLessons[j]=this.originalLessons[i];
                    j++;
                }
            }
            this.totalLessons=filteredLessons;
        }
        else
        {
          this.totalLessons=this.originalLessons;
        }
    },
    getAllLezioni()
    {
      let self=this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
          method:"getAllLezioni"
        },
        function(response){
          //window.alert(JSON.stringify(response));
          self.totalLessons=response;
          self.originalLessons=self.totalLessons;
      })      
    }
  }
}
</script>
