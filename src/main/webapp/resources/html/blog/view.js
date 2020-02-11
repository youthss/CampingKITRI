var param = location.href.split('?')[1];


$(document).ready(function () {
  $('#heun-header').load('../header.html', function () {
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  });

  $('#heun-footer').load('../footer.html', function () {
  });

  if (param) {
    loadData(param.split('=')[1]) 
  }
  
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })

});

function loadData(no) {

  
  $("#cont").on('keydown keyup', function () {
    $(this).height(1).height( $(this).prop('scrollHeight')+12 );  
  });

  $.getJSON("../../app/json/blog/detail?no=" + no, function (data) {
    $('#no').attr('data-no', data.blog.no);
    $('#no').attr('data-title', data.blog.title);
    $('#name').html("작성자 : " + data.blog.name);
    $('h1').html(data.blog.title);
    $('#cont').html(data.blog.content);
    $('.tooltip').attr('title', data.blog.rmsAddr + " " + data.blog.rmsDetailAddr);
    $('#createdDate').html("작성일 : " + data.blog.createdDate);
    $('#rmsName').html("방문했던 숙소 : " + data.blog.rmsName);
    $('#grade').html("평점 : " + data.blog.grade);
    var sp = '\u00A0\u00A0';
    $('.likecount').html("좋아요" + sp + data.count);
    
    console.log('좋아요 갯수 === > ' + data.count);

    if(data.loginNo != undefined){
      console.log('유저 로그인 한 상태임..');
      $('#no').attr('data-uno', data.loginNo);
      console.log($('#no').attr('data-uno')); 
    } else {
      console.log('유저 로그인 안 한 상태임..')
      $('.like-btn').hide();
    }

    // 이 게시글이 받은 좋아요 갯수
    console.log(data.count);

    if(data.blog.userNo != data.loginNo){
      $('#delete-btn').hide();
      $('#update-btn').hide();
    }

    loadLike();

  }); // getJSON의 끝

}

function loadLike() {

  // 로그인한 유저일 경우, 
  if($('#no').attr('data-uno') != undefined){

    $.ajax({
      url: '../../app/json/blog/checkView',
      type: 'POST',
      data: {
        userNo: $('#no').attr('data-uno'),      
        boardNo: $('#no').attr('data-no')
      },
      dataType: 'json',
      success: function(response) {
        console.log('like 데이터 생겼는지 확인 점 해바바');
      },
      fail: function(error) {
        alert('확인 실패!');
      }
    });

    // 좋아요를 눌렀을 시 하트가 활성화, 좋아요를 누른 적이 없으면 비 활성화
    $.ajax({
      url: '../../app/json/blog/checkBlike',
      type: 'POST',
      data: {
        userNo: $('#no').attr('data-uno'),      
        boardNo: $('#no').attr('data-no')
      },
      dataType: 'json',
      success: function(response) {

        if(response.blike == 0){
          $('.heart-btn').css("color", "#c0c0c0");
        } else if (response.blike == 1){
          $('.heart-btn').css("color", "red");
        }
      },
      fail: function(error){

      }
    });

  
    // like 버튼을 눌렀을 때 숫자 증가, 감소 + 하트 활성화, 비활성화
    $('.like-btn').on('click', function(){
      
      $('.fa-heart').attr('class', 'fas fa-heart');

      // 좋아요 수를 check한 후, 0이면 +1, 1이면 -1
      $.ajax({
        url: '../../app/json/blog/checkBlike',
        type: 'POST',
        data: {
          userNo: $('#no').attr('data-uno'),      
          boardNo: $('#no').attr('data-no')
        },
        dataType: 'json',
        success: function(response) {
          console.log(response);
          if(response.blike == 0){

            $.ajax({
              url: '../../app/json/blog/increaseLike',
              type: 'POST',
              data: {
                userNo: $('#no').attr('data-uno'),      
                boardNo: $('#no').attr('data-no')
              },
              dataType: 'json',
              success: function(response) {
                $('.heart-btn').css("color", "red");
                console.log('make sure increaseLike');
                
                $('.fa-heart').attr('class', 'fas fa-heart animated heartBeat');
                
                $.ajax({
                  url: '../../app/json/blog/countLike',
                  type: 'POST',
                  data: {   
                    no: $('#no').attr('data-no')
                  },
                  dataType: 'json',
                  success: function(response) {
                    var sp = '\u00A0\u00A0';
                    $('.likecount').html("좋아요" + sp + response.count);
                    console.log(response);

                  },
                  fail: function(error) {
                    alert('변경 실패!!');
                  }
                }); // countLike 
              },
              fail: function(error) {
                alert('변경 실패!!');
              }
            }); // increaseLike 

          } else if (response.blike == 1) {

            $.ajax({
              url: '../../app/json/blog/decreaseLike',
              type: 'POST',
              data: {
                userNo: $('#no').attr('data-uno'),      
                boardNo: $('#no').attr('data-no')
              },
              dataType: 'json',
              success: function(response) {
                $('.heart-btn').css("color", "#c0c0c0");
                console.log('make sure decreaseLike');
               
                $('.fa-heart').attr('class', 'fas fa-heart animated heartBeat');
                
                $.ajax({
                  url: '../../app/json/blog/countLike',
                  type: 'POST',
                  data: {   
                    no: $('#no').attr('data-no')
                  },
                  dataType: 'json',
                  success: function(response) {
                    var sp = '\u00A0\u00A0';
                    $('.likecount').html("좋아요" + sp + response.count);
                    console.log(response);
                    
                    
                  },
                  fail: function(error) {
                    alert('변경 실패!!');
                  }
                }); // countLike 
                
              },
              fail: function(error) {
                alert('변경 실패!!');
              }
            }); // decreaseLike

            
          }
        }, // success의 끝
        fail: function(error) {
          alert('변경 실패!!');
        }
      }); // checkBlike의 끝

    }) // like click 이벤트의 끝
  }
} // loadLike의 끝



// 업데이트 버튼
$('#update-btn').on('click', function(e) {
  e.preventDefault();
  console.log(e.target);
  window.location.href = 'update.html?no=' + $('#no').attr('data-no');
});


// 업데이트 기능
function updateDate() {
  var markupStr = $('#summernote').summernote('code');

  $.ajax({
    url: '../../app/json/blog/update',
    type: 'POST',
    data: {
      no: $('#no').attr('data-no'),      
      title: $('#update-title').val(),
      content: markupStr
    },
    dataType: 'json',
    success: function(response) {
      location.href = 'index.html';
    },
    fail: function(error) {
      alert('변경 실패!!');
    }
  });
}

// 삭제 기능
$('#delete-btn').on('click', function () {

  Swal.fire({
    title: '삭제하시겠어요?',
    text: "당신의 글이 삭제됩니다!",
    type: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '네, 삭제할게요!',
    cancelButtonText: '아니요!'
  }).then((result) => {
    
    if(result.value) {
      
      Swal.fire(
          '삭제!',
          '글이 삭제되었습니다!',
          'success'
      ).then(() => {
        
        $.ajax({
          url: '../../app/json/blog/delete?no=' + $('#no').attr('data-no'),
          type: 'GET',
          dataType: 'json',
          success: function (response) {
            location.href = 'index.html';
          },
          fail: function (error) {
            alert('삭제 실패!!');
          }
        });
        
      })
    }

  })
});
