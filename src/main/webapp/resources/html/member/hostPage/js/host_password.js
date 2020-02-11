$.holdReady(true);
(function($) {
  $.ajax({
    url: '/heunheuntrip/app/json/auth/authCheck',
    type: 'GET',
    dataType: 'json',
    success: function (response) {
      
      if(response.auth == "호스트"){
        $.holdReady(false);
      } else {
        $('#main').html('');
        Swal.fire({
          type: 'error',
          title: "잘못된 접근입니다!",
          allowOutsideClick: false
        }).then((result) => {
          if (result.value) {
            location.href = '/heunheuntrip/html'
          }
        })
      }
    },
    error: function (error) {
    }
  })
})(jQuery);

$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  loadProfile();
})
"use strict"

$("#n-pwd").keyup(function(){
  var pwd=$(this).val();
  var rePwd = $("#n-pwd-ck").val();
  // 비밀번호 검증할 정규 표현식
  var reg = /^([0-9a-zA-Z_~!@#$%^&*()_+|<>?:{}]){8,16}$/;
  if (reg.test(pwd)) {//정규표현식을 통과 한다면
    $("#pwdRegErr").hide();
    successState("#n-pwd");
    if(pwd != rePwd){
      errorState("#n-pwd-ck");
    }else{
      $("#rePwdErr").hide();
      successState("#n-pwd-ck");
    }
  } else if(!reg.test(pwd) & pwd != rePwd){
    errorState("#n-pwd-ck");
    errorState("#n-pwd");
    $("#pwdRegErr").show();
  }else if (rePwd != pwd) {
    errorState("#n-pwd-ck");
  } else {//정규표현식을 통과하지 못하면
    $("#pwdRegErr").show();
    errorState("#n-pwd");
  }
});

$("#n-pwd-ck").keyup(function () {
  var rePwd = $(this).val();
  var pwd = $("#n-pwd").val();
  // 비밀번호 같은지 확인
  if (rePwd == pwd) {//비밀번호 같다면
    $("#rePwdErr").hide();
    successState("#n-pwd-ck");
  } else {//비밀번호 다르다면
    $("#rePwdErr").show();
    errorState("#n-pwd-ck");
  }
});

function chkValue() {
  // 공통입력폼내의 모든 입력오브젝트
  var inputObjs = $("#signupForm .required");
  var focus;
  if ($("#n-pwd-ck").hasClass('is-valid') &
    !$("#n-pwd").hasClass('is-valid')){
    Swal.fire({
      type: 'error',
      title: "새 비밀번호를 입력해주세요."
    })
  } else if (!$("#n-pwd-ck").hasClass('is-valid') &
    $("#n-pwd").hasClass('is-valid')){
    Swal.fire({
      type: 'error',
      title: "새 비밀번호를 확인해주세요"
    })
  } else if (!$("#n-pwd-ck").hasClass('is-valid') &
      !$("#n-pwd").hasClass('is-valid')){
      Swal.fire({
        type: 'error',
        title: "새 비밀번호를 입력 해주세요"
      })
  } else if ($("#n-pwd-ck").hasClass('is-valid') &
      $("#n-pwd").hasClass('is-valid')){
    
    $.ajax({
      url: '/heunheuntrip/app/json/auth/user',
      type: 'GET',
      dataType: 'json',
      success: function(response) {

        if(response.status == 'success'){
          updatepwd();
          
        } // if문 
      }, //ajax 유저정보요청 sucess 
      error: function(error) {
        alert('시스템 오류가 발생했습니다.');
      }
    });
  }
  }

  $('#btn1').on('click', function () {
    return chkValue()
    })

function successState(sel) {
  $(sel)
  .removeClass("is-invalid")
  .addClass("is-valid")
  .show();

  $("#myForm button[type=submit]")
  .removeAttr("disabled");
};
//에러 상태로 바꾸는 함수
function errorState(sel) {
  $(sel)
  .removeClass("is-valid")
  .addClass("is-invalid")
  .show();

  $("#myForm button[type=submit]")
  .attr("disabled", "disabled");
};

function updatepwd() {
  $.ajax({
    url:'/heunheuntrip/app/json/member/updatepwd',
    type:'POST',
    data:{
      password: $("#n-pwd").val()
    },
    dataType: 'json',
    success: function(response) {
      Swal.fire({
        type: 'success',
        title:"비밀번호 변경이 완료 되었습니다!"
      }).then((result) =>{
        if(result.value){
          $.ajax({
            url:'/heunheuntrip/app/json/auth/logout',
            type:'GET',
            dataType:'json'
          })
          location.href='/heunheuntrip/html/auth/signin.html'
        } 
      });// alert창
    }//success
  })// ajax 요청
}



