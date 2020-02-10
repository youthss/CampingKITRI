"use strict"
$.holdReady(true);
(function ($) {
  $.ajax({
    url: '/heunheuntrip/app/json/auth/authCheck',
    type: 'GET',
    dataType: 'json',
    success: function (response) {

      if (response.auth == "관리자") {
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

$(document).on('load', function () {
  Loadlist(0);
})

function Loadlist(pn) {
  $.ajax({
    url: '/heunheuntrip/app/json/loginlog/list?pageNo=' + pn,
    type: 'GET',
    data: {
    },
    dataType: 'json',
    success: function (response) {
      // pageNo = response.pageNo;
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










