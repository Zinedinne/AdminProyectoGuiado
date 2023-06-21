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
import util.Utilidades;
import static util.Utilidades.obtenerNombreCompletoPorIdUsuario;

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
                        "CONCAT_WS(', ', estudiante.nombre, IFNULL(estudiante2.nombre, '')) AS estudiantesAsignados, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, " +
                        "anteproyecto.Usuarios_idUsuarios, creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis, " +
                        "comentario, " +
                        "anteproyecto.Academia_idAcademia, academia.nombreAcademia AS nombreAcademia " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "INNER JOIN academia ON anteproyecto.Academia_idAcademia = academia.idAcademia " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "LEFT JOIN usuario AS estudiante2 ON anteproyectousuario.Usuario_idUsuario2 = estudiante2.idUsuario " +
                        "WHERE encargado.tipoUsuario = 'Director de tesis' ";
                
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
                    int nombreCreador = resultado.getInt("Usuarios_idUsuarios");
                    String Usuarios_idUsuarios = Utilidades.obtenerNombreCompletoPorIdUsuario(nombreCreador);
                    anteproyecto.setNombreCreador(Usuarios_idUsuarios);
                    anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                    int idEncargadoDeTesis = resultado.getInt("idEncargadoDeTesis");
                    String nombreEncargadoDeTesis = Utilidades.obtenerNombreCompletoPorIdUsuario(idEncargadoDeTesis);
                    anteproyecto.setNombreEncargadoDeTesis(nombreEncargadoDeTesis);
                    anteproyecto.setComentario(resultado.getString("comentario"));
                    anteproyecto.setEstudiantesAsignados(resultado.getString("estudiantesAsignados"));
                    anteproyecto.setIdAcademia(resultado.getInt("Academia_idAcademia"));
                    anteproyecto.setNombreAcademia(resultado.getString("nombreAcademia"));
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
    
    public static AnteproyectoModuloRespuesta obtenerInformacionAnteproyectoDirector(){
        AnteproyectoModuloRespuesta respuesta = new AnteproyectoModuloRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT anteproyecto.idAnteproyecto, " +
                        "CONCAT_WS(', ', anteproyectousuario.nombreUsuario, IF(anteproyectousuario.nombreUsuario2 IS NOT NULL, anteproyectousuario.nombreUsuario2, '')) AS estudiantesAsignados, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, " +
                        "anteproyecto.Usuarios_idUsuarios, creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis, " +
                        " comentario, " +
                        "anteproyecto.Academia_idAcademia, academia.nombreAcademia AS nombreAcademia " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "INNER JOIN academia ON anteproyecto.Academia_idAcademia = academia.idAcademia " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "LEFT JOIN usuario AS estudiante2 ON anteproyectousuario.Usuario_idUsuario2 = estudiante2.idUsuario " +
                        "WHERE encargado.idUsuario = ? ";
                int idDirectorTesis = Singleton.getId();
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idDirectorTesis);
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
                    int nombreCreador = resultado.getInt("Usuarios_idUsuarios");
                    String Usuarios_idUsuarios = Utilidades.obtenerNombreCompletoPorIdUsuario(nombreCreador);
                    anteproyecto.setNombreCreador(Usuarios_idUsuarios);
                    anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                    int idEncargadoDeTesis = resultado.getInt("idEncargadoDeTesis");
                    String nombreEncargadoDeTesis = Utilidades.obtenerNombreCompletoPorIdUsuario(idEncargadoDeTesis);
                    anteproyecto.setNombreEncargadoDeTesis(nombreEncargadoDeTesis);
                    anteproyecto.setComentario(resultado.getString("comentario"));
                    anteproyecto.setEstudiantesAsignados(resultado.getString("estudiantesAsignados"));
                    anteproyecto.setIdAcademia(resultado.getInt("Academia_idAcademia"));
                    anteproyecto.setNombreAcademia(resultado.getString("nombreAcademia"));
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
    
    public static AnteproyectoModuloRespuesta obtenerInformacionAnteproyectoUsuario(){
        AnteproyectoModuloRespuesta respuesta = new AnteproyectoModuloRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT anteproyecto.idAnteproyecto, " +
                        "CONCAT_WS(', ', anteproyectousuario.nombreUsuario, IF(anteproyectousuario.nombreUsuario2 IS NOT NULL, anteproyectousuario.nombreUsuario2, '')) AS estudiantesAsignados, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, " +
                        "anteproyecto.Usuarios_idUsuarios, creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis, " +
                        " comentario, " +
                        "anteproyecto.Academia_idAcademia, academia.nombreAcademia AS nombreAcademia " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "INNER JOIN academia ON anteproyecto.Academia_idAcademia = academia.idAcademia " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "LEFT JOIN usuario AS estudiante2 ON anteproyectousuario.Usuario_idUsuario2 = estudiante2.idUsuario " +
                        "WHERE encargado.tipoUsuario = 'Director de tesis' " +
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
                    int nombreCreador = resultado.getInt("Usuarios_idUsuarios");
                    String Usuarios_idUsuarios = Utilidades.obtenerNombreCompletoPorIdUsuario(nombreCreador);
                    anteproyecto.setNombreCreador(Usuarios_idUsuarios);
                    anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                    int idEncargadoDeTesis = resultado.getInt("idEncargadoDeTesis");
                    String nombreEncargadoDeTesis = Utilidades.obtenerNombreCompletoPorIdUsuario(idEncargadoDeTesis);
                    anteproyecto.setNombreEncargadoDeTesis(nombreEncargadoDeTesis);
                    anteproyecto.setComentario(resultado.getString("comentario"));
                    anteproyecto.setEstudiantesAsignados(resultado.getString("estudiantesAsignados"));
                    anteproyecto.setIdAcademia(resultado.getInt("Academia_idAcademia"));
                    anteproyecto.setNombreAcademia(resultado.getString("nombreAcademia"));
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
                        "CONCAT_WS(', ', anteproyectousuario.nombreUsuario, IF(anteproyectousuario.nombreUsuario2 IS NOT NULL, anteproyectousuario.nombreUsuario2, '')) AS estudiantesAsignados, " +
                        "nombreAnteproyecto, fechaInicio, duracion, modalidad, estado, descripcion, " +
                        "anteproyecto.LGAC_idLGAC, lgac.nombre AS nombreLGAC, " +
                        "anteproyecto.Usuarios_idUsuarios, creador.nombre AS nombreCreador, " +
                        "anteproyecto.idEncargadoDeTesis, encargado.nombre AS nombreEncargadoDeTesis, " +
                        " comentario, " +
                        "anteproyecto.Academia_idAcademia, academia.nombreAcademia AS nombreAcademia " +
                        "FROM anteproyecto " +
                        "INNER JOIN lgac ON anteproyecto.LGAC_idLGAC = lgac.idLGAC " +
                        "INNER JOIN usuario AS creador ON anteproyecto.Usuarios_idUsuarios = creador.idUsuario " +
                        "INNER JOIN usuario AS encargado ON anteproyecto.idEncargadoDeTesis = encargado.idUsuario " +
                        "INNER JOIN academia ON anteproyecto.Academia_idAcademia = academia.idAcademia " +
                        "LEFT JOIN anteproyectousuario ON anteproyecto.idAnteproyecto = anteproyectousuario.Anteproyecto_idAnteproyecto " +
                        "LEFT JOIN usuario AS estudiante ON anteproyectousuario.Usuario_idUsuario = estudiante.idUsuario " +
                        "LEFT JOIN usuario AS estudiante2 ON anteproyectousuario.Usuario_idUsuario2 = estudiante2.idUsuario " +
                        "JOIN academico ON anteproyecto.Academia_idAcademia = academico.idAcademia " +
                        "WHERE academico.idUsuario = ? ";
                int idResponsableAcademia = Singleton.getId();
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idResponsableAcademia);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<AnteproyectoModulo> anteproyectoConsultaValidacion = new ArrayList();
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
                    int nombreCreador = resultado.getInt("Usuarios_idUsuarios");
                    String Usuarios_idUsuarios = Utilidades.obtenerNombreCompletoPorIdUsuario(nombreCreador);
                    anteproyecto.setNombreCreador(Usuarios_idUsuarios);
                    anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                    int idEncargadoDeTesis = resultado.getInt("idEncargadoDeTesis");
                    String nombreEncargadoDeTesis = Utilidades.obtenerNombreCompletoPorIdUsuario(idEncargadoDeTesis);
                    anteproyecto.setNombreEncargadoDeTesis(nombreEncargadoDeTesis);
                    anteproyecto.setComentario(resultado.getString("comentario"));
                    anteproyecto.setEstudiantesAsignados(resultado.getString("estudiantesAsignados"));
                    anteproyecto.setIdAcademia(resultado.getInt("Academia_idAcademia"));
                    anteproyecto.setNombreAcademia(resultado.getString("nombreAcademia"));
                    anteproyectoConsultaValidacion.add(anteproyecto);
                }
                respuesta.setAnteproyectosModulo(anteproyectoConsultaValidacion);
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
                        + "LGAC_idLGAC, Usuarios_idUsuarios, idEncargadoDeTesis, Academia_idAcademia) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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
                prepararSentencia.setInt(10,anteproyectoNuevo.getIdAcademia());
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
                        + "Usuarios_idUsuarios = ?, idEncargadoDeTesis = ?, comentario = ?, Academia_idAcademia = ? "
                        + "WHERE idAnteproyecto = ? ";
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
                prepararSentencia.setString(10, "");
                prepararSentencia.setInt(11,anteproyectoEdicion.getIdAcademia());
                prepararSentencia.setInt(12, anteproyectoEdicion.getIdAnteproyecto());
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
    
    
    public static int asignarPrimerEstudianteAnteproyecto(AnteproyectoModulo anteproyectoValido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String nombreCompleto = obtenerNombreCompletoPorIdUsuario(anteproyectoValido.getIdEstudianteAsignado());
                String sentencia = "INSERT INTO proyecto.anteproyectousuario (Anteproyecto_idAnteproyecto, Usuario_idUsuario, nombreUsuario) " +
                        "VALUES (?, ?, ?) ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoValido.getIdAnteproyecto());
                prepararSentencia.setInt(2, anteproyectoValido.getIdEstudianteAsignado());
                prepararSentencia.setString(3, nombreCompleto);
 
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
    
    public static int asignarSegundoEstudianteAnteproyecto(AnteproyectoModulo anteproyectoValido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String nombreCompleto = obtenerNombreCompletoPorIdUsuario(anteproyectoValido.getIdEstudianteAsignado());
                String sentencia = "UPDATE proyecto.anteproyectousuario " +
                        "SET Usuario_idUsuario = IFNULL(Usuario_idUsuario, ?), " +
                        "Usuario_idUsuario2 = ? , nombreUsuario2 = ? " +
                        "WHERE Anteproyecto_idAnteproyecto = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoValido.getIdEstudianteAsignado());
                prepararSentencia.setInt(2, anteproyectoValido.getIdEstudianteAsignado());
                prepararSentencia.setString(3, nombreCompleto);
                prepararSentencia.setInt(4, anteproyectoValido.getIdAnteproyecto());
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
    
    public static int asignarComentarioAnteproyecto(AnteproyectoModulo anteproyectoValido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD !=null){
            try{
                String sentencia = "UPDATE proyecto.anteproyecto SET comentario = ? WHERE idAnteproyecto = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, anteproyectoValido.getComentario());
                prepararSentencia.setInt(2, anteproyectoValido.getIdAnteproyecto());
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
    
    public static int obtenerCantidadAnteproyectoUsuario(int idUsuario) {
        int cantidad = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT COUNT(*) FROM Proyecto.AnteproyectoUsuario WHERE Usuario_idUsuario = ? OR Usuario_idUsuario2 = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUsuario);
                prepararSentencia.setInt(2, idUsuario);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    cantidad = resultado.getInt(1);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cantidad;
    }
    
    public static int obtenerCantidadEstudiantesAnteproyecto(int idAnteproyecto) {
        int cantidad = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT COUNT(DISTINCT Usuario_idUsuario) + COUNT(DISTINCT Usuario_idUsuario2) AS total_count " +
                        "FROM anteproyectousuario " +
                        "WHERE Anteproyecto_idAnteproyecto = ? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAnteproyecto);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    cantidad = resultado.getInt(1);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cantidad;
    }
    
}
