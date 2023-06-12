/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModuloRespuesta;
import modelo.ConexionBD;
import util.Constantes;

/**
 *
 * @author Alvaro
 */
public class AnteproyectoModuloDAO {
    public static AnteproyectoModuloRespuesta obtenerInformacionAnteproyecto(){
        AnteproyectoModuloRespuesta respuesta = new AnteproyectoModuloRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT anteproyecto.idAnteproyecto, anteproyectousuario.Usuario_idUsuario AS idEstudianteAsignado, estudiante.nombre AS estudianteAsignado, " +
                "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, " +
                "anteproyecto.Usuarios_idUsuarios, creador.nombre AS nombreCreador, " +
                "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis " +
                "FROM anteproyecto " +
                "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                "INNER JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                "INNER JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                "WHERE encargado.tipoUsuario = 'Encargado de tesis'; ";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<AnteproyectoModulo> anteproyectoConsulta = new ArrayList();
                while(resultado.next()){
                    AnteproyectoModulo anteproyecto = new AnteproyectoModulo();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setNombreAnteproyecto(resultado.getString("nombreAnteproyecto"));
                    anteproyecto.setFechaInicio(resultado.getString("fechaInicio"));
                    anteproyecto.setDuracion(resultado.getString("duracion"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setDescripcion(resultado.getString("descripcion"));
                    anteproyecto.setIdLGAC(resultado.getInt("LGAC_idLGAC"));
                    anteproyecto.setNombreLGAC(resultado.getString("nombreLGAC"));
                    anteproyecto.setIdUsuario(resultado.getInt("Usuarios_idUsuarios"));
                    anteproyecto.setNombreCreador(resultado.getString("nombreCreador"));
                    anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                    anteproyecto.setNombreEncargadoDeTesis(resultado.getString("nombreEncargadoDeTesis"));
                    anteproyecto.setIdEstudianteAsignado(resultado.getInt("idEstudianteAsignado"));
                    anteproyecto.setEstudianteAsignado(resultado.getString("estudianteAsignado"));
                    anteproyectoConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectosModulo(anteproyectoConsulta);
                conexionBD.close();
            } catch (SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
