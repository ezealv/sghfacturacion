<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<%
request.setAttribute("success", request.getParameter("success"));
request.setAttribute("idEstadia", request.getParameter("idEstadia"));
%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Habitacion/Pasajero por Estadia</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>

	<div class="generic-container">
		<%@include file="../../authheader.jsp" %>
		<div class="panel-heading"><span class="lead">Habitacion/Pasajero por Estadia</span></div>
			<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<table class="table table-hover">
	    		<thead>
		      		<tr>

<% /* ******************************************************************************** *
	  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
	  ******************************************************************************** */ %>
				       <th>Estadia</th>
				       <th>Habitacion</th>
				       <th>Pasajero</th>

				        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${entities}" var="entity">
					<tr>

<% /* ******************************************************************************** *
	  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
	  ******************************************************************************** */ %>

				<c:choose>
				    <c:when test="${entity.estadia.idEstadia == idEstadia}">
				        <td>${entity.estadia.descripcion}</td>
						<td>${entity.habitacion}</td>
						<td>${entity.pasajero.nombreCompleto}</td>

<% /* ******************************************************************************** *
	  *********************** FIN DE LOS CAMPOS DE LA ENTIDAD ************************ *
	  ******************************************************************************** */ %>

					    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
							<td><a href="<c:url value='edit-${entity.id}?idEstadia=${entity.estadia.idEstadia}&idPE=${entity.id}'/>" class="btn btn-success custom-width">Modificar</a></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
							<td><a href="<c:url value='delete-${entity.id}?idEstadia=${entity.estadia.idEstadia}' />" class="btn btn-danger custom-width">Eliminar</a></td>
        				</sec:authorize>
        			   </c:when>
			</c:choose>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
		<sec:authorize access="hasRole('ADMIN')">
			<td><a href="<c:url value='new?idEstadia=${idEstadia}' />" class="btn btn-success custom-width">Agregar</a></td>
	 	</sec:authorize>
   	</div>
</body>
</html>