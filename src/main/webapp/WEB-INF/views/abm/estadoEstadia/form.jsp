<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Estados de Estadia</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="../../authheader.jsp" %>

		<div class="well lead">Estado de Estadia</div>
	 	<form:form method="POST" modelAttribute="entity" class="form-horizontal">
<% /* ******************************************************************************** *
	  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
	  ******************************************************************************** */ %>
			<form:input type="hidden" path="idEstadoEstadia" id="id"/>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="estado">Estado</label>
					<div class="col-md-7">
						<form:input readonly="true" type="text" path="estado" id="estado" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="estado" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="color">Color</label>
					<div class="col-md-7">
						<form:select path="color" id="color" class="form-control input-sm">

							<form:option value="coral" label="Coral"/>
							<form:option value="aquamarine" label="Verde Agua"/>
							<form:option value="darkgreen" label="Verde Oscuro"/>
							<form:option value="crimson" label="Rojo Palido"/>
							<form:option value="dodgerBlue" label="Celeste"/>
							<form:option value="midnightblue" label="Azul"/>
							<form:option value="darkcyan" label="Cian"/>
							<form:option value="gray" label="Gris"/>
							<form:option value="red" label="Rojo"/>
							<form:option value="yellow" label="Amarillo"/>

						</form:select>
						<div class="has-error">
							<form:errors path="color" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

<% /* ******************************************************************************** *
	  ********************** FIN DE LOS CAMPOS DEL FORMULATION *********************** *
	  ******************************************************************************** */ %>

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