$(function () {

	    $('.datepicker').bootstrapMaterialDatePicker({
	        format: 'DD-MM-YYYY',
	        clearButton: true,
	        weekStart: 1,
	        time: false
	    });

	    //Masked Input ============================================================================================================================
	    var $demoMaskedInput = $('.demo-masked-input');

	    //Date
	    $demoMaskedInput.find('.date').inputmask('dd/mm/yyyy', { placeholder: '__/__/____' });

	    //Time
	    $demoMaskedInput.find('.time12').inputmask('hh:mm t', { placeholder: '__:__ _m', alias: 'time12', hourFormat: '12' });
	    $demoMaskedInput.find('.time24').inputmask('hh:mm', { placeholder: '__:__ _m', alias: 'time24', hourFormat: '24' });

	    //Date Time
	    $demoMaskedInput.find('.datetime').inputmask('d/m/y h:s', { placeholder: '__/__/____ __:__', alias: "datetime", hourFormat: '24' });

	    //Mobile Phone Number
	    $demoMaskedInput.find('.mobile-phone-number').inputmask('+99 (999) 999-99-99', { placeholder: '+__ (___) ___-__-__' });
	    //Phone Number
	    $demoMaskedInput.find('.phone-number').inputmask('+99 (999) 999-99-99', { placeholder: '+__ (___) ___-__-__' });

	    //Dollar Money
	    $demoMaskedInput.find('.money-dollar').inputmask('99,99 $', { placeholder: '__,__ $' });
	    //Euro Money
	    $demoMaskedInput.find('.money-euro').inputmask('99,99 €', { placeholder: '__,__ €' });

	    //IP Address
	    $demoMaskedInput.find('.ip').inputmask('999.999.999.999', { placeholder: '___.___.___.___' });

	    //Credit Card
	    $demoMaskedInput.find('.credit-card').inputmask('9999 9999 9999 9999', { placeholder: '____ ____ ____ ____' });

	    //Email
	    $demoMaskedInput.find('.email').inputmask({ alias: "email" });

	    //Serial Key
	    $demoMaskedInput.find('.key').inputmask('****-****-****-****', { placeholder: '____-____-____-____' });
	    //===========================================================================================================================================

	  
	seleccionarSelectTipoIva();
	calcularSubtotales();
	if ($('.js-basic-example').length > 0) { 
		$('.js-basic-example').DataTable({
	        responsive: true
	    });
	}
	if ($('.js-exportable').length > 0) { 
		$('.js-exportable').DataTable({
	        dom: 'Bfrtip',
	        responsive: true,
	        buttons: [
	            'csv', 'excel', 'pdf', 'print'
	        ]
	    });
	}
	$('.form-line').removeClass('focused');
});



function agregarConcepto(){
	var indice = parseInt($("#indiceConceptos").val());
	$("#conceptos-tbody").append(
			'<tr id="row-concepto-'+indice+'">'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><div class="form-line"><input class="form-control" type="number" id="cantidad-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].cantidad" /></div></div></td>'
			+'<td class="col-md-4"><div class="form-group form-group-sm"><div class="form-line"><input class="form-control" type="text" id="descripcion-'+indice+'" name="conceptos['+indice+'].descripcion" /></div></div></td>'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><div class="form-line"><input class="form-control" type="number" id="precio-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].precio" /></div></div></td>'
			+'<td class="col-md-2"><select id="tipoIva-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].tipoIva" class="form-control">'
			+'<option value="1">No Gravado</option>'
			+'<option value="2">Excento</option>'
			+'<option value="3">0 %</option>'
			+'<option value="4">10,50 %</option>'
			+'<option value="5">21 %</option>'
			+'<option value="6">27 %</option>'
			+'</select></td>'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><input class="form-control" type="number" id="subtotal-'+indice+'" name="subtotal" readonly/></div></td>'
			+'<td class="col-md-2"><button class="btn btn-danger btn-circle waves-effect waves-circle waves-float" onclick="eliminarConcepto('+indice+')"> <i class="material-icons">delete</i></button></td>'+
			'</tr>');
	$("#tipoIva-"+indice).selectpicker();
	indice++;
	$("#indiceConceptos").val(indice);
}

function agregarPreTicket(){
	var id = $("#clienteComprobante").val();
	$.ajax({
        type: 'GET',
        data: { id: id },
        url: '../comprobante/TraerPreTicketsPorCliente',
        success: function (data) {
        	$('#pretickets-modal-body').html(data);
        	$('#modalPreTickets').modal('show');
        },
        error: function (data, errorThrown) {
        	showNotification("alert-danger","Ha ocurrido un error al agregar el Pre-Ticket.","top","right","","",1000);
        }
    });
}

