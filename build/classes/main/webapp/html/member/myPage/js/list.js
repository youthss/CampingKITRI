$.holdReady(true);
(function($) {
  $.ajax({
    url: '/heunheuntrip/app/json/auth/authCheck',
    type: 'GET',
    dataType: 'json',
    success: function (response) {
      
      if(response.auth == "일반회원"){
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
rmsNo = 0,
paginateSrc = $('#page-template').html();

Handlebars.registerHelper('paginate', paginate);
var pageGenerator = Handlebars.compile(paginateSrc);


$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  loadList(1);
  loadProfile();
})

function loadList(pn) {
  $.getJSON('/heunheuntrip/app/json/rev/listup?pageNo=' + pn, function(obj) {
    
    console.log(obj);
    
    if(obj.status === 'success'){

    form.html('');

    for(l of obj.list){
      if(l.count !== true){
        
        if(l.status === "체크아웃"){
          l.isBtn = true;
        } else {
          l.isBtn = false;
        }

        if(l.status === "결제완료"){

          if(l.revUpdate !== 0) {
            l.isCheck = false;
            l.isupdate = true;
            l.isDelete = false;
          } else if (l.revDelete === 1){
            l.isDelete = true;
            l.isupdate = false;
            l.isCheck = false;
          } else {
            l.isDelete = false;
            l.isupdate = false;
            l.isCheck = true;
          }
        }
        
      }

      l.revCharge = AddComma(l.revCharge);
    }

    console.log(obj.list)

    pageNo = obj.pageNo;

    obj.pagination = {
        page: obj.pageNo,
        pageCount: obj.totalPage
    };

    $(trGenerator(obj)).appendTo(form);

    $('.pagination-menu').html('');
    $(pageGenerator(obj)).appendTo('.pagination-menu');

    } else if(obj.status === "fail"){
      
      form.html("<div class='row justify-content-md-center'>" +
                   "<div class='col col-lg-8' style='margin-top: 20px; color: #777777'>" +
                      "<div class='error-template text-center'> <i class='fas fa-exclamation-triangle fa-5x text-success mb50 animated zoomIn'></i>" +
                        "<h5 class='main-title centered'><span>예약 목록이 없습니다.</span></h5>" +
                            "<div class='main-title-description'> 원하는 숙소를 예약해보세요! </div>" +
                          "</div>" +
                        "</div>" +
                      "</div>");
      
    }
    
    $(document.body).trigger('loaded-list');

  }); 

} // loadList()

function AddComma(data_value) {
  return Number(data_value).toLocaleString('en');
}

$(document.body).bind('loaded-list', (e) => {

  // 타이틀을 눌렀을 때 해당 숙소 디테일 화면으로 넘어간다.
  $('.item-title').off('click').on('click', function(e){

    var rmsNo = $(this).attr('data-rmsNo');

    location.href="/heunheuntrip/html/room/view.html?no=" + rmsNo;

  })

  var now = moment().format('YYYY-MM-DD');

  $('#main-data').dateRangePicker({
    format: 'YYYY-MM-DD',
    autoClose: true,
    inline: true,
    startDate : now,
    container: '#main-calendar', 
    language: 'ko',
    separator : ' ~ ',
    selectForward: true,
    showShortcuts: true,
    customShortcuts: [
      {
        name: '날짜 지우기',
        dates : function() {
        }
      }
      ],
      getValue: function() {
        if ($('.heun-h1').val() && $('.heun-h2').val()) 
          return $('.heun-h1').val() + ' ~ ' + $('.heun-h2').val();
        else 
          return '';
      },
      setValue: function(s, s1, s2) {
        $('.heun-h1').val(s1);
        $('.heun-h2').val(s2);
      }
  }).bind('datepicker-change',function(event,obj) {
  });

  $('.riw-check').off('click').on('click', function(e){
    
    window.rmsNo = $(this).next().data('no');
    
    var pd = $(e.target).parent().parent().parent().next()
        .children().children('.item-details').children().children('.people-data').children().html();
    
    $('rev-reas').val(' ');
    $('.heun-h1').val($(e.target).attr('data-ci'))
    $('.heun-h2').val($(e.target).attr('data-co'))
    
    $('.check-person').attr('data-p', pd);
    $('.check-person').html('인원 ' + pd + '명');
    
    var no = $(e.target).attr('data-revNo');
    
    $('.check-person').attr('data-revNo', no);
    
    });
  
  $('.dropdown-menu > button').on('click', function(e){
    person = $(this).attr('data-p');
    $('.check-person').html($(this).html());
    $('.check-person').removeAttr('data-p');
    $('.check-person').attr('data-p', person);
  })
  
  
  $('.riw-write').off('click').on('click', function(e){
    
    var no = $(this).next().data('no');
    var pn = $(e.target).parent().parent().parent().parent().parent().parent().children('.rev-page').attr('data-page');

    $('#exampleModal').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget) 
      var recipient = button.data('whatever') 
      var modal = $(this)
      modal.find('.modal-title').text('Review')
      modal.find('.modal-body input').val(recipient)
    });
    

    $('.my-rating').starRating({
      totalStars: 5,
      starShape: 'rounded',
      emptyColor: 'lightgray',
      hoverColor: 'gold',
      activeColor: 'gold',
      strokeWidth: 0,
      disableAfterRate: false,
      useGradient: false,
      callback: function(currentRating, $el){
        window.rating = currentRating;
      }
    });
    
    $('.insert-riw').off('click').on('click', function(e){
      
      $.ajax({
        url: '/heunheuntrip/app/json/riw/add',
        type: 'POST',
        data: {
          roomNo: no,
          grd: window.rating,
          contents: $('#message-text').val()
        },
        dataType: 'json',
        success: function(response) {
          loadList(pn);
          $('#message-text').val("");
          $('#exampleModal').modal("hide");
        },
        fail: function(error) {
          alert('등록 실패!!');
        }
      });
      
    })
  })
  
  $('.rev-update').off('click').on('click', function(e){
    
    var roomNo = window.rmsNo;
    
    var no = $(e.target).parent().parent().parent().parent().children()
        .children('.modal-body').children().children('.check-first').children('.check-pp').children('#input-m').attr('data-revNo');
    
    var pn = $(e.target).parent().parent().parent().parent().parent().parent().children('.rev-page').attr('data-page');
    var revNo = $(e.target).attr('data-revNo');
    var checkIn = $('.heun-h1').val();
    var checkOut = $('.heun-h2').val();
    
    var content = $('.rev-reas').val();
    var pers = $('.check-person').attr('data-p');
    
    Swal.fire({
      text: "예약을 변경하시겠습니까?",
      type: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '네',
      cancelButtonText: '아니오'
    }).then((result) => {

      if (result.value) {
        
        $.ajax({
          url: '/heunheuntrip/app/json/rev/update',
          type: 'POST',
          data: {
            revUpdate: no,
            checkIn: checkIn,
            checkOut: checkOut,
            revReason: content,
            revPerson: pers
          },
          dataType: 'json',
          success: function(response) {
            
            $.ajax({
              url: '/heunheuntrip/app/json/rev/updatesms',
              type: 'GET',
              data: {
                roomNo : roomNo
              },
              dataType: 'json',
              success: function(response) {
                Swal.fire(
                    'Success!',
                    '변경 요청이 성공적으로 전송되었습니다.',
                    'success'
                  ).then(() => {
                    loadList(1);
                    $('#leadform').modal("hide");
                    window.rmsNo = 0;
                  })
              },
              fail: function(error) {
                alert('등록 실패!!');
              }
            });
          },
          fail: function(error) {
            alert('등록 실패!!');
          }
        });
        
      }
    })
    
  })
  
  $('.rev-cancel').off('click').on('click', function(e){
    
    var roomNo = window.rmsNo;
    
    var no = $(e.target).parent().parent().parent().parent().children()
    .children('.modal-body').children().children('.check-first').children('.check-pp').children('#input-m').attr('data-revNo');
    var pn = $('.rev-page').attr('data-page');
    
    // 예약 취소 시 호스트에게 문자 메세지로 알림.
    Swal.fire({
      text: "예약을 취소하시겠습니까?",
      type: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '네',
      cancelButtonText: '아니오'
    }).then((result) => {

      if (result.value) {
        
        $.ajax({
          url: '/heunheuntrip/app/json/rev/cancel',
          type: 'POST',
          data: {
            no: no
          },
          dataType: 'json',
          success: function(response) {
            
            if(response.status == "success"){

              $.ajax({
                url: '/heunheuntrip/app/json/rev/cancelsms',
                type: 'GET',
                data: {
                  roomNo : roomNo
                },
                dataType: 'json',
                success: function(response) {
                  Swal.fire(
                      'Success!',
                      '취소 요청이 성공적으로 전송되었습니다.',
                      'success'
                    ).then(() => {
                      loadList(1);
                      $('#leadform').modal("hide");
                      window.rmsNo = 0;
                    })
                }
              });
              
            } else if (response.status == "fail"){
              Swal.fire(
                  '오류 발생!',
                  '예약 취소 요청이 실패하였습니다.',
                  'error')
              }
          }
        });
      }
    
    })
  });
  
  $('.cancel-update').off('click').on('click', function(e){
    
    $('.rev-reas').val('');
    
  })
  
  // 호스트에게 메세지 보내기
  $('.send-message').off('click').on('click', function(e){
    var revNo = $(e.target).parent().attr('data-no');
    location.href="/heunheuntrip/html/message/send.html?no=" + revNo;
 });

});
