$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  $(document.body).trigger("facebook");
})

if (window.localStorage.getItem('email')) {
  document.querySelector('#email').value = localStorage.email;
}

$('body').on('facebook', function () {
  $('#face-btn').on('click', function(){
    FB.login(function(response) {
      if (response.authResponse) {
          access_token = response.authResponse.accessToken; //get access token
          user_id = response.authResponse.userID; //get FB UID
          checkLoginState();
      }
  }, {scope: 'email,public_profile,user_birthday',
      return_scopes: true});

  })
})

$("#password").keydown(function(key) {
  if (key.keyCode == 13) {
    HeLogin();
  }
});

$("#email").keydown(function(key) {
  if (key.keyCode == 13) {
    HeLogin();
  }
});
  
  document.querySelector('#login-btn').onclick = () => {
    HeLogin();
  };

function HeLogin() {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '../../app/json/auth/login', true);
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  var email = document.querySelector('#email').value;
  var password = document.querySelector('#password').value;
  var sns_no = 0;
  if (document.querySelector('#remember_me:checked') != null) {
    localStorage.email = email;
  } else {
    window.localStorage.removeItem('email');
  }
  var qs = 'email=' + email + '&password=' + password + '&sns_no=' + sns_no;
  xhr.send(qs);
  xhr.onreadystatechange = function () {
    if (xhr.readyState != 4 || xhr.status != 200)
      return;
    var data = JSON.parse(xhr.responseText);
    if (data.status == 'success') {
      location.href = '../index.html';
    } else {
      Swal.fire({
        type: 'error',
        title: "없는 이메일이거나 비밀번호가 맞지 않습니다."
      });
    }
  };
}

var naverLogin = new naver.LoginWithNaverId(
    {
      clientId: "9GsCl1_fOwukSDhiTmng",
      callbackUrl: "http://team5.bitcamp.co.kr:8080/heunheuntrip/html/auth/callback.html",
      isPopup: false, /* 팝업을 통한 연동처리 여부 */
      loginButton: { color: "grean", type: 3, height: 40 } /* 로그인 버튼의 타입을 지정 */
    }
);
/* 설정정보를 초기화하고 연동을 준비 */
naverLogin.init();