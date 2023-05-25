<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="9" :lg="7" :xl="6">
          <CCard class="mx-4">
            <CCardBody class="p-4">
              <CForm>
                <h1>Register</h1>
                <p class="text-medium-emphasis">Create your account</p>
                <CInputGroup class="mb-3">
                  <CInputGroupText>
                    <CIcon icon="cil-user" />
                  </CInputGroupText>
                  <CFormInput placeholder="Username"  v-model="user"  v-if="invalid.invalidUser" invalid />
                  <CFormInput placeholder="Username"  v-model="user"  v-else/>
                </CInputGroup>
                <CInputGroup class="mb-3">
                  <CInputGroupText>@</CInputGroupText>
                  <CFormInput placeholder="Email"  v-model="mail"  v-if="invalid.invalidMail" invalid />
                  <CFormInput placeholder="Email"  v-model="mail"  v-else/>
                </CInputGroup>
                <CInputGroup class="mb-3">
                  <CInputGroupText>
                    <CIcon icon="cil-lock-locked" />
                  </CInputGroupText>
                  <CFormInput placeholder="Password" type="password" v-model="pwd"  v-if="invalid.invalidPwd" invalid />
                  <CFormInput placeholder="Password" type="password" v-model="pwd"  v-else/>
                </CInputGroup>
                <CInputGroup class="mb-4">
                  <CInputGroupText>
                    <CIcon icon="cil-lock-locked" />
                  </CInputGroupText>
                  <CFormInput placeholder="Repeat password" type="password" v-model="confirmpwd"  v-if="invalid.invalidConfirmPwd" invalid />
                  <CFormInput placeholder="Repeat password" type="password" v-model="confirmpwd"  v-else/>
                </CInputGroup>
                <tr class="mb-3">
                  <input type="radio" id="type" name="tipo di account" value=1 v-model="accountType" v-if="invalid.invalidType">
                  <input type="radio" id="type" name="tipo di account" value=1 v-model="accountType" v-else>
                  <label for="type">Ospite</label><br>
                  <input type="radio" id="type2" name="tipo di account" value=0 v-model="accountType">
                  <label for="type2">Admin</label><br>
                </tr>
                <div class="d-grid">
                  <CButton color="success" @click="newUser()">Create Account</CButton>
                </div>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
      <div
          v-if="showLabel"
          class="alert alert-danger"
          role="alert">
          Username già esistente, scegline un altro!
      </div>
    </CContainer>
  </div>
</template>

<script>
import $ from 'jquery';
export default {
  
  name: 'Register',
  data: () => ({
    accountType:0,
    user:'',
    pwd:'',
    confirmpwd:'',
    mail:'',
    showLabel:false,
    invalid:{
      invalidMail:false,
      invalidUser:false,
      invalidPwd:false,
      invalidConfirmPwd:false,
      invalidType:false,
    },
  }),
  methods:{
    newUser(){
      var pattern = new RegExp(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/) ;
      this.mail==""?this.invalid.invalidMail=true:this.invalid.invalidMail=false;
      this.mail.match(pattern)==null?this.invalid.invalidMail=true:this.invalid.invalidMail=false;
      this.user==""?this.invalid.invalidUser=true:this.invalid.invalidUser=false;
      this.pwd==""?this.invalid.invalidPwd=true:this.invalid.invalidPwd=false;
      this.accountType!=0!=1!=2?this.invalid.invalidType=true:this.invalid.invalidType=false;
      this.pwd!=this.confirmpwd?this.invalid.invalidConfirmPwd=true:this.invalid.invalidConfirmPwd=false;
      
      if(!this.invalid.invalidConfirmPwd&&!this.invalid.invalidMail&&!this.invalid.invalidPwd&&!this.invalid.invalidUser){
        let self=this;
        let writtenType="";
        this.accountType==0?writtenType="admin":writtenType="utente";
        //window.alert(writtenType);
        $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletSignUp",
            {
              type:writtenType,
              username:self.user,
              password:self.pwd
            },
            function(response){
            if(response=="duplicated"){
              self.showLabel=true;
              self.invalid.invalidUser=true;
            }
            else
            {
              self.invalid.invalidUser=false;
              self.showLabel=false;
            }
            if(response=="OK")
            {
              self.$router.push("/guest");
            }
        })
      }
    }
  }
}
</script>
