<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%
	request.setAttribute("success", request.getParameter("success"));
%>
<div class="row clearfix">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="card">
			<div class="body">
				<div class="row clearfix">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Descripcion</th>
								<th>Precio</th>
								<th>Fecha</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${preTickets}" var="entity">
								<tr>
									<td>${entity.descripcion}</td>
									<td>${entity.precio}</td>
									<td>${entity.fecha}</td>
									<td class="col-md-1">
										<button class="btn bg-green btn-circle waves-effect waves-circle waves-float"
											onclick="preTicketSeleccionado(${entity.idPreTicket},'${entity.descripcion}',${entity.precio})">
											<i class="material-icons" aria-hidden="true">add</i>
										</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
