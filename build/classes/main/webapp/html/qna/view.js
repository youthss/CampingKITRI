var param = location.href.split('?')[1],
    templateSrc = $('#tr-template').html(),
    category = $('#categorylist').html(),
    cateGenerator = Handlebars.compile(category),
    re = $('#reply').html(),
    reGenerator = Handlebars.compile(re),
    trGenerator = Handlebars.compile(templateSrc);


function loadCategory() {
  $.getJSON('../../app/json/qna/categorylist', function(obj) {
    $(cateGenerator(obj)).appendTo('.heun-category');
    $(document.body).trigger('loaded-cate');
  }); 
};
 
$(document.body).on('loaded-cate', function() {
  $('.heun-category > a').on('click', function() {
    $('#dropdownMenuButton').html($(this).html());
    $('#dropdownMenuButton').attr('data-no', $(this).attr('data-no'));
  });
})

$(document).ready(function(){

  $('#heun-header').load('../header.html', function(){
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  });

  $('#heun-footer').load('../footer.html', function(){
  });

  if (param) {
    loadData(param.split('=')[1])
  }

  loadCategory();
});



function loadData(no) {

  $("#cont").on('keydown keyup', function () {
    $(this).height(1).height( $(this).prop('scrollHeight')+12 );  
  });

  $.getJSON("../../app/json/qna/detail?no=" + no, function(data) {
    $('#no').val(data.qna.qnaNo);
    $('#userNo').html(data.qna.name + '(' + data.qna.auth + ')');
    $('#cateName').html(data.qna.category);
    $('#cateName').attr('data-no', data.qna.categoryNo);
    $('#title').html(data.qna.title);
    $('#auth').val(data.auth);
    $('#cont').html(data.qna.content);
    $('#createdDate').html(data.qna.createdDate);
    $('#title').attr('data-parent', data.qna.parent);
    $('#title').attr('data-order', data.qna.order);
    $('#title').attr('data-step', data.qna.step);
    $('#title').attr('data-uno', data.qna.userNo);

    if($('#title').attr('data-uno') != data.userNo) {
      $('#delete-btn').hide();
      $('#update-btn').hide();
    }
    
    if($('#auth').val() !== "관리자"){
      $('#re-btn').hide();
    }
  });
};

$('#delete-btn').on('click', function() {

  $.ajax({
    url: '../../app/json/qna/delete?no=' + $('#no').val() + '&parent=' + $('#title').attr('data-parent') +
    '&order=' + $('#title').attr('data-order'),
    type: 'get',
    dataType: 'json',
    success: function(response) {
      location.href = 'index.html';
    },
    fail: function(error) {
      alert('삭제 실패!!' + data.message);
    }
  });
})



$('#re-btn').on('click', async function() {

  $(reGenerator()).appendTo('#reply-form');

  $('#summernote').summernote({
    placeholder: '답변을 작성해주세요.',
    tabsize: 2,
    height: 400
  });

  $('#re-btn').hide();

  $('#re-add-btn').on('click', function() {
    
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
        
        $.ajax({
          url: '../../app/json/qna/add',
          type: 'POST',
          data: {
            categoryNo: $('#cateName').attr('data-no'),
            title: $('#re-title').val(),
            content: markupStr,
            password: $('#re-password').val(),
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
})


$('#update-btn').on('click', function() {
  location.href = 'update.html?no=' + $('#no').val(); 
});



