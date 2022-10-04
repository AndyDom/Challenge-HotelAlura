package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;
	
	public ReservaDAO(Connection connection) {
		this.con = connection;
	}
	
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fechaEntrada, fechaSalida, valor, formaPago) VALUES (?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				preparedStatement.setDate(1, reserva.getFechaEntrada());
				preparedStatement.setDate(2, reserva.getFechaSalida());
				preparedStatement.setString(3, reserva.getTarifa());
				preparedStatement.setString(4, reserva.getFormaPago());

				preparedStatement.executeUpdate();

				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.execute();

				transformarResultSetEnReserva(reservas, preparedStatement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscarId(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {

			String sql = "SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas WHERE id = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, id);
				preparedStatement.execute();

				transformarResultSetEnReserva(reservas, preparedStatement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Eliminar(Integer id) {
		try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM reservas WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		try (PreparedStatement preparedStatement = con
				.prepareStatement("UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?")) {
			preparedStatement.setDate(1, fechaE);
			preparedStatement.setDate(2, fechaS);
			preparedStatement.setString(3, valor);
			preparedStatement.setString(4, formaPago);
			preparedStatement.setInt(5, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
						
	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet preparedStatement = pstm.getResultSet()) {
			while (preparedStatement.next()) {
				Reserva produto = new Reserva(preparedStatement.getInt(1), preparedStatement.getDate(2), preparedStatement.getDate(3), preparedStatement.getString(4), preparedStatement.getString(5));

				reservas.add(produto);
			}
		}
	}
}
