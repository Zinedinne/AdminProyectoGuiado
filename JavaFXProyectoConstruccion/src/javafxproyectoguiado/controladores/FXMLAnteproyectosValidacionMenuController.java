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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;
import javafxproyectoguiado.modelo.dao.AnteproyectoModuloDAO;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModuloRespuesta;
import util.Constantes;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosValidacionMenuController implements Initializable{

    @FXML
    private TableView<AnteproyectoModulo> tvAnteproyectosValidados;
    private ObservableList<AnteproyectoModulo> anteproyectos;
    @FXML
    private TableColumn colNombreAnteproyecto;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaFin;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colLGAC;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colResponsable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    private void configurarTabla(){
        colNombreAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("duracion"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("nombreLGAC"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        Utilidades.asignarTextoEstado(colEstado);
        colResponsable.setCellValueFactory(new PropertyValueFactory("nombreCreador"));
    }
    
    private void cargarInformacionTabla(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta respuestaBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyectoValidacion();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Sin conexión",
                            "Los sentimos por el momento no hay conexión para poder cargar la información", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    anteproyectos.addAll(respuestaBD.getAnteproyectosModulo());
                    tvAnteproyectosValidados.setItems(anteproyectos);
                break;
        }
    }
    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvAnteproyectosValidados.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnValidarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectosValidados.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            
            if (anteproyectos.get(posicion).getEstado().equals("1")) {
            Utilidades.mostrarDiallogoSimple("Anteproyecto ya validado", 
                    "No se puede validar un anteproyecto que ya ha sido validado", 
                    Alert.AlertType.WARNING);
            return;
            }
            
            if (anteproyectos.get(posicion).getEstado().equals("2")) {
            Utilidades.mostrarDiallogoSimple("Anteproyecto denegado", 
                    "No se puede validar un anteproyecto que ya ha sido denegado", 
                    Alert.AlertType.WARNING);
            return;
            }
            
            boolean validarRegistro = Utilidades.mostrarDialogoConfirmacion("Validar anteproyecto", 
                    "¿Estás seguro de que deseas validar el registro del anteproyecto: "
                            +anteproyectos.get(posicion).getNombreAnteproyecto());
            if(validarRegistro){
                 int codigoRespuesta = AnteproyectoModuloDAO.validarEstadoAnteproyecto(anteproyectos.get(posicion).getIdAnteproyecto(), 
                         anteproyectos.get(posicion).getEstado());
                switch(codigoRespuesta){
                    case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Sin conexión",
                            "Los sentimos por el momento no hay conexión para poder cargar la información", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDiallogoSimple("Registro validado",
                            "Se ha validado exitosamente el registro",
                            Alert.AlertType.INFORMATION);
                    cargarInformacionTabla();
                break;
                }
            }
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un anteproyecto", 
                    "Para validar un anteproyecto debes seleccionarlo previamente en la tabla", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnDenegarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectosValidados.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            
            if (anteproyectos.get(posicion).getEstado().equals("1")) {
            Utilidades.mostrarDiallogoSimple("Anteproyecto ya validado", 
                    "No se puede denegar un anteproyecto que ya ha sido validado", 
                    Alert.AlertType.WARNING);
            return;
            }
            
            if (anteproyectos.get(posicion).getEstado().equals("2")) {
            Utilidades.mostrarDiallogoSimple("Anteproyecto denegado", 
                    "No se puede denegar un anteproyecto que ya ha sido denegado", 
                    Alert.AlertType.WARNING);
            return;
            }
            
            boolean validarRegistro = Utilidades.mostrarDialogoConfirmacion("Denegar anteproyecto", 
                    "¿Estás seguro de que deseas denegar el registro del anteproyecto: "
                            +anteproyectos.get(posicion).getNombreAnteproyecto());
            if(validarRegistro){
                 int codigoRespuesta = AnteproyectoModuloDAO.denegarEstadoAnteproyecto(anteproyectos.get(posicion).getIdAnteproyecto(), 
                         anteproyectos.get(posicion).getEstado());
                switch(codigoRespuesta){
                    case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Sin conexión",
                            "Los sentimos por el momento no hay conexión para poder cargar la información", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    Utilidades.mostrarDiallogoSimple("Registro denegado",
                            "Se ha denegado exitosamente el registro",
                            Alert.AlertType.INFORMATION);
                    cargarInformacionTabla();
                break;
                }
            }
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un anteproyecto", 
                    "Para validar un anteproyecto debes seleccionarlo previamente en la tabla", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnConsultarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectosValidados.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            irConsulta(anteproyectos.get(posicion));
            //tvAlumnos.getSelectionModel().getSelectedItem();
        }else{
            Utilidades.mostrarDiallogoSimple("Selecione un anteproyecto", 
                    "Debe seleccionar un registro en la tabla de anteproyectos para su consulta", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void irConsulta(AnteproyectoModulo anteproyectoConsulta){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLAnteproyectosConsulta.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAnteproyectosConsultaController consulta = accesoControlador.getController();
            
            consulta.inicializarInformacionConsulta(anteproyectoConsulta);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Consultar Anteproyecto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLAnteproyectosConsultaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
