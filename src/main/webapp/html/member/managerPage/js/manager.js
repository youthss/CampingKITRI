$.holdReady(true);
(function($) {
  $.ajax({
    url: '/heunheuntrip/app/json/auth/authCheck',
    type: 'GET',
    dataType: 'json',
    success: function (response) {
       
      if(response.auth == "관리자"){
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

var tbody = $('tbody'),
templateSrc = $('#tr-template').html(),
trGenerator = Handlebars.compile(templateSrc),
paginateSrc = $('#page-template').html();
selector=4,
val='';

//handlebars에 paginate 함수를 추가한다.
Handlebars.registerHelper('paginate', paginate);
var pageGenerator = Handlebars.compile(paginateSrc);


$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
  loadProfile();
 
  
}) 

$(document).on('load',function() {
	Loadmemberlist(0);
})
"use strict"
function Loadmemberlist(pn) {
  
  $.ajax({
    url: '/heunheuntrip/app/json/member/list?pageNo=' + pn,
    type: 'GET',
    data: {
    	selector:window.selector,
    	val:window.val
    },
    dataType: 'json',
    success: function (response) {

        
        pageNo = response.pageNo;
        console.log(tbody);
        tbody.html('');
        response.pagination = {
            page: response.pageNo,
            pageCount: response.totalPage
        };
        $(trGenerator(response)).appendTo(tbody);
        $('.pagination-menu').html('');
        $(pageGenerator(response)).appendTo('.pagination-menu');
        
    },
  });
}
$('.heun-search > a').on('click', function() {

	  $('.searchselect').html($(this).text());

	  $('.search-btn').on('click', function(e){
	    e.preventDefault();
	    console.log("이벤트발생~~~")
	// selector로 사용자가 입력한값이 작성자인지 제목인지 둘다인지를 선택한다.
	    if($('.searchselect').html() == "이름") {
	      window.selector = 1 ;
	      window.val = $('.search-box').val();
	      Loadmemberlist(0);
	    } 
	    if($('.searchselect').html() == "이메일") {
	      window.selector = 2;
	      window.val = $('.search-box').val();
	      Loadmemberlist(0);
	    } 
	    if($('.searchselect').html() == "권한") {
	      window.selector = 3;
	      window.val = $('.search-box').val();
	      Loadmemberlist(0);
	      
	    } 
	  });
	})  









