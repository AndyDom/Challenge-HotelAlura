package jdbc.modelo;

import java.sql.Date;

public class Reserva {
	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String tarifa;
	private String formaPago;
	
	public Reserva(Date fechaEntrada, Date fechaSalida, String tarifa, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.tarifa = tarifa;
		this.formaPago = formaPago;
	}
	
	public Reserva(Integer id, Date fechaEntrada, Date fechaSalida, String tarifa, String formaPago) {

		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.tarifa = tarifa;
		this.formaPago = formaPago;
	}



	public Integer getId() {
		return id;
	}
	

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public String getTarifa() {
		return tarifa;
	}

	public String getFormaPago() {
		return formaPago;
	}


	
	
	
	
}
