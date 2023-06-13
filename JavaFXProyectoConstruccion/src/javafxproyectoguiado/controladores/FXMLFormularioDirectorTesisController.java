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
import javafxproyectoguiado.modelo.dao.DirectorTesisDAO;
import javafxproyectoguiado.modelo.pojo.DirectorTesis;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLFormularioDirectorTesisController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfCorreoTesis;
    @FXML
    private TextField tfNumeroTesis;
    @FXML
    private TextField tfUsernameTesis;
    @FXML
    private TextField tfPasswordTesis;
    @FXML
    private TextField tfApellidoMaternoTesis;
    @FXML
    private TextField tfApellidoPaternoTesis;
    @FXML
    private TextField tfNumeroTrabajadorTesis;
    @FXML
    private TextField tfNombreTesis;
    private boolean esEdicion;
    private DirectorTesis directorTesisEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, DirectorTesis directorTesisEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.directorTesisEdicion = directorTesisEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Director de Tesis");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Director de Tesis");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNumeroTrabajadorTesis.setText(directorTesisEdicion.getNumeroTrabajador());
        tfNumeroTrabajadorTesis.setEditable(false);
        tfNombreTesis.setText(directorTesisEdicion.getNombre());
        tfApellidoPaternoTesis.setText(directorTesisEdicion.getApellidoPaterno());
        tfApellidoMaternoTesis.setText(directorTesisEdicion.getApellidoMaterno());
        tfUsernameTesis.setText(directorTesisEdicion.getUsername());
        tfPasswordTesis.setText(directorTesisEdicion.getPassword());
        tfCorreoTesis.setText(directorTesisEdicion.getCorreoInstitucional());
        tfNumeroTesis.setText(directorTesisEdicion.getNumeroTelefono());
    }

    @FXML
    private void clicBtnGuardarTesis(ActionEvent event) {
        validarCamposRegistro();
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombreTesis.getText();
        String apellidoPaterno = tfApellidoPaternoTesis.getText();
        String apellidoMaterno = tfApellidoMaternoTesis.getText();
        String username = tfUsernameTesis.getText();
        String password = tfPasswordTesis.getText();
        String correo = tfCorreoTesis.getText();
        String numero = tfNumeroTesis.getText();
        String numeroTrabajador = tfNumeroTrabajadorTesis.getText();
        
        if(!Utilidades.soloLetras(nombre)){
            tfNombreTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoPaterno)){
            tfApellidoPaternoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoMaterno)){
            tfApellidoMaternoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(username)){
            tfUsernameTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(password)){
            tfPasswordTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numero)){
            tfNumeroTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numeroTrabajador)){
            tfNumeroTrabajadorTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            DirectorTesis tesisValidado = new DirectorTesis();
            tesisValidado.setNombre(nombre);
            tesisValidado.setApellidoPaterno(apellidoPaterno);
            tesisValidado.setApellidoMaterno(apellidoMaterno);
            tesisValidado.setUsername(username);
            tesisValidado.setPassword(password);
            tesisValidado.setCorreoInstitucional(correo);
            tesisValidado.setNumeroTelefono(numero);
            tesisValidado.setNumeroTrabajador(numeroTrabajador);
            
            if(esEdicion){
                tesisValidado.setIdUsuario(directorTesisEdicion.getIdUsuario());
                actualizarDirectorTesis(tesisValidado);
            }else{
                registrarDirectorTesis(tesisValidado);
            }
        }
    }
    
    private void registrarDirectorTesis(DirectorTesis directorTesisNuevo ){
        int codigoRespuesta = DirectorTesisDAO.guardarDirectorTesis(directorTesisNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El director de tesis no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del director de tesis no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Director de Tesis Registrado", 
                        "La información del director de tesis fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void actualizarDirectorTesis(DirectorTesis directorTesisEdicion){
        int codigoRespuesta = DirectorTesisDAO.modificarDirectorTesis(directorTesisEdicion);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El director de tesis no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del director de tesis no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Director de Tesis Actualizado", 
                        "La información del director de tesis fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
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
        tfApellidoMaternoTesis.setStyle(estiloNormal);
        tfApellidoPaternoTesis.setStyle(estiloNormal);
        tfCorreoTesis.setStyle(estiloNormal);
        tfNombreTesis.setStyle(estiloNormal);
        tfNumeroTesis.setStyle(estiloNormal);
        tfNumeroTrabajadorTesis.setStyle(estiloNormal);
        tfPasswordTesis.setStyle(estiloNormal);
        tfUsernameTesis.setStyle(estiloNormal);
    }
    
}
