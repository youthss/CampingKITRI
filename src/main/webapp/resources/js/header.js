$(document).ready(function() {

	$('#logout-btn').on('click', function(e) {
		$.ajax({
			url : 'auth/logout',
			type : 'GET',
			dataType : 'json',
			success : function(response) {
				location.href = '/app/auth/signin'
			},
			error : function(error) {
			}
		})
	});

	loadLoginUser();

})

$('.heun-myPage')
		.on(
				'click',
				function(e) {
					e.preventDefault();

					$
							.ajax({
								url : 'auth/authCheck',
								type : 'GET',
								dataType : 'json',
								success : function(response) {

									if (response.auth == "일반회원") {
										location.href = '/heunheuntrip/html/member/myPage/my_profile.html';
									} else if (response.auth == "호스트") {
										location.href = '/heunheuntrip/html/member/hostPage/host_profile.html';
									} else if (response.auth == "관리자") {
										location.href = '/heunheuntrip/html/member/managerPage/manager_room_ceco.html';
									} else if (response.status == "fail") {
										location.href = '/heunheuntrip/html/auth/signin.html';
									}
								},
								error : function(error) {
								}
							})
				})

$('#heun-header-search').submit(function() {
	search($('#heun-search-val').val());
	return false;
});

function search(val) {
	if (val === '') {
		location.href = '/heunheuntrip/html/room/index.html';
	} else {
		var places = new daum.maps.services.Places();
		places.keywordSearch(val, function(result, status) {
			if (status === daum.maps.services.Status.OK) {
				location.href = '/heunheuntrip/html/room/index.html?lati='
						+ result[0].y + '&longi=' + result[0].x;
			}
		});
	}
}

function loadLoginUser() {
	$
			.ajax({
				url : '/heunheuntrip/app/json/member/profile',
				type : 'GET',
				dataType : 'json',
				success : function(response) {
					if (response.status == 'success') {
						$('#register-btn').hide();
						$('#login-btn').hide();
						$('#login-username').append(response.member.name + "님");
						if (response.member.photo == null) {
							$('#login-userphoto').css('background-image',
									"url('/heunheuntrip/images/default.jpeg')");
						} else if (response.member.photo != null
								&& response.member.auth == "일반회원"
								|| response.member.auth == "호스트") {
							$('#login-userphoto').css(
									'background-image',
									"url('/heunheuntrip/app/json/images/down/"
											+ response.member.photo + "_header"
											+ "')");
						}

					} else {
						$('#login-btn').show();
						$('#logout-btn').hide();
						$('#register-btn').show();
						$('#mypage-btn').hide();
						$('#message-btn').hide();
					}
				},
				error : function(error) {
				}
			});

}
