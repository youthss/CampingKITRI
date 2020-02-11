$(document).ready(function () {
	$("#heun-header").load("/heunheuntrip/html/header.html", function () {
		$(".heun-header-nav").removeClass("navbar-over absolute-top");
	});
	$("#heun-footer").load("/heunheuntrip/html/footer.html");
})

$("#email").keyup(function () {
	var email = $(this).val();
	// 이메일 검증할 정규 표현식
	var reg = /^[A-Za-z0-9_\.\-]+@[A-Za-z\-]+\.[A-Za-z\-]{2,8}$/;
	if (reg.test(email)) {//정규표현식을 통과 한다면
		$("#emailErr").hide();
		successState("#email");
		$('#add-btn').removeAttr("disabled");

	} else {//정규표현식을 통과하지 못하면
		$("#emailErr").show();
		errorState("#email");
		$('#add-btn').attr("disabled", "disabled");
	}
});

// 인증 번호 보내기
$('#add-btn').on('click', function () {
	// 먼저 서버에 사용자가 입력한 이메일을 서버에 보내서 인증번호를 받아옴
	$.ajax({
		url: '../../app/json/member/email',
		type: 'GET',
		data: {
			email: $("#email").val()
		},
		dataType: 'json',
		success: function (response) {
			// 요청이 성공하면 먼저 엘럿창을 띄움
			Swal.fire({
				type: 'success',
				title: "이메일을 확인해주세요."
			})

			// 인증번호 입력창의 readonly를 해제
			$('#play').attr("readonly", false);
			$('#play').val('');

			// 기존 성공상태를 실패상태로 바꿈
			$('#play').removeClass("is-valid")
				.addClass("is-invalid")
				.show();

			$("#play").keyup(function () {
				var play = $(this).val();
				if (play == response.ranNo) {
					$('#play').removeClass("is-invalid")
						.addClass("is-valid")
						.show();
				} else {
					$('#play').removeClass("is-valid")
						.addClass("is-invalid")
						.show();
				}
			});
		},
		fail: function (error) {
			alert('등록 실패!!');
		}
	});
})

// 인증 번호 보내기
$('#re-btn').on('click', function () {
	if ($('#play').hasClass("is-valid")) {
		$.ajax({
			url: '../../app/json/member/resetemail',
			type: 'GET',
			data: {
				email: $("#play").val()
			},
			dataType: 'json',
			success: function (response) {
				// 요청이 성공하면 먼저 엘럿창을 띄움
				Swal.fire({
					type: 'success',
					title: "이메일을 확인해주세요."
				}).then((result) => {
					if (result.value) {
						location.href = 'signin.html'
					}
				});
				$.ajax({
					url: '../../app/json/member/Emailupdate',
					type: 'POST',
					data: {
						email: $("#email").val(),
						password: response.EnranNo
					}
				})
			},
			fail: function (error) {
				alert('등록 실패!!');
			}
		});
	} else {
		Swal.fire({
			type: 'error',
			title: "이메일 인증을 진행해 주세요."
		})
	}
})

//성공 상태로 바꾸는 함수
function successState(sel) {
	$(sel)
		.removeClass("is-invalid")
		.addClass("is-valid")
		.show();

	$("#myForm button[type=submit]")
		.removeAttr("disabled");
};

//에러 상태로 바꾸는 함수
function errorState(sel) {
	$(sel)
		.removeClass("is-valid")
		.addClass("is-invalid")
		.show();

	$("#myForm button[type=submit]")
		.attr("disabled", "disabled");
};

"use strict"



