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
import javafxproyectoguiado.modelo.dao.EstudianteDAO;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademico;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import javafxproyectoguiado.modelo.pojo.RegistroUsuario;
import util.Constantes;
import util.INotificacionOperacionUsuario;
import util.Utilidades;

public class FXMLFormularioUsuarioController implements Initializable {

    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfApellidoPaternoEstudiante;
    @FXML
    private TextField tfApellidoMaternoEstudiante;
    @FXML
    private TextField tfPasswordEstudiante;
    @FXML
    private TextField tfUsernameEstudiante;
    @FXML
    private TextField tfNumeroEstudiante;
    @FXML
    private TextField tfCorreoEstudiante;
    @FXML
    private TextField tfCorreoProfesor;
    @FXML
    private TextField tfNumeroProfesor;
    @FXML
    private TextField tfUsernameProfesor;
    @FXML
    private TextField tfPasswordProfesor;
    @FXML
    private TextField tfApellidoMaternoProfesor;
    @FXML
    private TextField tfApellidoPaternoProfesor;
    @FXML
    private TextField tfNumeroTrabajadorProfesor;
    @FXML
    private TextField tfNombreProfesor;
    @FXML
    private TextField tfNombreTesis;
    @FXML
    private TextField tfNumeroTrabajadorTesis;
    @FXML
    private TextField tfApellidoPaternoTesis;
    @FXML
    private TextField tfApellidoMaternoTesis;
    @FXML
    private TextField tfPasswordTesis;
    @FXML
    private TextField tfUsernameTesis;
    @FXML
    private TextField tfNumeroTesis;
    @FXML
    private TextField tfCorreoTesis;
    @FXML
    private TextField tfCorreoAcademia;
    @FXML
    private TextField tfNumeroAcademia;
    @FXML
    private TextField tfUsernameAcademia;
    @FXML
    private TextField tfApellidoMaternoAcademia;
    @FXML
    private TextField tfApellidoPaternoAcademia;
    @FXML
    private TextField tfNumeroTrabajadorAcademia;
    @FXML
    private TextField tfNombreAcademia;
    @FXML
    private TextField tfPasswordAcademia;
    @FXML
    private ComboBox<CuerpoAcademico> cbCuerpoAcademico;
    @FXML
    private Label lbTitulo;
    private boolean esEdicion;
    private RegistroUsuario usuarioEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    private ObservableList<CuerpoAcademico> cuerposAcademicos;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformarcionCuerpoAcademico();
    }

    public void inicializarInformacionFormulario(boolean esEdicion, RegistroUsuario usuarioEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.usuarioEdicion = usuarioEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Usuario");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Usuario");
        }
    }
    
    private void cargarInformacionEdicion(){
        
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardarEstudiante(ActionEvent event) {
        validarCamposRegistroEstudiante();
    }
    
    public void validarCamposRegistroEstudiante(){
        establecerEstiloNormalEstudiante();
        boolean datosValidados = true;
        
        String nombre = tfNombreEstudiante.getText();
        String apellidoPaterno = tfApellidoPaternoEstudiante.getText();
        String apellidoMaterno = tfApellidoMaternoEstudiante.getText();
        String username = tfUsernameEstudiante.getText();
        String password = tfPasswordEstudiante.getText();
        String correo = tfCorreoEstudiante.getText();
        String numero = tfNumeroEstudiante.getText();
        String matricula = tfMatricula.getText();
        
        if(nombre.isEmpty()){
            tfNombreEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoPaterno.isEmpty()){
            tfApellidoPaternoEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoMaterno.isEmpty()){
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
        if(numero.isEmpty()){
            tfNumeroEstudiante.setStyle(estiloError);
            datosValidados = false;
        }
        if(matricula.isEmpty()){
            tfMatricula.setStyle(estiloError);
            datosValidados = false;
        }
        /*if(!Utilidades.correoValido(correo)){
            tfCorreoEstudiante.setStyle(estiloError);
            datosValidados = false;
        }*/
        
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
                
            }else{
                registrarEstudiante(estudianteValidado);
            }
        }
    }
    
    private void registrarEstudiante(Estudiante estudianteNuevo ){
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

    @FXML
    private void clicBtnGuardarProfesor(ActionEvent event) {
    }

    @FXML
    private void clicBtnGuardarTesis(ActionEvent event) {
    }

    @FXML
    private void clicBtnGuardarAcademico(ActionEvent event) {
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void cargarInformarcionCuerpoAcademico(){
       cuerposAcademicos = FXCollections.observableArrayList();
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
                    cuerposAcademicos.addAll(cuerpoAcademicoBD.getAcademias());
                    cbCuerpoAcademico.setItems(cuerposAcademicos);
                break;
        }
    }
    
    private int obtenerPosicionComboCuerpoAcademico(int idAcademia){
        for(int i = 0; i < cuerposAcademicos.size(); i++){
            if(cuerposAcademicos.get(i).getIdAcademia() == idAcademia)
                return i;
        }
        return 0;
    }
    
    private void establecerEstiloNormalEstudiante(){
        tfApellidoMaternoEstudiante.setStyle(estiloNormal);
        tfApellidoPaternoEstudiante.setStyle(estiloNormal);
        tfCorreoEstudiante.setStyle(estiloNormal);
        tfMatricula.setStyle(estiloNormal);
        tfNombreEstudiante.setStyle(estiloNormal);
        tfNumeroEstudiante.setStyle(estiloNormal);
        tfPasswordEstudiante.setStyle(estiloNormal);
        tfUsernameEstudiante.setStyle(estiloNormal);
    }
    
    private void establecerEstiloNormalProfesor(){
        tfApellidoMaternoProfesor.setStyle(estiloNormal);
        tfApellidoPaternoProfesor.setStyle(estiloNormal);
        tfCorreoProfesor.setStyle(estiloNormal);
        tfNombreProfesor.setStyle(estiloNormal);
        tfNumeroProfesor.setStyle(estiloNormal);
        tfNumeroTrabajadorProfesor.setStyle(estiloNormal);
        tfPasswordProfesor.setStyle(estiloNormal);
        tfUsernameProfesor.setStyle(estiloNormal);
    }
    
    private void establecerEstiloNormalTesis(){
        tfApellidoMaternoTesis.setStyle(estiloNormal);
        tfApellidoPaternoTesis.setStyle(estiloNormal);
        tfCorreoTesis.setStyle(estiloNormal);
        tfNombreTesis.setStyle(estiloNormal);
        tfNumeroTesis.setStyle(estiloNormal);
        tfNumeroTrabajadorTesis.setStyle(estiloNormal);
        tfPasswordTesis.setStyle(estiloNormal);
        tfUsernameTesis.setStyle(estiloNormal);
    }
    
    private void establecerEstiloNormalAcademico(){
        tfApellidoPaternoAcademia.setStyle(estiloNormal);
        tfApellidoMaternoAcademia.setStyle(estiloNormal);
        tfCorreoAcademia.setStyle(estiloNormal);
        tfNombreAcademia.setStyle(estiloNormal);
        tfNumeroAcademia.setStyle(estiloNormal);
        tfNumeroTrabajadorAcademia.setStyle(estiloNormal);
        tfPasswordAcademia.setStyle(estiloNormal);
        tfUsernameAcademia.setStyle(estiloNormal);
        cbCuerpoAcademico.setStyle(estiloNormal);
    }
}
