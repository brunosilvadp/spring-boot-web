$('document').ready(function(){
	$('#phone').mask('(00) 0 0000-0000');
	$('#cpf').mask('000.000.000-00', {reverse: true});
	$('#monthlyGoal').mask("#.##0,00", {reverse: true});
	
	
	$('.close-sidenav').click(function(){
		showSidenav($(this).data('sidenav'));
	})
	
	$('#report-form').submit(function(e){
		e.preventDefault();
		$.ajax({
			method: "GET",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: `report/seller/sale?${$(this).serialize()}`,
			success: function(response){
				let total = parseFloat(response[0]) || 0 ;
				$('#report-sidenav #saleTotal').val(total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}))
				$('#report-sidenav #saleQuantity').val(response['1'])
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		})
	})
	
	$('#seller-form').submit(function(e){
		e.preventDefault();
		
		let arrayData = $(this).serializeArray();
		let data = {};
		
		arrayData.forEach((value) => {
			data[value.name] = value.value;
		});

		data.monthlyGoal = data.monthlyGoal.replace('.', '').replace(',', '.');

		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/seller/store",
			data: JSON.stringify(data),
			success: function(response){
				$('#sidenav-of-creation .close-sidenav').trigger('click');
				table.ajax.reload();
				showNotification('success', response);
				$('#seller-form')[0].reset();
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		});
	})
})

function removeSeller(row){
	let data = table.row( row ).data();
	delete data.registerDate;
	$.ajax({
		method: "DELETE",
		headers: { 
	        'Content-Type': 'application/json' 
	    },
		url: "/seller/destroy",
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

function showReportSidenav(row){
	$('#report-sidenav input').val('');
	$('#report-form input[name="sellerId"]').val(table.row(row).data().id);
	$('#report-sidenav input[name="sellerName"]').val(table.row(row).data().name);
	showSidenav('#report-sidenav');
}