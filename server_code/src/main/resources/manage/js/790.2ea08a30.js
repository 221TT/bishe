"use strict";(self["webpackChunkvue3_nb0"]=self["webpackChunkvue3_nb0"]||[]).push([[790],{11790:(e,l,o)=>{o.r(l),o.d(l,{default:()=>U});o(27825),o(19680),o(80078);var a=o(55701),n=o(7280),u=o(19588),t=o(52679),i=o(32259),s=function(e){return(0,a.dD)("data-v-5e879caf"),e=e(),(0,a.Cn)(),e},r={class:"login_view"},v=s((function(){return(0,a._)("div",{class:"outTitle_view"},[(0,a._)("div",{class:"outTilte"},"馒乐意——新中式馒头销售平台登录")],-1)})),d={key:0,class:"list_item"},c=s((function(){return(0,a._)("div",{class:"list_label"}," 账号： ",-1)})),m={key:1,class:"list_item"},f=s((function(){return(0,a._)("div",{class:"list_label"}," 密码： ",-1)})),p=["onKeydown"],g={key:2,class:"list_type"},w={key:3,class:"remember_view"},_={class:"btn_view"};const h={__name:"login",setup:function(e){var l,o=(0,i.oR)(),s=(0,t.iH)([]),h=(0,t.iH)([]),k=(0,t.iH)({role:"",username:"",password:""}),b=(0,t.iH)(""),U=(0,t.iH)(1),y=(0,t.iH)(!0),S=null===(l=(0,a.FN)())||void 0===l?void 0:l.appContext.config.globalProperties,N=function(){if(k.value.username)if(k.value.password){if(s.value.length>1){if(!k.value.role)return null===S||void 0===S||S.$toolUtil.message("请选择角色","error"),void verifySlider.value.reset();for(var e=0;e<h.value.length;e++)h.value[e].roleName==k.value.role&&(b.value=h.value[e].tableName)}else b.value=s.value[0].tableName,k.value.role=s.value[0].roleName;$()}else null===S||void 0===S||S.$toolUtil.message("请输入密码","error");else null===S||void 0===S||S.$toolUtil.message("请输入用户名","error")},$=function(){null===S||void 0===S||S.$http({url:"".concat(b.value,"/login?username=").concat(k.value.username,"&password=").concat(k.value.password),method:"post"}).then((function(e){if(y.value){var l=JSON.parse(JSON.stringify(k.value));delete l.code,null===S||void 0===S||S.$toolUtil.storageSet("loginForm",JSON.stringify(l))}else null===S||void 0===S||S.$toolUtil.storageRemove("loginForm");null===S||void 0===S||S.$toolUtil.storageSet("Token",e.data.token),null===S||void 0===S||S.$toolUtil.storageSet("role",k.value.role),null===S||void 0===S||S.$toolUtil.storageSet("sessionTable",b.value),null===S||void 0===S||S.$toolUtil.storageSet("adminName",k.value.username),o.dispatch("user/getSession"),null===S||void 0===S||S.$router.push("/")}),(function(e){}))},V=function(){var e={page:1,limit:1,sort:"id"};null===S||void 0===S||S.$http({url:"menu/list",method:"get",params:e}).then((function(e){h.value=JSON.parse(e.data.data.list[0].menujson);for(var l=0;l<h.value.length;l++)"是"==h.value[l].hasBackLogin&&s.value.push(h.value[l]);var o=null===S||void 0===S?void 0:S.$toolUtil.storageGet("loginForm");o||(k.value.role=s.value[0].roleName),null===S||void 0===S||S.$toolUtil.storageSet("menus",JSON.stringify(h.value))}))},D=function(){V();var e=null===S||void 0===S?void 0:S.$toolUtil.storageGet("loginForm");e&&(k.value=JSON.parse(e))};return(0,a.bv)((function(){D()})),function(e,l){var o=(0,a.up)("el-radio"),t=(0,a.up)("el-checkbox"),i=(0,a.up)("el-button"),h=(0,a.up)("el-form"),b=(0,a.up)("Vcode");return(0,a.wg)(),(0,a.iD)("div",null,[(0,a._)("div",r,[v,(0,a.Wm)(h,{model:k.value,class:"login_form"},{default:(0,a.w5)((function(){return[1==U.value?((0,a.wg)(),(0,a.iD)("div",d,[c,(0,a.wy)((0,a._)("input",{class:"list_inp","onUpdate:modelValue":l[0]||(l[0]=function(e){return k.value.username=e}),placeholder:"请输入账号",name:"username"},null,512),[[n.nr,k.value.username]])])):(0,a.kq)("",!0),1==U.value?((0,a.wg)(),(0,a.iD)("div",m,[f,(0,a.wy)((0,a._)("input",{class:"list_inp","onUpdate:modelValue":l[1]||(l[1]=function(e){return k.value.password=e}),type:"password",placeholder:"请输入密码",onKeydown:(0,n.D2)(N,["enter","native"])},null,40,p),[[n.nr,k.value.password]])])):(0,a.kq)("",!0),s.value.length>1?((0,a.wg)(),(0,a.iD)("div",g,[((0,a.wg)(!0),(0,a.iD)(a.HY,null,(0,a.Ko)(s.value,(function(e,n){return(0,a.wg)(),(0,a.j4)(o,{key:n,modelValue:k.value.role,"onUpdate:modelValue":l[2]||(l[2]=function(e){return k.value.role=e}),label:e.roleName},{default:(0,a.w5)((function(){return[(0,a.Uk)((0,u.zw)(e.roleName),1)]})),_:2},1032,["modelValue","label"])})),128))])):(0,a.kq)("",!0),1==U.value?((0,a.wg)(),(0,a.iD)("div",w,[(0,a.Wm)(t,{modelValue:y.value,"onUpdate:modelValue":l[3]||(l[3]=function(e){return y.value=e}),label:"记住密码",size:"large","true-label":!0,"false-label":!1},null,8,["modelValue"])])):(0,a.kq)("",!0),(0,a._)("div",_,[1==U.value?((0,a.wg)(),(0,a.j4)(i,{key:0,class:"login",type:"success",onClick:N},{default:(0,a.w5)((function(){return[(0,a.Uk)("登录")]})),_:1})):(0,a.kq)("",!0)])]})),_:1},8,["model"])]),(0,a.Wm)(b,{show:e.isShow,onSuccess:e.success,onClose:e.close,onFail:e.fail},null,8,["show","onSuccess","onClose","onFail"])])}}};var k=o(23927);const b=(0,k.Z)(h,[["__scopeId","data-v-5e879caf"]]),U=b}}]);
//# sourceMappingURL=790.2ea08a30.js.map