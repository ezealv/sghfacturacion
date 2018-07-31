<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%
	request.setAttribute("success", request.getParameter("success"));
	request.setAttribute("titulo", "Lista de Comprobantes");
%>

<%@include file="../../header.jsp"%>
<div class="block-header">
	<h2>
		COMPROBANTES <small>Lista de comprobantes guardados</a></small>
	</h2>
</div>
<!-- Basic Examples -->
<div class="row clearfix">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="card">
			<div class="header">
                <sec:authorize access="hasRole('ADMIN')">
					<a href="<c:url value='newComprobante' />"
						class="btn btn-primary waves-effect">Crear Comprobante</a>
				</sec:authorize>
            </div>
			<div class="body">
				<table
					class="table table-bordered table-striped table-hover js-basic-example dataTable">
					<thead>
						<tr>

							<%
								/* ******************************************************************************** *
								  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
								  ******************************************************************************** */
							%>
							<th>Nro</th>
							<th>Tipo</th>
							<th>Cliente</th>
							<th>Moneda</th>
							<th>Forma de Pago</th>
							<th>Importe Total</th>
							<th>CAE</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${entities}" var="entity">
							<tr>

								<%
									/* ******************************************************************************** *
											  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
											  ******************************************************************************** */
								%>
								<td>${entity.nroComprobante}</td>
								<td>${entity.tipoComprobante.descripcion}</td>
								<td>${entity.clienteComprobante.nombre}</td>
								<td>${entity.moneda.descripcion}</td>
								<td>${entity.formaDePago.descripcion}</td>
								<td>${entity.importeTotal}</td>
								<td>${entity.cae}</td>
							

								<%
									/* ******************************************************************************** *
											  *********************** FIN DE LOS CAMPOS DE LA ENTIDAD ************************ *
											  ******************************************************************************** */
								%>
								<td>
								<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
									<button type="button" onclick="javascript:location.href='<c:url value='editComprobante-${entity.id}' />'" class="btn btn-default btn-circle waves-effect waves-circle waves-float">
									    <i class="material-icons">mode_edit</i>
									</button>
								</sec:authorize>
								<sec:authorize access="hasRole('ADMIN')">
									<button type="button" onclick="javascript:location.href='<c:url value='delete-${entity.id}' />'" class="btn bg-red btn-circle waves-effect waves-circle waves-float">
									    <i class="material-icons">delete</i>
									</button>
								</sec:authorize>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<%@include file="../../footer.jsp"%>
<!-- Jquery DataTable Plugin Js -->
<script
	src=" <c:url value= '/static/material/plugins/jquery-datatable/jquery.dataTables.js'/>"
	type="text/javascript"></script>
<script
	src=" <c:url value= '/static/material/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js'/>"
	type="text/javascript"></script>
<script src=" <c:url value= '/static/js/comprobante.js'/>"
	type="text/javascript"></script>