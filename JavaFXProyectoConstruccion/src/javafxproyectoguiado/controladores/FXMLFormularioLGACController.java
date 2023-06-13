package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.LGACDAO;
import javafxproyectoguiado.modelo.pojo.LGAC;
import util.Constantes;
import util.INotificacionOperacionLGAC;
import util.Utilidades;

public class FXMLFormularioLGACController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private Label lbTitulo;
    private boolean esEdicion;
    private LGAC lgacEdicion;
    private INotificacionOperacionLGAC interfazNotificacion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inicializarInformacionFormulario(boolean esEdicion, LGAC lgacEdicion, INotificacionOperacionLGAC interfazNotificacion){
        this.esEdicion = esEdicion;
        this.lgacEdicion = lgacEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del LGAC");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo LGAC");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombre.setText(lgacEdicion.getNombre());
    }
    
    public void validarCampoRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombre.getText();
        
        if(!Utilidades.soloLetras(nombre)){
            tfNombre.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            LGAC lgacValidado = new LGAC();
            lgacValidado.setNombre(nombre);
            
            if(esEdicion){
                lgacValidado.setIdLGAC(lgacEdicion.getIdLGAC());
                actualizarLGAC(lgacValidado);
            }else{
                registrarLGAC(lgacValidado);
            }
        }
    }
    
    private void registrarLGAC (LGAC lgacRegistro){
        int codigoRespuesta = LGACDAO.guardarLGAC(lgacRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "La LGAC no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información de la LGAC no puede ser registrada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("LGAC Actualizado", 
                        "La información de la LGAC fue registrada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarLGAC();
                break;
        }
    }
    
    private void actualizarLGAC (LGAC lgacActualizar){
        int codigoRespuesta = LGACDAO.modificarLGAC(lgacActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "La LGAC no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información de la LGAC no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Curso Actualizado", 
                        "La información de la LGAC fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizarLGAC();
                break;
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCampoRegistro();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void establecerEstiloNormal(){
        tfNombre.setStyle(estiloNormal);
    }
    
}
