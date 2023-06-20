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
import javafx.scene.control.Button;
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
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosMenuController implements Initializable {

    @FXML
    private TableView<AnteproyectoModulo> tvAnteproyectos;
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
    @FXML
    private Button btnAsignarEstudiante;
    @FXML
    private TableColumn colResponsable;
    
    private AnteproyectoModulo anteproyectoEdicion;
    private boolean esEdicion;

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
        colAlumnoAsignado.setCellValueFactory(new PropertyValueFactory("estudiantesAsignados"));
    }
    
    private void cargarInformacionTabla(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta respuestaBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyectoDirector();
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
    private void clicBtnAsignarEstudiante(ActionEvent event) {
        int posicion = tvAnteproyectos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            AnteproyectoModulo anteproyecto = anteproyectos.get(posicion);
            if (anteproyecto.getEstado().equals("1")) {
                
                    irValidacion(anteproyecto);
                    cargarInformacionTabla();
                
            } else if (anteproyecto.getEstado().equals("0")) {
                Utilidades.mostrarDiallogoSimple("Anteproyecto aún postulado",
                    "El anteproyecto seleccionado aún se encuentra postulado.",
                    Alert.AlertType.WARNING);
            } else if (anteproyecto.getEstado().equals("2")) {
                Utilidades.mostrarDiallogoSimple("Anteproyecto está denegado",
                    "El anteproyecto seleccionado se encuentra denegado.",
                    Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarDiallogoSimple("Seleccione un anteproyecto",
                "Debe seleccionar un registro en la tabla de anteproyectos para su consulta.",
                Alert.AlertType.WARNING);
        }
    }

    private void irValidacion(AnteproyectoModulo anteproyectoConsulta){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLAsignarEstudianteAnteproyecto.fxml"));
            Parent vista = accesoControlador.load();
            FXMLAsignarEstudianteAnteproyectoController consulta = accesoControlador.getController();
            
            consulta.inicializarInformacionConsulta(anteproyectoConsulta);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Asignar Anteproyecto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLAsignarEstudianteAnteproyectoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}