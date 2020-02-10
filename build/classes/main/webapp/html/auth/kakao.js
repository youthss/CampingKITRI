var token;

// 사용할 앱의 JavaScript 키를 설정해 주세요.
Kakao.init('32bc4e1d51b78f721e0430e937d5bcde');
// 카카오 로그인 버튼을 생성합니다.
Kakao.Auth.createLoginButton({
	container: '#kakao-login-btn',
	success: function (authObj) {
		token = authObj.access_token;
		// 로그인 성공시, API를 호출합니다.
		Kakao.API.request({
			url: '/v2/user/me',
			success: function (res) {
				var email = res.kakao_account.email,
					name = res.properties.nickname
				if (typeof email == "undefined") {
					Swal.fire({
						type: 'error',
						title: "이메일은 필수 선택 사항입니다."
					}).then((result) => {
						if (result.value) {
							location.href = "/heunheuntrip/html/auth/signin.html"
							Kakao.API.request({ url: '/v1/user/unlink', });
						}
					}) // alert 끝
				} else {
					login(email, name);
				}
			},
			fail: function (error) {
				alert(JSON.stringify(error));
			}
		});
	},
	fail: function (err) {
		alert(JSON.stringify(err));
	}
});

function login(email, name) {
	$.post('/heunheuntrip/app/json/auth/snslogin', {
		token: token,
		email: email,
		sns_no: 2
	},
		function (data) {
			if (data.status == 'success') {
				location.href = '../index.html'
			} else if (data.status == 'accessTokenFail') {
				alert('올바르지 않는 접근이다')
				location.href = '../index.html'
			} else {
				signup(email, name);
			}
		})
}
function signup(email, name) {
	$.post('/heunheuntrip/app/json/member/snsadd', {
		email: email,
		name: name,
		auth: 1,
		sns_no: 2
	},
		function (data) {
			if (data.status == 'fail') {
				alert('계정생성오류')
				location.href = '../index.html'
			} else if (data.status == 'overlap') {
				alert('이미 있는 계정입니다.')
				location.href = '../index.html'
			} else if (data.status == 'success') {
				login(email, name)
			}
		})
}