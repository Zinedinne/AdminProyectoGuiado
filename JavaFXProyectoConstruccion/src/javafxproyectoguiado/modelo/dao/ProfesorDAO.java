package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Profesor;
import javafxproyectoguiado.modelo.pojo.ProfesorRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class ProfesorDAO {
    public static ProfesorRespuesta obtenerInformacionProfesor(){
        ProfesorRespuesta respuesta = new ProfesorRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAcadémico, numeroTrabajador, Academico.idUsuario, Usuario.nombre AS nombreAcademico, Usuario.apellidoPaterno AS apellidoPaternoAcademico, " 
                        +"Usuario.apellidoMaterno AS apellidoMaternoAcademico, Usuario.username AS usernameAcademico, Usuario.password AS passwordAcademico, Usuario.correoInstitucional AS correoAcademico," 
                        +"Usuario.numeroTelefono AS telefonoAcademico " 
                        +"From Academico " 
                        +"Inner join Usuario ON Academico.idUsuario = Usuario.idUsuario "
                        + "WHERE usuario.tipoUsuario = 'Profesor';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Profesor> profesorConsulta = new ArrayList();
                while(resultado.next()){
                    Profesor profesor = new Profesor();
                    profesor.setIdAcademico(resultado.getInt("idAcadémico"));
                    profesor.setNumeroTrabajador(resultado.getString("numeroTrabajador"));
                    profesor.setIdUsuario(resultado.getInt("idUsuario"));
                    profesor.setNombre(resultado.getString("nombreAcademico"));
                    profesor.setApellidoPaterno(resultado.getString("apellidoPaternoAcademico"));
                    profesor.setApellidoMaterno(resultado.getString("apellidoMaternoAcademico"));
                    profesor.setUsername(resultado.getString("usernameAcademico"));
                    profesor.setPassword(resultado.getString("passwordAcademico"));
                    profesor.setCorreoInstitucional(resultado.getString("correoAcademico"));
                    profesor.setNumeroTelefono(resultado.getString("telefonoAcademico"));
                    profesorConsulta.add(profesor);
                    
                }
                respuesta.setProfesores(profesorConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarProfesor(Profesor profesorNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Usuario (nombre, apellidoPaterno, apellidoMaterno, username, "
                        + "password, correoInstitucional, numeroTelefono, tipoUsuario) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, profesorNuevo.getNombre());
                prepararSentencia.setString(2, profesorNuevo.getApellidoPaterno());
                prepararSentencia.setString(3, profesorNuevo.getApellidoMaterno());
                prepararSentencia.setString(4, profesorNuevo.getUsername());
                prepararSentencia.setString(5, profesorNuevo.getPassword());
                prepararSentencia.setString(6, profesorNuevo.getCorreoInstitucional());
                prepararSentencia.setString(7, profesorNuevo.getNumeroTelefono());
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
                    String sentenciaEstudiante = "INSERT INTO Academico (numeroTrabajador, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaProfesor = conexionBD.prepareStatement(sentenciaEstudiante);
                    prepararSentenciaProfesor.setString(1, profesorNuevo.getNumeroTrabajador());
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
    
    public static int modificarProfesor (Profesor profesorEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Usuario SET username = ?, password = ?, apellidoPaterno = ?, apellidoMaterno = ?,nombre = ?,"
                        + "correoInstitucional = ?, numeroTelefono = ? "
                        + "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, profesorEdicion.getUsername());
                prepararSentencia.setString(2, profesorEdicion.getPassword());
                prepararSentencia.setString(3, profesorEdicion.getApellidoPaterno());
                prepararSentencia.setString(4, profesorEdicion.getApellidoMaterno());
                prepararSentencia.setString(5, profesorEdicion.getNombre());
                prepararSentencia.setString(6, profesorEdicion.getCorreoInstitucional());
                prepararSentencia.setString(7, profesorEdicion.getNumeroTelefono());
                prepararSentencia.setInt(8, profesorEdicion.getIdUsuario());
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
