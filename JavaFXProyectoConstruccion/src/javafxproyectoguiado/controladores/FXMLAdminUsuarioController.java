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
import javafxproyectoguiado.modelo.dao.RegistroUsuarioDAO;
import javafxproyectoguiado.modelo.pojo.RegistroUsuario;
import javafxproyectoguiado.modelo.pojo.RegistroUsuarioRespuesta;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLAdminUsuarioController implements Initializable, INotificacionOperacionUsuario {

    @FXML
    private TableView<RegistroUsuario> tvUsuarios;
    @FXML
    private TableColumn tcUsername;
    @FXML
    private TableColumn tcPassword;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcTipoUsuario;
    private ObservableList<RegistroUsuario> usuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcUsername.setCellValueFactory(new PropertyValueFactory("username"));
        tcPassword.setCellValueFactory(new PropertyValueFactory("password"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correoInstitucional"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("numeroTelefono"));
        tcTipoUsuario.setCellValueFactory(new PropertyValueFactory("tipoUsuario"));
    }
    
    private void cargarInformacionTabla(){
        usuarios = FXCollections.observableArrayList();
        RegistroUsuarioRespuesta respuestaBD = RegistroUsuarioDAO.obtenerInformacionUsuario();
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
                    usuarios.addAll(respuestaBD.getUsuarios());
                    tvUsuarios.setItems(usuarios);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvUsuarios.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        RegistroUsuario usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioSeleccionado != null){
            irFormulario(true, usuarioSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Usuario", 
                    "Selecciona el registro en la tabla del Usuario para su edicion", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        irFormulario(false,null);
    }
    
    private void irFormulario(boolean esEdicion, RegistroUsuario usuarioEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioUsuario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioUsuarioController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, usuarioEdicion,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("FormularioUsuario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioCursoController.class.getName()).log(Level.SEVERE, null, ex);
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
