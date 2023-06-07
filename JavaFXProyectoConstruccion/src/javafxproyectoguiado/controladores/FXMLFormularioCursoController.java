package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.CursoDAO;
import javafxproyectoguiado.modelo.dao.MateriaDAO;
import javafxproyectoguiado.modelo.dao.PeriodoDAO;
import javafxproyectoguiado.modelo.pojo.Curso;
import javafxproyectoguiado.modelo.pojo.Materia;
import javafxproyectoguiado.modelo.pojo.MateriaRespuesta;
import javafxproyectoguiado.modelo.pojo.Periodo;
import javafxproyectoguiado.modelo.pojo.PeriodoRespuesta;
import util.Constantes;
import util.INotificacionOperacionCurso;
import util.Utilidades;

public class FXMLFormularioCursoController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfBloque;
    @FXML
    private TextField tfNRC;
    @FXML
    private ComboBox<Materia> cbMateria;
    private ObservableList<Materia> materias;
    @FXML
    private ComboBox<String> cbSeccion;
    private ObservableList<String> secciones;
    @FXML
    private ComboBox<Periodo> cbPeriodo;
    private ObservableList<Periodo> periodos;
    private boolean esEdicion;
    private Curso cursoEdicion;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";
    private INotificacionOperacionCurso interfazNotificacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionSeccion();
        cargarInformacionMateria();
        cargarInformacionPeriodo();
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, Curso cursoEdicion, INotificacionOperacionCurso interfazNotificacion){
        this.esEdicion = esEdicion;
        this.cursoEdicion = cursoEdicion;
        this.interfazNotificacion = interfazNotificacion;
        
        if(esEdicion){
            lbTitulo.setText("Editar información del Curso");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo Curso");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfBloque.setText(String.valueOf(cursoEdicion.getBloque()));
        tfNRC.setText(cursoEdicion.getNrc());
        String seccion = cursoEdicion.getSeccion();
        cbSeccion.getSelectionModel().select(seccion);
        int posicionMateria = obtenerPosicionComboMateria(cursoEdicion.getIdMateria());
        cbMateria.getSelectionModel().select(posicionMateria);
        int posicionPeriodo = obtenerPosicionComboPeriodo(cursoEdicion.getIdPeriodo());
        cbPeriodo.getSelectionModel().select(posicionPeriodo);
        
    }
    
    public void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidados = true;
        
        String bloque = tfBloque.getText();
        String nrc = tfNRC.getText();
        String seccion = cbSeccion.getSelectionModel().getSelectedItem();
        int posicionMateria = cbMateria.getSelectionModel().getSelectedIndex();
        int posicionPeriodo = cbPeriodo.getSelectionModel().getSelectedIndex();
        
        if(bloque.isEmpty()){
            tfBloque.setStyle(estiloError);
            datosValidados = false;
        }
        if(nrc.isEmpty()){
            tfNRC.setStyle(estiloError);
            datosValidados = false;
        }
        if(seccion == null){
            cbSeccion.setStyle(estiloError);
            datosValidados = false;
        }
        if(posicionMateria == -1){
            cbMateria.setStyle(estiloError);
            datosValidados = false;
        }
        if(posicionPeriodo == -1){
            cbPeriodo.setStyle(estiloError);
            datosValidados = false;
        }
        
        if(datosValidados){
            Curso cursoValidado = new Curso();
            cursoValidado.setBloque(Integer.parseInt(bloque));
            cursoValidado.setNrc(nrc);
            cursoValidado.setSeccion(seccion);
            cursoValidado.setIdMateria(materias.get(posicionPeriodo).getIdMateria());
            cursoValidado.setIdPeriodo(periodos.get(posicionPeriodo).getIdPeriod());
            
            if(esEdicion){
                cursoValidado.setIdCurso(cursoEdicion.getIdCurso());
                actualizarCurso(cursoValidado);
            }else{
                registrarCurso(cursoValidado);
            }
        }
    }
    
    private void registrarCurso(Curso cursoRegistro){
        int codigoRespuesta = CursoDAO.guardarCurso(cursoRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El curso no puedo ser registrado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del curso no puede ser registrado, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Curso Registrado", 
                        "La información del curso fue registrado correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarCurso();
                break;
        }
    }
    
    private void actualizarCurso(Curso cursoActualizar){
        int codigoRespuesta = CursoDAO.modificarCurso(cursoActualizar);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El curso no puedo ser actualizado debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del curso no puede ser actualizada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Curso Actualizado", 
                        "La información del curso fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizarCurso();
                break;
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistro();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cargarInformacionSeccion(){
        secciones = FXCollections.observableArrayList("Matutino","Vespertino");
        cbSeccion.setItems(secciones);
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
    private int obtenerPosicionComboMateria(int idMateria){
        for(int i = 0; i < materias.size(); i++){
            if(materias.get(i).getIdMateria() == idMateria)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionComboPeriodo(int idPeriodo){
        for(int i = 0; i < periodos.size(); i++){
            if(periodos.get(i).getIdPeriod() == idPeriodo)
                return i;
        }
        return 0;
    }
    
    private void cargarInformacionMateria(){
        materias = FXCollections.observableArrayList();
        MateriaRespuesta materiasBD = MateriaDAO.obtenerInformacionMateria();
        switch(materiasBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de Conexión", 
                            "Error de conexion con la base de datos.", AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de Consulta", 
                            "Por el momento no se puede obtener la información.", AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    materias.addAll(materiasBD.getMaterias());
                    cbMateria.setItems(materias);
                break;
        }
    }
    
    private void cargarInformacionPeriodo(){
        periodos = FXCollections.observableArrayList();
        PeriodoRespuesta periodosBD = PeriodoDAO.obtenerInformacionPeriodo();
        switch(periodosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de Conexión", 
                            "Error de conexion con la base de datos.", AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de Consulta", 
                            "Por el momento no se puede obtener la información.", AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    periodos.addAll(periodosBD.getPeriodos());
                    cbPeriodo.setItems(periodos);
                break;
        }
    }
    
    private void establecerEstiloNormal(){
        tfBloque.setStyle(estiloNormal);
        tfNRC.setStyle(estiloNormal);
        cbSeccion.setStyle(estiloNormal);
        cbMateria.setStyle(estiloNormal);
        cbPeriodo.setStyle(estiloNormal);
    }
}
