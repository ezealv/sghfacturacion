<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@include file="../../header.jsp"%>

<div class="block-header">
	<h2>CREAR PRE-TICKET</h2>
</div>
<form:form method="POST" modelAttribute="entity" class="">
			<%
				/* ******************************************************************************** *
												  ********************* COMIENZO DE LOS CAMPOS DE LA ENTIDAD ********************* *
												  ******************************************************************************** */
			%>
			<form:input type="hidden" path="idPreTicket" id="id" />
			
			<div class="row clearfix">
				<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<div class="header bg-blue">
							<h2>CLIENTE</h2>
						</div>
						<div class="body">
							<div class="row clearfix">
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
									<label for="email_address_2">Cliente</label>
								</div>
								<div class="col-md-8">
									<form:select path="clientePreTicket"
										items="${clienteComprobante}" multiple="false"
										itemValue="idClienteComprobante" itemLabel="nombre"
										class="form-control show-tick" data-live-search="true" />
									<div class="has-error">
										<form:errors path="clientePreTicket" class="help-inline" />
									</div>
								</div>
								
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
									<label for="">Descripcion</label>
								</div>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<div class="form-group">
										<div class="form-line">
											<form:input type="text" path="descripcion"
												id="descripcion" class="form-control" />
											<div class="has-error">
												<form:errors path="descripcion" class="help-inline" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
									<label for="">Precio</label>
								</div>
								<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
									<div class="form-group">
										<div class="form-line">
											<form:input type="number" path="precio"
												id="precio" class="form-control" />
											<div class="has-error">
												<form:errors path="precio" class="help-inline" />
											</div>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
									<label for="">Fecha</label>
								</div>
								<div class="col-md-8">
									<div class="form-group">
										<div class="form-line">
											<form:input type="text" path="fecha"
												id="fecha" placeholder="Fecha del comprobante"
												class="datepicker form-control" />
											<div class="has-error">
												<form:errors path="fecha" class="help-inline" />
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${edit}">
						             <div class="row clearfix">
						                 <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 m-b-20">
						                     
						                     <input type="submit" id="btnSubmit" value="EDITAR" class="btn btn-block btn-lg btn-primary waves-effect" />
						                 </div>
						                 <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 m-b-20">
						                    <a class="btn btn-block btn-lg btn-default waves-effect" href="<c:url value='list' />">VOLVER</a>
						                 </div>
						             </div>
								</c:when>
								<c:otherwise>
						             <div class="row clearfix">
						                 
						                 <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 m-b-20">
						                     <input type="submit" id="btnSubmit" value="CREAR" class="btn btn-block btn-lg btn-primary waves-effect" />
						                 </div>
						                 <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 m-b-20">
						                    <a class="btn btn-block btn-lg btn-default waves-effect" href="<c:url value='list' />">VOLVER</a>
						                 </div>
						             </div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>

		</form:form>

	
<%@include file="../../footer.jsp"%>
<script src=" <c:url value= '/static/js/comprobante.js'/>"
	type="text/javascript"></script>