package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Curso;
import javafxproyectoguiado.modelo.pojo.CursoRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class CursoDAO {
    public static CursoRespuesta obtenerInformacionCurso(){
        CursoRespuesta respuesta = new CursoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select idCurso, bloque, nrc, seccion, curso.idMateria, materia.nombre AS nombreMateria, " +
                        "curso.idPeriodo, periodo.nombre AS nombrePeriodo " +
                        "from curso " +
                        "inner join Materia ON Curso.idMateria = Materia.idMateria " +
                        "inner join Periodo ON Curso.idPeriodo = Periodo.idPeriodo;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Curso> cursoConsulta = new ArrayList();
                while(resultado.next()){
                    Curso curso = new Curso();
                    curso.setIdCurso(resultado.getInt("idCurso"));
                    curso.setBloque(resultado.getString("bloque"));
                    curso.setNrc(resultado.getString("nrc"));
                    curso.setSeccion(resultado.getString("seccion"));
                    curso.setIdMateria(resultado.getInt("idMateria"));
                    curso.setNombreMateria(resultado.getString("nombreMateria"));
                    curso.setIdPeriodo(resultado.getInt("idPeriodo"));
                    curso.setNombrePeriodo(resultado.getString("nombrePeriodo"));
                    cursoConsulta.add(curso);
                }
                respuesta.setCursos(cursoConsulta);
                conexionBD.close();
            } catch (SQLException e)
            {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
