var form = $('.card-list'),
templateSrc = $('#tr-template').html(),
trGenerator = Handlebars.compile(templateSrc),
paginateSrc = $('#page-template').html(),
rating = 0;

Handlebars.registerHelper('paginate', paginate);
var pageGenerator = Handlebars.compile(paginateSrc);
 

$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");  
  
  loadProfile();
  loadList(1);
})

// 내 숙소에 회원이 등록한 리뷰를 가지고옴
function loadList(pn) {
  $.getJSON('/heunheuntrip/app/json/riw/listhostMypage?pageNo=' + pn, function(obj) {
    
    if(obj.status === "success"){
      pageNo = obj.pageNo;
      
      form.html('');
      
      console.log(obj)
      
      obj.pagination = {
        page: obj.pageNo,
        pageCount: obj.totalPage
      };
      
      $(trGenerator(obj)).appendTo(form);
      
      $('.pagination-menu').html('');
      $(pageGenerator(obj)).appendTo('.pagination-menu');
      
      console.log(obj)
      //핸들바스에서 reply가 빈문자열이 아닌 놈을 찾아서 버튼을 없앰
      for(var i = 0; i < obj.list.length; i++) {
//    console.log(obj.list[i].no);
        
        var a = obj.list[i].no;
        
        
        if($('#aaa-' + a).attr('data-reply') != '') {
          $('#no-reply-' + a).hide();
        }
      }
    } else if (obj.status === "fail"){
      
      form.html("<div class='row justify-content-md-center'>" +
          "<div class='col col-lg-8' style='margin-top: 20px; color: #777777'>" +
             "<div class='error-template text-center'> <i class='fas fa-exclamation-triangle fa-5x text-success mb50 animated zoomIn'></i>" +
               "<h5 class='main-title centered'><span> 작성한 리뷰가 없습니다.</span></h5>" +
                 "<div class='main-title-description'> 게스트의 리뷰에 답글을 달아보세요! </div>" +
                 "</div>" +
               "</div>" +
             "</div>");
    }
 
    $(document.body).trigger('loaded-list');
 
  }); 
}

$(document.body).bind('loaded-list', (e) => {
 
  $('.riw-update').off('click').on('click', function(e){
  
    var no = $(this).parent().data('no');
    var reply = $(this).attr('data-reply');
    var grd = $(this).attr('data-grd');
    var userNo = $(this).attr('data-userNo');
    var name = $(this).attr('data-name');
    var content = $(this).parent().prev().children().html();

    console.log(grd)
    
    var pn = $(e.target).parent().parent().parent().parent().parent().children('.riw-page').attr('data-page');
    
    $('#exampleModal').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget) 
      var recipient = button.data('whatever') 
      var modal = $(this)
     // modal.find('.modal-title').text('Review');
      modal.find('.modal-body input').val(recipient);
      modal.find('#user-text').text(name + "님의 후기");
      if(grd < 3){
   	  modal.find('.grdic').attr('class','fa fa-angry fa-lg');
      } else if(grd == 3) {
      modal.find('.grdic').attr('class','fa fa-grin-beam fa-lg');
      } else {
      modal.find('.grdic').attr('class','fa fa-grin-hearts fa-lg');  
      }
      modal.find('#message-text').html(content);
      modal.find('#remessage-text').val(reply);
    });
    
    $('.review-rating').starRating({
		totalStars: 5,
		starShape: 'rounded',
		emptyColor: 'lightgray',
		strokeWidth: 0,
		disableAfterRate: false,
		readOnly: true,
		initialRating:grd,
		starSize:20
	});
	
    $('.update-btn').off('click').on('click', function(e){
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
			
		
      $.ajax({
        url: '/heunheuntrip/app/json/riw/reply',
        type: 'POST',
        data: {
          no: no,
          userNo : userNo,
          reply: $('#remessage-text').val()
        },
        dataType: 'json',
        success: function(response) {
        	Swal.fire(
					'Success!',
					'성공적으로 등록됐어요.',
					'success'
				).then(() => {
					 loadList(pn);
			          $('#exampleModal').modal("hide");
				})
         },
        fail: function(error) {
          alert('등록 실패!!');
        }
      });
	
		})
    });
    
      $( "#remessage-text" ).keydown(function(key){
        
        if(key.keyCode == 13) {
          
          $.ajax({
            url: '/heunheuntrip/app/json/riw/reply',
            type: 'POST',
            data: {
              no: no,
              userNo : userNo,
              reply: $('#remessage-text').val()
            },
            dataType: 'json',
            success: function(response) {
              loadList(pn);
              $('#exampleModal').modal("hide");
            },
            fail: function(error) {
              alert('등록 실패!!');
            }
          });
          
        }
        
      });
    
    
  })
  
});

$.holdReady(true);
(function($) {
  $.ajax({
    url: '/heunheuntrip/app/json/auth/authCheck',
    type: 'GET',
    dataType: 'json',
    success: function (response) {
      
      if(response.auth == "호스트"){
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

