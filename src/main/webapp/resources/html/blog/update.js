var param = location.href.split('?')[1],
blogNo = param.split('=')[1],
filenames = [],
filecheck = false;

$(document).ready(function () {

  $('#heun-header').load('../header.html', function () {
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  });

  $('#heun-footer').load('../footer.html', function () {
  });

  if (param) {
    loadData(blogNo)
  }

  $('#summernote').summernote({
    placeholder: '내용을 입력해주세요.',
    tabsize: 2,
    height: 600,
    focus: true,
    callbacks: {
      onImageUpload: function (files, editor, welEditable) {
        for (var i = files.length - 1; i >= 0; i--) {
          sendFile(files[i], false, this);
        }
      }
    }
  });
})

function loadData(no) {

  $.getJSON("../../app/json/blog/detail?no=" + no, function (data) {
    $('#no').attr('data-no', data.blog.no);
    $('#no').attr('data-title', data.blog.title);
    $('#title').val(data.blog.title);

    var markupStr = data.blog.content;

    $('#summernote').summernote('code', markupStr);

    $(data.blog.photoFiles).each(function (i, e) {
      filenames.push(e.file);
    });

  }); // getJSON의 끝

}

//파일과 섬네일인지 아닌지 정보를 받아서 ajax 요청하여 파일정보를 넘겨준다.
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

function check_input() {
  summernote = $('#summernote').summernote('code');
  title = $('.heun-title').val();

  if (title == undefined || title == "") {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '제목을 반드시 입력해주세요!',
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

$('#update-btn').click(function () {

//  if (check_input() == "error") {
//    return;
//  }

  Swal.fire({
    title: '잠깐!',
    text: "후기를 이대로 변경하시겠어요?",
    type: 'question',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '네, 변경하겠습니다!',
    cancelButtonText: '아뇨, 다시 한번 볼게요!'
  }).then((result) => {

    if (result.value) {

      $.ajax({
        url: '../../app/json/blog/update',
        type: 'POST',
        data: {
          no: blogNo,
          title: $('#title').val(),
          content: $('#summernote').summernote('code'),
          filenames: filenames
        },
        dataType: 'json',
        success: function (response) {
          
          Swal.fire(
              'Success!',
              '당신의 후기가 성공적으로 변경됐어요.',
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
