var menuHeight = $('#menu').outerHeight(),
	templateSrc = $('#review-template').html(),
	reviewGenerator = Handlebars.compile(templateSrc),
	templateSrcs = $('#hostProfile-template').html(),
	hostProfileGenerator = Handlebars.compile(templateSrcs),
	rating = 0,
	paginateSrc = $('#page-template').html();


function roomreview(pn) {
	Handlebars.registerHelper('paginate', paginate);
	var pageGenerator = Handlebars.compile(paginateSrc);
	$.ajax({
		url: '../../app/json/room/review?pageNo=' + pn,
		type: 'GET',
		data: {
			no: paramNo
		},
		dataType: 'json',
		success: function (response) {
			pageNo = response.pageNo;
			//$(reviewGenerator(response)).appendTo(reviewlist);
			response.pagination = {
				page: response.pageNo,
				pageCount: response.totalPage
			};

			$('#review').html('');
			$(reviewGenerator(response)).appendTo($('#review'));

			$('.pagination-menu').html('');
			$(pageGenerator(response)).appendTo('.pagination-menu');


			$('.comment-input-box').hide();

			$('.heun-reply').on('click', function (e) {
				e.preventDefault();

				var no = $(this).attr('data-no');    //예약번호
				var userNo = $(this).attr('data-userNo'); // 회원번호
				var reply = $('.reply-' + no);        // reply데이터

				//원하는 핸들바스 부분만 여는 코드
				if ($('.cont-' + no).css('display') == 'none') {
					$('.cont-' + no).show();
				} else {
					$('.cont-' + no).hide();
				}

				$('.reply-submit').off('click').on('click', function (e) {
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

						if (result.value) {
							$.ajax({
								url: '../../app/json/riw/reply',
								type: 'POST',
								data: {
									no: no,
									userNo: userNo,
									reply: reply.val()
								},
								dataType: 'json',
								success: function (response) {
									Swal.fire(
										'Success!',
										'성공적으로 등록됐어요.',
										'success'
									).then(() => {
										roomreview(1);

									})
									roomreview(1);

								},
								fail: function (error) {
									alert('등록 실패!!');
								}
							});
						}
					})

				});
			});

			$('.delete1').hide();
			$('.delete2').hide();

			if (response.list) {

				if (response.list[0].hostname != response.hostname) {
					$('.btn-reply').hide();

					//그숙소 회원이 아니면 
				} else {
					$('.riw-update').hide();
					$('.delete2').show();

				}


				for (var i = 0; i < response.list.length; i++) {

					var a = response.list[i].no;
					var delete1 = response.list[i].name;
					var starcount = response.list[i].grd;

					//console.log($(".review-rating").attr("data-crd"))

					$('#star-' + a).starRating({
						totalStars: 5,
						starShape: 'rounded',
						emptyColor: 'lightgray',
						strokeWidth: 0,
						disableAfterRate: false,
						readOnly: true,
						initialRating: starcount,
						starSize: 20
					});

					if ($('#aaa-' + a).attr('data-reply') == '') {
						$('#no-reply-' + a).hide();
					} else {
						$('#cont-' + a).hide();
					}

					if ($('#delete1-' + a).attr('data-name') == response.hostname) {  //일반회원중 글쓴회원판별
						$('#delete1-' + a).show();
					}
				}

				$('.delete1').on('click', function (e) {
					e.preventDefault();

					Swal.fire({
						title: '잠깐!',
						text: "삭제하시겠어요?",
						type: 'question',
						showCancelButton: true,
						confirmButtonColor: '#3085d6',
						cancelButtonColor: '#d33',
						confirmButtonText: '네, 삭제하겠습니다!',
						cancelButtonText: '아뇨, 다시 한번 볼게요!'
					}).then((result) => {

						if (result.value) {


							var no = $(this).attr('data-no');    //예약번호


							$.ajax({
								url: '../../app/json/riw/delete',
								type: 'POST',
								data: {
									no: no
								},
								dataType: 'json',
								success: function (response) {
									Swal.fire(
										'삭제!',
										'글이 삭제되었습니다!',
										'success'

									).then(() => {
										roomreview(1);

									})

								},
								fail: function (error) {
									alert('삭제 실패!!');
								}
							});
						}
					})


				});


				$('.delete2').on('click', function (e) {
					e.preventDefault();

					Swal.fire({
						title: '잠깐!',
						text: "삭제하시겠어요?",
						type: 'question',
						showCancelButton: true,
						confirmButtonColor: '#3085d6',
						cancelButtonColor: '#d33',
						confirmButtonText: '네, 삭제하겠습니다!',
						cancelButtonText: '아뇨, 다시 한번 볼게요!'
					}).then((result) => {

						if (result.value) {
							var no = $(this).attr('data-no');    //예약번호

							$.ajax({
								url: '../../app/json/riw/replydelete',
								type: 'POST',
								data: {
									no: no
								},
								dataType: 'json',
								success: function (response) {
									Swal.fire(
										'삭제!',
										'글이 삭제되었습니다!',
										'success'
									).then(() => {
										roomreview(1);

									})

								},
								fail: function (error) {
									alert('삭제 실패!!');
								}
							});
						}
					})

				});

			} else {
				$('#review').html("<div style='text-align: center; font-size: 16px'> 작성된 리뷰가 없습니다. </div>");
				$('.pagination-menu').html('');
			}


		},
		fail: function (error) {
			alert('ㅠㅠ!!');
		}
	});

}

