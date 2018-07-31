<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Habitacion</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

<!--  <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>-->

</head>

<body>
	<div class="generic-container">
		<%@include file="../../authheader.jsp"%>

		<div class="well lead">Habitacion</div>
		<form:form method="POST" modelAttribute="entity"
			class="form-horizontal">
			<%
				/* ******************************************************************************** *
					  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
					  ******************************************************************************** */
			%>
			<form:input type="hidden" path="idHabitacion" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="descripcion">Descripción</label>
					<div class="col-md-7">
						<form:input type="text" path="descripcion" id="descripcion" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="descripcion" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="hotel">Hotel</label>
					<div class="col-md-7">
						<form:select path="hotel" items="${hotel}"
							multiple="false" itemValue="idHotel" itemLabel="nombreHotel"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="hotel" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="estadoHabitacion">Estado</label>
					<div class="col-md-7">
						<form:select path="estadoHabitacion" items="${estadoHabitacion}"
							multiple="false" itemValue="idEstadoHabitacion"
							itemLabel="descripcion" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="estadoHabitacion" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=tipoHabitacion>Tipo
						Habitacion</label>
					<div class="col-md-7">
						<form:select path="tipoHabitacion" items="${tipoHabitacion}"
							multiple="false" itemValue="idTipoHabitacion"
							itemLabel="toString" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="tipoHabitacion" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="caracteristicas">Caracteristicas</label>
					<div class="col-md-7">
						<form:select path="caracteristicas"
							items="${caracteristicaHabitacion}" multiple="true"
							itemValue="idCaracteristicaHabitacion" itemLabel="descripcion"
							hidden="false" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="caracteristicas" class="help-inline" />
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
								href="<c:url value='list' />">Cancelar</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Guardar"
								class="btn btn-primary btn-sm" /> or <a
								href="<c:url value='list' />">Cancelar</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

		</form:form>
	</div>
</body>
</html>