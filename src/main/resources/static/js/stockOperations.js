    /*<![CDATA[*/
   $(document ).ready(function() {
	    console.log( "ready!" );
	    $.ajax({
            url : "/v1/stocks",
            success : function(response) {
            	for(i=0; i< response.body.length; i++) {
            		
            		var updatedAt = new Date(response.body[i].updatedAt)
            		var formattedDate = updatedAt.getFullYear() + "/" + (updatedAt.getMonth() + 1) + "/" + updatedAt.getDate()+ "  " + updatedAt.getHours() + ":" + updatedAt.getMinutes() + ":" + updatedAt.getSeconds()
            		$('#stockTable').append('<tr>');
	                  	$('#stockTable').append('<td>' + response.body[i].id +'</td>');
	             		$('#stockTable').append('<td  style="word-break: break-word">' + response.body[i].name +'</td>');
	             		$('#stockTable').append('<td>' + response.body[i].currentPrice +'</td>');
	             		$('#stockTable').append('<td>' + formattedDate +'</td>');
	             		$('#stockTable').append('<td>  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editStockModal" data-stock-name="'+response.body[i].name +'", data-stock-id="'+ response.body[i].id + '", data-stock-price="'+response.body[i].currentPrice +'"> Edit</button></td>');
                  	$('#stockTable').append('</tr>');
            	}
            },
            error : function(e) {
               alert('Invalid input!: ' + e.responseJSON.msg);
            }
        }); 
	    
	});

	$('#editStockModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var stockName = button.data('stock-name') // Extract info from data-* attributes
		var stockPrice = button.data('stock-price')
		var stockId = button.data('stock-id')
		// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		var modal = $(this)
		modal.find('.modal-title').text('Edit Stock ')
		modal.find('#stock-id').val(stockId)
		modal.find('#stock-name').val(stockName)
		modal.find('#stock-curr-price').val(stockPrice)

	})
	
	
	function modifyStock(){  
	var editStock = {};
	editStock.name = $('#stock-name').val();
	editStock.id = $('#stock-id').val();
	editStock.currentPrice =  $('#stock-curr-price').val();
	
    var r = confirm("Are you sure you want to modify the stock ?");

    if (r == true) {
        $.ajax({
            type : "PUT",
            contentType: "application/json",
            url : "/v1/stocks/"+ editStock.id,
            data: JSON.stringify(editStock),
            dataType: 'json',
            success : function(response) {
              // alert("Suceeded!");
               location.reload();
            },
            error : function(e) {
               alert('Invalid input!: ' + e.responseJSON.msg);
            }
        }); 
    }
    

}
    /*]]>*/
