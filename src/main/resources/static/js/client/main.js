$('document').ready(function(){
	$('#phone').mask('(00) 0 0000-0000');
	$('#cpf').mask('000.000.000-00', {reverse: true});
	$('#credit-limit').mask("#.##0,00", {reverse: true});
	
	
	$('#floating-button > button, .close-sidenav').click(function(){
		if($('.overlay').length){
			$('.overlay').remove();
		}else{
			$('body').append('<div class="overlay"></div>');			
		}
		$('#sidenav-of-creation').toggle("slide", { direction: "right" }, 300);
	})
	
	
	
	$('#client-form').submit(function(e){
		e.preventDefault();
		
		let arrayData = $(this).serializeArray();
		let data = {};
		
		arrayData.forEach((value) => {
			data[value.name] = value.value;
		})
		
		data.creditLimit = data.creditLimit.replace('.', '').replace(',', '.');
		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/client/store",
			data: JSON.stringify(data),
			success: function(response){
				$('.close-sidenav').trigger('click');
				table.ajax.reload();
				showNotification('success', response);
				$('#client-form')[0].reset();
			},
			error: function(response){
				console.log(response);
				showNotification('error', response.responseText);
			}
		});
	})
})

function removeClient(row){
	let data = table.row( row ).data();
	delete data.registerDate;
	$.ajax({
		method: "DELETE",
		headers: { 
	        'Content-Type': 'application/json' 
	    },
		url: "/client/destroy",
		data: JSON.stringify(data),
		success: function(response){
			table.row(row).remove().draw();
			showNotification('success', response);
		},
		error: function(response){
			showNotification('error', response.responseText);
		}
	});
};

