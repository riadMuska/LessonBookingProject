<template>
  <CDropdown style="border:0px"><!---->
    <CDropdownToggle placement="bottom-end" style="border:0px" :caret="false"><!--class="py-0"-->
      <CAvatar :src="avatar" size="md"/>
    </CDropdownToggle>
    <CDropdownMenu ><!--class="pt-0"-->
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Account
      </CDropdownHeader>
      <CDropdownItem @click="logout"> <CIcon icon="cil-lock-locked"/> Logout </CDropdownItem>
    </CDropdownMenu>
  </CDropdown>
</template>

<script>
import avatar from '@/assets/images/avatars/8.jpg'
import $ from 'jquery';
export default {
  name: 'AppHeaderDropdownAccnt',
  setup() {
    return {
      avatar: avatar,
      itemsCount: 42,
    }
  },
  methods:{
    logout(){
      let self = this;
      $.post("http://localhost:8080/PROGETTO_TWEB_war_exploded/ServletLogOut;jsessionid="+localStorage.getItem("token"),
          {},
          function(response){
            if(response=="OK"){
              localStorage.clear();
              self.$router.push("/pages/login");
            }
            else if(response=="sessione_inesistente"){
              localStorage.clear();
              self.$router.push("/pages/login");
            }
          })
    },
  }
}
</script>
