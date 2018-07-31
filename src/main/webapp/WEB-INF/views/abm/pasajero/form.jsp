<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pasajeros</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>

<body>
	<div class="generic-container">
		<%@include file="../../authheader.jsp"%>

		<div class="well lead">Pasajeros</div>
		<form:form method="POST" modelAttribute="entity"
			class="form-horizontal">
			<%
				/* ******************************************************************************** *
						  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
						  ******************************************************************************** */
			%>
			<form:input type="hidden" path="idContacto" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="nombre">Nombre</label>
					<div class="col-md-7">
						<form:input type="text" path="nombre" id="nombre"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="nombre" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="apellido">Apellido</label>
					<div class="col-md-7">
						<form:input type="text" path="apellido" id="apellido"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="apellido" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="telefono">Telefono</label>
					<div class="col-md-7">
						<form:input type="text" path="telefono" id="telefono"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="telefono" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email">Email</label>
					<div class="col-md-7">
						<form:input type="text" path="email" id="email"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="email" class="help-inline" />
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="tipoDocumento">Tipo
						Documento</label>
					<div class="col-md-7">
						<form:select path="tipoDocumento" items="${tipoDocumento}"
							multiple="false" itemValue="idTipoDocumento"
							itemLabel="descripcion" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="tipoDocumento" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="documento">Documento</label>
					<div class="col-md-7">
						<form:input type="text" path="documento" id="documento"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="documento" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="fechaDeNacimiento">Fecha
						de Nacimiento</label>
					<div class="col-md-7">
						<form:input type="date" path="fechaDeNacimiento"
							id="fechaDeNacimiento" class=" form-control input-sm" />
						<div class="has-error">
							<form:errors path="fechaDeNacimiento" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="caracteristicas">Caracteristicas</label>
					<div class="col-md-7">
						<form:select path="caracteristicas"
							items="${caracteristicaPasajero}" multiple="true"
							itemValue="idCaracteristicaPasajero" itemLabel="descripcion"
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