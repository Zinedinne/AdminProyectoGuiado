package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.Singleton;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntregaDAO {
    public boolean entregarActividad(Entrega entrega) throws SQLException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(entrega.getArchivo());
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "INSERT INTO Entrega (fechaEntrega, documento, nombreDocumento, Actividades_idActividad) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, entrega.getFechaEntrega());
                prepararSentencia.setBinaryStream(2, fis, (int)entrega.getArchivo().length());
                prepararSentencia.setString(3, entrega.getNombreArchivo());
                prepararSentencia.setInt(4, entrega.getIdActividad());
                int executeUpdate = prepararSentencia.executeUpdate();
                if(executeUpdate == 1){
                    resultado = true;
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

    public boolean modificarActividad(Entrega entrega) throws SQLException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(entrega.getArchivo());
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "Update Entrega SET documento=?, nombreDocumento=?, fechaEntrega=? WHERE Actividades_idActividad = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setBinaryStream(1, fis, (int)entrega.getArchivo().length());
                prepararSentencia.setString(2, entrega.getNombreArchivo());
                prepararSentencia.setString(3, entrega.getFechaEntrega());
                prepararSentencia.setInt(4, entrega.getIdActividad());
                int executeUpdate = prepararSentencia.executeUpdate();
                if(executeUpdate == 1){
                    resultado = true;
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

    public Entrega verificarEntrega(int idActividad) throws SQLException {
        boolean resultado = false;
        Entrega entrega = new Entrega();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT * FROM Entrega WHERE Actividades_idActividad = ? ";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idActividad));
                ResultSet resultSet = prepararSentencia.executeQuery();
                if(resultSet.next()){
                    entrega.setNombreArchivo(resultSet.getString("nombreDocumento"));
                    entrega.setFechaEntrega(resultSet.getString("fechaEntrega"));
                    entrega.setIdEntrega(resultSet.getInt("idEntrega"));
                }
                conexion.close();
            } catch (SQLException ex) {
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return entrega;
    }
    public InputStream obtenerDocumento(int idActividad){
        InputStream inputStream = null;
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion!=null){
            String consulta = "SELECT documento FROM Entrega WHERE Actividades_idActividad = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idActividad));
                ResultSet resultSet = prepararSentencia.executeQuery();
                if(resultSet.next()){
                    inputStream = resultSet.getBinaryStream("documento");
                }
                conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return inputStream;
    }
}
