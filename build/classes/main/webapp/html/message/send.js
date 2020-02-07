var templateSrc = $('#message-template').html(),
  messageGenerator = Handlebars.compile(templateSrc),
  form = $('#heun-message'),
  param = location.href.split('?')[1];

if (param) {
  var no = param.split('=')[1];
}

$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  loadMessage(no);
  loadProfile(no);
})


function loadProfile(pn) {
  $.getJSON('../../app/json/rev/detail?no=' + pn,
    function (obj) {

      $("<img class='rounded-circle'>").attr('src',
        '/heunheuntrip/app/json/images/down/' + obj.rev.thumbnail)
        .css('width', '255px')
        .css('height', '255px')
        .appendTo($('#profileimg'));

      $('.house-title').text(obj.rev.rmsName);
      $('.sidcheck-in').text(obj.rev.checkIn);
      $('.sidcheck-out').text(obj.rev.checkOut);
      $('.sid-person').text(obj.rev.revPerson + "명");
      $('.sid-charge').text(obj.rev.revCharge + "원");
      $('.house-title').attr('data-no', obj.rev.no);
    });
}

function loadMessage(no) {
  $.getJSON('../../app/json/hostqna/hostqnalist?no=' + no,
    function (obj) {

      console.log(obj)

      for (var l of obj.list) {
        if (obj.loginUserNo != l.userNo) {
          l.sort = false;
          continue;
        }
        l.sort = true;
      }
      
      if(obj.userPhoto !== undefined){
         for(var l of obj.list) {
           l.yourphoto = obj.userPhoto;
         }
      } else if (obj.hostPhoto !== undefined) {
        for(var l of obj.list){
          l.yourphoto = obj.hostPhoto;
        }
      }

      console.log(obj);

      form.html('');
      $(messageGenerator(obj)).appendTo(form);
      $(document.body).trigger('loaded-list');
    });
}

$(document.body).on('loaded-list', function() {

  $('#sending-message').off('click').on('click', function(){

    $.ajax({
      url: '../../app/json/hostqna/add',
      type: 'POST',
      data: {
        content: $('.message-box').val(),
        revNo: $('.house-title').attr('data-no')
      },
      dataType: 'json',
      success: function (response) {
      
        Swal.fire(
            'Success!',
            '성공적으로 등록됐어요.',
            'success'
          ).then(() => {
            $('.message-box').val('');
            loadMessage($('.house-title').attr('data-no'));
          })
          
      },
      fail: function (error) {
        alert('시스템 오류가 발생했습니다.');
      }
    });
  })
})