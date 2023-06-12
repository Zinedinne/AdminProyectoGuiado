/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import util.INotificacionOperacionAnteproyectos;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosMenuController implements Initializable, INotificacionOperacionAnteproyectos {

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
    private TextField tfBusquedaAnteproyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla(){
        String ESTADOTEXTO = "";
        
        colNombreAnteproyecto.setCellValueFactory(new PropertyValueFactory("nombreAnteproyecto"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("duracion"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        colLGAC.setCellValueFactory(new PropertyValueFactory("nombreLGAC"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        Utilidades.asignarTextoEstado(colEstado);
        colAlumnoAsignado.setCellValueFactory(new PropertyValueFactory("estudianteAsignado"));
    }
    
    private void cargarInformacionTabla(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta respuestaBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyecto();
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
        Stage escenarioPrincipal = (Stage) tfBusquedaAnteproyecto.getScene().getWindow();
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
            //acceder a cualquier método
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
            irFormulario(true, anteproyectos.get(posicion));
            //tvAlumnos.getSelectionModel().getSelectedItem();
        }else{
            Utilidades.mostrarDiallogoSimple("Selecione un anteproyecto", 
                    "Debe seleccionar un registro en la tabla de anteproyectos para su edición", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnConsultarAnteproyecto(ActionEvent event) {
         AnteproyectoModulo anteproyectoSeleccionado = tvAnteproyectos.getSelectionModel().getSelectedItem();
         irConsulta(anteproyectoSeleccionado);
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
    private void clicBtnPostularAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnEliminarAnteproyecto(ActionEvent event) {
    }

    @Override
    public void notificarOperacionGuardarAnteproyecto() {
        cargarInformacionTabla();
    }

    @Override
    public void notificarOperacionActualizarAnteproyecto() {
        cargarInformacionTabla();
    }
    
}
