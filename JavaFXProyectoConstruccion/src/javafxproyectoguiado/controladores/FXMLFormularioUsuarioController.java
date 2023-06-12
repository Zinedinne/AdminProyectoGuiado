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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.CuerpoAcademicoDAO;
import javafxproyectoguiado.modelo.dao.RegistroUsuarioDAO;
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
    private Estudiante usuarioEdicion;
    private INotificacionOperacionUsuario interfazNotificacion;
    private ObservableList<CuerpoAcademico> cuerposAcademicos;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    @FXML
    private Tab tabEstudiante;
    @FXML
    private Tab tabProfesor;
    @FXML
    private TabPane tabPaneRegistro;
    @FXML
    private Tab tabDirector;
    @FXML
    private Tab tabResponsable;
    private ObservableList<CuerpoAcademico> academias;

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformarcionCuerpoAcademico();
    }

    public void inicializarInformacionFormulario(boolean esEdicion, Estudiante usuarioEdicion, INotificacionOperacionUsuario interfazNotificacion){
        this.esEdicion = esEdicion;
        this.usuarioEdicion = usuarioEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Usuario");
            if("Estudiante".equals(usuarioEdicion.getTipoUsuario())){
                tabPaneRegistro.getSelectionModel().select(tabEstudiante);
                tabProfesor.setDisable(true);
                tabDirector.setDisable(true);
                tabResponsable.setDisable(true);
                cargarInformacionEdicionEstudiante();
            }
            /*if("Profesor".equals(usuarioEdicion.getTipoUsuario())){
                tabPaneRegistro.getSelectionModel().select(tabProfesor);
                tabEstudiante.setDisable(true);
                tabDirector.setDisable(true);
                tabResponsable.setDisable(true);
                cargarInformacionEdicionProfesor();
            }
            if("Encargado de Tesis".equals(usuarioEdicion.getTipoUsuario())){
                tabPaneRegistro.getSelectionModel().select(tabDirector);
                tabEstudiante.setDisable(true);
                tabProfesor.setDisable(true);
                tabResponsable.setDisable(true);
                cargarInformacionEdicionTesis();
            }
            if("Responsable de Academia".equals(usuarioEdicion.getTipoUsuario())){
                tabPaneRegistro.getSelectionModel().select(tabResponsable);
                tabEstudiante.setDisable(true);
                tabProfesor.setDisable(true);
                tabDirector.setDisable(true);
                cargarInformacionEdicionResponsable();
            }*/
        }else{
            lbTitulo.setText("Registrar nuevo Usuario");
        }
    }
    
    private void cargarInformacionEdicionEstudiante(){
        tfMatricula.setText(usuarioEdicion.getMatricula());
        tfNombreEstudiante.setText(usuarioEdicion.getNombre());
        tfApellidoPaternoEstudiante.setText(usuarioEdicion.getApellidoPaterno());
        tfApellidoMaternoEstudiante.setText(usuarioEdicion.getApellidoMaterno());
        tfUsernameEstudiante.setText(usuarioEdicion.getUsername());
        tfPasswordEstudiante.setText(usuarioEdicion.getPassword());
        tfCorreoEstudiante.setText(usuarioEdicion.getCorreoInstitucional());
        tfNumeroEstudiante.setText(usuarioEdicion.getNumeroTelefono());
    }
    
    /*private void cargarInformacionEdicionProfesor(){
        tfNumeroTrabajadorProfesor.setText(usuarioEdicion.getNumeroTrabajador());
        tfNombreProfesor.setText(usuarioEdicion.getNombre());
        tfApellidoPaternoProfesor.setText(usuarioEdicion.getApellidoPaterno());
        tfApellidoMaternoProfesor.setText(usuarioEdicion.getApellidoMaterno());
        tfUsernameProfesor.setText(usuarioEdicion.getUsername());
        tfPasswordProfesor.setText(usuarioEdicion.getPassword());
        tfCorreoProfesor.setText(usuarioEdicion.getCorreoInstitucional());
        tfNumeroProfesor.setText(usuarioEdicion.getNumeroTelefono());
    }
    
    private void cargarInformacionEdicionTesis(){
        tfNumeroTrabajadorTesis.setText(usuarioEdicion.getNumeroTrabajador());
        tfNombreTesis.setText(usuarioEdicion.getNombre());
        tfApellidoPaternoTesis.setText(usuarioEdicion.getApellidoPaterno());
        tfApellidoMaternoTesis.setText(usuarioEdicion.getApellidoMaterno());
        tfUsernameTesis.setText(usuarioEdicion.getUsername());
        tfPasswordTesis.setText(usuarioEdicion.getPassword());
        tfCorreoTesis.setText(usuarioEdicion.getCorreoInstitucional());
        tfNumeroTesis.setText(usuarioEdicion.getNumeroTelefono());
    }
    
    private void cargarInformacionEdicionResponsable(){
        tfNumeroTrabajadorAcademia.setText(usuarioEdicion.getNumeroTrabajador());
        tfNombreAcademia.setText(usuarioEdicion.getNombre());
        tfApellidoPaternoAcademia.setText(usuarioEdicion.getApellidoPaterno());
        tfApellidoMaternoAcademia.setText(usuarioEdicion.getApellidoMaterno());
        tfUsernameAcademia.setText(usuarioEdicion.getUsername());
        tfPasswordAcademia.setText(usuarioEdicion.getPassword());
        tfCorreoAcademia.setText(usuarioEdicion.getCorreoInstitucional());
        tfNumeroAcademia.setText(usuarioEdicion.getNumeroTelefono());
        int posicionCuerpoAcademico = obtenerPosicionComboCuerpoAcademico(usuarioEdicion.getIdAcademia());
        cbCuerpoAcademico.getSelectionModel().select(posicionCuerpoAcademico);
    }*/

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
        if(!Utilidades.correoValido(correo)){
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
                 
            }else{
                registrarEstudiante(estudianteValidado);
            }
        }
    }
    
    private void registrarEstudiante(Estudiante estudianteNuevo){
        int codigoRespuesta = RegistroUsuarioDAO.guardarEstudiante(estudianteNuevo);
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
        validarCamposRegistroProfesor();
    } 
    
    public void validarCamposRegistroProfesor(){
        establecerEstiloNormalEstudiante();
        boolean datosValidados = true;
        
        String nombre = tfNombreProfesor.getText();
        String apellidoPaterno = tfApellidoPaternoProfesor.getText();
        String apellidoMaterno = tfApellidoMaternoProfesor.getText();
        String username = tfUsernameProfesor.getText();
        String password = tfPasswordProfesor.getText();
        String correo = tfCorreoProfesor.getText();
        String numero = tfNumeroProfesor.getText();
        String numeroTrabajador = tfNumeroTrabajadorProfesor.getText();
        
        if(nombre.isEmpty()){
            tfNombreProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoPaterno.isEmpty()){
            tfApellidoPaternoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoMaterno.isEmpty()){
            tfApellidoMaternoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(username.isEmpty()){
            tfUsernameProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(password.isEmpty()){
            tfPasswordProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(numero.isEmpty()){
            tfNumeroProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(numeroTrabajador.isEmpty()){
            tfNumeroTrabajadorProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoProfesor.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            RegistroUsuario profesorValidado = new RegistroUsuario();
            profesorValidado.setNombre(nombre);
            profesorValidado.setApellidoPaterno(apellidoPaterno);
            profesorValidado.setApellidoMaterno(apellidoMaterno);
            profesorValidado.setUsername(username);
            profesorValidado.setPassword(password);
            profesorValidado.setCorreo(correo);
            profesorValidado.setNumeroTelefono(numero);
            profesorValidado.setNumeroTrabajador(numeroTrabajador);
            
            if(esEdicion){
                 
            }else{
                registrarProfesor(profesorValidado);
            }
        }
    }
    
    private void registrarProfesor(RegistroUsuario profesorteNuevo ){
        int codigoRespuesta = RegistroUsuarioDAO.guardarProfesor(profesorteNuevo);
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
                Utilidades.mostrarDiallogoSimple("Estudiante Registrado", 
                        "La información del profesor fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }

    @FXML
    private void clicBtnGuardarTesis(ActionEvent event) {
        validarCamposRegistroTesis();
    }
    
    public void validarCamposRegistroTesis(){
        establecerEstiloNormalTesis();
        boolean datosValidados = true;
        
        String nombre = tfNombreTesis.getText();
        String apellidoPaterno = tfApellidoPaternoTesis.getText();
        String apellidoMaterno = tfApellidoMaternoTesis.getText();
        String username = tfUsernameTesis.getText();
        String password = tfPasswordTesis.getText();
        String correo = tfCorreoTesis.getText();
        String numero = tfNumeroTesis.getText();
        String numeroTrabajador = tfNumeroTrabajadorTesis.getText();
        
        if(nombre.isEmpty()){
            tfNombreTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoPaterno.isEmpty()){
            tfApellidoPaternoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoMaterno.isEmpty()){
            tfApellidoMaternoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(username.isEmpty()){
            tfUsernameTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(password.isEmpty()){
            tfPasswordTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(numero.isEmpty()){
            tfNumeroTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(numeroTrabajador.isEmpty()){
            tfNumeroTrabajadorTesis.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoTesis.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            RegistroUsuario tesisValidado = new RegistroUsuario();
            tesisValidado.setNombre(nombre);
            tesisValidado.setApellidoPaterno(apellidoPaterno);
            tesisValidado.setApellidoMaterno(apellidoMaterno);
            tesisValidado.setUsername(username);
            tesisValidado.setPassword(password);
            tesisValidado.setCorreo(correo);
            tesisValidado.setNumeroTelefono(numero);
            tesisValidado.setNumeroTrabajador(numeroTrabajador);
            
            if(esEdicion){
                 
            }else{
                registrarTesis(tesisValidado);
            }
        }
    }
    
    private void registrarTesis(RegistroUsuario tesisNuevo ){
        int codigoRespuesta = RegistroUsuarioDAO.guardarTesis(tesisNuevo);
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
                Utilidades.mostrarDiallogoSimple("Estudiante Registrado", 
                        "La información del Director de tesis fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarUsuario();
                break;
        }
    }

    @FXML
    private void clicBtnGuardarAcademico(ActionEvent event) {
        validarCamposRegistroResponsable();
    }
    
    public void validarCamposRegistroResponsable(){
        establecerEstiloNormalAcademico();
        boolean datosValidados = true;
        
        String nombre = tfNombreAcademia.getText();
        String apellidoPaterno = tfApellidoPaternoAcademia.getText();
        String apellidoMaterno = tfApellidoMaternoAcademia.getText();
        String username = tfUsernameAcademia.getText();
        String password = tfPasswordAcademia.getText();
        String correo = tfCorreoAcademia.getText();
        String numero = tfNumeroAcademia.getText();
        String numeroTrabajador = tfNumeroTrabajadorAcademia.getText();
        int posicionCuerpoAcademico = cbCuerpoAcademico.getSelectionModel().getSelectedIndex();
        
        if(nombre.isEmpty()){
            tfNombreAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoPaterno.isEmpty()){
            tfApellidoPaternoAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(apellidoMaterno.isEmpty()){
            tfApellidoMaternoAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(username.isEmpty()){
            tfUsernameAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(password.isEmpty()){
            tfPasswordAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(numero.isEmpty()){
            tfNumeroAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(numeroTrabajador.isEmpty()){
            tfNumeroTrabajadorAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(!Utilidades.correoValido(correo)){
            tfCorreoAcademia.setStyle(estiloError);
            datosValidados = false;
        }
        if(posicionCuerpoAcademico == -1){
            cbCuerpoAcademico.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            RegistroUsuario responsableValidado = new RegistroUsuario();
            responsableValidado.setNombre(nombre);
            responsableValidado.setApellidoPaterno(apellidoPaterno);
            responsableValidado.setApellidoMaterno(apellidoMaterno);
            responsableValidado.setUsername(username);
            responsableValidado.setPassword(password);
            responsableValidado.setCorreo(correo);
            responsableValidado.setNumeroTelefono(numero);
            responsableValidado.setNumeroTrabajador(numeroTrabajador);
            responsableValidado.setIdAcademia(academias.get(posicionCuerpoAcademico).getIdAcademia());
            
            if(esEdicion){
                 
            }else{
                registrarResponsable(responsableValidado);
            }
        }
    }
    
    private void registrarResponsable(RegistroUsuario ResponsableNuevo ){
        int codigoRespuesta = RegistroUsuarioDAO.guardarResponsable(ResponsableNuevo);
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
