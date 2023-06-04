/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.Usuario;
import modelo.ConexionBD;
import util.Constantes;

/**
 *
 * @author Zinedinne
 */
public class SesionDAO {
    
    public static Usuario verificarUsuarioSesion(String usuario, String password){
        Usuario usuarioVerificado = new Usuario();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            String consulta = "SELECT * FROM usuario WHERE username = ? AND password = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setUsername(resultado.getString("username"));
                    usuarioVerificado.setTipoUsuario(resultado.getString("tipoUsuario"));
                    Singleton.setLogin(usuarioVerificado);
                }
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        
        return usuarioVerificado;
    }
}
