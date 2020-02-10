var latitude = '';
var longitude = '';
var prevCheck = false;
 
// 모든 슬라이드의 유효성 검사 코드를 객체화
var slideRule = [
  {
    id: 's1',
    ele: $('#s1'),
    rule: [
      {
        name: 'area',
        InputEle: $('#area'),
        Pattern: /^[\s\S]{1,20}$/,
        message: '지역을 입력해주세요. \n(1자 이상 20자 이하)'
      }
    ]
  },
  {
    id: 's2',
    ele: $('#s2'),
    rule: [
      {
        name: 'bed',
        InputEle: $('#bed'),
        Pattern: /^([0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ _]){1,20}$/,
        message: '침대 갯수를 선택해주세요.'
      },
      {
        name: 'bath',
        InputEle: $('#bath'),
        Pattern: /^([0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ _]){1,20}$/,
        message: '욕실 갯수를 선택해주세요.'
      }
    ]
  },
  {
    id: 's3',
    ele: $('#s3'),
    rule: [
      {
        name: 'address',
        InputEle: $('#address'),
        Pattern: /^[\s\S]{1,100}$/,
        message: '주소를 입력해주세요.'
      },
      {
        name: 'detailAddress',
        InputEle: $('#detailAddress'),
        Pattern: /^[\s\S]{1,100}$/,
        message: '상세주소를 입력해주세요'
      }
    ]
  },
  {
    id: 's4',
    ele: $('#s4'),
    rule: [
      {
        name: 'convenience',
        InputEle: $('input[name=convenience]:checked'),
        Pattern: /^([0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ _]){1,20}$/,
        message: '편의시설을 1개 이상 선택해주세요.'
      }
    ]
  },
  {
    id: 's5',
    ele: $('#s5'),
    rule: [
      {
        name: 'safety',
        InputEle: $('input[name=safety]:checked'),
        Pattern: /^([0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ _]){1,}$/,
        message: '안전시설을 1개 이상 선택해주세요.'
      }
    ]
  },
  {
    id: 's7',
    ele: $('#s7'),
    rule: [
      {
        name: 'contents',
        InputEle: $('#contents'),
        Pattern: /^[\s\S]{10,1000}$/,
        message: '10자 이상 500자 이하로 작성해주세요!'
      }
    ]
  },
  {
    id: 's10',
    ele: $('#s10'),
    rule: [
      {
        name: 'heun-name',
        InputEle: $('#heun-name'),
        Pattern: /^[\s\S]{1,20}$/,
        message: '1자 이상 20자 이하로 입력해주세요.'
      }, {
        name: 'heun-price',
        InputEle: $('#heun-price'),
        Pattern: /^([0-9,]){1,20}$/,
        message: '숫자만 입력해주세요.'
      }
    ]
  }
]

// textarea - 숙소설명에 글을 작성할때 글자수를 계산하고 유효성 검사를 한다.
$('#contents').keyup(function (e) {
  var content = $(this).val();
  $('.counter').html((1000 - content.length) + ' 자');

  // textarea의 유효성 검사를 시행한다.
  var idAttr = $(this).attr('id');
  validKeyup($(this), idAttr);
});

// 키업했을때 실행되는 함수
function validKeyup(self, idAttr) {
  var nowEle = {}
  for (var r of slideRule) {
    for (var ele of r.rule) {
      if (ele.name === idAttr) {
        nowEle = ele
        break
      }
    }
  }

  if (!nowEle.InputEle.val() || !nowEle.Pattern.test(nowEle.InputEle.val())) {
    self.removeClass('is-valid');
    self.addClass('is-invalid');
    return false
  } else {
    self.removeClass('is-invalid');
    self.addClass('is-valid');
    self.next('.invalid-feedback').html('')
  }
  return true
}

