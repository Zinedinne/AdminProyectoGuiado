package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import modelo.ConexionBD;
import util.Constantes;

public class EstudianteDAO {
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
                    String queryLastID = "SELECT LAST_INSERT_ID()";
                    PreparedStatement stmtLastID = conexionBD.prepareStatement(queryLastID);
                    int idUsuario;
                    try (ResultSet rs = stmtLastID.executeQuery()) {
                        rs.next();
                        idUsuario = rs.getInt(1);
                    } 
                    String sentenciaEstudiante = "INSERT INTO Estudiante (matricula, idUsuario) "
                            + "VALUES(?, ?);";
                    PreparedStatement prepararSentenciaEstudiante = conexionBD.prepareStatement(sentenciaEstudiante);
                    prepararSentenciaEstudiante.setString(1, estudianteNuevo.getMatricula());
                    prepararSentenciaEstudiante.setInt(2, idUsuario);
                    prepararSentenciaEstudiante.executeQuery();
                }
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