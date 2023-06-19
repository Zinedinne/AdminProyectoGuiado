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
import javafxproyectoguiado.modelo.dao.CuerpoAcademicoDAO;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademico;
import util.Constantes;
import util.INotificacionOperacionCuerpoAcademico;
import util.Utilidades;

public class FXMLFormularioCuerpoAcademicoController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombre;
    private INotificacionOperacionCuerpoAcademico interfazNotificacion;
    private boolean esEdicion;
    private CuerpoAcademico cuerpoAcademicoEdicion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, CuerpoAcademico cuerpoAcademicoEdicion, INotificacionOperacionCuerpoAcademico interfazNotificacion){
        this.esEdicion = esEdicion;
        this.cuerpoAcademicoEdicion = cuerpoAcademicoEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Cuerpo Academico");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Cuerpo Academico");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombre.setText(cuerpoAcademicoEdicion.getNombreAcademia());
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombre.getText();
        
        if(!Utilidades.soloLetras(nombre)|| nombre.length() > 45 ){
            tfNombre.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            CuerpoAcademico cuerpoAcademicoValidado = new CuerpoAcademico();
            cuerpoAcademicoValidado.setNombreAcademia(nombre);
            
            if(esEdicion){
                cuerpoAcademicoValidado.setIdAcademia(cuerpoAcademicoEdicion.getIdAcademia());
                actualizarCuerpoAcademico(cuerpoAcademicoValidado);
            }else{
                registrarCuerpoAcademico(cuerpoAcademicoValidado);
            }
        }
    }
    
    private void registrarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoRegistro){
        int codigoRespuesta = CuerpoAcademicoDAO.guardarCuerpoAcademico(cuerpoAcademicoRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El cuerpo academico no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del cuerpo academico no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Curso Actualizado", 
                        "La información del cuerpo academico fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizarCuerpoAcademico();
                break;
        }
    }
    
    private void actualizarCuerpoAcademico(CuerpoAcademico cuerpoAcademicoActualizar){
        int codigoRespuesta = CuerpoAcademicoDAO.modificarCuerpoAcademico(cuerpoAcademicoActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El cuerpo academico no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del cuerpo academico no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Curso Actualizado", 
                        "La información del cuerpo academico fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizarCuerpoAcademico();
                break;
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    public void establecerEstiloNormal(){
        tfNombre.setStyle(estiloNormal);
    }
    
}
