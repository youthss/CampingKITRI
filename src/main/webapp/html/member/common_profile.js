function loadProfile() {
	$.getJSON('/heunheuntrip/app/json/member/profile',
			  function(obj) {
    if (obj.member.photo != null) {
      $("<img class='rounded-circle'>").attr('src',
        '/heunheuntrip/app/json/images/down/' + obj.member.photo + "_profile")
        .css('width', '255px')
        .css('height', '255px')
        .appendTo($('#profileimg'));
    } else {
      $("<img>").attr('src',
        '/heunheuntrip/images/default.jpeg')
        .css('width', '255px')
        .css('height', '255px')
        .appendTo($('#profileimg'));
    }
    	 $('.main-name').text(obj.member.name);
		  $('.name').val(obj.member.name);
		  $('.email').val(obj.member.email);
		  $('#tel').val(obj.member.tel);
		  $('#tel').attr("data-tel", obj.member.tel);
		  no = obj.member.no;
		  $(document).trigger('load');
	  }); 
  } 