function roomProfile() {
	var hostNo = $('.host-profile').data('no');
	$.ajax({
		url: '/heunheuntrip/app/json/member/detail',
		type: 'GET',
		data: {
			no: hostNo
		},
		dataType: 'json',
		success: function (response) {
			var reviewNo = 0;
			$.ajax({
				url: '/heunheuntrip/app/json/riw/countAllHost',
				type: 'POST',
				async: false,
				data: {
					no: hostNo
				},
				dataType: 'json',
				success: function (response) {
					reviewNo = response.riwNo;
				},
				fail: function (error) {
				}
			});

			response.count = reviewNo;
			$('.host-profile').html('');
			var general = $(hostProfileGenerator(response));
			general.appendTo($('.host-profile'));

		},
		fail: function (error) {
		}
	});
}

$('body').on('loaded-list', function () {
	roomreview(1);
	roomProfile();

	$('.riw-update').off('click').on('click', function (e) {

		//$('#message-text').val('');
		//		var no = $(this).parent().data('no');
		//		var grd = $(this).parent().data('grd');
		//		var content = $(this).parent().prev().children().html();

		//modal.find('#message-text').val("");

		window.rating = 0;

		$('#exampleModal').on('show.bs.modal', function (event) {
			var button = $(event.relatedTarget)
			var recipient = button.data('whatever')
			var modal = $(this)
			modal.find('.modal-title').text('Review')
			modal.find('.modal-body input').val(recipient)
			modal.find('#message-text').val('');

		});


		$('.my-rating').starRating({
			totalStars: 5,
			starShape: 'rounded',
			emptyColor: 'lightgray',
			hoverColor: 'gold',
			activeColor: 'gold',
			strokeWidth: 0,
			disableAfterRate: false,
			useGradient: false,
			callback: function (currentRating, $el) {
				window.rating = currentRating;
			}
		});

		//		$('.add-btn').off('click').on('click', function (e) {
		//
		//			$.ajax({
		//				url: '../../app/json/room/addriw',
		//				type: 'POST',
		//				data: {
		//					roomNo: window.param.split('=')[1],
		//					grd: window.rating,
		//					contents: $('#message-text').val()
		//				},
		//				dataType: 'json',
		//				success: function(response) {
		//					Swal.fire(
		//				              'Success!',
		//				              '성공적으로 등록됐어요.',
		//				              'success'
		//				            ).then(() => {
		//				          	location.href='view.html?no=' + window.param.split('=')[1];
		//				                        })
		//					//roomreview(1);
		//
		//					//  $('#exampleModal').modal("hide");
		//				},
		//				fail: function (error) {
		//					alert('등록 실패!!');
		//				}
		//			});
		//
		//		});


	})

	var menuHeight = $('#menu').outerHeight();
	$('.has-sidebar>*').theiaStickySidebar({
		additionalMarginTop: menuHeight + 30,
		additionalMarginBottom: 30,
		minWidth: 767,
	});

	// Photoswipe

	var initPhotoSwipeFromDOM = function (gallerySelector) {
		var parseThumbnailElements = function (el) {
			var thumbElements = $(el).closest(main_gallery).find('figure'),
				numNodes = thumbElements.length,
				items = [],
				figureEl,
				linkEl,
				size,
				item;

			for (var i = 0; i < numNodes; i++) {

				figureEl = thumbElements[i]; // <figure> element

				// include only element nodes 
				if (figureEl.nodeType !== 1) {
					continue;
				}

				linkEl = figureEl.children[0]; // <a> element

				size = linkEl.getAttribute('data-size').split('x');

				// create slide object
				item = {
					src: linkEl.getAttribute('href'),
					w: parseInt(size[0], 10),
					h: parseInt(size[1], 10)
				};



				if (figureEl.children.length > 1) {
					// <figcaption> content
					item.title = figureEl.children[1].innerHTML;
				}

				if (linkEl.children.length > 0) {
					// <img> thumbnail element, retrieving thumbnail url
					item.msrc = linkEl.children[0].getAttribute('src');
				}

				item.el = figureEl; // save link to element for getThumbBoundsFn
				items.push(item);
			}

			return items;
		};

		// find nearest parent element
		var closest = function closest(el, fn) {
			return el && (fn(el) ? el : closest(el.parentNode, fn));
		};

		// triggers when user clicks on thumbnail
		var onThumbnailsClick = function (e) {
			e = e || window.event;
			e.preventDefault ? e.preventDefault() : e.returnValue = false;

			var eTarget = e.target || e.srcElement;

			// find root element of slide
			var clickedListItem = closest(eTarget, function (el) {
				return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
			});

			if (!clickedListItem) {
				return;
			}
			var clickedGallery = clickedListItem.parentNode,
				childNodes = $(clickedListItem).closest(main_gallery).find('figure'),
				numChildNodes = childNodes.length,
				nodeIndex = 0,
				index;

			for (var i = 0; i < numChildNodes; i++) {
				if (childNodes[i].nodeType !== 1) {
					continue;
				}

				if (childNodes[i] === clickedListItem) {
					index = nodeIndex;
					break;
				}
				nodeIndex++;
			}
			if (index >= 0) {
				// open PhotoSwipe if valid index found
				openPhotoSwipe(index, clickedGallery);
			}
			return false;
		};

		var openPhotoSwipe = function (index, galleryElement, disableAnimation) {
			var pswpElement = document.querySelectorAll('.pswp')[0],
				gallery,
				options,
				items;

			items = parseThumbnailElements(galleryElement);

			// define options (if needed)
			options = {
				history: false,
				bgOpacity: 0.8,
				loop: false,
				barsSize: {
					top: 0,
					bottom: 'auto'
				},

				// define gallery index (for URL)
				galleryUID: $(galleryElement).closest(main_gallery).attr('data-pswp-uid'),

				getThumbBoundsFn: function (index) {
					// See Options -> getThumbBoundsFn section of documentation for more info
					var thumbnail = document.querySelectorAll(main_gallery + ' img')[index],
						//var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
						pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
						rect = thumbnail.getBoundingClientRect();

					return {
						x: rect.left,
						y: rect.top + pageYScroll,
						w: rect.width
					};
				}

			};

			options.index = parseInt(index, 10);

			// exit if index not found
			if (isNaN(options.index)) {
				return;
			}

			if (disableAnimation) {
				options.showAnimationDuration = 0;
			}

			// Pass data to PhotoSwipe and initialize it
			gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, items, options);
			gallery.init();
			gallery.shout('helloWorld', 'John' /* you may pass more arguments */);



			var totalItems = gallery.options.getNumItemsFn();

			function syncPhotoSwipeWithOwl() {
				var currentIndex = gallery.getCurrentIndex();
				galleryTop.slideTo(currentIndex);
				if (currentIndex == (totalItems - 1)) {
					$('.pswp__button--arrow--right').attr('disabled', 'disabled').addClass('disabled');
				} else {
					$('.pswp__button--arrow--right').removeAttr('disabled');
				}
				if (currentIndex == 0) {
					$('.pswp__button--arrow--left').attr('disabled', 'disabled').addClass('disabled');
				} else {
					$('.pswp__button--arrow--left').removeAttr('disabled');
				}
			};
			gallery.listen('afterChange', function () {
				syncPhotoSwipeWithOwl();
			});
			syncPhotoSwipeWithOwl();
		};

		// loop through all gallery elements and bind events
		var galleryElements = document.querySelectorAll(gallerySelector);

		for (var i = 0, l = galleryElements.length; i < l; i++) {
			galleryElements[i].setAttribute('data-pswp-uid', i + 1);
			galleryElements[i].onclick = onThumbnailsClick;
		}
	};
	var main_gallery = '.gallery-top';
	var galleryTop = new Swiper(main_gallery, {
		spaceBetween: 10,
		lazy: {
			loadPrevNext: true,
		},
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		}
		, on: {
			init: function () {
				initPhotoSwipeFromDOM(main_gallery);
			},
		}
	});
	var galleryThumbs = new Swiper('.gallery-thumbs', {
		spaceBetween: 10,
		centeredSlides: true,
		slidesPerView: 5,
		touchRatio: 0.2,
		slideToClickedSlide: true,
	});
	galleryTop.controller.control = galleryThumbs;
	galleryThumbs.controller.control = galleryTop;

	$(document).click(function (e) {
		var $target = $(e.target);
		if (!$target.is('.heun-h1') && !$target.is('.heun-h2')) {
			$('.heun-h1, .heun-h2').css('background-color', '');
		}
	})

	$('.heun-datein').on('click', function () {
		$('.heun-h1, .heun-h2').blur();
		$('.heun-h1, .heun-h2').css('background-color', 'rgb(210, 253, 255)');
	})

	$('.heun-h1').click(function (e) {
		e.stopPropagation();
		$('#heun-datetime').data('dateRangePicker').open();
	})

	var now = moment().format('YYYY-MM-DD');

	$('#heun-datetime').dateRangePicker({
		format: 'YYYY-MM-DD',
		autoClose: true,
		startDate: now,
		language: 'ko',
		separator: ' ~ ',
		selectForward: true,
		showShortcuts: true,
		customShortcuts: [
			{
				name: '날짜 지우기',
				dates: function () {
					$('#heun-datetime').data('dateRangePicker').clear();
					$('#date-range12-container').data('dateRangePicker').clear();
					$('#heun-rev').trigger('date-clear');
				}
			}
		],
		getValue: function () {
			if ($('.heun-h1').val() && $('.heun-h2').val()) {
				return $('.heun-h1').val() + ' ~ ' + $('.heun-h2').val();

			} else {
				$('#heun-datetime').data('dateRangePicker').clear();
				return '';
			}
		},
		setValue: function (s, s1, s2) {
			if (s1 && s2 && s1 === s2) {
				$('.heun-h1').val(s1);
				$('.heun-h2').val(moment(s2).add(1, 'days').format('YYYY-MM-DD'));
				return;
			}
			$('.heun-h1').val(s1);
			$('.heun-h2').val(s2);
			return;
		},
		beforeShowDay: showDay()

	}).bind('datepicker-change', function (event, obj) {
		var date = obj.value.split(" ~ ");
		if (date[0] === date[1]) {
			$('#date-range12-container').data('dateRangePicker').setDateRange(date[0], moment(date[1]).add(1, 'days').format('YYYY-MM-DD'));
		} else {
			$('#date-range12-container').data('dateRangePicker').setDateRange(date[0], date[1]);
		}
		$('.heun-h1, .heun-h2').css('background-color', '');
		$('#heun-rev').trigger('date-input');
	})


	$('#date-range12-container').dateRangePicker({
		format: 'YYYY-MM-DD',
		inline: true,
		startDate: now,
		container: '#date-range12-container',
		alwaysOpen: true,
		separator: ' ~ ',
		language: 'ko',
		selectForward: true,
		setValue: function (s, s1, s2) {
			if (s1 && s2 && s1 === s2) {
				$('.heun-h1').val(s1);
				$('.heun-h2').val(moment(s2).add(1, 'days').format('YYYY-MM-DD'));
				$('#date-range12-container').data('dateRangePicker').setDateRange(s1, moment(s2).add(1, 'days').format('YYYY-MM-DD'));
				return;
			}
			$('.heun-h1').val(s1);
			$('.heun-h2').val(s2);
			return;
		},
		showShortcuts: true,
		customShortcuts: [
			{
				name: '날짜 지우기',
				dates: function () {
					$('#date-range12-container').data('dateRangePicker').clear();
					$('#heun-datetime').data('dateRangePicker').close();
					$('#heun-rev').trigger('date-clear');
				}
			}
		],
		beforeShowDay: showDay()
	}).bind('datepicker-change', function (event, obj) {
		$('.heun-h1, .heun-h2').css('background-color', '');
		var date = obj.value.split(' ~ ');
		if (date[0] === date[1]) {
			return;
		}
		var start = moment(date[0]);
		var end = moment(date[1]);
		var day = moment.duration(end.diff(start)).asDays();
		$('#heun-rev').data('day', day);
		$('#heun-rev').trigger('date-input');
	})

	$('#heun-rev').on('date-input', function () {
		$('#price-table').html('');

		var price = parseInt($('#heun-rev').data('price'));
		var day = parseInt($('#heun-rev').data('day'));

		var priceDay = price * day;
		var tax = Math.floor(priceDay * 0.1);
		var sum = priceDay + tax;

		var priceComma = comma(String(price).replace(/[^0-9]/g, ''));
		var priceDayComma = comma(String(priceDay).replace(/[^0-9]/g, ''));
		var taxComma = comma(String(tax).replace(/[^0-9]/g, ''));
		var sumComma = comma(String(sum).replace(/[^0-9]/g, ''));

		var table = '<tbody>' +
			'  <tr>' +
			'    <td>￦ ' + priceComma + ' X ' + day + '박</td>' +
			'    <td>￦ ' + priceDayComma + '</td>' +
			'  </tr>' +
			'  <tr>' +
			'    <td>부가세</td>' +
			'    <td>￦ ' + taxComma + '</td>' +
			'  </tr>' +
			'  <tr>' +
			'    <td class="font-weight-bold">합계</td>' +
			'    <td class="font-weight-bold">￦ ' + sumComma + '</td>' +
			'  </tr>' +
			'</tbody>';

		var person = $('#input-m').data('p');

		$('#heun-modal-person').html('&nbsp;게스트 ' + person + '명');
		$('#heun-modal-day').html('&nbsp;' + $('.heun-h1').val() + ' ~ ' + $('.heun-h2').val());
		$('#heun-modal-cal').html('₩ ' + priceComma + ' x ' + day + '박');
		$('#heun-modal-sum').html('₩ ' + priceDayComma);
		$('#heun-modal-tex').html('₩ ' + taxComma);
		$('#heun-modal-texsum').html('₩ ' + sumComma);

		$('#price-table').append(table);
		$('#heun-rev').html('예약 요청');
		$('#heun-rev').attr('data-toggle', 'modal')
		$('#heun-rev').attr('data-target', '#leadform');
	});

	$('#heun-rev').on('date-clear', function () {
		$('#price-table').html('');
		$('#heun-rev').html('날짜 입력');
		$('#heun-rev').attr('data-toggle', '')
		$('#heun-rev').attr('data-target', '');
	});


	$('#heun-rev').click(function (e) {
		e.preventDefault();
		if ($(this).html() === '날짜 입력') {
			e.stopPropagation();
			$('#heun-datetime').data('dateRangePicker').open();
			return;
		}

	});

	$('.map-btn').click(function () {
		var no = $(this).data('no');
		if (no === 0) {
			hideMarkers();
			setMarkers(map, jmt);
		} else if (no === 1) {
			hideMarkers();
			setMarkers(map, myung);
		}
	});

	$('.heun-drop').click(function () {
		var no = $(this).data('p');
		$('#input-m').html('인원 ' + no + '명');
		$('#input-m').data('p', no);
		$('#heun-rev').trigger('date-input');
	});

	$('#heun-pay').click(function () {

		getUser(function (res) {
			if (res.status === "success") {

				if (res.user.tel === "" || res.user.tel === null) {
					Swal.fire({
						type: 'error',
						title: '전화번호를 등록해주세요!'
					}).then((result) => {
						if (result.value) {
							location.href = '/heunheuntrip/html/member/myPage/my_profile.html';
							return;
						}
					})
				} else {

					if ($('input[name=type]:checked').val()) {

						$.ajax({
							url: '../../app/json/rev/getbuyinfo',
							type: 'POST',
							dataType: 'json',
							data: {
								rmsNo: paramNo,
								checkIn: $('.heun-h1').val(),
								checkOut: $('.heun-h2').val(),
								revPerson: $('#input-m').data('p')
							},
							success: function (res) {
								buyPay(res.name, res.amount, res.buyer_email, res.buyer_name, res.buyer_tel, res.merchant_uid);
							},
							fail: function (error) {
								alert('시스템 오류가 발생했습니다.');
							}
						});

						return;
					} else {
						Swal.fire({
							type: 'error',
							title: '결제 방식을 선택해주세요!'
						}).then((result) => {
							if (result.value) {
								return;
							}
						})
						return;
					}

				}

			} else {
				Swal.fire({
					type: 'error',
					title: '로그인 해주세요!'
				}).then((result) => {
					if (result.value) {
						location.href = '/heunheuntrip/html/auth/signin.html';
						return;
					}
				})
				return;
			}
		});

	})

	function buyPay(name, amount, buyer_email, buyer_name, buyer_tel, merchant_uid) {
		IMP.request_pay({
			pg: 'html5_inicis', // version 1.1.0부터 지원.
			pay_method: $('input[name=type]:checked').val(),
			merchant_uid: merchant_uid,
			name: name,
			amount: amount,
			buyer_email: buyer_email,
			buyer_name: buyer_name,
			buyer_tel: buyer_tel
		}, function (rsp) {
			var isVaild = true;
			var msg;
			if (rsp.success) {
				//[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
				$.ajax({
					url: "../../app/json/rev/complete", //cross-domain error가 발생하지 않도록 동일한 도메인으로 전송
					type: 'POST',
					dataType: 'json',
					data: {
						imp_uid: rsp.imp_uid
						//기타 필요한 데이터가 있으면 추가 전달
					},
					beforeSend: function () {      // ajax 요청하기전에 실행되는 함수
						Swal.showLoading();
					}
				}).done(function (data) {
					//[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
					if (data.status === "success") {
						isVaild = true;
						msg = '결제가 완료되었습니다.';
					} else {
						//[3] 아직 제대로 결제가 되지 않았습니다.
						//[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
						isVaild = false;
						msg = '이미 예약된 방이거나 서버 오류 입니다!\n관리자에게 문의하세요!';
					}
				}).always(function () {
					Swal.fire({
						type: isVaild ? 'success' : 'error',
						title: msg
					}).then((result) => {
						if (result.value) {
							location.href = isVaild ? '/heunheuntrip/html/member/myPage/list.html' : '';
							return;
						}
					})
				});
			} else {
				var isVaild = false;
				var msg = '결제에 실패하였습니다.';
				msg += '\n에러내용 : ' + rsp.error_msg;
			}
		})
	}


	function getUser(cb) {
		$.ajax({
			url: '../../app/json/auth/user',
			type: 'GET',
			dataType: 'json',
			success: function (res) {
				cb(res);
			},
			fail: function (error) {
				alert('시스템 오류가 발생했습니다.');
			}
		});
	}

	$('.dropdown-toggle').off('click').on('click', function () {
		var width = $('.drop-people').outerWidth();
		$('.drop-item-p').css('width', width);
	});

	function showDay() {
		return function (t) {
			var valid = true;
			for (var r of revHistory) {
				if (moment(t).format('YYYY-MM-DD') === r) {
					valid = false;
					break;
				}
			}
			var _class = '';
			var _tooltip = valid ? '' : '예약완료';
			return [valid, _class, _tooltip];
		}
	}

})