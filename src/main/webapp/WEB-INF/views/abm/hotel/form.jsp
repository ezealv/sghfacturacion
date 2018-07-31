<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hotel</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="../../authheader.jsp" %>

		<div class="well lead">Hotel</div>
	 	<form:form method="POST" modelAttribute="entity" class="form-horizontal">
<% /* ******************************************************************************** *
	  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
	  ******************************************************************************** */ %>
			<form:input type="hidden" path="idHotel" id="id"/>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="nombreHotel">Nombre</label>
					<div class="col-md-7">
						<form:input type="text" path="nombreHotel" id="nombreHotel" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="nombreHotel" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="direccion">Direccion</label>
					<div class="col-md-7">
						<form:input type="text" path="direccion" id="direccion" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="direccion" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="telefono">Telefono</label>
					<div class="col-md-7">
						<form:input type="number" step="any" path="telefono" id="telefono" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="telefono" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email">Email</label>
					<div class="col-md-7">
						<form:input type="text" path="email" id="email" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="email" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="paginaWeb">Pagina Web</label>
					<div class="col-md-7">
						<form:input type="text" path="paginaWeb" id="paginaWeb" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="paginaWeb" class="help-inline"/>
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