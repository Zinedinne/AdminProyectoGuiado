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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
public class FXMLAsignarComentarioAnteproyectoController implements Initializable {

    
    @FXML
    private ComboBox<AnteproyectoModulo> cbAnteproyecto;
    private ObservableList<AnteproyectoModulo> anteproyectos;
    @FXML
    private TextArea taComentario;
    private AnteproyectoModulo anteproyectoConsulta;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionAnteproyectos();
        cbAnteproyecto.valueProperty().addListener(new ChangeListener<AnteproyectoModulo>() {
            @Override
            public void changed(ObservableValue<? extends AnteproyectoModulo> observable, 
                    AnteproyectoModulo oldValue, AnteproyectoModulo newValue) {
            }
        });
        cbAnteproyecto.setDisable(true);
        cbAnteproyecto.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
       
    }    
    
    public void inicializarInformacionConsulta(AnteproyectoModulo anteproyectoConsulta){
        this.anteproyectoConsulta = anteproyectoConsulta;
        cargarInformacionConsulta();
    }
    
    public void cargarInformacionConsulta(){
        int posicionAnteproyecto = obtenerPosicionComboAnteproyectos(anteproyectoConsulta.getIdAnteproyecto());
        cbAnteproyecto.getSelectionModel().select(posicionAnteproyecto);
    }
    
    @FXML
    private void clicBtnAsignarComentario(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void establecerEstiloNormal(){
        taComentario.setStyle(estiloNormal);
    }

    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        int posicionAnteproyecto = cbAnteproyecto.getSelectionModel().getSelectedIndex();
        String comentario = taComentario.getText();
        
        if(comentario.isEmpty()){
            taComentario.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(datosValidos){
            AnteproyectoModulo anteproyectoValido = new AnteproyectoModulo();
            anteproyectoValido.setIdAnteproyecto(anteproyectos.get(posicionAnteproyecto).getIdAnteproyecto());
            anteproyectoValido.setComentario(comentario);
            registrarAsignacion(anteproyectoValido);
        }
    }

    private void registrarAsignacion(AnteproyectoModulo anteproyectoValido){
        int codigoRespuesta = AnteproyectoModuloDAO.asignarComentarioAnteproyecto(anteproyectoValido);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El comentario no puede ser registrado debido a un error en la conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del comentario no puede ser registradado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Anteproyecto comentado", 
                        "El comentario fue asignado correctamente al anteproyecto", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) taComentario.getScene().getWindow(); 
        escenarioBase.close();
    }
    
    private void cargarInformacionAnteproyectos(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta productosBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyecto();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    anteproyectos.addAll(productosBD.getAnteproyectosModulo());
                    cbAnteproyecto.setItems(anteproyectos);
                break;
        }
    }
    
    private int obtenerPosicionComboAnteproyectos(int idAnteproyecto){
        for (int i = 0; i < anteproyectos.size(); i++) {
            if(anteproyectos.get(i).getIdAnteproyecto()== idAnteproyecto)
                return i;
        }
        return 0;
    }
    
}
