var param = location.href.split('?')[1],
form = $('#room-list'),
bookmarks = $('.bookmark-list'),
templateSrc2 = $('#bookmark-template').html(),
listGenerator2 = Handlebars.compile(templateSrc2),
templateSrc = $('#list-template').html(),
listGenerator = Handlebars.compile(templateSrc),
paginateSrc = $('#page-template').html(),
markers = [],
img = {},
points = [],
overlays = [],
memo = undefined,
selector = "",
a=0;

//handlebars에 paginate 함수를 추가한다.
Handlebars.registerHelper('paginate', paginate);
var pageGenerator = Handlebars.compile(paginateSrc);

$(document).ready(function () {
  $('#toggle-filters').sidr({
    side: 'left',
    displace: false,
    renaming: false,
    name: 'sidebar',
    source: function () {
      AOS.refresh();
    },
  });

  $('#heun-header').load('../header.html', function () {
    $('.heun-header-nav').removeClass('navbar-over absolute-top');
  })
  $('#heun-footer').load('../footer.html')

  
  // 페이지가 교체되면서 중복 발생을 차단한것
  if(a == 0) {
	  loadList(0);
	  
	  loadBmark(1);
  }
  
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })


  $('#myModal').on('show.bs.modal', function (e) {

    setTimeout(function () {

      var mapContainer = document.getElementById('modal-map'), // 지도를 표시할 div 
      mapOption = {
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
      };
      var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

      // // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
      // var mapTypeControl = new daum.maps.MapTypeControl();

      // // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
      // // daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
      // map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);

      // // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      // var zoomControl = new daum.maps.ZoomControl();
      // map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

      pointsLoad(map);

    }, 300)
  })
 

});


	$(".ui-select:visible").on('change', function() {   //이벤트가 발생한다. 
	    console.log($(this).val())
	    window.selector = $(this).val();
	    
	    if(selector == "최신순") {
	    	window.a = 1;
	    	loadList(0);
	    } else if (selector == "높은 가격순") {
	    	window.a = 2;
	    	loadList(0);
	    } else if (selector == "낮은 가격순") {
	    	window.a = 3;
	    	loadList(0);
	    } else {
	    	window.a = 4;
	    	loadList(0);
	    }
    });
	

function loadBmark (pn) {
  $.getJSON('../../app/json/bookmark/list',
      function(obj) {
    
    console.log(obj)
    
    if(obj.list == undefined){
      bookmarks.html('');
      
      $(document.body).trigger('loaded-list');
      
    } else {
      
      bookmarks.html('');
      
      $(listGenerator2(obj)).appendTo(bookmarks);
      
      $(document.body).trigger('loaded-list');
      
    }

  }); // Bitcamp.getJSON(
}

function loadList(pn) {
  var url = '../../app/json/room/list?pageNo=' + pn;
  if (param) {
    var query = param.split('&');
    for (var q of query) {
      if (q.indexOf('lati') != -1) {
        url += '&' + q;
      } else if (q.indexOf('longi') != -1) {
        url += '&' + q;
      }
    }
  }

  $.ajax({
    url: url,
    type: 'GET',
    dataType: 'json',
    data: {
        a: window.a
      },
    success: function (response) {
      
      // 북마크 여부
      for(r of response.list) {
        
        var roomNo = r.no;
        var countBM = false;
        
        $.ajax({
          url: '../../app/json/bookmark/count',
          type: 'POST',
          async: false,
          data: {
            roomNo: roomNo
          },
          dataType: 'json',
          success: function(response) {
            if(response.countNo == 1){
              countBM = true;
            }
          },
          fail: function(error) {
            alert('등록 실패!!');
          }
        });
        r.bookmarkC = countBM;
      }
      
      points = [];

      for (r of response.list) {
        var com = comma(String(r.price).replace(/[^0-9]/g, ''));
        r.price = com;
        points.push({
          no: r.no,
          point: new daum.maps.LatLng(r.latitude, r.longitude),
          name: r.name,
          area: r.area,
          thumbnail: r.thumbnail,
          price: com
        });
      }

      response.pagination = {
          page: response.pageNo,
          pageCount: response.totalPage
      };

      form.html('');

      $(listGenerator(response)).appendTo(form);
      
      if(response.loginNo == undefined) {
        $('.save-item').hide();
        $('.bookmark-card').hide();
      }
      
      $('.pagination-menu').html('');

      $(pageGenerator(response)).appendTo('.pagination-menu');

      $(document.body).trigger('loaded-list');

    },
    fail: function (error) {
      alert('시스템 오류가 발생했습니다.');
    }
  });
}

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
  center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
  level: 5 // 지도의 확대 레벨
};

