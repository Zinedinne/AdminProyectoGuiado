package javafxproyectoguiado.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;
import javafxproyectoguiado.modelo.dao.EstudianteDAO;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import javafxproyectoguiado.modelo.pojo.EstudianteRespuesta;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLAdminEstudiantesController implements Initializable, INotificacionOperacionUsuario{

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn tcMatricula;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcUsername;
    @FXML
    private TableColumn tcPassword;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    private ObservableList<Estudiante> estudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    private void configurarTabla(){
        tcMatricula.setCellValueFactory(new PropertyValueFactory("Matricula"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("ApellidoPaterno"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("ApellidoMaterno"));
        tcUsername.setCellValueFactory(new PropertyValueFactory("Username"));
        tcPassword.setCellValueFactory(new PropertyValueFactory("Password"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("CorreoInstitucional"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("numeroTelefono"));
    }
    
    private void cargarInformacionTabla(){
        estudiantes = FXCollections.observableArrayList();
        EstudianteRespuesta respuestaBD = EstudianteDAO.obtenerInformacionEstudiante();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error Conxeion", 
                            "Los sentimos por el momento no hay conexión para poder cargar la información", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    estudiantes.addAll(respuestaBD.getEstudiantes());
                    tvEstudiantes.setItems(estudiantes);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvEstudiantes.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnRegistrarEstudiante(ActionEvent event) {
        irFormulario(false,null);
    }

    @FXML
    private void clicBtnModificarEstudiante(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if(estudianteSeleccionado != null){
            irFormulario(true, estudianteSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Estudiante", 
                    "Selecciona el registro en la tabla del Estudiante para su edicion", Alert.AlertType.WARNING);
        }
    }
    
    private void irFormulario(boolean esEdicion, Estudiante estudianteEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioEstudiante.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioEstudianteController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, estudianteEdicion,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Formulario Estudiante");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioEstudianteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacionGuardarUsuario() {
        cargarInformacionTabla();
    }

    @Override
    public void notificarOperacionActualizarUsuario() {
        cargarInformacionTabla();
    }
    
}
