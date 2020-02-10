var param = location.href.split('?')[1],
    qnaNo = param.split('=')[1],
    category = $('#categorylist').html(),
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
  loadData(qnaNo);
})

// 카테고리 목록을 불러온다.
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

// 업데이트 할 이전 데이터를 불러온다.
function loadData(no) {
    
    $.getJSON("../../app/json/qna/detail?no=" + no, function(data) {
        $('#no').val(data.qna.qnaNo);
        $('#dropdownMenuButton').html(data.qna.category);
        $('#dropdownMenuButton').attr('data-no', data.qna.categoryNo);
        $('#title').val(data.qna.title);
        $('#title').attr('data-parent', data.qna.parent);
        $('#title').attr('data-order', data.qna.order);
        $('#title').attr('data-step', data.qna.step);
        $('#title').attr('data-uno', data.qna.userNo);
        
        var markupStr = data.qna.content;

        $('#summernote').summernote('code', markupStr);
    });
  };

$('#update-btn').on('click', function () {
  
  Swal.fire({
    title: '잠깐!',
    text: "변경하시겠어요?",
    type: 'question',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '네, 변경하겠습니다!',
    cancelButtonText: '아뇨, 다시 한번 볼게요!'
  }).then((result) => {

    if (result.value) {

      var markupStr = $('#summernote').summernote('code');
      
      $.ajax({
        url: '../../app/json/qna/update',
        type: 'POST',
        data: {
          categoryNo: $('#dropdownMenuButton').attr('data-no'),
          title: $('#title').val(),
          content: markupStr,
          password: $('#password').val(),
          qnaNo: qnaNo
        },
        dataType: 'json',
        success: function (response) {
          
          Swal.fire(
              'Success!',
              '성공적으로 변경됐어요.',
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





