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