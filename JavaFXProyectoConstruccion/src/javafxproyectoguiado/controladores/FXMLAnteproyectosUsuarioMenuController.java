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
import javafxproyectoguiado.modelo.dao.AnteproyectoModuloDAO;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModuloRespuesta;
import util.Constantes;
import util.INotificacionOperacionAnteproyecto;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosUsuarioMenuController implements Initializable, INotificacionOperacionAnteproyecto {

    @FXML
    private TableView tvAnteproyectos;
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
    private TableColumn colAlumnoAsignado;

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
        colAlumnoAsignado.setCellValueFactory(new PropertyValueFactory("estudiantesAsignados"));
    }
    
    private void cargarInformacionTabla(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta respuestaBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyectoUsuario();
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
                    tvAnteproyectos.setItems(anteproyectos);
                break;
        }
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tvAnteproyectos.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnCrearAnteproyecto(ActionEvent event) {
        irFormulario(false,null);
    }
    
    private void irFormulario(boolean esEdicion, AnteproyectoModulo anteproyectoEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLAnteproyectosFormulario.fxml"));
            Parent vista = accesoControlador.load();                        
            FXMLAnteproyectosFormularioController formulario = accesoControlador.getController();
            formulario.inicializarInformacionFormulario(esEdicion, anteproyectoEdicion, this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Formulario");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLAnteproyectosFormularioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicBtnModificarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            if (anteproyectos.get(posicion).getEstado().equals("1")) {
            Utilidades.mostrarDiallogoSimple("Anteproyecto ya validado", 
                    "No se puede modificar un anteproyecto que ya ha sido validado", 
                    Alert.AlertType.WARNING);
            return;
            }
            irFormulario(true, anteproyectos.get(posicion));
        }else{
            Utilidades.mostrarDiallogoSimple("Selecione un anteproyecto", 
                    "Debe seleccionar un registro en la tabla de anteproyectos para su edición", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnConsultarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            irConsulta(anteproyectos.get(posicion));
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

    @FXML
    private void clicBtnEliminarAnteproyecto(ActionEvent event) {
        int posicion = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            
            AnteproyectoModulo anteproyectoSeleccionado = anteproyectos.get(posicion);
            String alumnoAsignado = anteproyectoSeleccionado.getEstudianteAsignado();

            if (anteproyectos.get(posicion).getEstado().equals("1")) {
                Utilidades.mostrarDiallogoSimple("No se puede eliminar",
                    "No se puede eliminar el registro del anteproyecto \"" + anteproyectoSeleccionado.getNombreAnteproyecto()
                    + "\" porque ya ha sido validado.",
                    Alert.AlertType.WARNING);
            } else {
                boolean borrarRegistro = Utilidades.mostrarDialogoConfirmacion("Eliminar registro de anteproyecto",
                    "¿Estás seguro de que deseas eliminar el registro del anteproyecto: "
                    + anteproyectoSeleccionado.getNombreAnteproyecto());

                if (borrarRegistro) {
                    int codigoRespuesta = AnteproyectoModuloDAO.eliminarAnteproyecto(anteproyectoSeleccionado.getIdAnteproyecto());
                    switch (codigoRespuesta) {
                        case Constantes.ERROR_CONEXION:
                            Utilidades.mostrarDiallogoSimple("Sin conexión",
                                "Los sentimos por el momento no hay conexión para poder cargar la información",
                                Alert.AlertType.ERROR);
                            break;
                        case Constantes.ERROR_CONSULTA:
                            Utilidades.mostrarDiallogoSimple("Error al cargar los datos",
                                "Hubo un error al cargar la información, por favor inténtelo más tarde",
                                Alert.AlertType.WARNING);
                            break;
                        case Constantes.OPERACION_EXITOSA:
                            Utilidades.mostrarDiallogoSimple("Registro eliminado",
                                "Se ha eliminado exitosamente el registro",
                                Alert.AlertType.INFORMATION);
                            cargarInformacionTabla();
                            break;
                    }
                }
            }
        } else {
            Utilidades.mostrarDiallogoSimple("Selecciona un anteproyecto",
                "Para eliminar un anteproyecto debes seleccionarlo previamente de la tabla",
                Alert.AlertType.WARNING);
        }
    }

    @Override
    public void notificarOperacionGuardarAnteproyecto(String nombre) {
        cargarInformacionTabla();
        Utilidades.mostrarDiallogoSimple("Notificación", 
                "Anteproyecto: "+nombre+" . Ha sido guardadado", 
                Alert.AlertType.INFORMATION);
    }

    @Override
    public void notificarOperacionActualizarAnteproyecto(String nombreAnteproyecto) {
        cargarInformacionTabla();
        Utilidades.mostrarDiallogoSimple("Notificación", 
                "Anteproyecto: "+nombreAnteproyecto+" . Ha sido actualizado", 
                Alert.AlertType.INFORMATION);
    }
    
}
