package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Usuario;
import modelo.ConexionBD;
import util.Constantes;

public class UsuarioDAO {
    public static Usuario obtenerInformacionUsuario(){
       Usuario respuesta = new Usuario();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
               String consulta = "select idUsuario, usuario.nombre, apellidoPaterno, apellidoMaterno, username, "
                       + "password, correoInstitucional, numeroTelefono,tipoUsuario "
                       + "From Usuario;";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
               ResultSet resultado = prepararSentencia.executeQuery();
               ArrayList<Usuario> usuarioConsulta = new ArrayList();
               while(resultado.next()){
                   Usuario usuario = new Usuario();
                   usuario.setIdUsuario(resultado.getInt("idUsuario"));
                   usuario.setNombre(resultado.getString("nombre"));
                   usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                   usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                   usuario.setUsername(resultado.getString("username"));
                   usuario.setPassword(resultado.getString("password"));
                   usuario.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                   usuario.setNumeroTelefono(resultado.getInt("numeroTelefono"));
                   usuario.setTipoUsuario(resultado.getString("tipoUsuario"));
                   usuarioConsulta.add(usuario);
               }
               respuesta.setUsuarios(usuarioConsulta);
               conexionBD.close();
           }catch(SQLException e){
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return respuesta;
    }
}
