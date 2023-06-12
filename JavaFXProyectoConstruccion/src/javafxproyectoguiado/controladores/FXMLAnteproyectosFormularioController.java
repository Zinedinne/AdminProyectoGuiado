/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.LGACDAO;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.LGAC;
import javafxproyectoguiado.modelo.pojo.LGACRespuesta;
import util.Constantes;
import util.INotificacionOperacionAnteproyectos;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosFormularioController implements Initializable {

    @FXML
    private Button btnGuardarFormulario;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreAnteproyecto;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField tfModalidadTrabajo;
    @FXML
    private ComboBox<LGAC> cbLGAC;
    @FXML
    private ComboBox<?> cbEncargadoDeTesis;
    @FXML
    private TextArea taDescripción;
    
    private ObservableList<LGAC> lgacs;
    
    private AnteproyectoModulo anteproyectoEdicion;
    private boolean esEdicion;
    private INotificacionOperacionAnteproyectos interfazNotificacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarInformacionLACS();
        cbLGAC.valueProperty().addListener(new ChangeListener<LGAC>() {
            @Override
            public void changed(ObservableValue<? extends LGAC> observable, 
                    LGAC oldValue, LGAC newValue) {
            }
        });
    }    

    public void inicializarInformacionFormulario(boolean esEdicion, AnteproyectoModulo anteproyectoEdicion, 
            INotificacionOperacionAnteproyectos interfazNotificacion){
        this.esEdicion = esEdicion;
        this.anteproyectoEdicion = anteproyectoEdicion;
        this.interfazNotificacion = interfazNotificacion;
        //TO DO
        if(esEdicion){
            lbTitulo.setText("Editar información del anteproyecto: "+anteproyectoEdicion.getNombreAnteproyecto());
            
            //TODO cargarInformacionEdicion();
        }else{
            //TODO
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
    }
    
    private void cargarInformacionLACS(){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta productosBD = LGACDAO.obtenerInformacionLGAC();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    lgacs.addAll(productosBD.getLgacs());
                    cbLGAC.setItems(lgacs);
                break;
        }
    }
    
    private int obtenerPosicionComboProducto(int idProducto){
        for (int i = 0; i < lgacs.size(); i++) {
            if(lgacs.get(i).getIdLGAC()== idProducto)
                return i;
        }
        return 0;
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
}
