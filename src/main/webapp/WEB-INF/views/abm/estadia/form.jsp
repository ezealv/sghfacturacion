<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Estadia</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>

<body>
	<div class="generic-container">
		<%@include file="../../authheader.jsp"%>

		<div class="well lead">Estadia</div>
		<form:form method="POST" modelAttribute="entity"
			class="form-horizontal">
			<%
				/* ******************************************************************************** *
					  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
					  ******************************************************************************** */
			%>
			<form:input type="hidden" path="idEstadia" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=estadoEstadia>Estado</label>
					<div class="col-md-7">
						<form:select path="estadoEstadia" items="${estadoEstadia}"
							multiple="false" itemValue="idEstadoEstadia"
							itemLabel="estado" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="estadoEstadia" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for=planAlojamiento>Plan Alojamiento</label>
					<div class="col-md-7">
						<form:select path="planAlojamiento" items="${planAlojamiento}"
							multiple="false" itemValue="idPlanAlojamiento"
							itemLabel="plan" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="planAlojamiento" class="help-inline" />
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="precioPorNoche">Precio por noche</label>
					<div class="col-md-7">
						<form:input type="number" step="any" path="precioPorNoche" id="precioPorNoche" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="precioPorNoche" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="diaCheckIn">Ingreso</label>
					<div class="col-md-7">
						<form:input type="date" path="diaCheckIn"
							id="diaCheckIn" class=" form-control input-sm" />
						<div class="has-error">
							<form:errors path="diaCheckIn" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="diaCheckOut">Egreso</label>
					<div class="col-md-7">
						<form:input type="date" path="diaCheckOut"
							id="diaCheckOut" class=" form-control input-sm" />
						<div class="has-error">
							<form:errors path="diaCheckOut" class="help-inline" />
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