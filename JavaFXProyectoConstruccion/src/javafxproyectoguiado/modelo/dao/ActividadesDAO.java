package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
                    actividad.setDescripcion(resultado.getString("descripcion"));
                    actividad.setFechaInicio(resultado.getString("fechaInicio"));
                    actividad.setFechaFin(resultado.getString("fechaFin"));
                    actividad.setIdActividad(resultado.getInt("idActividad"));
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.valueOf("ERROR"));
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return actividad;
    }

    public boolean registrarActividad(Actividades actividades, int idAlumno) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "INSERT INTO actividad (`descripcion`, `fechaInicio`, `fechaFin`, `titulo`) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividades.getDescripcion());
                prepararSentencia.setString(2, actividades.getFechaInicio());
                prepararSentencia.setString(3, actividades.getFechaFin());
                prepararSentencia.setString(4, actividades.getTitulo());
                int executeUpdate = prepararSentencia.executeUpdate();
                if(executeUpdate >= 1){
                    String consulta2 = "INSERT INTO usuarioactividad (`Usuario_idUsuario`, `Actividad_idActividad`) VALUES (?,last_insert_id())";
                    PreparedStatement prepararSentencia2 = conexion.prepareStatement(consulta2);
                    prepararSentencia2.setInt(1, idAlumno);
                    int executeUpdate2 = prepararSentencia2.executeUpdate();
                    if(executeUpdate2 >= 1){
                        resultado = true;
                    }
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("Error", ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return resultado;
    }

    public boolean modificarActividad(Actividades actividad) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "UPDATE `proyecto`.`actividad` SET `descripcion` = ?, `fechaInicio` = ?, `fechaFin` = ?, `titulo` = ? WHERE (`idActividad` = ?);\n";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, actividad.getDescripcion());
                prepararSentencia.setString(2, actividad.getFechaInicio());
                prepararSentencia.setString(3, actividad.getFechaFin());
                prepararSentencia.setString(4, actividad.getTitulo());
                prepararSentencia.setInt(5, actividad.getIdActividad());
                int executeUpdate = prepararSentencia.executeUpdate();
                if(executeUpdate == 1){
                    resultado = true;
                }
                conexion.close();
            } catch (SQLException ex) {
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return resultado;
    }
}
