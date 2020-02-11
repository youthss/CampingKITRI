<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>HeunheunTrip</title>

  <link rel="shortcut icon" href="/app/resources/img/favicon.ico">
  <!-- Bootstrap -->
  <link href="https://fonts.googleapis.com/css?family=Libre+Franklin:100,200,300,400,500,700" rel="stylesheet">

  <link href="/app/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/app/resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
  <link href="/app/resources/lib/animate.css" rel="stylesheet">
  <link href="/app/resources/lib/selectric/selectric.css" rel="stylesheet">
  <link href="/app/resources/lib/swiper/css/swiper.min.css" rel="stylesheet">
  <link href="/app/resources/lib/aos/aos.css" rel="stylesheet">
  <link href="/app/resources/lib/Magnific-Popup/magnific-popup.css" rel="stylesheet">
  <link href="/app/resources/css/style.css?" rel="stylesheet">
  <link href="/app/resources/css/index.css?" rel="stylesheet">
  <link href="/app/resources/css/colors/blue.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Pacifico|ZCOOL+XiaoWei&display=swap&subset=cyrillic" rel="stylesheet">
  <link href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" rel="stylesheet">
  <link rel="stylesheet" href="/app/resources/css/header.css">

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="/app/resources/lib/jquery-3.2.1.min.js"></script>
  <script src="/app/resources/lib/popper.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="/app/resources/lib/bootstrap/js/bootstrap.min.js"></script>
  <script src="/app/resources/lib/selectric/jquery.selectric.js"></script>
  <script src="/app/resources/lib/swiper/js/swiper.min.js"></script>
  <script src="/app/resources/lib/aos/aos.js"></script>
  <script src="/app/resources/lib/Magnific-Popup/jquery.magnific-popup.min.js"></script>
  <script src="/app/resources/lib/sticky-sidebar/ResizeSensor.min.js"></script>
  <script src="/app/resources/lib/sticky-sidebar/theia-sticky-sidebar.min.js"></script>
  <script src="/app/resources/lib/lib.js"></script>
  <script src="/app/resources/js/handlebars.js"></script>
  <script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>
  <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
  <!-- <script type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d91f3d18bd10e8cd72b2f2827dea9f7c&libraries=services"></script> -->
</head>

<body>
  <div id="heun-header"></div>
  <div id="main">
    <!-- header 추가 -->
    <div class="home-search">
      <div class="main search-form v7">
        <div class="container">
          <div class="row justify-content-md-center">
            <div class="col-md-12 col-lg-10">
              <div class="heading">
                <h2 style="font-family: 'Pacifico', cursive; font-weight: normal"> Trip</h2>
                <h3>흔하지만 흔하지않은 여행</h3>
              </div>
              <form onsubmit="return false;">
                <div class="row justify-content-md-center">
                  <div class="col-md-9 col-lg-8">
                    <div class="input-group input-group-lg">
                      <input id="search-value" type="text" class="form-control"
                        placeholder="지역명이나 장소로 검색">
                      <span class="input-group-prepend">
                      <button id="heun-search" class="btn btn-primary btn-lg" type="button">검색</button>
                      </span>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <video class="search-video" autoplay loop width="0" height="0"
          src="http://uilove.in/realestate/listo/preview/img/demo/video.mp4"></video>
      </div>
    </div>
 <div id="content" class="pt0 pb0">
  <div class="feature-box centered">
    <div>
        <div class="row justify-content-md-center">
          <div class="col col-lg-12 col-xl-10">
            <div class="main-title"><span style="font-family: 'ZCOOL XiaoWei', serif; color: #777777">Today's Popular Blog 이 사이트는 내가 도둑질한다 ${pageContext.request.contextPath}, ${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}
            , ${requestScope["javax.servlet.forward.request_uri"]}

</span></div>
            <div class="swiper-container testimonials">
              <div class="swiper-wrapper">
              </div>
<!--              <div class="swiper-button-next"></div>
             <div class="swiper-button-prev"></div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
 </div>
    <button class="btn btn-primary btn-circle" id="to-top"><i class="fa fa-angle-up"></i></button>
    <div id="heun-footer"></div>
  </div>
  
  <script id="tr-template" type="text/x-handlebars-template">
<div class="swiper-slide">
  <div class="content-box">
    <div class="row">
    {{#each list1}}
      <div class="col-6 col-md-4 grid"> 
       <figure class="effect-lily">
        <img src="/heunheuntrip/app/json/images/down/{{mainPhoto}}" alt="thumbnail"/>
         <figcaption>
          <div>
            <h4><span>{{title}}</span></h4>
            <p>Write by {{name}}</p>
          </div>
          <a href="#" class="blog-detail" data-no={{no}}>View more</a>
         </figcaption>     
       </figure>
      </div>
    {{/each}}
     </div>
  </div>
</div>
</div>
<div class="swiper-slide">
  <div class="content-box">
    <div class="row">
    {{#each list2}}
      <div class="col-6 col-md-4 grid"> 
       <figure class="effect-lily">
        <img src="/heunheuntrip/app/json/images/down/{{mainPhoto}}" alt="thumbnail"/>
         <figcaption>
          <div>
            <h4><span>{{title}}</span></h4>
            <p>Write by {{name}}</p>
          </div>
          <a href="#" class="blog-detail" data-no={{no}}>View more</a>
         </figcaption>     
       </figure>
     </div>
    {{/each}}
    </div>
  </div>
</div>
<div class="swiper-slide">
  <div class="content-box">
    <div class="row">
    {{#each list3}}
      <div class="col-6 col-md-4 grid"> 
       <figure class="effect-lily">
        <img src="/heunheuntrip/app/json/images/down/{{mainPhoto}}" alt="thumbnail"/>
         <figcaption>
          <div>
            <h4><span>{{title}}</span></h4>
            <p>Write by {{name}}</p>
          </div>
          <a href="#" class="blog-detail" data-no={{no}} >View more</a>
         </figcaption>     
       </figure>
     </div>
    {{/each}}
    </div>
  </div>
</div>
  </script>
  <script src="/app/resources/js/index.js?ver=1"></script>
</body>
</html>