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

var form = $('.item-listing'),
templateSrc = $('#tr-template').html(),
trGenerator = Handlebars.compile(templateSrc),
rating = 0,
no = 0,
paginateSrc = $('#page-template').html();

Handlebars.registerHelper('paginate', paginate);
var pageGenerator = Handlebars.compile(paginateSrc);



$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  $(document.body).trigger('loaded-list');
  loadProfile();
 
  
}) 

$(document).on('load',function() {
	Loadroomlist(0);
})
"use strict"
function Loadroomlist(pn) {
  
  $.ajax({
    url: '/heunheuntrip/app/json/room/hostroom?pageNo=' + pn,
    type: 'GET',
    data: {
      no: window.no
    },
    dataType: 'json',
    success: function (response) {

      console.log(response.status)
      
      if(response.status === "success"){
        
        pageNo = response.pageNo;
        form.html('');
        for (l of response.list) {
          if (l.state == "0") {
            l.state = true;
          } else if (l.state == "1") {
            l.state = false;
          } else {
            l.state = false;
            l.restate = true;
          }
        }
        
        response.pagination = {
            page: response.pageNo,
            pageCount: response.totalPage
        };
        $(trGenerator(response)).appendTo(form);
        $('.pagination-menu').html('');
        $(pageGenerator(response)).appendTo('.pagination-menu');
        
      } else if (response.status === "fail"){
        
        form.html("<div class='row justify-content-md-center'>" +
            "<div class='col col-lg-8' style='margin-top: 20px; color: #777777'>" +
               "<div class='error-template text-center'> <i class='fas fa-exclamation-triangle fa-5x text-success mb50 animated zoomIn'></i>" +
                 "<h5 class='main-title centered'><span>등록한 숙소가 없습니다.</span></h5>" +
                     "<div class='main-title-description'> 흔흔여행에 숙소를 등록해보세요! </div>" +
                   "</div>" +
                 "</div>" +
               "</div>");
      }
      
      $(document.body).trigger('loaded-list');
    },
  });
}

$(document.body).bind('loaded-list', () => {
  $('.del').on('click', function () {
    var no = $(this).data('no')

    Swal.fire({
      title: '잠깐!',
      text: "정말로 삭제 하시겠습니까?",
      type: 'question',
      allowOutsideClick: false,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '삭제',
      cancelButtonText: '취소'
    }).then((result) => {
      if (result.value) {
        $.ajax({
          url: '/heunheuntrip/app/json/room/delete',
          type: 'GET',
          data: {
            no: no
          },
          dataType: 'json',
          success: function (response) {
            Swal.fire({
              type: 'success',
              title: "정상적으로 삭제 되었습니다!"
            }).then((result) => {
              if (result.value) {
                location.href = 'hostlist.html'
              }
            })
          },
          error: function (error) {
            alert('시스템 오류가 발생했습니다.');
          }
        });
      } else {
      }
    })

  })
});

$(document.body).bind('loaded-list', (e) => {
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })
})

$(document.body).bind('loaded-list', (e) => {
  $('.roomcontent').on('click', function () {

    var rno = $(this).attr('data-no')
    detail(rno)
  })
})

$(document.body).bind('loaded-list', (e) => {
  $('.cece-no-msg').on('click', function () {

    var msg = $(this).attr('data-msg')
    $('#message-text').text(msg)
  })
})


function detail(rno){

  $.ajax({
    url: '/heunheuntrip/app/json/room/hostroomdetail',
    type: 'GET',
    data: {
      no:rno
    },
    dataType: 'json',
    success: function (response) {
      //$('.media-heading').text(response.name)
      $('.room-photo').attr('src', "/heunheuntrip/app/json/images/down/" + response.thumbnail);
      $('.room-name').html(response.name)
      $('.room-area').html("<i class='fas fa-subway'></i>  " + response.area)
      $('.room-address').html("<i class='fas fa-map-marker-alt'></i>  "+response.address + " " + response.detailAddress)
      $('.room-price').html("<i class='fas fa-won-sign'></i>  "+response.price + "원")
      $('.room-type').html("<i class='fas fa-home'></i>  " +response.type )
      $('.room-maxperson').html("<i class='fas fa-user'></i> " + "최대" +response.maxPerson + "명")
      $('.room-bath').html("<i class='fas fa-bath'></i> "+ response.bath + "개")
      $('.room-bed').html("<i class=' fa fa-bed'></i> "+response.bed + "개")
      
      
      $('#room-details').html(response.details)
      $('#room-reservation').html(response.reservation)
      $('#room-welcome').html(response.welcome)
      $('#room-traffic').html(response.traffic)
      $('#room-content').html(response.content)
      
      
//      $('.media-left').html('')
//      $("<img class='media-room'>").attr('src',
//          '/heunheuntrip/app/json/images/down/' + response.thumbnail)
//          .css('width', '50px')
//          .css('height', '50px')
//          .appendTo($('.media-left'));
      
      
    },
    error: function (error) {
      alert('시스템 오류가 발생했습니다.');
    }
  });

}