function preTicketSeleccionado(id,descripcion,precio){
	var preTicketExistente = false;
	$("[id*='.preTicket']").each(function(elemento){
		  if($(this).val() == id){
			  preTicketExistente = true;
		  }
	});
	if(preTicketExistente){
		showNotification("alert-warning","El Pre-Ticket ya fue agregado previamente.","top","right","","",1000);
		return;
	}
	var indice = parseInt($("#indiceConceptos").val());
	$("#conceptos-tbody").append(
			'<tr id="row-concepto-'+indice+'">'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><input class="form-control" value="1" type="number" readonly id="cantidad-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].cantidad" /></div></td>'
			+'<td class="col-md-5"><div class="form-group form-group-sm"><input class="form-control" value="'+descripcion+'" readonly type="text" id="descripcion-'+indice+'" name="conceptos['+indice+'].descripcion" /></div></td>'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><input class="form-control" value="'+precio+'" type="number" readonly id="precio-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].precio" /></div></td>'
			+'<td class="col-md-2"><select id="tipoIva-'+indice+'" onchange="calcularSubtotal('+indice+')" name="conceptos['+indice+'].tipoIva" class="form-control">'
			+'<option value="1">No Gravado</option>'
			+'<option value="2">Excento</option>'
			+'<option value="3">0 %</option>'
			+'<option value="4">10,50 %</option>'
			+'<option value="5">21 %</option>'
			+'<option value="6">27 %</option>'
			+'</select></td>'
			+'<td class="col-md-1"><div class="form-group form-group-sm"><input class="form-control" type="number" id="subtotal-'+indice+'" name="subtotal" readonly/></div></td>'
			+'<td class="col-md-2"><span class="label label-success">Pre-Ticket</span> <button class="btn btn-danger btn-circle waves-effect waves-circle waves-float" onclick="eliminarConcepto('+indice+')"> <i class="material-icons">delete</i></button></td>'
			+'<input type="hidden" id="conceptos['+indice+'].preTicket" name="conceptos['+indice+'].preTicket" value="'+id+'" />'
			+'</tr>');
	$("#tipoIva-"+indice).selectpicker();
	indice++;
	$("#indiceConceptos").val(indice);
	calcularSubtotales();
	showNotification("alert-success","Pre-Ticket agregado con exito.","top","right","","",1000);
}


function calcularSubtotal(indice){
	var subtotal = $("#cantidad-"+indice).val()*$("#precio-"+indice).val();
	$("#subtotal-"+indice).val(subtotal);
	calcularTotales();
}


function eliminarConcepto(indice){
	$("#row-concepto-"+indice).remove();
	calcularSubtotales();
}

function calcularSubtotales(){
	var indiceConceptos= $("#indiceConceptos").val();
	for(var i = 0; i<=indiceConceptos;i++){
		$("#subtotal-"+i).val($("#cantidad-"+i).val()*$("#precio-"+i).val());
	}
	calcularTotales();
}

function calcularTotales(){
	var indiceConceptos= $("#indiceConceptos").val();
	var totalIva = 0;
	var total = 0;
	for(var i = 0; i<=indiceConceptos;i++){
		if($("#subtotal-"+i).length>0){
			var subtotalParcial = parseInt($("#subtotal-"+i).val());
			var ivaSeleccionado = $("#tipoIva-"+i).val();
			var valorIvaSeleccionado = $("#valoriva"+ivaSeleccionado).val();
			
			if(ivaSeleccionado > 2){
				totalIva = totalIva + ((subtotalParcial*valorIvaSeleccionado)/100);
			}
			total = total + subtotalParcial;
		}
	}
	total = total + totalIva;
	$("#totalIva").val(totalIva);
	$("#importeTotal").val(total);
}

function seleccionarSelectTipoIva(){
	$(".selectTipoIva").each(function() {
		var valor = $(this).attr("value");
	    $(this).val(valor);
	    $(this).selectpicker('render');
	});
	
}

//function facturar(){
//	$("#facturar").val("true");
//	$("#btnSubmit").click();
//}

function DescargarComprobante(){
	$("#btnSubmit").click();
}

function DescargarComprobante(id){
	$.ajax({
        type: 'GET',
        data: { id: id },
        url: '../comprobante/Download',
        success: function (data) {

        },
        error: function (data, errorThrown) {

        }
    });
}

function facturar() {
    swal({
        title: "Confirma la emision del comprobante ?",
        text: "Una vez emitido no podra volver a editarlo.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Confirmar",
        closeOnConfirm: true
    }, function () {
    	$("#facturar").val("true");
    	$("#btnSubmit").click();
    });
}


//
//// Start indexing at the size of the current list
//var index = ${fn:length(employer.employees)};
//
//// Add a new Employee
//$("#add").off("click").on("click", function() {
//    $(this).before(function() {
//        var html = '<div id="employees' + index + '.wrapper" class="hidden">';                    
//        html += '<input type="text" id="employees' + index + '.firstname" name="employees[' + index + '].firstname" />';
//        html += '<input type="text" id="employees' + index + '.lastname" name="employees[' + index + '].lastname" />';
//        html += '<input type="hidden" id="employees' + index + '.remove" name="employees[' + index + '].remove" value="0" />';
//        html += '<a href="#" class="employees.remove" data-index="' + index + '">remove</a>';                    
//        html += "</div>";
//        return html;
//    });
//    $("#employees" + index + "\\.wrapper").show();
//    index++;
//    return false;
//});
//
//// Remove an Employee
//$("a.employees\\.remove").off("click").on("click", function() {
//    var index2remove = $(this).data("index");
//    $("#employees" + index2remove + "\\.wrapper").hide();
//    $("#employees" + index2remove + "\\.remove").val("1");
//    return false;
//});
