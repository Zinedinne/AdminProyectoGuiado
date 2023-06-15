package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.AnteproyectoModuloDAO;
import javafxproyectoguiado.modelo.dao.EstudianteDAO;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModuloRespuesta;
import javafxproyectoguiado.modelo.pojo.Estudiante;
import javafxproyectoguiado.modelo.pojo.EstudianteRespuesta;
import util.Constantes;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAsignarEstudianteAnteproyectoController implements Initializable {

    @FXML
    private ComboBox<Estudiante> cbEstudiantes;
    private ObservableList<Estudiante> estudiantes;
    @FXML
    private ComboBox<AnteproyectoModulo> cbAnteproyecto;
    private ObservableList<AnteproyectoModulo> anteproyectos;
    
    private AnteproyectoModulo anteproyectoConsulta;
    
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionEstudiantes();
        cbEstudiantes.valueProperty().addListener(new ChangeListener<Estudiante>() {
            @Override
            public void changed(ObservableValue<? extends Estudiante> observable, 
                    Estudiante oldValue, Estudiante newValue) {
            }
        });
        
        cargarInformacionAnteproyectos();
        cbAnteproyecto.valueProperty().addListener(new ChangeListener<AnteproyectoModulo>() {
            @Override
            public void changed(ObservableValue<? extends AnteproyectoModulo> observable, 
                    AnteproyectoModulo oldValue, AnteproyectoModulo newValue) {
            }
        });
        cbAnteproyecto.setDisable(true);
        cbAnteproyecto.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
    }    
    
    public void inicializarInformacionConsulta(AnteproyectoModulo anteproyectoConsulta){
        this.anteproyectoConsulta = anteproyectoConsulta;
        
        cargarInformacionConsulta();
    }
    
    public void cargarInformacionConsulta(){
        int posicionAnteproyecto = obtenerPosicionComboAnteproyectos(anteproyectoConsulta.getIdAnteproyecto());
        cbAnteproyecto.getSelectionModel().select(posicionAnteproyecto);
    }
    
    @FXML
    private void clicBtnAsignar(ActionEvent event) {
        validarCamposRegistro();
    }
    
    private void establecerEstiloNormal(){
        cbEstudiantes.setStyle(estiloNormal);
    }

    private void validarCamposRegistro(){
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        int posicionEstudiante = cbEstudiantes.getSelectionModel().getSelectedIndex();
        int posicionAnteproyecto = cbAnteproyecto.getSelectionModel().getSelectedIndex();
        
        if(posicionEstudiante == -1){
            cbEstudiantes.setStyle(estiloError);
            datosValidos = false;
        }
        
        if(datosValidos){
            AnteproyectoModulo anteproyectoValido = new AnteproyectoModulo();
            anteproyectoValido.setIdEstudianteAsignado(estudiantes.get(posicionEstudiante).getIdUsuario());
            anteproyectoValido.setIdAnteproyecto(anteproyectos.get(posicionAnteproyecto).getIdAnteproyecto());
            int idEstudianteAsignado = anteproyectoValido.getIdEstudianteAsignado();
            int idAnteproyecto = anteproyectoValido.getIdAnteproyecto();
            
            int cantidadAnteproyectoUsuario = AnteproyectoModuloDAO.obtenerCantidadAnteproyectoUsuario(idEstudianteAsignado);
            
            if(cantidadAnteproyectoUsuario >= 1){
                Utilidades.mostrarDiallogoSimple("Alumno ya asignado", 
                        "El alumno seleccionado ya ha sido asignado a un anteproyecto", 
                        Alert.AlertType.WARNING);
            }else{
            registrarAsignacion(anteproyectoValido);
            }
        }
    }

    private void registrarAsignacion(AnteproyectoModulo anteproyectoValido){
        int codigoRespuesta = AnteproyectoModuloDAO.asignarEstudianteAnteproyecto(anteproyectoValido);
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
                Utilidades.mostrarDiallogoSimple("Anteproyecto asignado", 
                        "El anteproyecto fue asignado correctamente al alumno", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) cbEstudiantes.getScene().getWindow();
        escenarioBase.close();
    }
    
    private void cargarInformacionAnteproyectos(){
        anteproyectos = FXCollections.observableArrayList();
        AnteproyectoModuloRespuesta productosBD = AnteproyectoModuloDAO.obtenerInformacionAnteproyecto();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    anteproyectos.addAll(productosBD.getAnteproyectosModulo());
                    cbAnteproyecto.setItems(anteproyectos);
                break;
        }
    }
    
    private int obtenerPosicionComboAnteproyectos(int idAnteproyecto){
        for (int i = 0; i < anteproyectos.size(); i++) {
            if(anteproyectos.get(i).getIdAnteproyecto()== idAnteproyecto)
                return i;
        }
        return 0;
    }
    
    private void cargarInformacionEstudiantes(){
        estudiantes = FXCollections.observableArrayList();
        EstudianteRespuesta productosBD = EstudianteDAO.obtenerInformacionEstudiante();
        switch(productosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de conexión", 
                            "Error de conexion con la base de datos.", 
                            Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de consulta", 
                            "Por el momento no se puede obtener la información.", 
                            Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    estudiantes.addAll(productosBD.getEstudiantes());
                    cbEstudiantes.setItems(estudiantes);
                break;
        }
    }
    
    private int obtenerPosicionComboEstudiantes(int idEstudiante){
        for (int i = 0; i < estudiantes.size(); i++) {
            if(estudiantes.get(i).getIdEstudiante()== idEstudiante)
                return i;
        }
        return 0;
    }
    
}
