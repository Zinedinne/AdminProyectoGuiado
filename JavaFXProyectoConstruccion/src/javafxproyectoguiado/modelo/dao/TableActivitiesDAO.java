package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import javafxproyectoguiado.modelo.pojo.Usuario;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableActivitiesDAO {
    public static List<TableActivities> getActivities(int idUsuario) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        List<TableActivities> listaActividades = new ArrayList<>();
        if(conexion != null){
            String consulta = "SELECT a.*\n" +
                    "FROM Actividad a\n" +
                    "JOIN UsuarioActividad ua ON a.idActividad = ua.Actividad_idActividad\n" +
                    "JOIN Usuario u ON ua.Usuario_idUsuario = u.idUsuario\n" +
                    "WHERE u.idUsuario = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idUsuario));
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){

                    do{

                        TableActivities actividad = new TableActivities();
                        actividad.setTitulo(resultado.getString("titulo"));
                        actividad.setDescripcion(resultado.getString("descripcion"));
                        actividad.setFechaInicio(resultado.getString("fechaInicio"));
                        actividad.setFechaTermino(resultado.getString("fechaFin"));
                        actividad.setIdActividad(resultado.getInt("idActividad"));
                        listaActividades.add(actividad);
                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return listaActividades;
    }
}
