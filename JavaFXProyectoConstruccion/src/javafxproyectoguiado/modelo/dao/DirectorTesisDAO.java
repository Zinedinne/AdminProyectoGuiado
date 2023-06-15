package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.DirectorTesis;
import javafxproyectoguiado.modelo.pojo.DirectorTesisRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class DirectorTesisDAO {
    public static DirectorTesisRespuesta obtenerInformacionDirectorTesis(){
        DirectorTesisRespuesta respuesta = new DirectorTesisRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAcadémico, numeroTrabajador, Academico.idUsuario, Usuario.nombre AS nombreAcademico, Usuario.apellidoPaterno AS apellidoPaternoAcademico, " 
                        +"Usuario.apellidoMaterno AS apellidoMaternoAcademico, Usuario.username AS usernameAcademico, Usuario.password AS passwordAcademico, Usuario.correoInstitucional AS correoAcademico," 
                        +"Usuario.numeroTelefono AS telefonoAcademico " 
                        +"From Academico " 
                        +"Inner join Usuario ON Academico.idUsuario = Usuario.idUsuario "
                        + "WHERE usuario.tipoUsuario = 'Encargado de tesis';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<DirectorTesis> directorTesisConsulta = new ArrayList();
                while(resultado.next()){
                    DirectorTesis director = new DirectorTesis();
                    director.setIdAcademico(resultado.getInt("idAcadémico"));
                    director.setNumeroTrabajador(resultado.getString("numeroTrabajador"));
                    director.setIdUsuario(resultado.getInt("idUsuario"));
                    director.setNombre(resultado.getString("nombreAcademico"));
                    director.setApellidoPaterno(resultado.getString("apellidoPaternoAcademico"));
                    director.setApellidoMaterno(resultado.getString("apellidoMaternoAcademico"));
                    director.setUsername(resultado.getString("usernameAcademico"));
                    director.setPassword(resultado.getString("passwordAcademico"));
                    director.setCorreoInstitucional(resultado.getString("correoAcademico"));
                    director.setNumeroTelefono(resultado.getString("telefonoAcademico"));
                    directorTesisConsulta.add(director);
                    
                }
                respuesta.setDirectores(directorTesisConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarDirectorTesis(DirectorTesis directorNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, directorNuevo.getNombre());
                prepararSentencia.setString(2, directorNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, directorNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, directorNuevo.getUsername());
                prepararSentencia.setString(5, directorNuevo.getPassword());
                prepararSentencia.setString(6, directorNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, directorNuevo.getNumeroTelefono());
                prepararSentencia.setString(8, "Encargado de tesis");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0){
                    String queryLastID = "SELECT LAST_INSERT_ID() as idUltimo";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt("idUltimo");
                    } 
                    String sentenciaEstudiante = "INSERT INTO Academico (numeroTrabajador, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaDirector = conexionBD.prepareStatement(sentenciaEstudiante);
                    prepararSentenciaDirector.setString(1, directorNuevo.getNumeroTrabajador());
                    prepararSentenciaDirector.setInt(2, idUsuario);
                    prepararSentenciaDirector.executeUpdate();
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
    
    public static int modificarDirectorTesis (DirectorTesis directorTesisEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Usuario SET username = ?, password = ?, apellidoPaterno = ?, apellidoMaterno = ?,nombre = ?,"
                        + "correoInstitucional = ?, numeroTelefono = ? "
                        + "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, directorTesisEdicion.getUsername());
                prepararSentencia.setString(2, directorTesisEdicion.getPassword());
                prepararSentencia.setString(3, directorTesisEdicion.getApellidoPaterno());
                prepararSentencia.setString(4, directorTesisEdicion.getApellidoMaterno());
                prepararSentencia.setString(5, directorTesisEdicion.getNombre());
                prepararSentencia.setString(6, directorTesisEdicion.getCorreoInstitucional());
                prepararSentencia.setString(7, directorTesisEdicion.getNumeroTelefono());
                prepararSentencia.setInt(8, directorTesisEdicion.getIdUsuario());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
            }catch(SQLException e){
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
