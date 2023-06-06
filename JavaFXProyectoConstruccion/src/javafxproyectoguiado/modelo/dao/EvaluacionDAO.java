package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.Evaluacion;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvaluacionDAO {
    public Evaluacion verificarEvaluacion(int idActividad) throws SQLException {
        boolean resultado = false;
        Evaluacion evaluacion = new Evaluacion();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT * FROM evaluacion WHERE Actividad_idActividad = ? ";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idActividad));
                ResultSet resultSet = prepararSentencia.executeQuery();
                if(resultSet.next()){
                    evaluacion.setCalificacion(resultSet.getString("calificacion"));
                    evaluacion.setComentario(resultSet.getString("comentario"));
                    evaluacion.setIdEvaluacion(resultSet.getInt("idEvaluacion"));
                    evaluacion.setIdActividad(resultSet.getInt("Actividad_idActividad"));
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return evaluacion;
    }

    public boolean revisarActividad(Evaluacion evaluacion) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "Insert into evaluacion (calificacion, comentario, Actividad_idActividad) values (?,?,?)";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, evaluacion.getCalificacion());
                prepararSentencia.setString(2, evaluacion.getComentario());
                prepararSentencia.setInt(3, evaluacion.getIdActividad());
                int executeQuery = prepararSentencia.executeUpdate();
                if(executeQuery==1){
                    resultado = true;
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        }else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return resultado;
    }

    public boolean modificarRevision(Evaluacion evaluacion) throws SQLException{
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "UPDATE evaluacion SET calificacion = ?, comentario = ? WHERE idEvaluacion = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, evaluacion.getCalificacion());
                prepararSentencia.setString(2, evaluacion.getComentario());
                prepararSentencia.setInt(3, evaluacion.getIdEvaluacion());
                int executeUpdate = prepararSentencia.executeUpdate();
                if(executeUpdate==1){
                    resultado = true;
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.ERROR);

                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        }else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return resultado;
    }
}