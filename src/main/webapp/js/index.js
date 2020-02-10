var form = $('.swiper-wrapper'),
    templateSrc = $('#tr-template').html(),
    trGenerator = Handlebars.compile(templateSrc);
 
$(document).ready(function () {
  $("#heun-header").load("header", function() {
    $(".heun-search-form").hide();
  });
  $("#heun-footer").load("footer");
  
  loadList();

});

// 블로그 목록을 불러온다.
function loadList() {

  $.getJSON('../app/json/blog/indexList',
      function(obj) {
        
    form.html('');
    
    $(trGenerator(obj.lists)).appendTo(form);

    $(document.body).trigger('loaded-list');
  }); 
  
}

$('#heun-search').click(function (e) {
  search($('#search-value').val());
});

// 검색창에서 엔터를 누를시 클릭이벤트를 발생시킨다.
$('#search-value').keydown(function(key) {
  if (key.keyCode === 13) {
    $('#heun-search').trigger('click');
  }
})


$(document.body).bind('loaded-list', () => {

  
  var swiper = new Swiper('.swiper-container', {
    loop: true,
    centeredSlides: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false,
    },
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
// navigation: {
// nextEl: '.swiper-button-next',
// prevEl: '.swiper-button-prev',
// },
  });
  
  $('.blog-detail').off('click').on('click', function(e){
    e.preventDefault();
    
    var blogNo = $(this).attr('data-no');
    
     location.href="blog/view.html?no=" + blogNo;
    
  })
  
});

