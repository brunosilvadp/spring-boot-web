$('document').ready(function(){
    $('#find-sale-form').submit(function(e){
        table.clear().draw();
        e.preventDefault();
        $.ajax({
            method: 'GET',
            headers: { 
		        'Content-Type': 'application/json' 
		    },
            url: `/list/seller/sale?${$(this).serialize()}`,
            success: function(response){
                
                table.rows.add(response).draw();
                $('#find-sale-form')[0].reset();
            },
            error: function(response){
                showNotification('error', response.responseText);
            }
        })
    })
})

function removeSale(row){
	let id = table.row(row).data().id;
	$.ajax({
		method: "DELETE",
		headers: { 
	        'Content-Type': 'application/json' 
	    },
		url: `/sale/destroy?id=${id}`,
		success: function(response){
			table.row(row).remove().draw();
			showNotification('success', response);
		},
		error: function(response){
			showNotification('error', response.responseText);
		}
	});
};
