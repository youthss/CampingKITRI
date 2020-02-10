var templateSrc = $('#index-template').html(),
    indexGenerator = Handlebars.compile(templateSrc),
    form = $('.item-listing');

$(document).ready(function () {
    $("#heun-header").load("/heunheuntrip/html/header.html", function () {
      $(".heun-header-nav").removeClass("navbar-over absolute-top");
    });
    $("#heun-footer").load("/heunheuntrip/html/footer.html");
    
    loadList();

  })
  


  function loadList() {
    $.getJSON('../../app/json/hostqna/indexlist',
      function (obj) {
  
        console.log(obj.list.length)
        
        if(obj.list.length !== 0) {
          
          for(var l of obj.list){
            if(obj.userAuth === "일반회원"){
              l.usrA = true;
              
            } else if(obj.userAuth === "호스트") {
              l.usrA = false;
            }
          }
          
          form.html('');
          $(indexGenerator(obj)).appendTo(form);
          
        } else if(obj.list.length === 0) {
          
          form.html("<div class='row justify-content-md-center'>" +
              "<div class='col col-lg-8' style='margin-top: 20px; color: #777777'>" +
              "<div class='error-template text-center'> <i class='far fa-envelope-open fa-5x text-success mb50 animated zoomIn'></i>" +
                "<h5 class='main-title centered'><span>메세지 목록이 없습니다.</span></h5>" +
                    "<div class='main-title-description'> 마이페이지 -> 예약에서 메세지를 보내보세요! </div>" +
                  "</div>" +
                "</div>" +
              "</div>")
          
        }
        
        $(document.body).trigger('loaded-list');
      });
  }

  $(document.body).on('loaded-list', function() {

    $('.send-message').off('click').on('click', function(e){
    
      var revNo = $(e.target).attr('data-no');
  
      console.log(revNo);
      location.href="/heunheuntrip/html/message/send.html?no=" + revNo;
      
    })

    $('.room-title').off('click').on('click', function(e){
    
      var roomNo = $(e.target).parent().attr('data-no');
  
      console.log(roomNo);
      location.href="/heunheuntrip/html/room/view.html?no=" + roomNo;
      
    })
    
  })