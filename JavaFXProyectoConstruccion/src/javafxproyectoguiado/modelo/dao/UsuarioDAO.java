package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
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

public class UsuarioDAO {
    public List<Usuario> getEstudiantes() throws SQLException {
        List<Usuario> estudiantes = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT * from usuario where tipoUsuario = 'Estudiante'";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){

                    do{
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultado.getInt("idUsuario"));
                        usuario.setUsername(resultado.getString("username"));
                        usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                        usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                        usuario.setNombre(resultado.getString("nombre"));
                        usuario.setCorreo(resultado.getString("correoInstitucional"));
                        usuario.setTipoUsuario(resultado.getString("tipoUsuario"));
                        usuario.setNumeroTelefono(resultado.getString("numeroTelefono"));
                        estudiantes.add(usuario);
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
        return estudiantes;
    }
    public List<Usuario> getEstudiantesPorAnteproyecto(int idAnteproyecto) throws SQLException {
        List<Usuario> estudiantes = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT * from  usuario join anteproyectousuario au on au.Anteproyecto_idAnteproyecto = ? where tipoUsuario = 'Estudiante' and au.Usuario_idUsuario = idUsuario";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idAnteproyecto));
                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){

                    do{
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultado.getInt("idUsuario"));
                        usuario.setUsername(resultado.getString("username"));
                        usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                        usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                        usuario.setNombre(resultado.getString("nombre"));
                        usuario.setCorreo(resultado.getString("correoInstitucional"));
                        usuario.setTipoUsuario(resultado.getString("tipoUsuario"));
                        usuario.setNumeroTelefono(resultado.getString("numeroTelefono"));
                        estudiantes.add(usuario);
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
        return estudiantes;
    }
}
