
$(function() {
	$(".bs-example").each(function(index, val){
		var obj = $(this);
		obj.find(".btn-success").bind("click", function() {
			var data = obj.find(".form-horizontal").serialize();
			var action = obj.attr("action");
			data= data + "&action=" + action;
			post(data, obj);
		});
	});
	
});

function post(data, obj) {
	$.ajax({
		  type: 'post',
		  url: appHost + approot,
		  data: data,
		  success: function(res) {
			  obj.find(".bg-msg").remove();
			  if(res.state == 1) {
				  obj.append('<p class="bg-msg bg-success">' + res +'</p>');
			  } else {
				  obj.append('<p class="bg-msg bg-warning">' + res +'</p>');
			  }
		  }
	});
}
