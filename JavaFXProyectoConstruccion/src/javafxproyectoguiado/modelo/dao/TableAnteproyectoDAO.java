package javafxproyectoguiado.modelo.dao;

import javafxproyectoguiado.modelo.pojo.TableActivities;
import javafxproyectoguiado.modelo.pojo.TableAnteproyecto;
import modelo.ConexionBD;
import util.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableAnteproyectoDAO {
    public static List<TableAnteproyecto> getAnteproyecto(int idUsuario) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        List<TableAnteproyecto> listaAnteproyecto = new ArrayList<>();
        if(conexion != null){
            String consulta = "SELECT * from anteproyecto where Usuarios_idUsuarios = ?";
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idUsuario));
                ResultSet resultado = prepararSentencia.executeQuery();

                if(resultado.next()){

                    do{

                        TableAnteproyecto anteproyecto = new TableAnteproyecto();
                        anteproyecto.setDescripcion(resultado.getString("descripcion"));
                        anteproyecto.setFechaInicio(resultado.getString("fechaInicio"));
                        anteproyecto.setDuracion(resultado.getString("duracion"));
                        anteproyecto.setEstado(resultado.getBoolean("estado"));
                        anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                        anteproyecto.setModalidad(resultado.getString("modalidad"));
                        anteproyecto.setNombreAnteproyecto(resultado.getString("nombreAnteproyecto"));
                        anteproyecto.setIdUsuario(resultado.getInt("Usuarios_idUsuarios"));
                        listaAnteproyecto.add(anteproyecto);
                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return listaAnteproyecto;
    }
}
