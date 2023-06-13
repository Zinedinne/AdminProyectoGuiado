package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModuloRespuesta;
import javafxproyectoguiado.modelo.pojo.Singleton;
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
                String consulta = "SELECT anteproyecto.idAnteproyecto, " +
                        "anteproyectousuario.Usuario_idUsuario AS idEstudianteAsignado, " +
                        "estudiante.nombre AS estudianteAsignado, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, anteproyecto.Usuarios_idUsuarios, " +
                        "creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "WHERE encargado.tipoUsuario = 'Encargado de tesis' " +
                        "AND creador.idUsuario = ? ";
                int idUsuarioEncargado = Singleton.getId();
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuarioEncargado);
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
    
    public static AnteproyectoModuloRespuesta obtenerInformacionAnteproyectoValidacion(){
        AnteproyectoModuloRespuesta respuesta = new AnteproyectoModuloRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT anteproyecto.idAnteproyecto, " +
                        "anteproyectousuario.Usuario_idUsuario AS idEstudianteAsignado, " +
                        "estudiante.nombre AS estudianteAsignado, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, anteproyecto.Usuarios_idUsuarios, " +
                        "creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "WHERE encargado.tipoUsuario = 'Encargado de tesis' ";
                
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
    
    public static int guardarAnteproyecto(AnteproyectoModulo anteproyectoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String sentencia = "INSERT INTO proyecto.anteproyecto (descripcion, duracion, estado, fechaInicio, modalidad, nombreAnteproyecto, "
                        + "LGAC_idLGAC, Usuarios_idUsuarios, idEncargadoDeTesis) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoNuevo.getDescripcion());
                prepararSentencia.setString(2, anteproyectoNuevo.getDuracion());
                prepararSentencia.setString(3, "0");
                prepararSentencia.setString(4, anteproyectoNuevo.getFechaInicio());
                prepararSentencia.setString(5, anteproyectoNuevo.getModalidad());
                prepararSentencia.setString(6, anteproyectoNuevo.getNombreAnteproyecto());
                prepararSentencia.setInt(7, anteproyectoNuevo.getIdLGAC());
                prepararSentencia.setInt(8, Singleton.getId());
                prepararSentencia.setInt(9,anteproyectoNuevo.getIdEncargadoDeTesis());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch (SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;      
        }
        return respuesta;
    }
    
    public static int modificarAnteproyecto(AnteproyectoModulo anteproyectoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String sentencia = "UPDATE proyecto.anteproyecto "
                        + "SET descripcion = ?, duracion = ?, estado = ?, fechaInicio = ?, "
                        + "modalidad = ?, nombreAnteproyecto = ?, LGAC_idLGAC = ? , "
                        + "Usuarios_idUsuarios = ?, idEncargadoDeTesis = ? "
                        + "WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoEdicion.getDescripcion());
                prepararSentencia.setString(2, anteproyectoEdicion.getDuracion());
                prepararSentencia.setString(3, "0");
                prepararSentencia.setString(4, anteproyectoEdicion.getFechaInicio());
                prepararSentencia.setString(5, anteproyectoEdicion.getModalidad());
                prepararSentencia.setString(6, anteproyectoEdicion.getNombreAnteproyecto());
                prepararSentencia.setInt(7, anteproyectoEdicion.getIdLGAC());
                prepararSentencia.setInt(8, Singleton.getId());
                prepararSentencia.setInt(9,anteproyectoEdicion.getIdEncargadoDeTesis());
                prepararSentencia.setInt(10, anteproyectoEdicion.getIdAnteproyecto());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarAnteproyecto(int idAnteproyecto){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try {
                String sentencia = " DELETE FROM anteproyecto WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idAnteproyecto);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int validarEstadoAnteproyecto(int idAnteproyecto, String estado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                    String sentencia = "UPDATE proyecto.anteproyecto SET estado = ? WHERE idAnteproyecto = ?";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, "1");
                    prepararSentencia.setInt(2, idAnteproyecto);
                    int filasAfectadas = prepararSentencia.executeUpdate();
                    respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                    conexionBD.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    respuesta = Constantes.ERROR_CONSULTA;
                }
            } else {
                respuesta = Constantes.ERROR_CONEXION;
            }
            return respuesta;
    }
    
    public static int denegarEstadoAnteproyecto(int idAnteproyecto, String estado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE proyecto.anteproyecto SET estado = ? WHERE idAnteproyecto = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, "2");
                prepararSentencia.setInt(2, idAnteproyecto);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
                }
        } else {
             respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    
    public static int asignarEstudianteAnteproyecto(AnteproyectoModulo anteproyectoValido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String sentencia = "INSERT INTO proyecto.anteproyectousuario (Anteproyecto_idAnteproyecto, Usuario_idUsuario) "
                        + "VALUES (?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoValido.getIdAnteproyecto());
                prepararSentencia.setInt(2, anteproyectoValido.getIdEstudianteAsignado());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
