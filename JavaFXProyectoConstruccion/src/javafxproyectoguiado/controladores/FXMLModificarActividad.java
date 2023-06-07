package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLModificarActividad implements Initializable {

    @FXML
    private HTMLEditor htmlEditorDescripcion;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField textFieldTitulo;

    @FXML
    private Button btnModificarActividad;

    @FXML
    private Label labelDocente;

    @FXML
    private Label labelDate;

    @FXML
    private DatePicker datePickerFechaInicio;

    @FXML
    private DatePicker datePickerFechaEntrega;

    @FXML
    private Label labelAnteproyecto;

    @FXML
    private Label labelEstudiante;

    private int idActividad;
    private int idAlumno;
    private String nombreAlumno;
    private String nombreAnteproyecto;
    public void btnRegresarOnAction(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLActividadesMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnRegresar.getScene().getWindow();
            stage.setTitle("Menú de Actividades");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    public void btnModificarActividadOnAction(ActionEvent actionEvent) {
        LocalDate fechaInicio = datePickerFechaInicio.getValue();
        LocalDate fechaEntrega = datePickerFechaEntrega.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if (fechaInicio.isAfter(fechaEntrega)){
            Utilidades.mostrarDiallogoSimple("Error", "La fecha de inicio no puede ser mayor a la fecha de entrega", Alert.AlertType.ERROR);
        }else {
            ActividadesDAO activitiesDAO = new ActividadesDAO();
            Actividades actividad = new Actividades();
            actividad.setTitulo(textFieldTitulo.getText());
            actividad.setFechaInicio(fechaInicio.format(formatter));
            actividad.setDescripcion(htmlEditorDescripcion.getHtmlText());
            actividad.setFechaFin(fechaEntrega.format(formatter));
            actividad.setIdActividad(idActividad);
            try {
                if (activitiesDAO.modificarActividad(actividad)) {
                    Utilidades.mostrarDiallogoSimple("Exito", "Actividad registrada con exito", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException throwables) {
                if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error al registrar la actividad", Alert.AlertType.ERROR);
                } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarDiallogoSimple("Error", "Error desconocido", Alert.AlertType.ERROR);
                }
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idActividad != 0){
            obtenerActividad();
            configurarVentana();
        }
    }

    private void configurarVentana() {
        labelDocente.setText(Singleton.getName());
        labelEstudiante.setText(nombreAlumno);
        labelAnteproyecto.setText(nombreAnteproyecto);
    }

    public void setIdActividad(int idActividad, String nombre, int idAlumno, String nombreAnteproyecto) {
        this.idActividad = idActividad;
        this.nombreAlumno = nombre;
        this.idAlumno = idAlumno;
        this.nombreAnteproyecto = nombreAnteproyecto;
    }
    private void obtenerActividad() {
        ActividadesDAO actividadesDAO = new ActividadesDAO();
        Actividades actividades ;
        try {
            actividades = actividadesDAO.getActividad(idActividad);
            if(actividades!= null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fechaInicio = LocalDate.parse(actividades.getFechaInicio(), formatter);
                LocalDate fechaEntrega = LocalDate.parse(actividades.getFechaFin(), formatter);
                textFieldTitulo.setText(actividades.getTitulo());
                htmlEditorDescripcion.setHtmlText(actividades.getDescripcion());
                datePickerFechaInicio.setValue(fechaInicio);
                datePickerFechaEntrega.setValue(fechaEntrega);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
