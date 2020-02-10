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

"use strict"
var reheader = 0;

$(document).ready(function () {
  
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });

  $('p').hide();
  loadProfile();
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
})

$('#btn1').on('click', function(e) {
  e.preventDefault();
  update();

})

$('#fileupload').fileupload({
  url: '/heunheuntrip/app/json/member/updateprofile',        // 서버에 요청할 URL
  dataType: 'json',         // 서버가 보낸 응답이 JSON임을 지정하기
  singleFileUploads: false, // 한 요청에 여러 개의 파일을 전송시키기.  
  autoUpload: false,
  previewMaxWidth: 100,   // 미리보기 이미지 너비
  previewMaxHeight: 100,  // 미리보기 이미지 높이 
  previewCrop: true,      // 미리보기 이미지를 출력할 때 원본에서 지정된 크기로 자르기
  processalways: function (e, data) {
    var imagesDiv = $('#images-div');
    imagesDiv.html("");
    for (var i = 0; i < data.files.length; i++) {
      try {
        if (data.files[i].preview.toDataURL) {
          console.log(data.files[0].preview.toDataURL);
          $("<img>").attr('src',
              data.files[i].preview.toDataURL())
              .css('width', '100px')
              .appendTo(imagesDiv);
          $('p').show();
          // 자신이 선택한 파일 이름이 나오게 만듬
          $('.custom-file').find('label').html(data.files[i].name)
        }
      } catch (err) { }
    }
    $(document.body).trigger('loaded-file');
    //$('#file-btn1').unbind("click");

    $('#file-btn1').click(function () {
      // 파일버튼을 클릭했을때 필수값들의 다 들어오면 submit을 호출한다.
      data.formData = {
          name: $('.name').val(),
          tel: $('#tel').val()
      };
      data.submit();
    });
  },
  done: function (e, data) { // 서버에서 응답이 오면 호출된다. 각 파일 별로 호출된다.
    $.each(data.result.files, function (index, file) {
      $('<p/>').text(file.filename + " : " + file.filesize).appendTo(document.body);
    });
    Swal.fire({
      type: 'success',
      title: " 변경  !"
    }).then((result) => {
      if (result.value) {
        location.href = '../hostPage/host_profile.html'
      }
    })
  }
}); //fileupload

$("#fileupload").change(function (e) {
  // 기본 버튼을 숨김
  $('#btn1').hide();
  // 파일 전용 버튼을 나오게만듬 
  $('#file-btn1').show();
})

$('body').on('loaded-file', function () {
  $('#images-div').on('click', function () {
    $(this).find('img').remove();
    $('.custom-file').find('label').html('파일을 선택하세요')
    $('#btn1').show();
    $('p').hide();
    $('#file-btn1').hide();
  })
})

$('#tel-btn').click(function() {

  var tel = $('#tel').val();

  if (!tel) {
    alert('번호를 입력하거나 로그인 해주세요.');
    return;
  }

  $.getJSON('/heunheuntrip/app/json/member/sms?tel=' + tel, function(res) {
    if (res.status === "fail") {

      Swal.fire({
        type: 'error',
        title: res.message
      })

    } else {

      Swal.fire({
        type: 'success',
        title:"인증번호를 보냈습니다!"
      }).then((result) =>{
        if(result.value){
          $('#sms-tag').html('');
          var tag = '<div class="row form-group has-feedback in-line">' +
          ' <div class="col col-lg-8">' +
          '   <input class="form-control form-control-lg required" type="text" data-name="전화번호" name="tel" id="sms-number" /> ' +
          ' </div>' +
          ' <div class="col col-lg-4">' +
          '   <button type="button" class="btn btn-primary btn-lg" id="smstel-btn">인증하기</button>' +
          ' </div>' +
          '</div>';
          $('#sms-tag').append(tag);
          $(document).trigger('sms-load');
        } 
      });
    }
  })
})

$(document).on('sms-load', function() {
  $('#smstel-btn').click(function() {
    var number = $('#sms-number').val();
    if (!number) {
      alert('번호를 입력해주세요.');
      return;
    }
    $.getJSON('/heunheuntrip/app/json/member/smsConfirm?number=' + number, function(res) {
      console.log(res);
      if (res.status === "fail") {
        alert('인증번호가 틀렸거나 60초가 지났습니다.');
        return;
      } 
      $('#sms-tag').html('');

      Swal.fire({
        position: 'center',
        type: 'success',
        title: '인증 성공!',
        showConfirmButton: false,
        timer: 1500
      })
    })
  })
})

function update() {
  var data = {
      name: $('.name').val(),
      tel: $('#tel').val()
    };
  
  $.ajax({
    url: '/heunheuntrip/app/json/member/update',
    type: 'POST',
    data: data,
    dataType: 'json',
    success: function (response) {
      if (response.status == 'success') {
        location.href = '../hostPage/host_profile.html';
      } else {
        Swal.fire({
          type: 'error',
          title: '변경실패!',
          text: '프로필을 변경하지 못했습니다.'
        })
      }
    },
    fail: function (error) {
      alert('시스템 오류가 발생했습니다.');
    }
  });
}






