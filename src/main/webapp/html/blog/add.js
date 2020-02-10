var checkout = $('#checkoutlist').html(),
    checkoutGenerator = Handlebars.compile(checkout),
    filenames = [];

$(document).ready(function () {

  $('#heun-header').load('../header.html', function () {
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  });

  $('#heun-footer').load('../footer.html', function () {
  });

  loadCheckOut();

  $('#summernote').summernote({
    placeholder: 'Hello bootstrap 4',
    tabsize: 2,
    height: 400,
    focus: true,
    callbacks: {
      // 섬머노트 에디터에서 파일을 업로드 할때마다 실행되는 함수이다. sendFile() 함수를 호출하여 파일정보를 넘겨준다.
      onImageUpload: function (files, editor, welEditable) {
        for (var i = files.length - 1; i >= 0; i--) {
          sendFile(files[i], false, this);
        }
      }
    }
  });

})

// 파일과 섬네일인지 아닌지 정보를 받아서 ajax 요청하여 파일정보를 넘겨준다.
function sendFile(file, isMain, el) {
  var form_data = new FormData();

  form_data.append('file', file);
  form_data.append('isMain', isMain);
  
  $.ajax({
    data: form_data,
    type: "POST",
    url: '../../app/json/blog/addfile',
    cache: false,
    contentType: false,
    enctype: 'multipart/form-data',
    processData: false,
    success: function(url) {
      // el(태그) 가 파라미터로 넘어오면 true 값이 없으면 false
      if (el) {
        var path = '/heunheuntrip/app/json/images/down/' + url
        $(el).summernote('editor.insertImage', path);
        $('#imageBoard > ul').append('<li><img src="'+path+'" width="480" height="auto"/></li>');
        filenames.push(url);
      } else {
        filenames.push(url);
      }
    }
  });
}

function loadCheckOut() {
  $.getJSON('../../app/json/blog/roomCheckOut', function (obj) {

    $('#title').attr("data-no", obj.userNo);
    $('#title').attr("data-rno", obj.list[0].rmsNo);

    $(checkoutGenerator(obj)).appendTo('.heun-checkout');
    $(document.body).trigger('loaded-checkout');
  });
};

function check_input() {
  dropdown = $('.heun-cc').html();
  summernote = $('#summernote').summernote('code');
  title = $('#title').val();

  if (title == undefined || title == "") {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '제목을 반드시 입력해주세요!',
    })
    return "error";
  } else if (dropdown == '어느 숙소에 방문하셨나요?') {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '방문하신 숙소를 반드시 선택해주세요.',
    })
    return "error";
  } else if (summernote == '<p><br></p>') {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '내용을 반드시 입력해주세요.',
    })
    return "error";
  }
}

$(document.body).on('loaded-checkout', function () {
  $('.heun-checkout > a').on('click', function () {
    console.log(this);
    $('#dropdownMenuButton').html($(this).html());
    $('#dropdownMenuButton').attr('data-no', $('#title').attr('data-no'));
  });
})

$('#error-btn').on('click', function () {
  Swal.fire({
    type: 'error',
    title: '필수 입력사항이 비었습니다.',
    text: '메인이미지를 반드시 업로드해주세요.',
  })
});


$('#add-btn').click(function () {

  if (check_input() == "error") {
    return;
  }
  
  console.log($('#title').val());

  Swal.fire({
    title: '잠깐!',
    text: "후기를 등록하시겠어요?",
    type: 'question',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '네, 등록하겠습니다!',
    cancelButtonText: '아뇨, 다시 한번 볼게요!'
  }).then((result) => {

    if (result.value) {

      $.ajax({
        url: '../../app/json/blog/add',
        type: 'POST',
        data: {
              title: $('#title').val(),
              content: $('#summernote').summernote('code'),
              rmsNo: $('#title').attr('data-rno'),
              filenames: filenames
        },
        dataType: 'json',
        success: function (response) {
        
          Swal.fire(
              'Success!',
              '당신의 후기가 성공적으로 등록됐어요.',
              'success'
            ).then(() => {
              location.href = "index.html";
            })
            
        },
        fail: function (error) {
          alert('시스템 오류가 발생했습니다.');
        }
      });
    }
  })
  
})

$('#fileupload').on('change', function (e) {

  var files = e.target.files;

  $.each(files, function (index, file) {
    $('.custom-file-label').html(file.name);
  });
  
  sendFile(files[0], true);
})
