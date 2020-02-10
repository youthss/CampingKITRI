var fileCountCheck = true;
var fileMainCheck = true;
var thumbnail = '';
var photos = [];
var fileCount = 0;
var reqCount = 0;
 
$(document).ready(function () {

	$('input.gallery_media').fileuploader({
		limit: 100,
		fileMaxSize: 20,
		extensions: ['image/*', 'video/*', 'audio/*'],
		changeInput: ' ',
		theme: 'gallery',
		enableApi: true,
		thumbnails: {
			box: '<div class="fileuploader-items">' +
				'<ul class="fileuploader-items-list">' +
				'<li class="fileuploader-input"><div class="fileuploader-input-inner"><div class="fileuploader-main-icon"></div> <span>${captions.feedback}</span></div></li>' +
				'</ul>' +
				'</div>',
			item: '<li class="fileuploader-item file-has-popup">' +
				'<div class="fileuploader-item-inner">' +
				'<div class="actions-holder">' +
				'<a class="fileuploader-action fileuploader-action-sort is-hidden" title="${captions.sort}"><i></i></a>' +
				'<a class="fileuploader-action fileuploader-action-settings is-hidden" title="${captions.edit}"><i></i></a>' +
				'<a class="fileuploader-action fileuploader-action-remove" title="${captions.remove}"><i></i></a>' +
				'<div class="gallery-item-dropdown">' +
				'<a href="${data.url}" download>${captions.setting_download}</a>' +
				'<a class="fileuploader-action-popup">${captions.setting_edit}</a>' +
				'<a class="gallery-action-rename">${captions.setting_rename}</a>' +
				'<a class="gallery-action-asmain">${captions.setting_asMain}</a>' +
				'</div>' +
				'</div>' +
				'<div class="thumbnail-holder">' +
				'${image}' +
				'<span class="fileuploader-action-popup"></span>' +
				'<div class="progress-holder"><span></span>${progressBar}</div>' +
				'</div>' +
				'<div class="content-holder"><h5 title="${name}">${name}</h5><span>${size2}</span></div>' +
				'<div class="type-holder">${icon}</div>' +
				'</div>' +
				'</li>',
			item2: '<li class="fileuploader-item file-has-popup file-main-${data.isMain}">' +
				'<div class="fileuploader-item-inner">' +
				'<div class="actions-holder">' +
				'<a class="fileuploader-action fileuploader-action-sort" title="${captions.sort}"><i></i></a>' +
				'<a class="fileuploader-action fileuploader-action-settings" title="${captions.edit}"><i></i></a>' +
				'<a class="fileuploader-action fileuploader-action-remove" title="${captions.remove}"><i></i></a>' +
				'<div class="gallery-item-dropdown">' +
				'<a href="${data.url}" download>${captions.setting_download}</a>' +
				'<a class="fileuploader-action-popup">${captions.setting_edit}</a>' +
				'<a class="gallery-action-rename">${captions.setting_rename}</a>' +
				'<a class="gallery-action-asmain">${captions.setting_asMain}</a>' +
				'</div>' +
				'</div>' +
				'<div class="thumbnail-holder">' +
				'${image}' +
				'<span class="fileuploader-action-popup"></span>' +
				'</div>' +
				'<div class="content-holder"><h5 title="${name}">${name}</h5><span>${size2}</span></div>' +
				'<div class="type-holder">${icon}</div>' +
				'</div>' +
				'</li>',
			itemPrepend: true,
			startImageRenderer: true,
			canvasImage: false,
			onItemShow: function (item, listEl, parentEl, newInputEl, inputEl) {
				var api = $.fileuploader.getInstance(inputEl),
					color = api.assets.textToColor(item.format),
					$plusInput = listEl.find('.fileuploader-input'),
					$progressBar = item.html.find('.progress-holder');

				// put input first in the list
				$plusInput.prependTo(listEl);

				// color the icon and the progressbar with the format color
				item.html.find('.type-holder .fileuploader-item-icon')[api.assets.isBrightColor(color) ? 'addClass' : 'removeClass']('is-bright-color').css('backgroundColor', color);
				$progressBar.css('backgroundColor', color);
			},
			onImageLoaded: function (item, listEl, parentEl, newInputEl, inputEl) {
				var api = $.fileuploader.getInstance(inputEl);
				// check the image size
				if (item.format == 'image' && item.upload && !item.imU) {
					if (item.reader.node && (item.reader.width < 100 || item.reader.height < 100)) {
						alert(api.assets.textParse(api.getOptions().captions.imageSizeError, item));
						return item.remove();
					}
					
					item.image.hide();
					item.reader.done = true;

					item.html.find('.fileuploader-action-success').removeClass('fileuploader-action-success');

					item.html.find('.content-holder h5').attr('title', item.name).text(item.name);
					item.html.find('.content-holder span').text(item.size2);
					item.html.find('.gallery-item-dropdown [download]').attr('href', item.data.url);

					delete item.imU;
					item.html.find('.fileuploader-action-remove').addClass('fileuploader-action-success');
	
					setTimeout(function () {
						item.html.find('.progress-holder').hide();
	
						item.html.find('.fileuploader-action-popup, .fileuploader-item-image').show();
						item.html.find('.fileuploader-action-sort').removeClass('is-hidden');
						item.html.find('.fileuploader-action-settings').removeClass('is-hidden');
					}, 400);

					// item.upload.send();
				}
				fileCountCheck = false;
				fileCount++;
			},
			onItemRemove: function (html) {
				fileCount--;
				html.fadeOut(250);
			},
			popup: {
        container: '.fileuploader-popup-preview',
        arrows: true,
        loop: true,
        zoomer: true,
        template: function(data) {
					return '<span></span>';
				}
   	  }
		},
		dragDrop: {
			container: '.fileuploader-theme-gallery .fileuploader-input'
		},
		upload: {
			url: '../../app/json/room/fileAdd',
			data: null,
			type: 'POST',
			enctype: 'multipart/form-data',
			start: false,
			synchron: true,
			beforeSend: function (item) {
				Swal.showLoading();
				// check the image size first (onImageLoaded)
				if (item.format == 'image' && !item.reader.done)
					return false;

				// add editor to upload data after editing
				if (item.editor && (typeof item.editor.rotation != "undefined" || item.editor.crop)) {
					item.imU = true;
					item.upload.data.file = item.name;
					// item.upload.data.id = item.data.listProps.id;
					item.upload.data._editorr = JSON.stringify(item.editor);
				}
			},
			onSuccess: function (result, item) {
				
				if (result.thumbnail) {
					thumbnail = result.thumbnail;
				} else if (result.photo) {
					photos.push(result.photo);
				}
				
				if (++reqCount == fileCount) {
					$('body').trigger('file-success');
				}
				
				delete item.imU;
				item.html.find('.fileuploader-action-remove').addClass('fileuploader-action-success');

				setTimeout(function () {
					item.html.find('.progress-holder').hide();

					item.html.find('.fileuploader-action-popup, .fileuploader-item-image').show();
					item.html.find('.fileuploader-action-sort').removeClass('is-hidden');
					item.html.find('.fileuploader-action-settings').removeClass('is-hidden');
				}, 400);
			},
			onError: function (item) {
				item.html.find('.progress-holder, .fileuploader-action-popup, .fileuploader-item-image').hide();

				// add retry button
				item.upload.status != 'cancelled' && !item.imU && !item.html.find('.fileuploader-action-retry').length ? item.html.find('.actions-holder').prepend(
					'<a class="fileuploader-action fileuploader-action-retry" title="Retry"><i></i></a>'
				) : null;
			},
			onProgress: function (data, item) {
				console.log('onProgress()');
				var $progressBar = item.html.find('.progress-holder');

				if ($progressBar.length) {
					$progressBar.show();
					$progressBar.find('span').text(data.percentage + '%');
					$progressBar.find('.fileuploader-progressbar .bar').height(data.percentage + '%');
				}

				item.html.find('.fileuploader-action-popup, .fileuploader-item-image').hide();
			}
		},
		afterRender: function (listEl, parentEl, newInputEl, inputEl) {
			var api = $.fileuploader.getInstance(inputEl),
				$plusInput = listEl.find('.fileuploader-input');

			// bind input click
			$plusInput.on('click', function () {
				api.open();
			});

			// bind dropdown buttons
			$('body').on('click', function (e) {
				var $target = $(e.target),
					$item = $target.closest('.fileuploader-item'),
					item = api.findFile($item);

				// toggle dropdown
				$('.gallery-item-dropdown').hide();
				if ($target.is('.fileuploader-action-settings') || $target.parent().is('.fileuploader-action-settings')) {
					$item.find('.gallery-item-dropdown').show(150);
				}

				// rename
				if ($target.is('.gallery-action-rename')) {
					var x = prompt(api.getOptions().captions.rename, item.title);

					if (x && item.data.listProps) {
						$.post('php/ajax.php?type=rename', { name: item.name, id: item.data.listProps.id, title: x }, function (result) {
							try {
								var j = JSON.parse(result);

								// update the file name and url
								if (j.title) {
									item.title = j.title;
									item.name = item.title + (item.extension.length ? '.' + item.extension : '');
									$item.find('.content-holder h5').attr('title', item.name).html(item.name);
									$item.find('.gallery-item-dropdown [download]').attr('href', item.data.url);

									if (item.popup.html)
										item.popup.html.find('h5:eq(0)').text(item.name);

									if (j.url)
										item.data.url = j.url;
									if (item.appended && j.file)
										item.file = j.file;

									api.updateFileList();
								}

							} catch (e) {
								alert(api.getOptions().captions.renameError);
							}
						});
					}
				}

				// set main
				
				// if ($target.is('.gallery-action-asmain')) {
				if ($target.is('.fileuploader-action-popup') || $target.is('.gallery-action-asmain')) {
					e.preventDefault();
					api.getFiles().forEach(function (val) {
						item.upload.data.isMain = false;
						delete val.data.isMain;
						val.html.removeClass('file-main-0 file-main-1');
					});
					item.upload.data.isMain = true;
					fileMainCheck = false;
					// item.data.isMain = true;
					item.html.addClass('file-main-1');
					api.updateFileList();
				}
			});
		},
		captions: {
			feedback: 'Drag & Drop',
			setting_asMain: '메인 지정',
			setting_download: '다운로드',
			setting_edit: '사진수정',
			setting_rename: '이름변경',
			rename: '이름을 지정하고 엔터를 누르세요 :',
			renameError: 'Please enter another name.',
			imageSizeError: 'The image ${name} is too small.',
		}
	});
});
