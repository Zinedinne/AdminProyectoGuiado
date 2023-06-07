package javafxproyectoguiado.modelo.dao;

import javafx.scene.control.Alert;
import javafxproyectoguiado.modelo.pojo.Anteproyecto;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import modelo.ConexionBD;
import util.Constantes;
import util.Utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnteproyectoDAO {
    public List<Anteproyecto> getAllAnteproyectosPorProfesor(int idUsuario) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        List<Anteproyecto> listaAnteproyecto = new ArrayList<>();
        if(conexion != null){
            String consulta = "SELECT *\n" +
                    "FROM anteproyecto \n" +
                    "WHERE Usuarios_idUsuarios = ? or idEncargadoDeTesis=?";;
            try {
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, String.valueOf(idUsuario));
                prepararSentencia.setString(2, String.valueOf(idUsuario));

                ResultSet resultado = prepararSentencia.executeQuery();
                if(resultado.next()){
                    do{
                        Anteproyecto anteproyecto = new Anteproyecto();
                        anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                        anteproyecto.setDescripcion(resultado.getString("descripcion"));
                        anteproyecto.setFechaInicio(resultado.getString("fechaInicio"));
                        anteproyecto.setDuracion(resultado.getString("duracion"));
                        anteproyecto.setEstado(resultado.getBoolean("estado"));
                        anteproyecto.setModalidad(resultado.getString("modalidad"));
                        anteproyecto.setNombreAnteproyecto(resultado.getString("nombreAnteproyecto"));
                        anteproyecto.setIdLGAC(resultado.getInt("LGAC_idLGAC"));
                        anteproyecto.setIdUsuario(resultado.getInt("Usuarios_idUsuarios"));
                        anteproyecto.setIdEncargadoDeTesis(resultado.getInt("idEncargadoDeTesis"));
                        listaAnteproyecto.add(anteproyecto);

                    }while(resultado.next());
                }
                conexion.close();
            } catch (SQLException ex) {
                Utilidades.mostrarDiallogoSimple("",ex.getMessage(), Alert.AlertType.ERROR);
                throw new SQLException(String.valueOf(Constantes.ERROR_CONSULTA));
            }
        } else {
            throw new SQLException(String.valueOf(Constantes.ERROR_CONEXION));
        }
        return listaAnteproyecto;
    }
}
