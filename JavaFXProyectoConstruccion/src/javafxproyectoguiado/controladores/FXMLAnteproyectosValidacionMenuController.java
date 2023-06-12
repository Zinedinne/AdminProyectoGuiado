/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
public class FXMLAnteproyectosValidacionMenuController implements Initializable {

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
                    tvAnteproyectosValidados.setItems(anteproyectos);
                break;
        }
    }
    
    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escenarioPrincipal = (Stage) tfBusquedaAnteproyecto.getScene().getWindow();
        escenarioPrincipal.close();
    }

    @FXML
    private void clicBtnValidarAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnDenegarAnteproyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnConsultarAnteproyecto(ActionEvent event) {
    }
    
}
