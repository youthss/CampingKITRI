var category = $('#categorylist').html(),
  cateGenerator = Handlebars.compile(category);

$(document).ready(function () {
  $('#heun-header').load('../header.html', function () {
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  });

  $('#heun-footer').load('../footer.html', function () {
  });

  $('#summernote').summernote({
    placeholder: '문의사항 분류를 선택하고, 문의하실 내용을 작성해주세요.',
    tabsize: 2,
    height: 400
  }); 

  loadCategory();
})

function loadCategory() {
  $.getJSON('../../app/json/qna/categorylist', function (obj) {
    $(cateGenerator(obj)).appendTo('.heun-category');
    $(document.body).trigger('loaded-cate');
  });
};

$(document.body).on('loaded-cate', function () {
  $('.heun-category > a').on('click', function () {
    $('#dropdownMenuButton').html($(this).html());
    $('#dropdownMenuButton').attr('data-no', $(this).attr('data-no'));
  });
})

// 간단 유효성 검사
function check_input() {
  
  var markupStr = $('#summernote').summernote('code');
  
  categoryNo = $('#dropdownMenuButton').attr('data-no');
  title = $('#title').val();
  content = markupStr;
  
  if (title == undefined || title == "") {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '제목을 반드시 입력해주세요.',
    })
    return "error";
  } else if (categoryNo == undefined) {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '문의 유형을 반드시 선택해주세요.',
    })
    return "error";
  } else if (content == '<p><br></p>') {
    Swal.fire({
      type: 'error',
      title: '필수 입력사항이 비었습니다.',
      text: '내용을 반드시 입력해주세요.',
    })
    return "error";
  } 
}

$('#add-btn').on('click', function () {
  
  if (check_input() == "error") {
    return;
  }
  
  Swal.fire({
    title: '잠깐!',
    text: "등록하시겠어요?",
    type: 'question',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '네, 등록하겠습니다!',
    cancelButtonText: '아뇨, 다시 한번 볼게요!'
  }).then((result) => {

    if (result.value) {

      var markupStr = $('#summernote').summernote('code');
      
      console.log(markupStr);
      
      $.ajax({
        url: '../../app/json/qna/add',
        type: 'POST',
        data: {
          categoryNo: $('#dropdownMenuButton').data('no'),
          title: $('#title').val(),
          content: markupStr,
          password: $('#password').val(),
          parent: $('#title').attr('data-parent'),
          order: $('#title').attr('data-order'),
          step: $('#title').attr('data-step')
        },
        dataType: 'json',
        success: function (response) {
        
          Swal.fire(
              'Success!',
              '성공적으로 등록됐어요.',
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





