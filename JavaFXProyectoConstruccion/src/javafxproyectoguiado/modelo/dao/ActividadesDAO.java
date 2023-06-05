package javafxproyectoguiado.modelo.dao;

import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import modelo.ConexionBD;
import util.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadesDAO {

    public Actividades getActividad(int idActividad) throws SQLException {
        Actividades actividad = new Actividades();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT *\n" +
                    "FROM Actividad " +
                    "WHERE idActividad = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idActividad));
                ResultSet resultado = prepararSentencia.executeQuery();

                if(resultado.next()){
                    actividad.setTitulo(resultado.getString("titulo"));
                    actividad.setDescripcion(resultado.getString("descripción"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                }
                conexion.close();
            } catch (SQLException ex) {
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return actividad;
    }
}
