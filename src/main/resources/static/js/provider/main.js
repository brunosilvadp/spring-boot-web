$('document').ready(function(){
	$('#phone').mask('(00) 0 0000-0000');
	$('#cnpj').mask('00.000.000/0000-00', {reverse: true});
	
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
			url: `report/provider/purchase?${$(this).serialize()}`,
			success: function(response){
				let total = parseFloat(response[0]) || 0 ;
				$('#report-sidenav #purchaseTotal').val(total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL'}))
				$('#report-sidenav #purchaseQuantity').val(response['1'])
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		})
	})

	$('#shopping-form').submit(function(e){
		e.preventDefault();
		let arrayData = $(this).serializeArray();
		let data = {
			"provider": table.row($('input[name="provider-position"]').val()).data()
		};

		arrayData.forEach((value) => {
			data[value.name] = value.value;
		})
		data['purchaseItem'] = [];
		shoppingTable.rows().data().map(item => {
			let object = {
				'product': item,
				'purchaseQuantity': parseInt(item.quantity),
				'purchaseValue': item.unitPrice * item.quantity
			}
			data['purchaseItem'].push(object);
		})
		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/purchase/store",
			data: JSON.stringify(data),
			success: function(response){
				$('#buy-sidenav .close-sidenav').trigger('click');
				shoppingTable.clear().draw();
				showNotification('success', response);
				$('#shopping-form')[0].reset();
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		});
	})

	$('#provider-form').submit(function(e){
		e.preventDefault();
		
		let arrayData = $(this).serializeArray();
		let data = {};
		
		arrayData.forEach((value) => {
			data[value.name] = value.value;
		})
		
		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/provider/store",
			data: JSON.stringify(data),
			success: function(response){
				$('#sidenav-of-creation .close-sidenav').trigger('click');
				table.ajax.reload();
				showNotification('success', response);
				$('#provider-form')[0].reset();
			},
			error: function(response){
				console.log(response);
				showNotification('error', response.responseText);
			}
		});
	})
})

function addProduct(){
	let productCode = $('#productCode');
	let quantity = $('#quantity');

		$.ajax({
			method: "GET",
			headers: { 
				'Content-Type': 'application/json' 
			},
			url: `product/find?code=${productCode.val()}`,
			success: function(response){
				response['quantity'] = quantity.val();
				let shoppingData = shoppingTable.rows().data();
				if(shoppingData.filter(item => item.code == response.code).length && shoppingData.data().count()){
					showNotification('error', "Produto já adicionado");
				}else{
					productCode.val('')
					quantity.val('')
					shoppingTable.row.add(response).draw(false);
				}

			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		})
}

function showShoppingSidenav(row){
	$('#shopping-form input[name="name"]').val(table.row(row).data().name);
	$('#shopping-form input[name="provider-position"]').val(row);
	showSidenav('#buy-sidenav');
}

function removeProduct(row){
	shoppingTable.row(row).remove().draw();	
}

function removeProvider(row){
	let data = table.row( row ).data();
	delete data.registerDate;
	$.ajax({
		method: "DELETE",
		headers: { 
	        'Content-Type': 'application/json' 
	    },
		url: "/provider/destroy",
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
	$('#report-form input[name="providerId"]').val(table.row(row).data().id);
	$('#report-sidenav input[name="providerName"]').val(table.row(row).data().name);
	showSidenav('#report-sidenav');
}