<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
//prevents caching at the proxy server
%>


<html>

<!-- EJEMPLO EN https://developers.google.com/chart/interactive/docs/gallery/timeline -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ocupacion del Hotel</title>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/buttons.dataTables.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/jquery.dataTables.min.css' />" rel="stylesheet"></link>

<script src=" <c:url value= '/static/js/loader.js'/>" type="text/javascript"></script>
<script src=" <c:url value= '/static/js/jquery-1.12.4.js'/>" type="text/javascript"></script>


<script type="text/javascript">

	function dibujarTabla(desde,hasta){
		//Delete the datable object first
				$('#tablaOcupacion-container').empty();

				var colData = [];

				<c:forEach items="${fechas}" var="fechas">
					colData.push("${fechas}");
				</c:forEach>

			    var rowData = [];
			    var informacion = [];
			    var colores = [];
				<c:forEach items="${vista}" var="vista">

					var fila = ["${vista.habitacion.descripcion}"];
					var filaInf = ["${vista.habitacion.descripcion}"];
					var filaCol = ["${vista.habitacion.descripcion}"];

					<c:forEach items="${vista.ocupaciones}" var="ocupaciones">
						fila["${ocupaciones.columna}"] = "${ocupaciones.estadoEstadia}";
						filaInf["${ocupaciones.columna}"] = "${ocupaciones.informacion}";
						filaCol["${ocupaciones.columna}"] = "${ocupaciones.color}";
					</c:forEach>


					rowData.push(fila);
					informacion.push(filaInf);
					colores.push(filaCol);

				</c:forEach>


			    var data = {"Cols":colData, "Rows":rowData};

			    var table = $('<table/>').attr("id", "tablaOcupacion").addClass("table table-striped table-bordered").attr("cellspacing", "0").attr("width", "100%");

			    var tr = $('<tr/>');
			    table.append('<thead>').children('thead').append(tr);

			    for (var i=0; i< data.Cols.length; i++) {
			      tr.append('<td>'+data.Cols[i]+'</td>');
			    }

			    for(var r=0; r < data.Rows.length; r++){
			        var tr = $('<tr/>');
			        table.append(tr);
			        //loop through cols for each row...
			        for(var c=0; c < data.Cols.length; c++){

			        	if(data.Rows[r][c]){
			        		var info = data.Rows[r][c];
			        		tr.append('<td id="' + r + '-' + c + '" style="font-weight: bold; background: '+colores[r][c]+'">'+info+'</td>').attr("backgraund", "red");
			        	}
			        	else{
			        		var info = "";
			        		tr.append('<td id="' + r + '-' + c + '">'+info+'</td>');
			        	}

			        }
			    }

			    $('#tablaOcupacion-container').append(table);

			    var dataTable = $('#tablaOcupacion').removeAttr('width').DataTable( {
			    	scrollY:        "300px",
			        scrollX:        true,
			        scrollCollapse: true,
			        paging:         false,
			        columnDefs: [
			                     { width: 82, targets: 0 }
			                 ],
			        fixedColumns:   {
			            leftColumns: 1
			        },
			        responsive: true,
			    	retrieve: true,
					"language" : {
						"sProcessing" : "Procesando...",
						"sLengthMenu" : "Mostrar _MENU_ registros",
						"sZeroRecords" : "No se encontraron resultados",
						"sEmptyTable" : "Ningún dato disponible en esta tabla",
						"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
						"sInfoEmpty" : "Mostrando un total de 0 habitaciones",
						"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
						"sInfoPostFix" : "",
						"sSearch" : "Buscar:",
						"sUrl" : "",
						"sInfoThousands" : ",",
						"sLoadingRecords" : "Cargando...",
						"oPaginate" : {
							"sFirst" : "Primero",
							"sLast" : "Último",
							"sNext" : "Siguiente",
							"sPrevious" : "Anterior"
						}
					}
				});


			    var prueba = $('#tablaOcupacion').DataTable();
			    $('#tablaOcupacion tbody').on('click', 'tr', function() {
					var idClickeado = $(event.target)[0].id;
					var ids = idClickeado.split('-');

					var celda = document.getElementById(idClickeado);
					if(informacion[ids[0]][ids[1]])
					{
					  alert(informacion[ids[0]][ids[1]]);

					}

				});


	}

	$(document).ready(function() {

		$( "#fechas" ).submit(function(e) {
			var desde = document.getElementById("desde").value;
			var hasta = document.getElementById("hasta").value;
			document.location.href = "?desde=" + desde +"&hasta=" + hasta;

			e.preventDefault();
		});

		dibujarTabla(document.getElementById("desde").value, document.getElementById("hasta").value);

	});

</script>

</head>
<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">Ocupacion del Hotel</span>
			</div>
		</div>

		<sec:authorize access="hasAnyRole('ADMIN', 'USER','DBA')">
			<div>
				<form id="fechas" >
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="desde">Desde</label>
							<div class="col-md-7">
								<input type="date" path="desde" id="desde" class=" form-control input-sm" value="${desde}"/>
							</div>
						</div>

						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="hasta">Hasta</label>
							<div class="col-md-7">
								<input type="date" path="hasta" id="hasta"class=" form-control input-sm" value="${hasta}"/>
							</div>
						</div>

						<div class="form-actions floatRight">
							<input type="submit" value="Volver a consultar" class="btn btn-primary" />

						</div>
					</div>
				</form>

			</div>
		</sec:authorize>
	</div>

	<div class="generic-container">
		<sec:authorize access="hasAnyRole('ADMIN', 'USER','DBA')">
			<div>
				<div id="tablaOcupacion-container"></div>
			</div>
	</sec:authorize>
	</div>


</body>

<script src=" <c:url value= '/static/js/jquery.dataTables.min.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/dataTables.bootstrap.min.js'/>"
	type="text/javascript"></script>

<script src=" <c:url value= '/static/js/dataTables.fixedColumns.min.js'/>"
	type="text/javascript"></script>


<script src=" <c:url value= '/static/js/datatables.min.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/jszip.min.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/pdfmake.min.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/vfs_fonts.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/buttons.html5.min.js'/>"
	type="text/javascript"></script>
</html>