// 다음 버튼을 눌렀을때 실행
$('.heun-form-next').click(function () {

  // 다음버튼을 누를때마다 해당 페이지에 대해 유효성 검사를 실시한다.
  var idAttr = $(this).closest('.slide').attr('id');
  if (!validNext(idAttr)) {
    return;
  }

  // 숙소사진 등록 페이지면 
  if ($(this).closest('.slide').attr('id') == 's6') {
    // 사진을 한개이상 등록하고 메인사진을 체크안했으면
    if (fileCountCheck || fileMainCheck || fileCount < 2) {
      Swal.fire({
        type: 'error',
        title: '사진 등록 및 메인사진 지정!',
        text: '숙소 사진은 반드시 두장 이상 또는 메인사진을 등록해야 합니다!!! 이미지를 선택하여 메인사진을 지정해주세요.'
      })
      return;
    }
  }

  // 숙소설명 입력 후 선택사항 페이지면
  if ($(this).closest('.slide').attr('id') == 's7') {
    // 선택사항을 입력할건지 창을 띄운다
    Swal.fire({
      title: '정보를 추가로 입력하시겠습니까?',
      text: "자세한 내용을 공유하려면 추가 필드를 작성해주세요.",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes'
    }).then((result) => {

      // Yes를 눌렀을 때
      if (result.value) {
        // 다음 슬라이드로 넘어간다.
        fullpage_api.moveSlideRight();

        // cancel을 눌렀을 때
      } else {
        // 마지막 슬라이드로 넘어간다.
        fullpage_api.moveTo('firstPage', 9);
        prevCheck = true;
      }
    })
    // 이 함수(경고창)를 모두 실행 한뒤 리턴 한다. (마지막 라인이 실행되지 않게)
    return;
  }

  // 검사를 모두 통과하면 다음 페이지로 간다.
  fullpage_api.moveSlideRight();
})

// 다음 버튼을 눌렀을때 실행하는 함수
function validNext(idAttr) {
  var nowSlide = {}
  for (var ele of slideRule) {
    if (ele.id === idAttr) {
      nowSlide = ele
      break
    }
  }

  var inputs = nowSlide.rule;
  var isEmpty = true;
  $(inputs).each(function (i, e) {

    if (!e.InputEle.val() || !e.Pattern.test(e.InputEle.val())) {
      Swal.fire({
        type: 'error',
        title: e.message,
      }).then((value) => {
        setTimeout(function () {
          e.InputEle.focus();
        }, 500)
      });

      e.InputEle.removeClass('is-valid');
      e.InputEle.addClass('is-invalid');

      isEmpty = false;
      return isEmpty;
    }
  })

  if (!isEmpty) {
    return false;
  }
  return true;
}

// 폼 태그 밑에 모든 인풋 태그를 키업 했을때 유효성 검사 한다.
$('#heun-submit input').on('keyup', function () {
  var idAttr = $(this).attr('id')
  validKeyup($(this), idAttr)
})

// 이 페이지가 시작될 때 실행되는 함수
$(document).ready(function () {
  $("#heun-header").load("/heunheuntrip/html/header.html", function () {
    $(".heun-header-nav").removeClass("navbar-over absolute-top");
  });
  $("#heun-footer").load("/heunheuntrip/html/footer.html");

  $('#fullpage').fullpage({
    //options here 
    licenseKey: 'OPEN-SOURCE-GPLV3-LICENSE',
    autoScrolling: false,
    navigationPosition: 'right',
    scrollHorizontally: false,
    loopHorizontal: false,
    controlArrows: false,
    anchors: ['firstPage']
  });

});

// 이전 버튼을 클릭했을 때
$('.heun-form-prev').click(function () {

  //선택사항 입력을 안했을때 이전 버튼을 누르면 숙소설명창으로 간다.
  if (prevCheck) {
    fullpage_api.moveTo('firstPage', 7);
    // 이전버튼을 다시 눌렀을때 이 코드가 실행되지않게 초기화 한다.
    prevCheck = false;
  }

  fullpage_api.moveSlideLeft();
})

