package javafxproyectoguiado.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.AnteproyectoModuloDAO;
import javafxproyectoguiado.modelo.dao.DirectorTesisDAO;
import javafxproyectoguiado.modelo.dao.LGACDAO;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.DirectorTesis;
import javafxproyectoguiado.modelo.pojo.DirectorTesisRespuesta;
import javafxproyectoguiado.modelo.pojo.LGAC;
import javafxproyectoguiado.modelo.pojo.LGACRespuesta;
import util.Constantes;
import util.INotificacionOperacionAnteproyecto;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosFormularioController implements Initializable {

    @FXML
    private Button btnGuardarFormulario;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfNombreAnteproyecto;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField tfModalidadTrabajo;
    @FXML
    private ComboBox<LGAC> cbLGAC;
    @FXML
    private ComboBox<DirectorTesis> cbEncargadoDeTesis;
    @FXML
    private TextArea taDescripcion;

    private ObservableList<LGAC> lgacs;
    private ObservableList<DirectorTesis> encargados;
    
    private AnteproyectoModulo anteproyectoEdicion;
    private boolean esEdicion;
    private INotificacionOperacionAnteproyecto interfazNotificacion;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        cargarInformacionLACS();
        cbLGAC.valueProperty().addListener(new ChangeListener<LGAC>() {
            @Override
            public void changed(ObservableValue<? extends LGAC> observable, 
                    LGAC oldValue, LGAC newValue) {
            }
        });
        
        cargarInformacionEncargado();
        cbEncargadoDeTesis.valueProperty().addListener(new ChangeListener<DirectorTesis>() {
            @Override
            public void changed(ObservableValue<? extends DirectorTesis> observable, 
                    DirectorTesis oldValue, DirectorTesis newValue) {
            }
        });
        dpFechaInicio.setEditable(false);
        dpFechaFin.setEditable(false);
    }    
    
    public void inicializarInformacionFormulario(boolean esEdicion, AnteproyectoModulo anteproyectoEdicion, 
            INotificacionOperacionAnteproyecto interfazNotificacion){
        this.esEdicion = esEdicion;
        this.anteproyectoEdicion = anteproyectoEdicion;
        this.interfazNotificacion = interfazNotificacion;
        if(esEdicion){
            lbTitulo.setText("Editar información del anteproyecto: "+anteproyectoEdicion.getNombreAnteproyecto());
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nuevo anteproyecto");
        }
    }
    
    private void cargarInformacionEdicion(){
        tfNombreAnteproyecto.setText(anteproyectoEdicion.getNombreAnteproyecto());
        tfModalidadTrabajo.setText(anteproyectoEdicion.getModalidad());
        int posicionLGAC = obtenerPosicionComboLGAC(anteproyectoEdicion.getIdLGAC());
        cbLGAC.getSelectionModel().select(posicionLGAC); 
        dpFechaInicio.setValue(LocalDate.parse(anteproyectoEdicion.getFechaInicio()));
        dpFechaFin.setValue(LocalDate.parse(anteproyectoEdicion.getDuracion()));
        int posicionEncargado = obtenerPosicionComboEncargado(anteproyectoEdicion.getIdEncargadoDeTesis());
        cbEncargadoDeTesis.getSelectionModel().select(posicionEncargado);
        taDescripcion.setText(anteproyectoEdicion.getDescripcion());
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void establecerEstiloNormal(){
        tfNombreAnteproyecto.setStyle(estiloNormal);
        tfModalidadTrabajo.setStyle(estiloNormal);
        cbLGAC.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFin.setStyle(estiloNormal);
        cbEncargadoDeTesis.setStyle(estiloNormal);
        taDescripcion.setStyle(estiloNormal);
    }
    
    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        String nombreAnteproyecto = tfNombreAnteproyecto.getText();
        String modalidad = tfModalidadTrabajo.getText();
        int posicionLGAC = cbLGAC.getSelectionModel().getSelectedIndex();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        int posicionEncargado = cbEncargadoDeTesis.getSelectionModel().getSelectedIndex();
        String descripcion = taDescripcion.getText();
        
        if(nombreAnteproyecto.isEmpty()){
            tfNombreAnteproyecto.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(!Utilidades.soloLetras(nombreAnteproyecto)){
            tfNombreAnteproyecto.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(modalidad.isEmpty()){
            tfModalidadTrabajo.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(!Utilidades.soloLetras(modalidad)){
            tfModalidadTrabajo.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionLGAC == -1){
            cbLGAC.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(fechaInicio == null){
            dpFechaInicio.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(fechaFin == null){
            dpFechaFin.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(posicionEncargado == -1){
            cbEncargadoDeTesis.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(descripcion.isEmpty()){
            taDescripcion.setStyle(estiloError);
            datosValidos = false;
        }
        
        if (dpFechaFin.getValue() != null && dpFechaInicio.getValue() != null) {
            if (dpFechaFin.getValue().isBefore(dpFechaInicio.getValue())) {
                Utilidades.mostrarDiallogoSimple("Fecha no valida",
                "La fecha del finalización del anteproyecto no puede ser una fecha anterior a la fecha de inicio",
                Alert.AlertType.WARNING);
                dpFechaInicio.setStyle(estiloError);
                dpFechaFin.setStyle(estiloError);
                datosValidos = false;
            }
        }
        
        if(datosValidos){
            AnteproyectoModulo anteproyectoValido = new AnteproyectoModulo();
            anteproyectoValido.setNombreAnteproyecto(nombreAnteproyecto);
            anteproyectoValido.setModalidad(modalidad);
            anteproyectoValido.setIdLGAC(lgacs.get(posicionLGAC).getIdLGAC());
            anteproyectoValido.setFechaInicio(fechaInicio.toString());
            anteproyectoValido.setDuracion(fechaFin.toString());
            anteproyectoValido.setIdEncargadoDeTesis(encargados.get(posicionEncargado).getIdUsuario());
            anteproyectoValido.setDescripcion(descripcion);
            
            if(esEdicion){
                anteproyectoValido.setIdAnteproyecto(anteproyectoEdicion.getIdAnteproyecto());
                actualizarAnteproyecto(anteproyectoValido);
            }else{
                registrarAnteproyecto(anteproyectoValido);
            }
            
        }
    }
    
    private void registrarAnteproyecto(AnteproyectoModulo anteproyectoNuevo){
        int codigoRespuesta = AnteproyectoModuloDAO.guardarAnteproyecto(anteproyectoNuevo);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "El anteproyecto no puede ser registrado debido a un error en la conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la información", 
                        "La información del anteproyecto no puede ser registradada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDiallogoSimple("Anteproyecto Registrado", 
                        "La información del Anteproyecto fue registrada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionGuardarAnteproyecto(anteproyectoNuevo.getNombreAnteproyecto());
                break;
        }
    }
    
    private void actualizarAnteproyecto(AnteproyectoModulo anteproyectoActualizar){
        int codigoRespuesta = AnteproyectoModuloDAO.modificarAnteproyecto(anteproyectoActualizar);
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
                Utilidades.mostrarDiallogoSimple("Anteproyecto Actualizado", 
                        "La información del anteproyecto fue actualizada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                interfazNotificacion.notificarOperacionActualizarAnteproyecto(anteproyectoActualizar.getNombreAnteproyecto());
                break;
        }
        
    }
    
    private void cargarInformacionLACS(){
        lgacs = FXCollections.observableArrayList();
        LGACRespuesta productosBD = LGACDAO.obtenerInformacionLGAC();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    lgacs.addAll(productosBD.getLgacs());
                    cbLGAC.setItems(lgacs);
                break;
        }
    }
    
    private int obtenerPosicionComboLGAC(int idLGAC){
        for (int i = 0; i < lgacs.size(); i++) {
            if(lgacs.get(i).getIdLGAC()== idLGAC)
                return i;
        }
        return 0;
    }
    
    private void cargarInformacionEncargado(){
        encargados = FXCollections.observableArrayList();
        DirectorTesisRespuesta productosBD = DirectorTesisDAO.obtenerInformacionDirectorTesis();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    encargados.addAll(productosBD.getDirectores());
                    cbEncargadoDeTesis.setItems(encargados);
                break;
        }
    }
    
    private int obtenerPosicionComboEncargado(int idEncargado){
        for (int i = 0; i < encargados.size(); i++) {
            if(encargados.get(i).getIdUsuario()== idEncargado)
                return i;
        }
        return 0;
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }
    
}
