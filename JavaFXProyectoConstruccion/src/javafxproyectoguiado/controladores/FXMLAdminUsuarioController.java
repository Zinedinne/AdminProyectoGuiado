package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.UsuarioDAO;
import javafxproyectoguiado.modelo.pojo.Usuario;
import util.Constantes;
import util.Utilidades;

public class FXMLAdminUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tvUsuarios;
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
    private ObservableList<Usuario> usuarios;

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
        Usuario respuestaBD = UsuarioDAO.obtenerInformacionUsuario();
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
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        
    }
    
}
