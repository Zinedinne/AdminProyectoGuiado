package javafxproyectoguiado.controladores;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafxproyectoguiado.modelo.pojo.Anteproyecto;
import javafxproyectoguiado.modelo.pojo.Usuario;

public class FXMLRegistrarActividad {

    @FXML
    private ComboBox<Anteproyecto> comboBoxProyecto;

    @FXML
    private ComboBox<Usuario> comboBoxEstudiante;

    @FXML
    private Button btnRegresar;

    @FXML
    private TextField textFieldTitulo;

    @FXML
    private Label labelDocente;

    @FXML
    private Label labelDate;

    @FXML
    private DatePicker datePickerFechaInicio;

    @FXML
    private DatePicker datePickerFechaEntrega;

    @FXML
    private Button btnRegistrarActividad;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegistrarActividadOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxEstudianteOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxProyectoOnAction(ActionEvent event) {

    }

}