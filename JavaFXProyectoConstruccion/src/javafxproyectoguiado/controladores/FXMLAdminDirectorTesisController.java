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
import javafxproyectoguiado.modelo.dao.DirectorTesisDAO;
import javafxproyectoguiado.modelo.pojo.DirectorTesis;
import javafxproyectoguiado.modelo.pojo.DirectorTesisRespuesta;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLAdminDirectorTesisController implements Initializable, INotificacionOperacionUsuario {

    @FXML
    private TableView<DirectorTesis> tvDirectores;
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
    private ObservableList<DirectorTesis> directores;

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
        directores = FXCollections.observableArrayList();
        DirectorTesisRespuesta respuestaBD = DirectorTesisDAO.obtenerInformacionDirectorTesis();
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
                    directores.addAll(respuestaBD.getDirectores());
                    tvDirectores.setItems(directores);
                break;
        }
    }

    @FXML
    private void clicBtnRegistrarDirectorTesis(ActionEvent event) {
        irFormulario(false,null);
    }

    @FXML
    private void clicBtnModificarDirectorTesis(ActionEvent event) {
        DirectorTesis directorTesisSeleccionado = tvDirectores.getSelectionModel().getSelectedItem();
        if(directorTesisSeleccionado != null){
            irFormulario(true, directorTesisSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Director de Tesis", 
                    "Selecciona el registro en la tabla del Director de Tesis para su edicion", Alert.AlertType.WARNING);
        }
    }
    
    private void irFormulario(boolean esEdicion, DirectorTesis directorTesisEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioDirectorTesis.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioDirectorTesisController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, directorTesisEdicion,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Formulario Director Tesis");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioDirectorTesisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvDirectores.getScene().getWindow();
        escearioPrincipal.close();
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
