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
import javafxproyectoguiado.modelo.dao.ProfesorDAO;
import javafxproyectoguiado.modelo.pojo.Profesor;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLFormularioProfesorController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreProfesor;
    @FXML
    private TextField tfNumeroTrabajadorProfesor;
    @FXML
    private TextField tfApellidoPaternoProfesor;
    @FXML
    private TextField tfApellidoMaternoProfesor;
    @FXML
    private TextField tfPasswordProfesor;
    @FXML
    private TextField tfUsernameProfesor;
    @FXML
    private TextField tfNumeroProfesor;
    @FXML
    private TextField tfCorreoProfesor;
    private boolean esEdicion;
    private Profesor profesorEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicializarInformacionFormulario(boolean esEdicion, Profesor profesorEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.profesorEdicion = profesorEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Profesor");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Profesor");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNumeroTrabajadorProfesor.setText(profesorEdicion.getNumeroTrabajador());
        tfNumeroTrabajadorProfesor.setEditable(false);
        tfNombreProfesor.setText(profesorEdicion.getNombre());
        tfApellidoPaternoProfesor.setText(profesorEdicion.getApellidoPaterno());
        tfApellidoMaternoProfesor.setText(profesorEdicion.getApellidoMaterno());
        tfUsernameProfesor.setText(profesorEdicion.getUsername());
        tfPasswordProfesor.setText(profesorEdicion.getPassword());
        tfCorreoProfesor.setText(profesorEdicion.getCorreoInstitucional());
        tfNumeroProfesor.setText(profesorEdicion.getNumeroTelefono());
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardarProfesor(ActionEvent event) {
        validarCamposRegistro();
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombreProfesor.getText();
        String apellidoPaterno = tfApellidoPaternoProfesor.getText();
        String apellidoMaterno = tfApellidoMaternoProfesor.getText();
        String username = tfUsernameProfesor.getText();
        String password = tfPasswordProfesor.getText();
        String correo = tfCorreoProfesor.getText();
        String numero = tfNumeroProfesor.getText();
        String numeroTrabajador = tfNumeroTrabajadorProfesor.getText();
        
        if(!Utilidades.soloLetras(nombre)){
            tfNombreProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoPaterno)){
            tfApellidoPaternoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoMaterno)){
            tfApellidoMaternoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(username)){
            tfUsernameProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(password)){
            tfPasswordProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numero)){
            tfNumeroProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numeroTrabajador)){
            tfNumeroTrabajadorProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            Profesor profesorValidado = new Profesor();
            profesorValidado.setNombre(nombre);
            profesorValidado.setApellidoPaterno(apellidoPaterno);
            profesorValidado.setApellidoMaterno(apellidoMaterno);
            profesorValidado.setUsername(username);
            profesorValidado.setPassword(password);
            profesorValidado.setCorreoInstitucional(correo);
            profesorValidado.setNumeroTelefono(numero);
            profesorValidado.setNumeroTrabajador(numeroTrabajador);
            
            if(esEdicion){
                profesorValidado.setIdUsuario(profesorEdicion.getIdUsuario());
                actualizarProfesor(profesorValidado);
            }else{
                registrarProfesor(profesorValidado);
            }
        }
    }
    
    private void registrarProfesor(Profesor profesorNuevo ){
        int codigoRespuesta = ProfesorDAO.guardarProfesor(profesorNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El profesor no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del profesor no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Profesor Registrado", 
                        "La información del profesor fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void actualizarProfesor(Profesor profesorEdicion){
        int codigoRespuesta = ProfesorDAO.modificarProfesor(profesorEdicion);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El profesor no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del profesor no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Profesor Actualizado", 
                        "La información del Profesor fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void establecerEstiloNormal(){
        tfApellidoMaternoProfesor.setStyle(estiloNormal);
        tfApellidoPaternoProfesor.setStyle(estiloNormal);
        tfCorreoProfesor.setStyle(estiloNormal);
        tfNombreProfesor.setStyle(estiloNormal);
        tfNumeroProfesor.setStyle(estiloNormal);
        tfNumeroTrabajadorProfesor.setStyle(estiloNormal);
        tfPasswordProfesor.setStyle(estiloNormal);
        tfUsernameProfesor.setStyle(estiloNormal);
    }
    
}
