package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huespedes;

public class HuespedesDAO {
private Connection con;
	
	public HuespedesDAO(Connection connection) {
		this.con = connection;
	}
	
	public void guardar(Huespedes huesped) {
		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				preparedStatement.setString(1, huesped.getNombre());
				preparedStatement.setString(2, huesped.getApellido());
				preparedStatement.setDate(3, huesped.getFechaNacimiento());
				preparedStatement.setString(4, huesped.getNacionalidad());
				preparedStatement.setString(5, huesped.getTelefono());
				preparedStatement.setInt(6, huesped.getIdReserva());

				preparedStatement.execute();

				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Huespedes> listarHuespedes() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.execute();

				transformarResultSetEnHuesped(huespedes, preparedStatement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huespedes> buscarId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {

			String sql = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes WHERE idReserva = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setString(1, id);
				preparedStatement.execute();

				transformarResultSetEnHuesped(huespedes, preparedStatement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		try (PreparedStatement preparedStatement = con
				.prepareStatement("UPDATE huespedes SET nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono = ?, idReserva = ? WHERE id = ?")) {
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, apellido);
			preparedStatement.setDate(3, fechaNacimiento);
			preparedStatement.setString(4, nacionalidad);
			preparedStatement.setString(5, telefono);
			preparedStatement.setInt(6, idReserva);
			preparedStatement.setInt(7, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void Eliminar(Integer id) {
		try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM huespedes WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEnHuesped(List<Huespedes> reservas, PreparedStatement preparedStatement) throws SQLException {
		try (ResultSet resultSet = preparedStatement.getResultSet()) {
			while (resultSet.next()) {
				Huespedes huespedes = new Huespedes(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
				reservas.add(huespedes);
			}
		}				
	}
	
	
		
}