$('#post-search').click(function () {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      var extraAddr = ''; // 참고항목 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
      if (data.userSelectedType === 'R') {
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if (data.buildingName !== '' && data.apartment === 'Y') {
          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if (extraAddr !== '') {
          extraAddr = ' (' + extraAddr + ')';
        }
        // 조합된 참고항목을 해당 필드에 넣는다.
        $("#extraAddress").val(extraAddr);

      } else {
        $("#extraAddress").val('');
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      var addressEle = $("#address")
      $('#postcode').val(data.zonecode);
      addressEle.val(addr);
      // 우편변호와 주소 정보를 필드에 넣은 후 주소 인풋창에 통과 되었음을 보여준다.
      addressEle.removeClass('is-invalid');
      addressEle.addClass('is-valid');

      // 커서를 상세주소 필드로 이동한다.
      $("#detailAddress").focus();
    }
  }).open();
})

$('.heun-push').click(function () {
  var api = $.fileuploader.getInstance($('.gallery_media'));
  api.uploadStart();

  $('body').on('file-success', function () {

    var address = $('#address').val();

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new daum.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function (result, status) {
      // 정상적으로 검색이 완료됐으면 
      if (status === daum.maps.services.Status.OK) {
        window.latitude = result[0].y;
        window.longitude = result[0].x;
        $('body').trigger('xy');
      }
    });

    var convenience = [];
    $("input[name=convenience]:checked").each(function () {
      convenience.push($(this).val());
    })

    var safety = [];
    $("input[name=safety]:checked").each(function () {
      safety.push($(this).val());
    })

    $('body').on('xy', function () {

      var price = $('#heun-price').val();
      price = price.replace(/,/gi, '');

      var allData = {
        type: $('#type').val(),
        maxPerson: $('#maxp').val(),
        area: $('#area').val(),
        bed: $('#bed').val(),
        bath: $('#bath').val(),
        postcode: $('#postcode').val(),
        address: address,
        detailAddress: $('#detailAddress').val(),
        content: $('#contents').val(),
        details: $('#details').val(),
        reservation: $('#reservation').val(),
        welcome: $('#welcome').val(),
        traffic: $('#traffic').val(),
        name: $('#heun-name').val(),
        price: price,
        latitude: window.latitude,
        longitude: window.longitude,
        convenience: convenience,
        safety: safety,
        thumbnail: thumbnail,
        files: photos
      }
      
      var isVaild = true;
      var msg = '';
      $.ajax({
        url: '../../app/json/room/add',
        type: 'POST',
        data: allData,
        dataType: 'json',
        success: function (response) {
          if (response.status == 'success') {
            isVaild = true;
            msg = '숙소 등록 성공!';
          } else {
            isVaild = false;
            msg = '숙소 등록 실패!';
          }
        },
        fail: function (error) {
          isVaild = false;
          msg = '숙소 등록 실패!';
        }
      }).always(function() {
        Swal.fire({
          type: isVaild ? 'success' : 'error',
          title: msg
        }).then((result) => {
          if (result.value) {
            location.href = '/heunheuntrip/html/member/hostPage/hostlist.html';
            return;
          }
        })
      });

      var result = '';
      $("input[name=convenience]:checked").each(function () {
        result += $(this).val() + ' ';
      })

      result = '';
      $("input[name=safety]:checked").each(function () {
        result += $(this).val() + ' ';
      })

    })
  })
})

// 키를 눌렀을때
$("#heun-price").keypress(function (event) {
  //숫자만 받기
  if (event.which && (event.which < 48 || event.which > 57)) {
    event.preventDefault();
  }
  // 키를 누르고 뗏을때
}).keyup(function () {
  if ($(this).val() != null && $(this).val() != '') {
    var text = $(this).val().replace(/[^0-9]/g, '');
    $(this).val(comma(text));
  }
});

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