var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

function panTo(latitude, longitude) {
  // 이동할 위도 경도 위치를 생성합니다 
  var moveLatLon = new daum.maps.LatLng(latitude, longitude);
  // 지도 중심을 부드럽게 이동시킵니다
  // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
  map.panTo(moveLatLon);
}

//콤마찍기
function comma(x) {
  var temp = "";

  num_len = x.length;
  // 콤마 찍을 자릿수 설정
  co = 3;
  // 받은 숫자 길이가 0개 이상일때 까지 반복. 즉 한번씩
  while (num_len > 0) {
    // 받은 숫자 길이에 콤마 찍을 자릿수 뺀다 
    num_len = num_len - co;
    // 뺀 길이가 음수면 
    if (num_len < 0) {
      // 콤마 찍을 자릿수 + 받은 자릿수
      co = num_len + co;
      // 받은 자릿수 0 으로 초기화
      num_len = 0;
    }
    // 자릿수 계산하여 temp의 담는다
    temp = "," + x.substr(num_len, co) + temp;
  }
  // 리턴 하기전에 맨 앞 ","를 뺀 나머지 리턴
  return temp.substr(1);
}

$('body').on('loaded-list', function () {

  $('.heun-room').mouseenter(function () {
    $(img).css('border', '');
    img = $(this);
    img.css('border', '2px solid #eee');

    var latitude = $(this).data('latitude');
    var longitude = $(this).data('longitude');

    // 마커가 표시될 위치입니다 
    var markerPosition = new daum.maps.LatLng(latitude, longitude);
    // 마커를 생성합니다
    var marker = new daum.maps.Marker({
      position: markerPosition
    });
    // 기존 마커를 지운다.
    markers.forEach(function (e) {
      e.setMap(null);
    })
    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    // 마커를 배열에 저장한다.		
    markers.push(marker);
    panTo(latitude, longitude);
  })

  
  $('.heun-room').click(function() {
    var no = $(this).data('no');
    location.href = '/heunheuntrip/html/room/view.html?no=' + no;
  })
  
  $('.heun-BMname').click(function() {
    var no = $(this).data('no');
    console.log(no);
    location.href = '/heunheuntrip/html/room/view.html?no=' + no;
  })
  
  
//  if($('.save-item').data('no') == 1) {
//    $('.fa-star').css("color", "#ffe449");
//  } else if ($('.save-item').data('no') == 0){
//    $('.fa-star').css("color", "gray");
//  }
  

  // 찜 기능
  $('.bM-empty').off('click').on('click', function(e){
    
    var no = $(this).parent().children('.heun-room').data('no');
    
    bookmark();
    window.memo = undefined;
    
    async function bookmark(){
      
      Swal.mixin({
        confirmButtonText: '입력',
        showCancelButton: true,
        cancelButtonText: '취소'
      }).queue([
        {
          title: '해당 숙소를 찜 했습니다!',
          type: 'success',
          text: '메모를 추가하시겠습니까?'
        },
        {
          title: '메모',
          input: 'textarea'
        }
        ]).then((result) => {
          if (result.value) {
            memo = result.value[1];
          }
          
          $.ajax({
            url: '../../app/json/bookmark/add',
            type: 'POST',
            data: {
              roomNo: no,
              memo: window.memo
            },
            dataType: 'json',
            success: function(response) {
              
              $(e.target).parent().parent().children(".bM-full").children().addClass('fa fa-star animated flash');
              
              $(e.target).parent().parent().children(".bM-full").css("display","");
              $(e.target).parent().parent().children(".bM-empty").css("display","none");
              
              loadBmark(1);
              
            },
            fail: function(error) {
              alert('등록 실패!!');
            }
          });
          
        })
    }; // bookmark
    
    
  }) // save-item function

    // 찜 삭제기능
  $('.bM-full').off('click').on('click', function(e){
    e.preventDefault();
    
    var no = $(this).parent().children('.heun-room').data('no');
    
    Swal.fire({
      title: '찜 목록에서 제거하시겠습니까?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '삭제',
      cancelButtonText: '취소'
    }).then((result) => {
      if (result.value) {

        $.ajax({
          url: '../../app/json/bookmark/delete',
          type: 'POST',
          data: {
            roomNo: no
          },
          dataType: 'json',
          
          success: function(response) {
            
            Swal.fire(
                'Success!',
                '삭제되었습니다!',
                'success'
              )
              
            $(e.target).parent().parent().children(".bM-empty").children().addClass('fa fa-star animated flash');
              
            $(e.target).parent().parent().children(".bM-full").css("display","none");
            $(e.target).parent().parent().children(".bM-empty").css("display","");
            
            loadBmark(1);
            
          },
          fail: function(error) {
            alert('등록 실패!!');
          }
        });
        
      }
    })
    
  }) // save-item function
  
})


