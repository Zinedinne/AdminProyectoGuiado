package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Evaluacion;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.Utilidades;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLGestionarActividad extends Stage implements Initializable {

    @FXML
    private Button btnAdjuntarArchivo;

    @FXML
    private WebView webViewDescripcion;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnRegresar;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelAlumno;

    @FXML
    private Label labelCalificacion;

    @FXML
    private Label labelFechaFin;

    @FXML
    private Label labelArchivo;

    @FXML
    private Label labelFechaInicio;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

    }

    @FXML
    void btnEnviarOnAction(ActionEvent event) {

    }
    private int idActividad;

    private Actividades actividad;
    private Evaluacion evaluacion = new Evaluacion();
    private File selectedFile = null;


    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idActividad != 0){
            configurarVentana();
        }
    }

    public void configurarVentana(){

        obtenerActividad();
        labelAlumno.setText(Singleton.getName());
        labelFechaInicio.setText(actividad.getFechaInicio());
        labelFechaFin.setText(actividad.getFechaFin());
        labelCalificacion.setText(evaluacion.getCalificacion());
        WebEngine webEngine = webViewDescripcion.getEngine();
        webEngine.loadContent(actividad.getDescripcion());
    }

    public void obtenerActividad(){
        ActividadesDAO actividadesDAO = new ActividadesDAO();
        try{
            actividad= actividadesDAO.getActividad(idActividad);

        }catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar las actividades",  Alert.AlertType.ERROR);
            }
            else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))){
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos",  Alert.AlertType.ERROR);
            }else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido",  Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    void btnAdjuntarArchivoOnAction(ActionEvent event) {
        Utilidades.mostrarDiallogoSimple("Adjuntar Archivo", "Adjuntar Archivo", Alert.AlertType.INFORMATION);
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            labelArchivo.setText(selectedFile.getName());
        } else {
            labelArchivo.setText("No se ha seleccionado ningún archivo");
        }
    }
}