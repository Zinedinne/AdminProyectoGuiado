package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Materia;
import javafxproyectoguiado.modelo.pojo.MateriaRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class MateriaDAO {
    public static MateriaRespuesta obtenerInformacionMateria(){
        MateriaRespuesta respuesta = new MateriaRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM Materia";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Materia> materiaConsulta = new ArrayList();
                while(resultado.next()){
                    Materia materia = new Materia();
                    materia.setIdMateria(resultado.getInt("idMateria"));
                    materia.setNombre(resultado.getString("nombre"));
                    materiaConsulta.add(materia);
                }
                respuesta.setMaterias(materiaConsulta);
                conexionBD.close();
            } catch (SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
