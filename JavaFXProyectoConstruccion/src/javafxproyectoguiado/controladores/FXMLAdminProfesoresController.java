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
import javafxproyectoguiado.modelo.dao.ProfesorDAO;
import javafxproyectoguiado.modelo.pojo.Profesor;
import javafxproyectoguiado.modelo.pojo.ProfesorRespuesta;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLAdminProfesoresController implements Initializable, INotificacionOperacionUsuario {

    @FXML
    private TableView<Profesor> tvProfesores;
    @FXML
    private TableColumn tcNumeroTrabajador;
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
    private ObservableList<Profesor> profesores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla(){
        tcNumeroTrabajador.setCellValueFactory(new PropertyValueFactory("numeroTrabajador"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("ApellidoPaterno"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("ApellidoMaterno"));
        tcUsername.setCellValueFactory(new PropertyValueFactory("Username"));
        tcPassword.setCellValueFactory(new PropertyValueFactory("Password"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("CorreoInstitucional"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("numeroTelefono"));
    }
    
    private void cargarInformacionTabla(){
        profesores = FXCollections.observableArrayList();
        ProfesorRespuesta respuestaBD = ProfesorDAO.obtenerInformacionProfesor();
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
                    profesores.addAll(respuestaBD.getProfesores());
                    tvProfesores.setItems(profesores);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvProfesores.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnModificarProfesor(ActionEvent event) {
        Profesor profesorSeleccionado = tvProfesores.getSelectionModel().getSelectedItem();
        if(profesorSeleccionado != null){
            irFormulario(true, profesorSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Estudiante", 
                    "Selecciona el registro en la tabla del Profesor para su edicion", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnRegistrarProfesor(ActionEvent event) {
        irFormulario(false,null);
    }
    
    private void irFormulario(boolean esEdicion, Profesor profesorEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioProfesor.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioProfesorController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, profesorEdicion,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Formulario Profesor");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioProfesorController.class.getName()).log(Level.SEVERE, null, ex);
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
