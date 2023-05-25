<template>
    <Header>
</Header>
    <CContainer>

<section id="disponibili">

    <CRow>
        <CCol :md="12">
            <CCard class="mb-4">    
            <CCardHeader> Lezioni disponibili </CCardHeader>
            <CCardBody>
                <CDropdown>
                    <select class="btn btn-primary dropdown-toggle" type="button" @change="reloadDocs($event)" style="margin-right: 5px;">
                        <option class="bnt btn-secodary" href="#" v-for="(doc, index) in docs" v-bind:key="index" v-bind:value="doc.idDocente" >{{doc.nome}} {{ doc.cognome}}</option>
                        <option href="#" value="*">tutte le lezioni</option>
                    </select>
                        
                </CDropdown>
                <CDropdown>
                    <select class="btn btn-primary dropdown-toggle" type="button" @change="filterCourses($event)">
                        <option class="bnt btn-secodary" href="#" v-for="(course, index) in courses" v-bind:key="index" v-bind:value="course.idCorso" >{{course.titolo}}</option>
                        <option href="#" value="*">tutte i corsi</option>
                    </select>
                </CDropdown>
                <CTable>
                    <CTableHead>
                        <CTableRow>
                        <CTableHeaderCell scope="col">Corso</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Docente</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Inizio</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Fine</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Azioni disponibili</CTableHeaderCell>
                        </CTableRow>
                    </CTableHead>
                <CTableBody>
                    <CTableRow v-for="(lezione, index) in lezioni" v-bind:key="index">
                    <CTableDataCell>{{lezione.corso}}</CTableDataCell>
                    <CTableDataCell>{{lezione.docente}}</CTableDataCell>
                    <CTableDataCell>{{lezione.data_ora_inizio}}</CTableDataCell>
                    <CTableDataCell>{{lezione.data_ora_fine}}</CTableDataCell>
                    <th scope="col">
                    <input id="newPrenotation" class="btn btn-info btn-md-2" value="PRENOTA ORA" @click="createPrenotation(lezione.idLezione)"/>
                    </th>
                    </CTableRow>
                </CTableBody>
                </CTable>
            </CCardBody>
            <CAlert v-if="this.prenotationOk" class="alert alert-success" role="alert" :visible="this.prenotationOk" id="label" dismissible>
                <h3>Lezione prenotata con successo</h3>
                </CAlert>  
            </CCard>
        </CCol>
    </CRow>

    <CRow>
    <CCol :md="12" v-if="logged">
        <CCard class="mb-4">
        <CCardHeader> Mie prenotazioni </CCardHeader>
        <CCardBody>

            <CDropDown>
                <select class="btn btn-primary dropdown-toggle" type="button" @change="filterMyLesson($event)">
                    <option href="#" value="" disabled selected>Filtra per stato</option>
                    <option href="#" value="*">Tutti gli stati</option>
                    <option href="#" value="done">Effettuate</option>
                    <option href="#" value="todo">Da effettuare</option>
                    <option href="#" value="aborted">Disdette</option>
            </select>
            </CDropDown>
            <CTable>
            <CTableHead>
                <CTableRow>
                <CTableHeaderCell scope="col">Corso</CTableHeaderCell>
                <CTableHeaderCell scope="col">Docente</CTableHeaderCell>
                <CTableHeaderCell scope="col">Inizio</CTableHeaderCell>
                <CTableHeaderCell scope="col">Fine</CTableHeaderCell>
                <CTableHeaderCell scope="col">Azioni disponibili</CTableHeaderCell>
                <CTableHeaderCell scope="col">Segna come effettuata</CTableHeaderCell>
                </CTableRow>
            </CTableHead>
            <CTableBody>
                <CTableRow v-for="(lezioneUser, index) in userLezioni" v-bind:key="index">
                <CTableDataCell>{{lezioneUser.corso}}</CTableDataCell>
                <CTableDataCell>{{lezioneUser.docente}}</CTableDataCell>
                <CTableDataCell>{{lezioneUser.data_ora_inizio}}</CTableDataCell>
                <CTableDataCell>{{lezioneUser.data_ora_fine}}</CTableDataCell>
                <th scope="col" v-if="(!lezioneUser.disdetta&&!lezioneUser.effetuata && ctrlDate(lezioneUser))">
                <input id="disdiciPrenotazione" class="btn btn-outline-danger btn-md" value="DISDICI" @click="disdiciLezione(lezioneUser)"/>
                </th>
                <th scope="col" v-else-if="lezioneUser.effetuata">
                    <input color="success" class="btn btn-success btn-md" value="FINITA!" disabled/>
                </th>
                <th scope="col" v-else-if="lezioneUser.disdetta">
                    <input color="success" class="btn btn-danger btn-md" value="DISDETTA!" disabled/>
                </th>
                <th scope="col" v-else>
                    <input color="success" class="btn btn-success btn-md" value="FINITA!" disabled/>
                </th>
                <th scope="col" v-if="!lezioneUser.disdetta && !lezioneUser.effetuata && ctrlDate(lezioneUser)">
                    <input color="success" class="btn btn-info btn-md" value="SVOLTA" @click="segnaSvolta(lezioneUser)"/>
                </th>
                <th scope="col" v-else>
                </th>
                </CTableRow>
            </CTableBody>
            </CTable>

        </CCardBody>
        <CAlert v-if="this.operationOk" class="alert alert-success" role="alert" :visible="this.operationOk" id="label" dismissible>
                <h3>Operazione effettuata con successo</h3>
                </CAlert>  
        </CCard>
    </CCol>
    </CRow>
