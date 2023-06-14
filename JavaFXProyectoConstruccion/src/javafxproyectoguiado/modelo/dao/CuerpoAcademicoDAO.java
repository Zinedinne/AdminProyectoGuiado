package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademico;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademicoRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class CuerpoAcademicoDAO {
    public static CuerpoAcademicoRespuesta obtenerInformaciónCuerpoAcademico(){
        CuerpoAcademicoRespuesta respuesta = new CuerpoAcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM Academia ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<CuerpoAcademico> cuerpoAcademicoConsulta = new ArrayList();
                while(resultado.next()){
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdAcademia(resultado.getInt("idAcademia"));
                    cuerpoAcademico.setNombreAcademia(resultado.getString("nombreAcademia"));
                    cuerpoAcademicoConsulta.add(cuerpoAcademico);
                }
                respuesta.setAcademias(cuerpoAcademicoConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarCuerpoAcademico (CuerpoAcademico cuerpoAcademicoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO academia (nombreAcademia) "
                        + "VALUES(?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, cuerpoAcademicoNuevo.getNombreAcademia());
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
    
    public static int modificarCuerpoAcademico (CuerpoAcademico cuerpoAcademicoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE academia SET nombreAcademia = ? "
                        + "WHERE idAcademia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, cuerpoAcademicoEdicion.getNombreAcademia());
                prepararSentencia.setInt(2, cuerpoAcademicoEdicion.getIdAcademia());
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
    
    public static int obtenerCantidadAcademia(int idAcademia) {
        int cantidad = 0;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT COUNT(*) FROM proyecto.academico WHERE idAcademia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAcademia);
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
