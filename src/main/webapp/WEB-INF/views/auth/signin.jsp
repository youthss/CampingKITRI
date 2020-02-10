<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>로그인</title>

  <link rel="shortcut icon" href="/mvc/resources/img/favicon.ico">
  <!-- Bootstrap -->
  <link href="https://fonts.googleapis.com/css?family=Libre+Franklin:100,200,300,400,500,700" rel="stylesheet">

  <link href="/mvc/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/mvc/resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
  <link href="/mvc/resources/lib/animate.css" rel="stylesheet">
  <link href="/mvc/resources/lib/selectric/selectric.css" rel="stylesheet">
  <link href="/mvc/resources/lib/swiper/css/swiper.min.css" rel="stylesheet">
  <link href="/mvc/resources/lib/aos/aos.css" rel="stylesheet">
  <link href="/mvc/resources/lib/Magnific-Popup/magnific-popup.css" rel="stylesheet">
  <link href="/mvc/resources/css/style.css" rel="stylesheet">
  <link href="/mvc/resources/css/index.css" rel="stylesheet">
  <link href="/mvc/resources/css/colors/blue.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Pacifico|ZCOOL+XiaoWei&display=swap&subset=cyrillic" rel="stylesheet">
  <link href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" rel="stylesheet">
  <link rel="stylesheet" href="/mvc/resources/css/header.css">

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="/mvc/resources/lib/jquery-3.2.1.min.js"></script>
  <script src="/mvc/resources/lib/popper.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/mvc/resources/lib/bootstrap/js/bootstrap.min.js"></script>
  <script src="/mvc/resources/lib/selectric/jquery.selectric.js"></script>
  <script src="/mvc/resources/lib/swiper/js/swiper.min.js"></script>
  <script src="/mvc/resources/lib/aos/aos.js"></script>
  <script src="/mvc/resources/lib/Magnific-Popup/jquery.magnific-popup.min.js"></script>
  <script src="/mvc/resources/lib/sticky-sidebar/ResizeSensor.min.js"></script>
  <script src="/mvc/resources/lib/sticky-sidebar/theia-sticky-sidebar.min.js"></script>
  <script src="/mvc/resources/lib/lib.js"></script>
  <script src="/mvc/resources/js/handlebars.js"></script>
  <script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>
  <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

  <style>
    @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic&display=swap');
    input[type=password] {
      font-family: 'Nanum Gothic', sans-serif;

    }
  </style>
</head>
<body>
  <div id="main">
    <div id='heun-header'></div>
    <div class="container">
    </div> <!-- main div 종료-->
    <div id="content">
      <div class="container">
        <div class="row justify-content-md-center align-items-center">
          <div class="col col-md-6  col-lg-5 col-xl-4" style="margin-bottom: 40px">
            <div class='a'>
            </div> <!-- a div 종료-->
            <ul class="nav nav-tabs tab-lg" role="tablist">
              <li role="presentation" class="nav-item">
                <a class="nav-link active" href="signin.html">로그인</a>
              </li>
              <li role="presentation" class="nav-item">
                <a class="nav-link" href="register.html">회원가입</a>
              </li>
            </ul>
            <div class="tab-content">
              <div role="tabpanel" class="tab-pane active" id="login">
                <form>
                  <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" id="email" class="form-control form-control-lg" placeholder="이메일을 입력해주세요">
                  </div>
                  <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" class="form-control form-control-lg"
                      placeholder="비밀번호를 입력해주세요">
                  </div>
                  <div class="row">
                    <div class='col col-lg-6 em'>
                      <div class="checkbox">
                        <input type="checkbox" id="remember_me" name="saveEmail">
                        <label for="remember_me">이메일 저장 </label>
                      </div>
                    </div>
                    <div class="col col-lg-6 pw">
                      <p class="text-lg">
                        <a href="forget-password.html">비밀번호를 잊으셨나요?</a>
                      </p>
                    </div>
                  </div>
                  <button type="button" class="btn btn-primary btn-lg" id='login-btn' style='float:right;'>로그인</button>
                </form>
              </div> <!-- tabpanel div 종료-->
            </div> <!-- tab-content 종료-->
            <div></div>
          </div> <!--  col div 종료-->
          <div class="col-md-5 col-lg-4 col-xl-3 sns">
            <div class="socal-login-buttons">
              <a id="kakao-login-btn"></a>
              <div id="naverIdLogin" class='aa'></div>
              <div id="fb-root"></div>
              <a id="face-btn" href = '#' class="btn btn-social btn-block btn-facebook" style="font-size:15px; width:186px; margin-top:10px;"><i class="fab fa-facebook-f"></i> &emsp;페이스북으로 로그인</a>
            </div>
          </div>
        </div> <!-- row div 종료-->
      </div> <!-- container div 종료-->
    </div> <!-- content div 종료-->
  </div>
  <button class="btn btn-primary btn-circle" id="to-top">
    <i class="fa fa-angle-up"></i>
  </button>
  <div id='heun-footer'></div>
  <script src="/mvc/resources/js/kakao.js"></script>
  <script src='/mvc/resources/js/signin.js'></script>
</body>
</html>