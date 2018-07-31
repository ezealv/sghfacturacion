package sgh.mansilla.modelo.datos.facturacion;

import sgh.mansilla.modelo.dao.Identificable;
import sgh.mansilla.modelo.datos.persona.TipoDocumento;

public class ClienteComprobante implements Identificable<Integer>{

	private Integer idClienteComprobante;
	private String nombre;
	private String apellido;
	private String razonSocial;
	private TipoDocumento tipoDocumento;
	private String documento;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return idClienteComprobante;
	}
	
	public ClienteComprobante(){
		
	}

	public Integer getIdClienteComprobante() {
		return idClienteComprobante;
	}

	public void setIdClienteComprobante(Integer idClienteComprobante) {
		this.idClienteComprobante = idClienteComprobante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
