var form = $('.blog-form-list'),
templateSrc = $('#tr-template').html(),
trGenerator = Handlebars.compile(templateSrc),
i = 1,
pageNo = 0,
totalPage = 0,
order = 0,
deorder = 0,
blike = 0;


//header, footer 가져오기
$(document).ready(function () {
	$('#heun-header').load('../header.html', function () {
		$('.heun-header-nav').removeClass('navbar-over absolute-top');
	});

	$('#heun-footer').load('../footer.html', function () {
	});
})


var isLoadingData = false;

function loadList(pn, order, blike, dedorder) {
	isLoading = true;
	$.getJSON('../../app/json/blog/list?pageNo=' + pn + '&order=' + order + '&blike=' + blike + '&deorder=' + deorder,
			function(obj) {
	  
		console.log(obj)
		
		if (obj.status === "fail") {
		  
		   form.attr('class', 'item-listing blog-form-list')
		  
			 form.html("<div class='row justify-content-md-center'>" +
			          "<div class='col col-lg-8' style='margin-top: 20px; color: #777777'>" +
			             "<div class='error-template text-center'> <i class='fas fa-exclamation-triangle fa-5x text-success mb50 animated zoomIn'></i>" +
			               "<h5 class='main-title centered'><span>블로그 목록이 없습니다.</span></h5>" +
			                   "<div class='main-title-description'> 블로그를 직접 작성해보세요! </div>" +
			                 "</div>" +
			               "</div>" +
			             "</div>");
			 $(document.body).trigger('loaded-list');
			
		} else if (obj.status == "success") {
			pageNo = obj.pageNo;
			totalPage = obj.totalPage
			
			obj.pagination = {
					page: obj.pageNo,
					pageCount: obj.totalPage
			};
			
			$(trGenerator(obj)).appendTo(form);
			
			window.i++;
			
			$(document.body).trigger('loaded-list');
			isLoading = false;
		 }
		
		
	}); // Bitcamp.getJSON(

} // loadList()



//페이지를 출력한 후 1페이지 목록을 로딩한다.
loadList(window.i, 0, 0, 0);

var jDocument = $(document);
var jWindow = $(window);
var jFooter = $('#heun-footer');

$(document).scroll(function(event){

	var documentHeight = jDocument.height();
	var windowHeight = jWindow.height();
	var scrollTop = jDocument.scrollTop(); //스크롤바의 상단위치
    var footerHeight = Math.floor(jFooter.height());
    var currentScrollHeight = Math.floor(scrollTop + jWindow.height());
    var bottomMarginHeight = footerHeight + 100;

	// scrollbar의 thumb가 바닥 전 20px까지 도달 하면 리스트를 가져온다.
	if( (currentScrollHeight +  bottomMarginHeight >= documentHeight) && 
			!isLoading &&
			pageNo < totalPage){
		
		console.log("이벤트발생", totalPage, pageNo);
		console.log(currentScrollHeight, bottomMarginHeight);
		//console.log(window.i, window.order, window.blike, window.deorder);
		loadList(window.i, window.order, window.blike, window.deorder);
		var cut = window.i;
		
	  }
   })	



//필터기능  
$('.heun-search > a').on('click', function() {

	$('.searchselect').html($(this).html());


	window.i= 1;
	form.html('');


	if($('.searchselect').html() == "최신순") {
		console.log("최신순")

		window.deorder = 0;
		window.blike = 0;
		window.order = 1;

		loadList(window.i, window.order, window.blike, window.deorder);
	} 

	if($('.searchselect').html() == "인기순") {
		console.log("인기순");

		window.deorder = 0;
		window.blike = 1;
		window.order = 0;

		loadList(window.i, window.order, window.blike, window.deorder);      
	} 

	if($('.searchselect').html() == "오래된순") {
		console.log("오래된순");

		window.deorder = 1;
		window.blike = 0;
		window.order = 0;

		console.log(window.i);
		loadList(window.i, window.order, window.blike, window.deorder);      
	} 

})


$(document.body).bind('loaded-list', () => {

	$('.bit-view-link').off('click').on('click', function (e) {
		e.preventDefault();
		console.log($(this))
		window.location.href = 'view.html?no=' + $(this).data('no');
	})

	$('.check-btn').on('click', function (e) {
		
		e.preventDefault();
	
		$.ajax({
			url: '../../app/json/blog/checkUser',
			type: 'GET',
			dataType: 'json',
			success: function (response) {
				if (response.status == 'success') {

					location.href = 'add.html';

				} else {
					Swal.fire({
						type: 'error',
						title: '에러!',
						text: '체크아웃 목록이 없어 블로그를 작성할 수 없습니다.'
					})
				}
			},
			fail: function (error) {
				alert('시스템 오류가 발생했습니다.');
			}
		});
	});
});
