$('document').ready(function(){
    $('#find-sale-form').submit(function(e){
        table.clear().draw();
        e.preventDefault();
        $.ajax({
            method: 'GET',
            headers: { 
		        'Content-Type': 'application/json' 
		    },
            url: `/list/client/sale?${$(this).serialize()}`,
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