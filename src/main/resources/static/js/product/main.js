$('document').ready(function(){
	$('#unitPrice').mask("#.##0,00", {reverse: true});
	
	
	$('#floating-button > button, .close-sidenav').click(function(){
		if($('.overlay').length){
			$('.overlay').remove();
		}else{
			$('body').append('<div class="overlay"></div>');			
		}
		$('#sidenav-of-creation').toggle("slide", { direction: "right" }, 300);
	})
	
	
	
	$('#product-form').submit(function(e){
		e.preventDefault();
		
		let arrayData = $(this).serializeArray();
		let data = {};
		
		arrayData.forEach((value) => {
			data[value.name] = value.value;
		})
		
		data.unitPrice = data.unitPrice.replace('.', '').replace(',', '.');

		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/product/store",
			data: JSON.stringify(data),
			success: function(response){
				$('.close-sidenav').trigger('click');
				table.ajax.reload();
				showNotification('success', response);
				$('#product-form')[0].reset();
			},
			error: function(response){
				console.log(response);
				showNotification('error', response.responseText);
			}
		});
	})
})

function removeProduct(code, row){
	$.ajax({
		method: "DELETE",
		headers: { 
	        'Content-Type': 'application/json' 
	    },
		url: `/product/destroy?id=${code}`,
		success: function(response){
			table.row(row).remove().draw();
			showNotification('success', response);
		},
		error: function(response){
			showNotification('error', response.responseText);
		}
	});
};

