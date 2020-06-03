$('document').ready(function(){
	$('#phone').mask('(00) 0 0000-0000');
	$('#cpf').mask('000.000.000-00', {reverse: true});
	$('#credit-limit').mask("#.##0,00", {reverse: true});
	
	
	$('.close-sidenav').click(function(){
		showSidenav($(this).data('sidenav'));
	})
	
	$('#sale-form').submit(function(e){
		e.preventDefault();
		let arrayData = $(this).serializeArray();
		let data = {
			"client": table.row($('input[name="client-position"]').val()).data(),
			'seller': sellerTable.row('.selected').data()
		};

		arrayData.forEach((value) => {
			data[value.name] = value.value;
		})
		data['saleItem'] = [];
		shoppingTable.rows().data().map(item => {
			let object = {
				'product': item,
				'saleQuantity': parseInt(item.quantity),
				'saleValue': item.unitPrice * item.quantity
			}
			delete object.product.quantity;
			data['saleItem'].push(object);
		})
		$.ajax({
			method: "POST",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: "/sale/store",
			data: JSON.stringify(data),
			success: function(response){
				$('#sale-sidenav .close-sidenav').trigger('click');
				shoppingTable.clear().draw();
				showNotification('success', response);
				$('#sale-form')[0].reset();
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		});
	})

	$('#report-form').submit(function(e){
		e.preventDefault();
		$.ajax({
			method: "GET",
			headers: { 
		        'Content-Type': 'application/json' 
		    },
			url: `report/client/sale?${$(this).serialize()}`,
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
				showSidenav("#sidenav-of-creation");
				table.ajax.reload();
				showNotification('success', response);
				$('#client-form')[0].reset();
			},
			error: function(response){
				showNotification('error', response.responseText);
			}
		});
	})

	$('#seller-table tbody').on( 'click', 'tr', function () {
		if($(this).hasClass('selected')){
      		$(this).removeClass('selected');
		}else{
			if($('#seller-table tbody tr.selected').length){
				$('#seller-table tbody tr.selected').removeClass('selected');
			}
			$(this).addClass('selected'); 
		}
    });
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

function removeProduct(row){
	shoppingTable.row(row).remove().draw();	
}

function showSaleSidenav(row){
	$('#sale-form input[name="clientName"]').val(table.row(row).data().name);
	$('#sale-form input[name="client-position"]').val(row);
	showSidenav('#sale-sidenav');
}

function showReportSidenav(row){
	$('#report-sidenav input').val('');
	$('#report-form input[name="clientId"]').val(table.row(row).data().id);
	$('#report-sidenav input[name="clientName"]').val(table.row(row).data().name);
	showSidenav('#report-sidenav');
}