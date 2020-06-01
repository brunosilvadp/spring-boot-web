function showNotification(type, message){
	new Noty({
		text: message,
		layout: "topRight",
		theme: "sunset",
		type,
		closeWith: ['button', 'click'],
		progressBar: true,
		timeout: 3000
	}).show();
}

function showSidenav(sidenav){
	if($('.overlay').length){
		$('.overlay').remove();
	}else{
		$('body').append('<div class="overlay"></div>');			
	}
	$(sidenav).toggle("slide", { direction: "right" }, 300);
}