function pointsLoad(map) {
  // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
  var bounds = new daum.maps.LatLngBounds();

  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "/heunheuntrip/images/marker_place_off.png";
  var imageSize = new daum.maps.Size(16, 22);

  // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
  var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

  var i, marker;

  points.forEach(e => {
    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
    marker = new daum.maps.Marker({
      position: e.point,
      image: markerImage,
      map: map
    });

    var content = '<div class="wrap">' +
    '    <div class="info">' +
    '        <div class="title">' + e.name +
    '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' +
    '        </div>' +
    '        <div class="body">' +
    '            <div class="img">' +
    '              <a href="/heunheuntrip/html/room/view.html?no=' + e.no + '">' +
    '                <img src="/heunheuntrip/app/json/images/down/' + e.thumbnail + '" width="73" height="70">' +
    '              </a>' +
    '           </div>' +
    '            <div class="desc">' +
    '                <div class="ellipsis"> $ ' + e.price + '</div>' +
    '                <div class="jibun ellipsis">' + e.area + '</div>' +
    '                <div></div>' +
    '            </div>' +
    '        </div>' +
    '    </div>' +
    '</div>';

    var overlay = new daum.maps.CustomOverlay({
      content: content,
      // map: map,
      position: marker.getPosition()      
    });

    markers.push(marker);
    overlays.push(overlay);

    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    daum.maps.event.addListener(marker, 'click', openOverlay(marker, overlay, map));

    // LatLngBounds 객체에 좌표를 추가합니다
    bounds.extend(e.point);

  });
  map.setBounds(bounds);
}

function closeOverlay() {
  overlays.forEach(e => {
    e.setMap(null);
  })
  markers.forEach(e => {
    var imageSrc = "/heunheuntrip/images/marker_place_off.png";
    var imageSize = new daum.maps.Size(16, 22);
    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);
    e.setImage(markerImage);
  })
}

function openOverlay(marker, overlay, map) {
  return function() {
    closeOverlay(); 
    var position = overlay.getPosition();
    console.log(position.jb, position.ib);
    var moveLatLon = new daum.maps.LatLng(position.Ga, position.Fa);
    var imageSrc = "/heunheuntrip/images/marker_place.png";
    var imageSize = new daum.maps.Size(31, 44);
    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);
    marker.setImage(markerImage);
    map.panTo(moveLatLon);
    overlay.setMap(map);
  }
}