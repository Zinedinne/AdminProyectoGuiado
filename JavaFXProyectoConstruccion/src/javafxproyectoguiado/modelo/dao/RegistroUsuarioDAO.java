package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import javafxproyectoguiado.modelo.pojo.EstudianteRespuesta;
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
    
    public static EstudianteRespuesta obtenerInformacionEstudiante(){
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idEstudiante, matricula, Estudiante.idUsuario, Usuario.nombre AS nombreEstudiante, Usuario.apellidoPaterno AS apellidoPaternoEstudiante, " 
                        +"Usuario.apellidoMaterno AS apellidoMaternoEstudiante, Usuario.username AS usernameEstudiante, Usuario.password AS passwordEstudiante, Usuario.correoInstitucional AS correoEstudiante," 
                        +"Usuario.numeroTelefono AS telefonoEstudiante " 
                        +"From Estudiante " 
                        +"Inner join Usuario ON Estudiante.idUsuario = Usuario.idUsuario;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante> estudianteConsulta = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setIdUsuario(resultado.getInt("idUsuario"));
                    estudiante.setNombre(resultado.getString("nombreEstudiante"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaternoEstudiante"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaternoEstudiante"));
                    estudiante.setUsername(resultado.getString("usernameEstudiante"));
                    estudiante.setPassword(resultado.getString("passwordEstudiante"));
                    estudiante.setCorreoInstitucional(resultado.getString("correoEstudiante"));
                    estudiante.setTipoUsuario(resultado.getString("telefonoEstudiante"));
                    estudianteConsulta.add(estudiante);
                }
                respuesta.setEstudiantes(estudianteConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarEstudiante(Estudiante estudianteNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, estudianteNuevo.getNombre());
                prepararSentencia.setString(2, estudianteNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, estudianteNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, estudianteNuevo.getUsername());
                prepararSentencia.setString(5, estudianteNuevo.getPassword());
                prepararSentencia.setString(6, estudianteNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, estudianteNuevo.getNumeroTelefono());
                prepararSentencia.setString(8, "Estudiante");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0){
                    String queryLastID = "SELECT LAST_INSERT_ID() as idUltimo";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt("idUltimo");
                    } 
                    String sentenciaEstudiante = "INSERT INTO Estudiante (matricula, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaEstudiante = conexionBD.prepareStatement(sentenciaEstudiante);
                    prepararSentenciaEstudiante.setString(1, estudianteNuevo.getMatricula());
                    prepararSentenciaEstudiante.setInt(2, idUsuario);
                    prepararSentenciaEstudiante.executeUpdate();
                }
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int guardarProfesor(RegistroUsuario profesorteNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, profesorteNuevo.getNombre());
                prepararSentencia.setString(2, profesorteNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, profesorteNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, profesorteNuevo.getUsername());
                prepararSentencia.setString(5, profesorteNuevo.getPassword());
                prepararSentencia.setString(6, profesorteNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, profesorteNuevo.getNumeroTelefono());
                prepararSentencia.setString(8, "Profesor");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0){
                    String queryLastID = "SELECT LAST_INSERT_ID() as idUltimo";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt("idUltimo");
                    } 
                    String sentenciaProfesor = "INSERT INTO academico (numeroTrabajador, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaProfesor = conexionBD.prepareStatement(sentenciaProfesor);
                    prepararSentenciaProfesor.setString(1, profesorteNuevo.getNumeroTrabajador());
                    prepararSentenciaProfesor.setInt(2, idUsuario);
                    prepararSentenciaProfesor.executeUpdate();
                }
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int guardarTesis(RegistroUsuario tesisNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, tesisNuevo.getNombre());
                prepararSentencia.setString(2, tesisNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, tesisNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, tesisNuevo.getUsername());
                prepararSentencia.setString(5, tesisNuevo.getPassword());
                prepararSentencia.setString(6, tesisNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, tesisNuevo.getNumeroTelefono());
                prepararSentencia.setString(8, "Encargado de Tesis");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0){
                    String queryLastID = "SELECT LAST_INSERT_ID() as idUltimo";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt("idUltimo");
                    } 
                    String sentenciaTesis = "INSERT INTO academico (numeroTrabajador, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaTesis = conexionBD.prepareStatement(sentenciaTesis);
                    prepararSentenciaTesis.setString(1, tesisNuevo.getNumeroTrabajador());
                    prepararSentenciaTesis.setInt(2, idUsuario);
                    prepararSentenciaTesis.executeUpdate();
                }
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int guardarResponsable(RegistroUsuario ResponsableNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, ResponsableNuevo.getNombre());
                prepararSentencia.setString(2, ResponsableNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, ResponsableNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, ResponsableNuevo.getUsername());
                prepararSentencia.setString(5, ResponsableNuevo.getPassword());
                prepararSentencia.setString(6, ResponsableNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, ResponsableNuevo.getNumeroTelefono());
                prepararSentencia.setString(8, "Responsable de Academia");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0){
                    String queryLastID = "SELECT LAST_INSERT_ID() as idUltimo";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt("idUltimo");
                    } 
                    String sentenciaTesis = "INSERT INTO academico (numeroTrabajador, idUsuario, idAcademia) "
                            + "VALUES(?, ?, ?);";
                    PreparedStatement prepararSentenciaTesis = conexionBD.prepareStatement(sentenciaTesis);
                    prepararSentenciaTesis.setString(1, ResponsableNuevo.getNumeroTrabajador());
                    prepararSentenciaTesis.setInt(2, idUsuario);
                    prepararSentenciaTesis.setInt(3, ResponsableNuevo.getIdAcademia());
                    prepararSentenciaTesis.executeUpdate();
                }
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
