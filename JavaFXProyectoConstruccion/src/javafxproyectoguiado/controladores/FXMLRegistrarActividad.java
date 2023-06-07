package javafxproyectoguiado.controladores;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.dao.AnteproyectoDAO;
import javafxproyectoguiado.modelo.dao.UsuarioDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Anteproyecto;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.Usuario;
import util.Constantes;
import util.Utilidades;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLRegistrarActividad implements Initializable {

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
    private HTMLEditor htmlEditorDescripcion;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegistrarActividadOnAction(ActionEvent event) {
        validarCampos();
    }

    private void validarCampos() {
        if (textFieldTitulo.getText().isEmpty() || datePickerFechaInicio.getValue() == null || datePickerFechaEntrega.getValue() == null ||htmlEditorDescripcion.getHtmlText().isEmpty()){
            Utilidades.mostrarDiallogoSimple("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
        }else {
            registrarActividad();
        }
    }

    private void registrarActividad() {
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
            try {
                if (activitiesDAO.registrarActividad(actividad, comboBoxEstudiante.getValue().getIdUsuario())) {
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

    private void limpiarCampos() {
        htmlEditorDescripcion.setHtmlText("");
        datePickerFechaEntrega = null;
        datePickerFechaInicio = null;
    }

    @FXML
    void comboBoxEstudianteOnAction(ActionEvent event) {
        btnRegistrarActividad.setDisable(false);
    }

    @FXML
    void comboBoxProyectoOnAction(ActionEvent event) {
        comboBoxEstudiante.setDisable(false);
        setEstudiantesToCombobox();
    }

    private void setEstudiantesToCombobox() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            List<Usuario> usuarioList = usuarioDAO.getEstudiantesPorAnteproyecto(comboBoxProyecto.getSelectionModel().getSelectedItem().getIdAnteproyecto());
            ObservableList<Usuario> usuarioObservableList = FXCollections.observableArrayList();
            usuarioObservableList.addAll(usuarioList);
            comboBoxEstudiante.setItems(usuarioObservableList);
        }catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar los Estudiantes",  Alert.AlertType.ERROR);
            }
            else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))){
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos",  Alert.AlertType.ERROR);
            }else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido",  Alert.AlertType.ERROR);
            }
        }

    }

    public void setAnteproyectosToCombobox(){
        AnteproyectoDAO anteproyectoDAO = new AnteproyectoDAO();
        try {
            List<Anteproyecto> anteproyectoList = anteproyectoDAO.getAllAnteproyectosPorProfesor(Singleton.getId());
            ObservableList<Anteproyecto> anteproyectoObservableList = FXCollections.observableArrayList();
            anteproyectoObservableList.addAll(anteproyectoList);
            comboBoxProyecto.setItems(anteproyectoObservableList);
        } catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar los Anteproyectos",  Alert.AlertType.ERROR);
            }
            else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))){
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos",  Alert.AlertType.ERROR);
            }else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido",  Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAnteproyectosToCombobox();
    }
}