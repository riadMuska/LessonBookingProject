<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="8">
          <CCardGroup>
            <CCard class="p-4">
              <CCardBody>
                <CForm>
                  <h1>Login</h1>
                  <p class="text-medium-emphasis">Sign In to your account</p>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-user" />
                    </CInputGroupText>
                    <CFormInput placeholder="Username"  v-model="user"  v-if="invalid.invalidUser" invalid />
                    <CFormInput placeholder="Username"  v-model="user"  v-else/>
                  </CInputGroup>
                  <CInputGroup class="mb-4">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput type="password" placeholder="Password" autocomplete="current-password" v-model="pwd" v-if="invalid.invalidPwd" invalid />
                    <CFormInput placeholder="Password" type="password"  v-model="pwd"  v-else/>
                  </CInputGroup>
                  <CRow>
                    <CCol :xs="6">
                      <CButton color="primary" class="px-4" @click="login">
                        Login
                      </CButton>
                    </CCol>
                    <CCol :xs="6" class="text-right">
                      <CButton color="link" class="px-0" @click="this.$router.replace('/guest')">
                        Entra come guest  
                      </CButton>
                    </CCol>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
            <CCard class="text-white bg-primary py-5" style="width: 44%">
              <CCardBody class="text-center">
                <div>
                  <h2>myRipetizioni</h2>
                  <p>
                    Questo sito è stato realizzato per la prenotazioni  di lezioni
                  </p>
                  <CButton color="light" variant="outline" class="mt-3">
                    Register Now!
                  </CButton>
                </div>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
</template>

<script>
import $ from 'jquery'

export default {
  name: 'Login',
  data: () => ({
    user: '',
    pwd: '',
    invalid:{
      invalidUser:false,
      invalidPwd:false,
    },
  }),
  mounted() {
    //TODO
  },
  methods: {
    login() {
      if(this.user==''){
        this.invalid.invalidUser=true;
      }
      if(this.pwd==''){
        this.invalid.invalidPwd=true;
      }
      let self = this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/servletLogin",
            {user: this.user, pwd: this.pwd},
            function(response){
              if( response!=null &&response.username!="" && response.username!=null){
                self.invalid.invalidUser=false;
                self.invalid.invalidPwd=false;
                localStorage.clear();
              //window.alert(JSON.stringify(response));
                localStorage.setItem("token", response.token);
                localStorage.setItem("username", response.username);
                localStorage.setItem("ruolo", response.ruolo);
                localStorage.setItem("idUser", response.idUser);
                if(response.ruolo=="admin")
                {
                  self.$router.push("/");
                }
                else {
                  self.$router.push("/guest");
                }
              }
              else{
                self.invalid.invalidUser=true;
                self.invalid.invalidPwd=true;
              }
          })
    },
  },
}
</script>
