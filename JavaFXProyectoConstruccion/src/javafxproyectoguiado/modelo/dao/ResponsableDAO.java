package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Responsable;
import javafxproyectoguiado.modelo.pojo.ResponsableRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class ResponsableDAO {
    public static ResponsableRespuesta obtenerInformacionResponsable(){
        ResponsableRespuesta respuesta = new ResponsableRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idAcadémico, numeroTrabajador, Academico.idUsuario, Usuario.nombre AS nombreAcademico, Usuario.apellidoPaterno AS apellidoPaternoAcademico, " 
                        +"Usuario.apellidoMaterno AS apellidoMaternoAcademico, Usuario.username AS usernameAcademico, Usuario.password AS passwordAcademico, Usuario.correoInstitucional AS correoAcademico," 
                        +"Usuario.numeroTelefono AS telefonoAcademico, Academico.idAcademia, Academia.nombreAcademia AS nombreAcademia " 
                        +"From Academico " 
                        +"Inner join Usuario ON Academico.idUsuario = Usuario.idUsuario "
                        + "Inner join Academia ON Academico.idAcademia = Academia.idAcademia "
                        + "WHERE usuario.tipoUsuario = 'Responsable de Academia';";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                    ArrayList<Responsable> ResponsableConsulta = new ArrayList();
                while(resultado.next()){
                    Responsable responsable = new Responsable();
                    responsable.setIdAcademico(resultado.getInt("idAcadémico"));
                    responsable.setNumeroTrabajador(resultado.getString("numeroTrabajador"));
                    responsable.setIdUsuario(resultado.getInt("idUsuario"));
                    responsable.setNombre(resultado.getString("nombreAcademico"));
                    responsable.setApellidoPaterno(resultado.getString("apellidoPaternoAcademico"));
                    responsable.setApellidoMaterno(resultado.getString("apellidoMaternoAcademico"));
                    responsable.setUsername(resultado.getString("usernameAcademico"));
                    responsable.setPassword(resultado.getString("passwordAcademico"));
                    responsable.setCorreoInstitucional(resultado.getString("correoAcademico"));
                    responsable.setNumeroTelefono(resultado.getString("telefonoAcademico"));
                    responsable.setIdAcademia(resultado.getInt("idAcademia"));
                    responsable.setNombreAcademia(resultado.getString("nombreAcademia"));
                    ResponsableConsulta.add(responsable);
                    
                }
                respuesta.setResponsables(ResponsableConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarResponsable (Responsable ResponsableNuevo){
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
                    String sentenciaEstudiante = "INSERT INTO Academico (numeroTrabajador, idUsuario, idAcademia) "
                            + "VALUES(?, ?, ?);";
                    PreparedStatement prepararSentenciaResponsable = conexionBD.prepareStatement(sentenciaEstudiante);
                    prepararSentenciaResponsable.setString(1, ResponsableNuevo.getNumeroTrabajador());
                    prepararSentenciaResponsable.setInt(2, idUsuario);
                    prepararSentenciaResponsable.setInt(3, ResponsableNuevo.getIdAcademia());
                    prepararSentenciaResponsable.executeUpdate();
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
    
    public static int modificarResponsable (Responsable responsableEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Usuario SET username = ?, password = ?, apellidoPaterno = ?, apellidoMaterno = ?,nombre = ?,"
                        + "correoInstitucional = ?, numeroTelefono = ? "
                        + "WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, responsableEdicion.getUsername());
                prepararSentencia.setString(2, responsableEdicion.getPassword());
                prepararSentencia.setString(3, responsableEdicion.getApellidoPaterno());
                prepararSentencia.setString(4, responsableEdicion.getApellidoMaterno());
                prepararSentencia.setString(5, responsableEdicion.getNombre());
                prepararSentencia.setString(6, responsableEdicion.getCorreoInstitucional());
                prepararSentencia.setString(7, responsableEdicion.getNumeroTelefono());
                prepararSentencia.setInt(8, responsableEdicion.getIdUsuario());
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
