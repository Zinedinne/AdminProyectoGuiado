package javafxproyectoguiado.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;
import javafxproyectoguiado.modelo.dao.CursoDAO;
import javafxproyectoguiado.modelo.pojo.Curso;
import javafxproyectoguiado.modelo.pojo.CursoRespuesta;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.INotificacionOperacionCurso;
import util.Utilidades;

public class FXMLAdminCursosController implements Initializable, INotificacionOperacionCurso {

    @FXML
    private TableColumn tcBloque;
    @FXML
    private TableColumn tcNRC;
    @FXML
    private TableColumn tcSeccion;
    @FXML
    private TableColumn tcNombreMateria;
    @FXML
    private TableColumn tcNombrePeriodo;
    @FXML
    private TableView<Curso> tvCursos;
    private ObservableList<Curso> cursos;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        switch(Singleton.getRol()){
            case "Estudiante":
                btnModificar.setVisible(false);
                btnRegistrar.setVisible(false);
                break;
            case "Profesor":
                btnRegistrar.setVisible(false);
                break;
        }
    }

    private void configurarTabla(){
        tcBloque.setCellValueFactory(new PropertyValueFactory("Bloque"));
        tcNRC.setCellValueFactory(new PropertyValueFactory("Nrc"));
        tcSeccion.setCellValueFactory(new PropertyValueFactory("Seccion"));
        tcNombreMateria.setCellValueFactory(new PropertyValueFactory("NombreMateria"));
        tcNombrePeriodo.setCellValueFactory(new PropertyValueFactory("NombrePeriodo"));
    }
    
    private void cargarInformacionTabla(){
        cursos = FXCollections.observableArrayList();
        CursoRespuesta respuestaBD = CursoDAO.obtenerInformacionCurso();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error Conxeion", 
                            "Los sentimos por el momento no hay conexión para poder cargar la información", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error al cargar los datos", 
                            "Hubo un error al cargar la información por favor inténtelo más tarde", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    cursos.addAll(respuestaBD.getCursos());
                    tvCursos.setItems(cursos);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvCursos.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        irFormulario(false,null);
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        Curso cursoSeleccionado = tvCursos.getSelectionModel().getSelectedItem();
        if(cursoSeleccionado != null){
            irFormulario(true, cursoSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Curso", 
                    "Selecciona el registro en la tabla del Curso para su edicion", Alert.AlertType.WARNING);
        }
    }
    
    private void irFormulario(boolean esEdicion, Curso cursoEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioCurso.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioCursoController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, cursoEdicion,
                    this);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("FormularioCurso");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLFormularioCursoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarOperacionGuardarCurso() {
        cargarInformacionTabla();
    }

    @Override
    public void notificarOperacionActualizarCurso() {
        cargarInformacionTabla();
    }

    @FXML
    private void dobleClicConsultarCurso(MouseEvent event) {
        Curso cursoSeleccionado = tvCursos.getSelectionModel().getSelectedItem();
        if(event.getClickCount() == 2){
            irConsulta(cursoSeleccionado);
        }
    }
    
    private void irConsulta(Curso cursoConsulta){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLConsultaCurso.fxml"));
            Parent vista = accesoControlador.load();
            FXMLConsultaCursoController consulta = accesoControlador.getController();
            
            consulta.inicializarInformacionConsulta(cursoConsulta);
            
            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene (vista));
            escenarioFormulario.setTitle("Consultar Empleado");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex){
            Logger.getLogger(FXMLConsultaCursoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
