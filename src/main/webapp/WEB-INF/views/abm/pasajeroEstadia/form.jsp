<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asignar Habitacion/Pasajero a Estadia</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

<script src=" <c:url value= '/static/js/jquery-1.12.4.js'/>" type="text/javascript"></script>

<script type="text/javascript">
     $(document).ready(function(){
    	 var dato = <%= request.getParameter("idEstadia") %>;
    	 if(dato != null)
    	 {
    		 $("#estadia").val(<%= request.getParameter("idEstadia") %>).attr('selected', 'selected');
    		 document.getElementById("estadia").setAttribute("readonly", true);

    	 }

    	 $("#Actualizar").attr("href", "<c:url value='list?idEstadia="+dato+"'/>");
    	 $("#Guardar").attr("href", "<c:url value='list?idEstadia="+dato+"'/>");

     });
</script>

</head>

<body>
	<div class="generic-container">
		<%@include file="../../authheader.jsp"%>

		<div class="well lead">Asignar Habitacion/Pasajero a Estadia</div>
		<form:form method="POST" modelAttribute="entity"
			class="form-horizontal">
			<%
				/* ******************************************************************************** *
						  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
						  ******************************************************************************** */
			%>
			<form:input type="hidden" path="idPasajeroEstadia" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=estadia>Estadia</label>
					<div class="col-md-7">
						<form:select path="estadia" items="${estadia}" id="estadia"
							multiple="false" itemValue="idEstadia"
							itemLabel="descripcion" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="estadia" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=pasajero>Pasajero</label>
					<div class="col-md-7">
						<form:select path="pasajero"
							multiple="false" class="form-control input-sm" >
							<form:option  value="" />
    						<form:options items="${pasajero}" itemValue="idContacto"
							itemLabel="nombreCompleto" />
							</form:select>
						<div class="has-error">
							<form:errors path="pasajero" class="help-inline" />
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=habitacion>Habitacion</label>
					<div class="col-md-7">
						<form:select path="habitacion" items="${habitacion}"
							multiple="false" itemValue="idHabitacion"
							itemLabel="descripcion" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="habitacion" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<%
				/* ******************************************************************************** *
						  ********************** FIN DE LOS CAMPOS DEL FORMULATION *********************** *
						  ******************************************************************************** */
			%>

			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Actualizar"
								class="btn btn-primary btn-sm" /> or <a
								id="Actualizar">Cancelar</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Guardar"
								class="btn btn-primary btn-sm" /> or <a
								id="Guardar">Cancelar</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>