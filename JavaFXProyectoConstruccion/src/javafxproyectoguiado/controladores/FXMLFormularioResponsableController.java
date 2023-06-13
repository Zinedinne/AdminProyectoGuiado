package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.CuerpoAcademicoDAO;
import javafxproyectoguiado.modelo.dao.ResponsableDAO;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademico;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxproyectoguiado.modelo.pojo.Responsable;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLFormularioResponsableController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoResponsable;
    @FXML
    private TextField tfPasswordResponsable;
    @FXML
    private TextField tfNombreResponsable;
    @FXML
    private TextField tfNumeroTrabajadorResponsable;
    @FXML
    private TextField tfApellidoPaternoResponsable;
    @FXML
    private TextField tfApellidoMaternoResponsable;
    @FXML
    private TextField tfUsernameResponsable;
    @FXML
    private TextField tfNumeroResponsable;
    @FXML
    private TextField tfCorreoResponsable;
    private boolean esEdicion;
    private Responsable responsableEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    private ObservableList<CuerpoAcademico> academias;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformarcionCuerpoAcademico();
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, Responsable responsableEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.responsableEdicion = responsableEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Responsable de Academia");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Responsable de Academia");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNumeroTrabajadorResponsable.setText(responsableEdicion.getNumeroTrabajador());
        tfNumeroTrabajadorResponsable.setEditable(false);
        tfNombreResponsable.setText(responsableEdicion.getNombre());
        tfApellidoPaternoResponsable.setText(responsableEdicion.getApellidoPaterno());
        tfApellidoMaternoResponsable.setText(responsableEdicion.getApellidoMaterno());
        tfUsernameResponsable.setText(responsableEdicion.getUsername());
        tfPasswordResponsable.setText(responsableEdicion.getPassword());
        tfCorreoResponsable.setText(responsableEdicion.getCorreoInstitucional());
        tfNumeroResponsable.setText(responsableEdicion.getNumeroTelefono());
        int posicionCuerpoAcademico = obtenerPosicionComboCuerpoAcademico(responsableEdicion.getIdAcademia());
        cbCuerpoResponsable.getSelectionModel().select(posicionCuerpoAcademico);
        cbCuerpoResponsable.setEditable(false);
        cbCuerpoResponsable.setDisable(false);
        cbCuerpoResponsable.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardarResponsable(ActionEvent event) {
        validarCamposRegistro();
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String nombre = tfNombreResponsable.getText();
        String apellidoPaterno = tfApellidoPaternoResponsable.getText();
        String apellidoMaterno = tfApellidoMaternoResponsable.getText();
        String username = tfUsernameResponsable.getText();
        String password = tfPasswordResponsable.getText();
        String correo = tfCorreoResponsable.getText();
        String numero = tfNumeroResponsable.getText();
        String numeroTrabajador = tfNumeroTrabajadorResponsable.getText();
        int posicionCuerpoAcademico = cbCuerpoResponsable.getSelectionModel().getSelectedIndex();
        
        if(!Utilidades.soloLetras(nombre)){
            tfNombreResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoPaterno)){
            tfApellidoPaternoResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(apellidoMaterno)){
            tfApellidoMaternoResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(username)){
            tfUsernameResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloLetras(password)){
            tfPasswordResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numero)){
            tfNumeroResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.soloNumeros(numeroTrabajador)){
            tfNumeroTrabajadorResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        if(posicionCuerpoAcademico == -1){
            cbCuerpoResponsable.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            Responsable responsableValidado = new Responsable();
            responsableValidado.setNombre(nombre);
            responsableValidado.setApellidoPaterno(apellidoPaterno);
            responsableValidado.setApellidoMaterno(apellidoMaterno);
            responsableValidado.setUsername(username);
            responsableValidado.setPassword(password);
            responsableValidado.setCorreoInstitucional(correo);
            responsableValidado.setNumeroTelefono(numero);
            responsableValidado.setNumeroTrabajador(numeroTrabajador);
            responsableValidado.setIdAcademia(academias.get(posicionCuerpoAcademico).getIdAcademia());
            
            if(esEdicion){
                responsableValidado.setIdUsuario(responsableEdicion.getIdUsuario());
                actualizarDirectorTesis(responsableValidado);
            }else{
                registrarResponsable(responsableValidado);
            }
        }
    }
    
    private void registrarResponsable(Responsable ResponsableNuevo ){
        int codigoRespuesta = ResponsableDAO.guardarResponsable(ResponsableNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El responsable de academia no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del responsable de academia no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Responsable de Academia Registrado", 
                        "La información del Responsable de Academia fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void actualizarDirectorTesis(Responsable ResponsableEdicion){
        int codigoRespuesta = ResponsableDAO.modificarResponsable(ResponsableEdicion);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El responsable de academia no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del responsable de academia no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Responsable de Academia Actualizado", 
                        "La información del responsable de academia fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }
    
    private void cargarInformarcionCuerpoAcademico(){
       academias = FXCollections.observableArrayList();
       CuerpoAcademicoRespuesta cuerpoAcademicoBD = CuerpoAcademicoDAO.obtenerInformaciónCuerpoAcademico();
       switch(cuerpoAcademicoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de Conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de Consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    academias.addAll(cuerpoAcademicoBD.getAcademias());
                    cbCuerpoResponsable.setItems(academias);
                break;
        }
    }
    
    private int obtenerPosicionComboCuerpoAcademico(int idAcademia){
        for(int i = 0; i < academias.size(); i++){
            if(academias.get(i).getIdAcademia() == idAcademia)
                return i;
        }
        return 0;
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void establecerEstiloNormal(){
        tfApellidoPaternoResponsable.setStyle(estiloNormal);
        tfApellidoMaternoResponsable.setStyle(estiloNormal);
        tfCorreoResponsable.setStyle(estiloNormal);
        tfNombreResponsable.setStyle(estiloNormal);
        tfNumeroResponsable.setStyle(estiloNormal);
        tfNumeroTrabajadorResponsable.setStyle(estiloNormal);
        tfPasswordResponsable.setStyle(estiloNormal);
        tfUsernameResponsable.setStyle(estiloNormal);
        cbCuerpoResponsable.setStyle(estiloNormal);
    }
}
