var param = location.href.split('?')[1],
  form = $('#heun-detail'),
  templateSrc = $('#detail-template').html(),
  listGenerator = Handlebars.compile(templateSrc),
  no = '',
  overlays = [],
  markers = [],
  map,
  jmt,
  myung,
  revHistory = [],
  paramNo;

IMP.init('imp74040300');

if (param) {
  paramNo = param.split('=')[1]
  pageload(paramNo);
}

$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");
});

function pageload(no) {
  $.ajax({
    url: '../../app/json/room/detail?no=' + no,
    type: 'GET',
    dataType: 'json',
    success: function (response) {

      infoLoad(response.address + ' 맛집', function(result) {
        map = maploaded(response, 'daum-map1');
        jmt = result;
        setMarkers(map, jmt);
      });
      infoLoad(response.address + ' 명소', function(result) {
        myung = result;
      });

      response.originprice = response.price;
      var com = comma(String(response.price).replace(/[^0-9]/g, ''));
      response.price = com;

      response.content = tagParse(response.content.split('\n'));

      if (response.details) {
        response.details = tagParse(response.details.split('\n'));
        response.isDetails = true;
      } else {
        response.isDetails = false;
      }

      if (response.reservation) {
        response.reservation = tagParse(response.reservation.split('\n'));
        response.isReservation = true;
      } else {
        response.isReservation = false;
      }

      if (response.welcome) {
        response.welcome = tagParse(response.welcome.split('\n'));
        response.isWelcome = true;
      } else {
        response.isWelcome = false;
      }

      if (response.traffic) {
        response.traffic = tagParse(response.traffic.split('\n'));
        response.isTraffic = true;
      } else {
        response.isTraffic = false;
      }

      var persons = []; 
      for (var i = 1; i <= response.maxPerson; i++) {
        var m = {i: i};
        persons.push(m);
      };
      
      response.persons = persons;

      revHistory = response.reservationHistory;

      form.html('');

      $(listGenerator(response)).appendTo(form);

      $('body').trigger('loaded-list');

    },
    fail: function (error) {
      alert('시스템 오류가 발생했습니다.');
    }
  });
}

function maploaded(data, mapId) {
  var mapContainer = document.getElementById(mapId), // 지도를 표시할 div 
      mapOption = {
        center: new daum.maps.LatLng(data.latitude, data.longitude), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
      };
  // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
  var map = new daum.maps.Map(mapContainer, mapOption);
  // 마커가 표시될 위치입니다 
  var markerPosition = new daum.maps.LatLng(data.latitude, data.longitude);
  // 마커를 생성합니다
  var marker = new daum.maps.Marker({
    position: markerPosition
  });
  // 마커가 지도 위에 표시되도록 설정합니다
  marker.setMap(map);
  return map;
}

function setMarkers(map, infoArr) {
  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "/heunheuntrip/images/marker_place_off.png";

  // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
  var bounds = new daum.maps.LatLngBounds();

  for (var j of infoArr) {

    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new daum.maps.Size(16, 22);

    // 마커 이미지를 생성합니다    
    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new daum.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: j.latlng, // 마커를 표시할 위치
      title: j.place_name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage // 마커 이미지 
    });

    markers.push(marker);

    var content = '<div class="customoverlay">' +
      '  <a href="' + j.place_url + '" target="_blank">' +
      '    <span class="title">' + j.place_name + '</span>' +
      '  </a>' +
      '</div>';

    var overlay = new daum.maps.CustomOverlay({
      content: content,
      // map: map,
      position: marker.getPosition()
    });

    overlays.push(overlay);

    // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
    daum.maps.event.addListener(marker, 'click', openOverlay(marker, overlay, map));

    bounds.extend(j.latlng);
  }
  map.setBounds(bounds);
}

function hideMarkers() {
  for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
  }
  overlays.forEach(e => {
    e.setMap(null);
  })   
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
  return function () {
    closeOverlay();
    var position = overlay.getPosition();
    var moveLatLon = new daum.maps.LatLng(position.Ga, position.Fa);
    var imageSrc = "/heunheuntrip/images/marker_place.png";
    var imageSize = new daum.maps.Size(31, 44);
    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);
    marker.setImage(markerImage);
    map.panTo(moveLatLon);

    overlay.setMap(map);
  }
}

function infoLoad(addr, cb) {
  var places = new daum.maps.services.Places();
  places.keywordSearch(addr, function (result, status) {
    if (status === daum.maps.services.Status.OK) {
      for (var r of result) {
        r.latlng = new daum.maps.LatLng(r.y, r.x);
      }
      cb(result);
    }
  });
}

function tagParse(arr) {
  var content = '';
  for (var c of arr) {
    if (c.length > 0) {
      c = '<p>'.concat(c);
      c = c.concat('</p>');
      content += c;
    }
  }
  return content;
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

