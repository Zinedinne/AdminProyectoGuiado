package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import javafxproyectoguiado.modelo.pojo.TableAnteproyecto;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableAnteproyectoDAO {
    public static List<TableAnteproyecto> getAnteproyecto(int idUsuario) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        List<TableAnteproyecto> listaAnteproyecto = new ArrayList<>();
        if(conexion != null){
            String consulta = "SELECT a.*,ua.*,u.nombre, u.apellidoPaterno, u.apellidoMaterno\n" +
                    "FROM Anteproyecto a                    \n" +
                    "JOIN anteproyectousuario ua ON a.idAnteproyecto = ua.Anteproyecto_idAnteproyecto\n" +
                    "JOIN Usuario u ON ua.Usuario_idUsuario = u.idUsuario\n" +
                    "JOIN Usuario u2 ON a.Usuarios_idUsuarios = u2.idUsuario \n" +
                    "Join Usuario u3 ON a.idEncargadoDeTesis = u3.idUsuario\n" +
                    "WHERE u2.idUsuario=? OR u3.idUsuario =?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idUsuario));
                prepararSentencia.setString(2, String.valueOf(idUsuario));

                ResultSet resultado = prepararSentencia.executeQuery();

                if(resultado.next()){

                    do{

                        TableAnteproyecto anteproyecto = new TableAnteproyecto();
                        anteproyecto.setDescripcion(resultado.getString("descripcion"));
                        anteproyecto.setFechaInicio(resultado.getString("fechaInicio"));
                        anteproyecto.setDuracion(resultado.getString("duracion"));
                        anteproyecto.setEstado(resultado.getBoolean("estado"));
                        anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                        anteproyecto.setModalidad(resultado.getString("modalidad"));
                        anteproyecto.setNombreAnteproyecto(resultado.getString("nombreAnteproyecto"));
                        anteproyecto.setIdUsuario(resultado.getInt("Usuario_idUsuario"));
                        anteproyecto.setNombreUsuario(resultado.getString("nombre") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno"));
                        listaAnteproyecto.add(anteproyecto);
                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return listaAnteproyecto;
    }
    public static List<TableAnteproyecto> getAnteproyectoPorEstudiante(int idUsuario) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        List<TableAnteproyecto> listaAnteproyecto = new ArrayList<>();
        if(conexion != null){
            String consulta = "SELECT a.*,ua.*,u.nombre, u.apellidoPaterno, u.apellidoMaterno\n" +
                    "FROM Anteproyecto a                    \n" +
                    "JOIN anteproyectousuario ua ON a.idAnteproyecto = ua.Anteproyecto_idAnteproyecto\n" +
                    "JOIN Usuario u ON ua.Usuario_idUsuario = u.idUsuario\n" +
                    "JOIN Usuario u2 ON a.Usuarios_idUsuarios = u2.idUsuario \n" +
                    "Join Usuario u3 ON a.idEncargadoDeTesis = u3.idUsuario\n" +
                    "WHERE u.idUsuario = ? and (u2.idUsuario=? OR u3.idUsuario =?)";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idUsuario));
                prepararSentencia.setString(2, String.valueOf(Singleton.getId()));
                prepararSentencia.setString(3, String.valueOf(Singleton.getId()));
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    do{
                        TableAnteproyecto anteproyecto = new TableAnteproyecto();
                        anteproyecto.setDescripcion(resultado.getString("descripcion"));
                        anteproyecto.setFechaInicio(resultado.getString("fechaInicio"));
                        anteproyecto.setDuracion(resultado.getString("duracion"));
                        anteproyecto.setEstado(resultado.getBoolean("estado"));
                        anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                        anteproyecto.setModalidad(resultado.getString("modalidad"));
                        anteproyecto.setNombreAnteproyecto(resultado.getString("nombreAnteproyecto"));
                        anteproyecto.setIdUsuario(resultado.getInt("Usuario_idUsuario"));
                        anteproyecto.setNombreUsuario(resultado.getString("nombre") + " " + resultado.getString("apellidoPaterno") + " " + resultado.getString("apellidoMaterno"));
                        listaAnteproyecto.add(anteproyecto);
                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("", ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return listaAnteproyecto;
    }
}
