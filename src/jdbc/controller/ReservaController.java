package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import jdbc.factory.ConnectionFactory;
import jdbc.dao.ReservaDAO;
import jdbc.modelo.Reserva;

public class ReservaController {
 private ReservaDAO reservaDAO;
 
 public ReservaController() {
		Connection con = new ConnectionFactory().recuperarConexion();
		this.reservaDAO = new ReservaDAO(con);
	}
 
	public void guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
	}
		
	public List<Reserva> buscar() {
		return this.reservaDAO.buscar();
	}
	
	public List<Reserva> buscarId(String id) {
		return this.reservaDAO.buscarId(id);
	}
	
	public void actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		this.reservaDAO.Actualizar(fechaE, fechaS, valor, formaPago, id);
	}
	
	public void Eliminar(Integer id) {
		this.reservaDAO.Eliminar(id);
	}
}
