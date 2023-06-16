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
import javafxproyectoguiado.modelo.dao.EstudianteDAO;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLFormularioEstudianteController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfCorreoEstudiante;
    @FXML
    private TextField tfNumeroEstudiante;
    @FXML
    private TextField tfUsernameEstudiante;
    @FXML
    private TextField tfPasswordEstudiante;
    @FXML
    private TextField tfApellidoMaternoEstudiante;
    @FXML
    private TextField tfApellidoPaternoEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfNombreEstudiante;
    private boolean esEdicion;
    private Estudiante estudianteEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inicializarInformacionFormulario(boolean esEdicion, Estudiante estudianteEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.estudianteEdicion = estudianteEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Estudiante");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Estudiante");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfMatricula.setText(estudianteEdicion.getMatricula());
        tfMatricula.setEditable(false);
        tfNombreEstudiante.setText(estudianteEdicion.getNombre());
        tfApellidoPaternoEstudiante.setText(estudianteEdicion.getApellidoPaterno());
        tfApellidoMaternoEstudiante.setText(estudianteEdicion.getApellidoMaterno());
        tfUsernameEstudiante.setText(estudianteEdicion.getUsername());
        tfPasswordEstudiante.setText(estudianteEdicion.getPassword());
        tfCorreoEstudiante.setText(estudianteEdicion.getCorreoInstitucional());
        tfNumeroEstudiante.setText(estudianteEdicion.getNumeroTelefono());
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombreEstudiante.getText();
        String apellidoPaterno = tfApellidoPaternoEstudiante.getText();
        String apellidoMaterno = tfApellidoMaternoEstudiante.getText();
        String username = tfUsernameEstudiante.getText();
        String password = tfPasswordEstudiante.getText();
        String correo = tfCorreoEstudiante.getText();
        String numero = tfNumeroEstudiante.getText();
        String matricula = tfMatricula.getText();
        
        if(!Utilidades.soloLetras(nombre)){
            tfNombreEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoPaterno)){
            tfApellidoPaternoEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoMaterno)){
            tfApellidoMaternoEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(username.isEmpty()){
            tfUsernameEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(password.isEmpty()){
            tfPasswordEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numero) || numero.length() != 10){
            tfNumeroEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.matricula(matricula) || matricula.length() != 10){
            tfMatricula.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValidoEstudiante(correo)){
            tfCorreoEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            Estudiante estudianteValidado = new Estudiante();
            estudianteValidado.setNombre(nombre);
            estudianteValidado.setApellidoPaterno(apellidoPaterno);
            estudianteValidado.setApellidoMaterno(apellidoMaterno);
            estudianteValidado.setUsername(username);
            estudianteValidado.setPassword(password);
            estudianteValidado.setCorreoInstitucional(correo);
            estudianteValidado.setNumeroTelefono(numero);
            estudianteValidado.setMatricula(matricula);
            
            if(esEdicion){
                estudianteValidado.setIdUsuario(estudianteEdicion.getIdUsuario());
                actualizarEstudiante(estudianteValidado);
            }else{
                registrarEstudiante(estudianteValidado);
            }
        }
    }
    
    private void registrarEstudiante(Estudiante estudianteNuevo){
        int codigoRespuesta = EstudianteDAO.guardarEstudiante(estudianteNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El estudiante no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del estudiante no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Estudiante Registrado", 
                        "La información del Estudiante fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void actualizarEstudiante(Estudiante estudianteEdicion){
        int codigoRespuesta = EstudianteDAO.modificarEstudiante(estudianteEdicion);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El estudiante no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del estudiante no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Estudiante Actualizado", 
                        "La información del Estudiante fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }

    @FXML
    private void clicBtnGuardarEstudiante(ActionEvent event) {
        validarCamposRegistro();
        
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
        tfMatricula.setStyle(estiloNormal);
        tfNombreEstudiante.setStyle(estiloNormal);
        tfApellidoPaternoEstudiante.setStyle(estiloNormal);
        tfApellidoMaternoEstudiante.setStyle(estiloNormal);
        tfUsernameEstudiante.setStyle(estiloNormal);
        tfPasswordEstudiante.setStyle(estiloNormal);
        tfCorreoEstudiante.setStyle(estiloNormal);
        tfNumeroEstudiante.setStyle(estiloNormal);
    }
    
}
