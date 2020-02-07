var tbody = $('tbody'),
    templateSrc = $('#tr-template').html(),
    trGenerator = Handlebars.compile(templateSrc),
    paginateSrc = $('#page-template').html();

Handlebars.registerHelper('paginate', paginate);

var pageGenerator = Handlebars.compile(paginateSrc);

//JSON 형식의 데이터 목록 가져오기
function loadList(pn) {

  $.getJSON('../../app/json/qna/list?pageNo=' + pn, 
      function(obj) {

    pageNo = obj.pageNo;

    tbody.html('');

    for (l of obj.list) {
      var re = '';
      for (var i = 1; i < l.step; i++) {
        re += 'ㄴ ';
      }
      l.re = re;
    } 
    
    obj.pagination = {
        page: obj.pageNo,
        pageCount: obj.totalPage
    };
    
    $(trGenerator(obj)).appendTo(tbody);
    
    $('.pagination-menu').html('');
    $(pageGenerator(obj)).appendTo('.pagination-menu');
    
//    pagiNation.html('');
//
//    var arr = [];
//    console.log(obj.pageNo);
//    for (var i = 1; i <= obj.totalPage; i++) {
//      arr.push({
//        page : i,
//        active : (obj.pageNo == i ? 'active' : '')
//      });
//    }
//
//    obj.pageNoList = arr;
//
//
//    $(pageGenerator(obj)).appendTo($('.pagination'));
    
//    var resultTag = '<li id="prevPage" class="page-item"><a class="page-link" href="#">이전</a></li>\n';
//    for (var i = obj.firstPage; i <= obj.lastPage; i++) {
//      resultTag += '<li class="page-item ' + (obj.pageNo == i ? "active" : "") + '"><a href="#" class="page-link currPage" data-no="' + i + '">' + i + '</a></li>\n';
//    }
//    resultTag += '<li id="nextPage" class="page-item"><a class="page-link" href="#">다음</a></li>';
//    
//    $('.pagination').html(resultTag);

    // 1페이지일 경우 버튼을 비활성화 한다.
//    if (obj.pageNo == 1) {
//      $('#prevPage').addClass('disabled');
//    } else {
//      $('#prevPage').removeClass('disabled');
//    } 

    // 마지막 페이지일 경우 버튼을 비활성화 한다.
//    if (obj.pageNo == obj.totalPage) {
//      $('#nextPage').addClass('disabled');
//    } else {
//      $('#nextPage').removeClass('disabled');
//    }


    // 데이터 로딩이 완료되면 body 태그에 이벤트를 전송한다.
    $(document.body).trigger('loaded-list');

  }); // Bitcamp.getJSON()

} // loadList()

//페이지를 출력한 후 1페이지 목록을 로딩한다.
loadList(1);

//테이블 목록 가져오기를 완료했으면 제목 a 태그에 클릭 리스너를 등록한다. 
$(document.body).bind('loaded-list', () => {
  // 제목을 클릭했을 때 view.html로 전환시키기
  $('.bit-view-link').click((e) => {
    e.preventDefault();
    window.location.href = 'view.html?no=' + 
    $(e.target).attr('data-no');
  });
  
//  $('#prevPage > a').click((e) => {
//    e.preventDefault();
//    loadList(pageNo - 1);
//  });
//
//  $('#nextPage > a').click((e) => {
//    e.preventDefault();
//    loadList(pageNo + 1);
//  });
//  
//  $('.currPage').on('click', function(e) {
//    e.preventDefault();
//    loadList($(this).attr('data-no'));
//  })
  
  
});







