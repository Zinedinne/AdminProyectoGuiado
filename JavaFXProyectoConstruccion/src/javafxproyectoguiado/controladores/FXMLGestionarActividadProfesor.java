package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.dao.EntregaDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.Evaluacion;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.Utilidades;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLGestionarActividadProfesor implements Initializable {

    @FXML
    private HTMLEditor htmlEditorDescripcion;
    @FXML
    private HTMLEditor htmlEditorRetroalimentacion;
    @FXML
    private Button btnCalificar;

    @FXML
    private Button btnRegresar;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelAlumno;

    @FXML
    private Label labelFechaInicio;

    @FXML
    private Label labelCalificacion;

    @FXML
    private Label labelFechaFin;

    @FXML
    private Label labelArchivo;

    @FXML
    private Button btnDescargarDocumento;
    private int idActividad;
    private Actividades actividad;
    private final Evaluacion evaluacion = new Evaluacion();
    private File selectedFile = null;
    private static Entrega entrega = new Entrega();

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

    }

    @FXML
    void btnCalificarOnAction(ActionEvent event) {

    }

    @FXML
    void btnDescargarDocumentoOnAction(ActionEvent event) {

    }
    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;

    }
    public void obtenerActividad() {
        ActividadesDAO actividadesDAO = new ActividadesDAO();
        try {
            actividad = actividadesDAO.getActividad(idActividad);

        } catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar las actividades", Alert.AlertType.ERROR);
            } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            } else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido", Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idActividad != 0) {
            configurarVentana();
            verificarEntrega();
        }
    }
    public void verificarEntrega() {
        EntregaDAO entregaDAO = new EntregaDAO();
        try {
            entrega =entregaDAO.verificarEntrega(idActividad);
            if (entrega.getIdEntrega()!=0) {
                labelArchivo.setText(entrega.getNombreArchivo());
                btnDescargarDocumento.setVisible(true);
            }
        } catch (SQLException throwables) {
            if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al verificar la entrega", Alert.AlertType.ERROR);
            }else if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
    }
    public void configurarVentana() {

        obtenerActividad();
        labelAlumno.setText(Singleton.getName());
        labelFechaInicio.setText(actividad.getFechaInicio());
        labelFechaFin.setText(actividad.getFechaFin());
        labelCalificacion.setText(evaluacion.getCalificacion());
        labelTitulo.setText(actividad.getTitulo());
    }
}
