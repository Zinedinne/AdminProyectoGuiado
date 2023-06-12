package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import javafxproyectoguiado.modelo.pojo.EstudianteRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class EstudianteDAO {
    
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
                    estudiante.setNumeroTelefono(resultado.getString("telefonoEstudiante"));
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
    
    public static int modificarEstudiante (Estudiante estudianteEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Usuario SET username = ?, password = ?, apellidoPaterno = ?, apellidoMaterno = ?,nombre = ?,"
                        + "correoInstitucional = ?, numeroTelefono = ? "
                        + "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, estudianteEdicion.getUsername());
                prepararSentencia.setString(2, estudianteEdicion.getPassword());
                prepararSentencia.setString(3, estudianteEdicion.getApellidoPaterno());
                prepararSentencia.setString(4, estudianteEdicion.getApellidoMaterno());
                prepararSentencia.setString(5, estudianteEdicion.getNombre());
                prepararSentencia.setString(6, estudianteEdicion.getCorreoInstitucional());
                prepararSentencia.setString(7, estudianteEdicion.getNumeroTelefono());
                prepararSentencia.setInt(8, estudianteEdicion.getIdUsuario());
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