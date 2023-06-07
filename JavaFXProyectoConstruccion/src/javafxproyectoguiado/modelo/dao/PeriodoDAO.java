package javafxproyectoguiado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxproyectoguiado.modelo.pojo.Periodo;
import javafxproyectoguiado.modelo.pojo.PeriodoRespuesta;
import modelo.ConexionBD;
import util.Constantes;

public class PeriodoDAO {
    public static PeriodoRespuesta obtenerInformacionPeriodo(){
        PeriodoRespuesta respuesta = new PeriodoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT * FROM Periodo;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Periodo> periodoConsulta = new ArrayList();
                while(resultado.next()){
                    Periodo periodo = new Periodo();
                    periodo.setIdPeriod(resultado.getInt("idPeriodo"));
                    periodo.setFechaInicio(resultado.getString("fechaInicio"));
                    periodo.setFechaFin(resultado.getString("fechaFin"));
                    periodo.setNombre(resultado.getString("nombre"));
                    periodoConsulta.add(periodo);
                }
                respuesta.setPeriodos(periodoConsulta);
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
