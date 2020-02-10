
var auth;

$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
})

var tbody = $('.faq-form'),
  templateSrc = $('#tr-template').html(),
  trGenerator = Handlebars.compile(templateSrc),
  detail = $('#detail').html(),
  detailGenerator = Handlebars.compile(detail);

//JSON 형식의 데이터 목록 가져오기
function loadList(pn) {
  $.getJSON('../../app/json/faq/list',
    function (obj) {
	  if(obj.member == null) {
		  auth = "비회원";
	  } else {
		  auth = obj.member.auth;
	  }
      tbody.html('');
      $(trGenerator(obj)).appendTo(tbody);
     
      
      
      // 데이터 로딩이 완료되면 body 태그에 이벤트를 전송한다.
      $(document.body).trigger('loaded-list');
      $(document.body).trigger('loaded-add');
      $('.faq-list').find('div').css('display', 'none');
      $('.faq-view').find('div').css('display', 'none');
      $('.faq-add').find('div').css('display', 'none')      
      
    }); // Bitcamp.getJSON()
} // loadList()

$('body').on('loaded-list', function () {

	
	
	  if(auth == "일반회원" || auth == "호스트" ){
			$('#addview-btn').hide();
			$('.delete-btn').hide();
			$('.update-btn').hide();
		  } else if (auth =="관리자"){
				$('#addview-btn').show();
				$('.btn').show();
		  }else if(auth=="비회원"){
				$('#addview-btn').hide();
				$('.delete-btn').hide();
				$('.update-btn').hide();
		  }
	  
	
  $('.delete-btn').on('click', function (e) {
    var no = $(e.target).attr('data-no')
    $.getJSON({
      url: '../../app/json/faq/delete?no=' + no,
      success: function (response) {
        location.href = 'index.html';
      },
      fail: function (error) {
        alert('변경 실패!!');
      }
    });
  })
  
  $('.heun-content').on('click', function() {
	  if(auth == "관리자"){
		  $(this).replaceWith(' <textarea class="con heun-content" id="content-' + $(this).data('no') + '" data-no=' + $(this).data('no') + '>' + $(this).text() + '</textarea>')
	  }
  })
  
})

$('body').on('loaded-list', function () {
  $('.update-btn').on('click', function (e) {
    var no = $(this).attr('data-no')
    console.log(no);
    $.post({
      url: '../../app/json/faq/update',
      data: {
        no: no,
        content: $('#content-' + no).val()
      },
      success: function (response) {
        location.href = 'index.html';
      },
      fail: function (error) {
        alert('변경 실패!!');
      }
    });
  })
})

$('body').on('loaded-list', function () {
  $('.faq-list').on('click', function (e) {
	  $('.faq-list').each(function(i, e) {
		  $(e).parents('.faq-pa').find('.faq-list').find('.title').css('color', 'black');
	  })
	  
    //1) ".faq-detail > div" 없으면 서버에서 가져온다..
    //2) ".faq-detail > div" 를 모두 감춘다.
    //3) 현재 이벤트가 발생한 객체의 div 만 보인다.
    // if($(this).css('display') == 'none'){
    //      $('.aa').find('div').css('display', '');
    // }else{
    // }
	  
	  var tag = $(this).parents('.faq-pa').find('.faq-view').find('div');
	  
	  
    if (tag.css('display') == 'none') {
      //$(this).parents('.faq-pa').find('.faq-view').find('div').css('display', 'none')
      $('.faq-view').find('div').css('display', 'none');
      
      tag.css('display', '');
      
      $(this).parents('.faq-pa').find('.faq-list').find('.title').css('color', '#3b8ced')
      $('#addview-btn').hide();
    } else if(auth == "일반회원" || auth == "호스트"){
	   $(this).parents('.faq-pa').find('.faq-view').find('div').css('display', 'none');
      $(this).parents('.faq-pa').find('.faq-list').find('.title').css('color', 'black')
      $('#addview-btn').hide();
      $(this).val('');
    }else if(auth=="관리자"){
    	 $(this).parents('.faq-pa').find('.faq-view').find('div').css('display', 'none');
         $(this).parents('.faq-pa').find('.faq-list').find('.title').css('color', 'black')
         $('#addview-btn').show();
         $(this).val('');
    }else if(auth == "비회원"){
    	 $(this).parents('.faq-pa').find('.faq-view').find('div').css('display', 'none');
         $(this).parents('.faq-pa').find('.faq-list').find('.title').css('color', 'black')  
         $('#addview-btn').hide();
         $(this).val('');
    }
  }); // Bitcamp.getJSON()
})

$('body').on('loaded-add', function () {
  $('#addview-btn').on('click', function () {
    $('.faq-add').find('div').css('display', '');
  })
})
$('#add-btn').on('click', function () {
  var text = $('textarea#content').val();
console.log('실행');

  $.ajax({
    url: '../../app/json/faq/add',
    type: 'POST',
    data: {
      title: $('#title').val(),
      content: text
    },
    dataType: 'json',
    success: function (response) {
    	if (response.status == "success"){
      location.href = 'index.html';
    	} else {
    		alert("잘못된 요청입니다.")
    	}
    },
    fail: function (error) {
      alert('등록 실패!!');
    }
  });
})
loadList(1);
	  $('#add-canbtn').on('click', function () {
	    $('.faq-add').find('div').css('display', 'none');
	  })




