</section>


<div v-if="this.errNotLogged" class="alert alert-danger" role="alert" :visible="this.errNotLogged" id="label">
          <h3>Devi accedere prima</h3>
          <CButton color="link" class="px-0" @click="this.$router.push('pages/login')">
            Portami al login
            </CButton>
</div>  
</CContainer>



</template>

  
<script>
import DefaultLayout from '@/layouts/DefaultLayout.vue';
import SideBar from '@/components/AppSidebar.vue';
import Footer from '@/components/AppFooter.vue'
import Header from '@/components/AppHeader.vue'
import ModalLog from '@/components/ModalNotLogged.vue'
import { CContainer } from '@coreui/vue'
import { CPaginationItem } from '@coreui/vue'

import $ from 'jquery';

export default {
name: 'Guest',
data: () => ({
    lezioni:[],
    userLezioni:[],
    docs:[],
    courses:[],
    ruolo:'',
    user:'',
    totalLessons:[],
    totalPersonalLesson:[],
    token:'',
    logged:false,
    errNotLogged:false,
    operationOk:false,
    prenotationOk:false,
}),

components: {
    Header,
    Footer,
    //AppHeaderDropdownAccnt,
    SideBar,
    ModalLog,
    DefaultLayout,
    CContainer,
    CPaginationItem,
},
mounted(){
    this.$router.push(this.$route.name);
    this.ctrlSession();
    /*window.alert("res: "+this.res);
    if(this.res=="OK"){
        window.alert("OK");
        this.user=localStorage.getItem("username").toString();
        this.idUser=localStorage.getItem("idUser");
        this.ruolo= localStorage.getItem("ruolo").toString();
        this.token= localStorage.getItem("token").toString();
        //this.ctrlSession();
        this.getLessons();
        this.getUserLessons();
        this.getDocs();
        this.getCourses();

    }
    else{
        self.$router.push("/login");
    }*/
},
methods:{
    ctrlSession(){
        let self=this;
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletErrorNotLog;jsessionid="+localStorage.getItem("token"),
            {
                user:localStorage.getItem("username"),
                ruolo:"utente",
            },
            function(response){
            if(response=="not_logged" || response=="sessione_inesistente"|| response=="no_permission"){
                self.logged=false;
                localStorage.clear();
                self.getLessons();
                self.getCourses();
                self.getDocs();
                //self.$router.push("pages/login");
            }
            else if(response=="logged_in"){
                self.logged=true;
                self.user=localStorage.getItem("username").toString();
                self.idUser=localStorage.getItem("idUser");
                self.ruolo= localStorage.getItem("ruolo").toString();
                self.token= localStorage.getItem("token").toString();
                //this.ctrlSession();
                self.getLessons();
                self.getUserLessons();
                self.getDocs();
                self.getCourses();
            }
        })
    },
    getLessons(){
        let self=this;
        if(self.logged){
            $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
            {
                method:"getLezioniDisponibili"
            },
            function (response){
                self.totalLessons=response;
                self.lezioni=self.totalLessons;
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.logged=false;
                    //self.$router.push("pages/login");
                }
            })
        }
        else{
            $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData",
            {
                method:"getLezioniDisponibili"
            },
            function (response){
                //window.alert(JSON.stringify(response));
                self.totalLessons=response;
                self.lezioni=self.totalLessons;
                if(response=="sessione_inesistente"){
                    self.logged=false;
                    localStorage.clear();
                    //self.$router.push("pages/login");
                }
            })
        }
        
    },
    getUserLessons(){
        let self=this;
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
            {
                method:"getPrenotazioniUtente",
                utente:localStorage.getItem("idUser"),
            },
            function(response){
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.$router.push("pages/login");
                }
                self.totalPersonalLesson=response;
                self.userLezioni=self.totalPersonalLesson;
            }
        )
    },
    createPrenotation(id){
        let self = this;
        if(self.logged){
            $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
            {
                method:"createPrenotation",
                idLezione:id,
                idUtente:localStorage.getItem("idUser"),
            },
            function(response){
                if(response=="OK"){
                    self.getUserLessons();
                    self.getLessons();
                    self.prenotationOk=true;
                }
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.$router.push("pages/login");
                }
            }
        )
        }
        else{
            self.errNotLogged=true;
            //self.$router.push("pages/login");
        }
        
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
                self.getUserLessons();
                self.operationOk=true;
                }
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.$router.push("pages/login");
                }
            }
        )
    },
    getDocs(){
      let self=this;
      if(self.logged){
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
            {
              method:"getDocs"
            },
            function(response){
                self.docs=response;
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.$router.push("pages/login");
                }
            }
      )
      }
      else{
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData",
            {
              method:"getDocs"
            },
            function(response){
                //window.alert(JSON.stringify(response));
                self.docs=response;
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    //self.$router.push("pages/login");
                }
            }
      )
      }
      
    },
    getCourses(){
      let self=this;
      if(self.logged){
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
            method:"getCourses"
        },
        function(response){
            if(response!="sessione_inesistente"){
                self.courses=response;
            }
            else{
                localStorage.clear();
                self.$route.push("pages/login");
            }
        }
      )
      }
      else{
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletServletGetData;jsessionid="+localStorage.getItem("token"),
        {
            method:"getCourses"
        },
        function(response){
            self.courses=response;
            localStorage.clear();
            //self.$route.push("pages/login");
        }
        )
      }
      
    },
    reloadDocs(id){
        if(id.target.value!="*")
        {
            let filteredLessons=[];
            let j=0;
            for(let i=0;i<this.totalLessons.length;i++)
            {
                if(this.totalLessons[i].idDocente==id.target.value)
                {
                    filteredLessons[j]=this.totalLessons[i];
                    j++;
                }
            }
            this.lezioni=filteredLessons;
        }
        else
        {
            this.lezioni=this.totalLessons;
        }
    },
    filterCourses(id)
    {
        if(id.target.value!="*")
        {
            let filteredLessons=[];
            let j=0;
            for(let i=0;i<this.totalLessons.length;i++)
            {
                if(this.totalLessons[i].idCorso==id.target.value)
                {
                    filteredLessons[j]=this.totalLessons[i];
                    j++;
                }
            }
            this.lezioni=filteredLessons;
            this.$parent.reload();
        }
        else
        {
            this.lezioni=this.totalLessons;
            this.$parent.reload();
        }
    },
    segnaSvolta(lesson){
        let self = this;
            $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletCreateData;jsessionid="+localStorage.getItem("token"),
            {
                method:"setEffetuata",
                idLezione:lesson.idLezione,
            },
            function(response){
                if(response=="OK"){
                    self.getUserLessons();
                    self.getLessons();
                    self.operationOk=true;
                }
                if(response=="sessione_inesistente"){
                    localStorage.clear();
                    self.$router.push("pages/login");
                }
            }
        )
    },  
    filterMyLesson(state){
        switch(state.target.value)
        {
            case "*":{
                this.userLezioni=this.totalPersonalLesson;
                break;
            }
            case "aborted":{
                let filteredLessons=[];
                let j=0;
                for(let i=0;i<this.totalPersonalLesson.length;i++)
                {
                    if(this.totalPersonalLesson[i].disdetta)
                    {
                        filteredLessons[j]=this.totalPersonalLesson[i];
                        j++;
                    }
                }
                this.userLezioni=filteredLessons;
                break;
            }
            case "todo":{
                let filteredLessons=[];
                let j=0;
                for(let i=0;i<this.totalPersonalLesson.length;i++)
                {
                    if(this.ctrlDate(this.totalPersonalLesson[i])&&!this.totalPersonalLesson[i].disdetta)
                    {
                        filteredLessons[j]=this.totalPersonalLesson[i];
                        j++;
                    }
                }
                this.userLezioni=filteredLessons;
                break;
            }
            case "done":{
                let filteredLessons=[];
                let j=0;
                for(let i=0;i<this.totalPersonalLesson.length;i++)
                {
                    if(!this.ctrlDate(this.totalPersonalLesson[i])&&!this.totalPersonalLesson[i].disdetta)
                    {
                        filteredLessons[j]=this.totalPersonalLesson[i];
                        j++;
                    }
                }
                this.userLezioni=filteredLessons;
                break;
            }
        }
    },
    }
}
</script>
  