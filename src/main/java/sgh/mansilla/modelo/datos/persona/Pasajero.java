package sgh.mansilla.modelo.datos.persona;

import java.io.Serializable;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import sgh.mansilla.modelo.dao.Identificable;

/**
 * @author Romina E. Mansilla - UNLA
 *
 */
public class Pasajero extends PersonaAbstracta implements Identificable<Integer>, Serializable{

	private String nombre;
	private String apellido;
	private TipoDocumento tipoDocumento;
	private String documento;
	private Date fechaDeNacimiento;
	Set<CaracteristicaPasajero> caracteristicas = new HashSet<CaracteristicaPasajero>();

	@Transient
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.idContacto;
	}

	public Pasajero() {
	}


	public Pasajero(String nombre, String apellido, TipoDocumento tipoDocumento, String documento,
			Date fechaDeNacimiento, Set<CaracteristicaPasajero> caracteristicas) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.caracteristicas = caracteristicas;
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

	@Temporal(TemporalType.DATE)
	public Date getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(Date fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}
	
	public String getNombreCompleto() {
		return apellido +", "+nombre;
	}


	public void addCaracteristicaPasajero(CaracteristicaPasajero caracteristica) {
		this.caracteristicas.add(caracteristica);
	}

	public Set<CaracteristicaPasajero> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(Set<CaracteristicaPasajero> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	@Transient
	public int getEdad() {
		Calendar nac = Calendar.getInstance();
		nac.setTime(fechaDeNacimiento);
		// return Calendar.getInstance().get(Calendar.YEAR) -
		// nac.get(Calendar.YEAR);

		long diasPasados = (Calendar.getInstance().getTimeInMillis() - nac.getTimeInMillis()) / (24 * 60 * 60 * 1000);

		return (int) diasPasados / 365;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("");
		if (nombre != null)
			builder.append(apellido).append(", ");
		if (apellido != null)
			builder.append(nombre).append(" (");
		if (tipoDocumento != null)
			builder.append(tipoDocumento).append(": ");
		if (documento != null)
			builder.append(documento);
		builder.append(")");
		return builder.toString();
	}


}
