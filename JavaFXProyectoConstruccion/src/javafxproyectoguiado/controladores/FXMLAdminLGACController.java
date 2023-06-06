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
import javafxproyectoguiado.modelo.dao.LGACDAO;
import javafxproyectoguiado.modelo.pojo.LGAC;
import javafxproyectoguiado.modelo.pojo.LGACRespuesta;
import util.Constantes;
import util.Utilidades;

public class FXMLAdminLGACController implements Initializable {

    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<LGAC> tvLGAC;
    @FXML
    private TableColumn tcLGAC;
    private ObservableList<LGAC> lgacs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla(){
        tcLGAC.setCellValueFactory(new PropertyValueFactory("nombre"));
    }
    
    private void cargarInformacionTabla(){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta respuestaBD = LGACDAO.obtenerInformacionLGAC();
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
                    lgacs.addAll(respuestaBD.getLgacs());
                    tvLGAC.setItems(lgacs);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        LGAC lgacSeleccionado = tvLGAC.getSelectionModel().getSelectedItem();
        if(lgacSeleccionado != null){
            irFormulario(true, lgacSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona una LGAC", 
                    "Selecciona el registro en la tabla de la LGAC para su edicion", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        irFormulario(false,null);
    }
    
    private void irFormulario(boolean esEdicion, LGAC lgacEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioLGAC.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioLGACController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, lgacEdicion);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("FormularioLGAC");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioCursoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
