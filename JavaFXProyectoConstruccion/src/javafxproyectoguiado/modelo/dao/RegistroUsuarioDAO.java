package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.RegistroUsuario;
import javafxproyectoguiado.modelo.pojo.RegistroUsuarioRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class RegistroUsuarioDAO {
    public static RegistroUsuarioRespuesta obtenerInformacionUsuario(){
        RegistroUsuarioRespuesta respuesta = new RegistroUsuarioRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select idUsuario, usuario.nombre, apellidoPaterno, apellidoMaterno, "
                        + "username, password, correoInstitucional, numeroTelefono,tipoUsuario "
                        + "From Usuario;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<RegistroUsuario> usuarioConsulta = new ArrayList();
                while(resultado.next()){
                    RegistroUsuario usuario = new RegistroUsuario();
                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setUsername(resultado.getString("username"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setCorreo(resultado.getString("correoInstitucional"));
                    usuario.setTipoUsuario(resultado.getString("tipoUsuario"));
                    usuario.setNumeroTelefono(resultado.getString("numeroTelefono"));
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
