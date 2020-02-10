<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset='utf-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <title>회원가입</title>
  
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
  <div id='main'>
    <div id='heun-header'></div>
  </div>
  <div id='content'>
    <div class='container'>
      <div class='row justify-content-md-center align-items-center'>
        <div class='col col-md-6  col-lg-7 col-xl-5'>
          <ul class='nav nav-tabs tab-lg' role='tablist'>
            <li role='presentation' class='nav-item'><a class='nav-link' href='signin.html'>로그인</a>
            </li>
            <li role='presentation' class='nav-item'><a class='nav-link active' href='register'>회원가입</a></li>
          </ul>
          <div class='tab-content'>
            <div role='tabpanel' class='tab-pane active' id='login'>
              <form action='' id='signupForm' enctype='multipart/form-data'>

                <div class="form-group has-feedback">
                  <label class="control-label" for="name">이름*</label> 
                  <input class="form-control required" type="text" data-name='이름' name="name" id="name" autocomplete=off /> 
                  <span id="nameErr" style="color: red" class="help-block">잘못된 형식입니다.</span> 
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>
                <div>
                  <label class="control-label" for="email">이메일*</label>
                  <div class="row form-group has-feedback in-line">
                    <div class="col col-lg-8">
                      <input class="form-control required" type="text" data-name='이메일' name="email" id="email" /> 
                      <span id="emailErr" class="help-block">올바른 이메일 형식이 아닙니다. 다시입력해 주세요.</span>
                    </div>
                    <div class="col col-lg-4">
                      <button type='button' class='btn btn-primary btn-lg' id='add-btn' style='font-size: small' disabled="disabled">인증번호 받기</button>
                    </div>
                  </div>
                </div>
                
                <span class="glyphicon glyphicon-ok form-control-feedback" ></span>
                <div class="row form-group has-feedback in-line"id='checkemail' >
                <div class="col col-lg-8">
                  <input class="form-control required" id='play' type="text" placeholder='인증번호를 입력해주세요' data-name='이메일확인' readonly />
                  </div>
                  <div class="col col-lg-4">
                  <button type="button" class='btn btn-primary btn-lg'style='font-size: small' disabled="disabled"id="check-btn">인증 확인</button>
                  </div>
                  <span id="emailErr" class="help-block">인증번호가 올바르지 않습니다. </span>
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                  <label class="control-label" for="pwd">비밀번호*</label> 
                  <input class="form-control required" type="password" data-name='비밀번호' name="pwd" id="pwd" autocomplete="new-password" /> 
                  <span id="pwdRegErr" style="color: red" class="help-block">8~16자 영문 소문자,숫자,특수문자를 사용하세요 </span> 
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                  <label class="control-label" for="rePwd">비밀번호 재확인</label> 
                  <input class="form-control required" type="password" data-name='비밀번호 확인' name="rePwd" id="rePwd" autocomplete=off /> 
                  <span id="rePwdErr" style="color: red" class="help-block">비밀번호와 일치하지 않습니다. 다시 입력해 주세요.</span> 
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>
                
                <div>
                  <label class="control-label" for="email">전화번호</label>
                  <div class="row form-group has-feedback in-line">
                    <div class="col col-lg-8">
                      <input class="form-control required" type="text" data-name='전화번호' name="tel" id="tel" /> 
                    </div>
                    <div class="col col-lg-4">
                      <button type='button' class='btn btn-primary btn-lg' id='tel-btn' style='font-size: small'>인증번호 받기</button>
                    </div>
                  </div>
                  <div id="sms-tag"></div>
                </div>
                
                <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                <div class="row form-group has-feedback in-line" id='checktel'>
                <div class="col col-lg-8">
                  <input class="form-control required" id='playtel' type="text" placeholder='인증번호를 입력해주세요' data-name='전화번호확인' readonly />
                  </div>
                  <div class="col col-lg-4">
                  <button type="button" class='btn btn-primary btn-lg'style='font-size: small' disabled="disabled"id="checktel-btn">인증 확인</button>
                  </div>
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                  <label class="control-label" for="photo">프로필 사진</label>
                  <div class="input-group mb-3">
                    <div class="custom-file">
                      <input type="file" class="custom-file-input" id="fileupload" src='../../images/default.jpg' name="photo" method='post' autocomplete=off aria-describedby="inputGroupFileAddon01"> 
                      <label class="custom-file-label" for="inputGroupFile01">파일을 선택하세요</label>
                    </div>
                  </div>
                  <div id='images-div'></div>
                  <p style="color: red">이메일을 삭제하시려면 이미지를 클릭해주세요</p>
                  <span class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <label id="bank-labal" class="control-label" for="photo" style="display:none">은행*</label>
                <div id='host' class="input-group mb-3">
                  <div class="input-group-prepend">
                    <button class="btn btn-outline-secondary dropdown-toggle" id='nn' type="button"
                      data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">은행</button>
                    <div class="dropdown-menu">
                      <a class="dropdown-item">신한</a>
                      <a class="dropdown-item">기업</a>
                      <a class="dropdown-item">하나</a>
                      <a class="dropdown-item">농협</a>
                      <a class="dropdown-item">우리</a>
                    </div>
                  </div>
                  <input id='bnk_no' type="text" class="form-control" placeholder='-없이 계좌번호를 입력해주세요'
                    aria-label="Text input with dropdown button" onkeydown="banknumber(event);">
                </div>
                <label >*은 필수입력사항입니다.</label>
                <div id='all-btn'>
                  <button type='button' class='btn btn-primary btn-lg' id='btn1'>가입</button>
                  <button type='button' class='btn btn-primary btn-lg' id='file-btn1' style="display: none">가입</button>
                </div>
              </form>
            </div>
          </div>
          <div></div>
        </div>
      </div>
    </div>
  </div>
  <button class='btn btn-primary btn-circle' id='to-top'>
    <i class='fa fa-angle-up'></i>
  </button>
  <div id='heun-footer'></div>
  <script src='/mvc/resources/js/register.js'></script>
</body>
</html>