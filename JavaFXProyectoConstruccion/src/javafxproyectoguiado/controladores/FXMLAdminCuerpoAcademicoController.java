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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;
import javafxproyectoguiado.modelo.dao.CuerpoAcademicoDAO;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademico;
import javafxproyectoguiado.modelo.pojo.CuerpoAcademicoRespuesta;
import util.Constantes;
import util.Utilidades;
import util.INotificacionOperacionCuerpoAcademico;

public class FXMLAdminCuerpoAcademicoController implements Initializable, INotificacionOperacionCuerpoAcademico{

    @FXML
    private TableView<CuerpoAcademico> tvCuerpoAcademico;
    @FXML
    private TableColumn tcNombre;
    private ObservableList<CuerpoAcademico> academias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }

    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombreAcademia"));
    }
    
    private void cargarInformacionTabla(){
        academias = FXCollections.observableArrayList();
        CuerpoAcademicoRespuesta respuestaBD = CuerpoAcademicoDAO.obtenerInformaciónCuerpoAcademico();
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
                    academias.addAll(respuestaBD.getAcademias());
                    tvCuerpoAcademico.setItems(academias);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tvCuerpoAcademico.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        CuerpoAcademico cuerpoAcademicoSeleccionado = tvCuerpoAcademico.getSelectionModel().getSelectedItem();
        if(cuerpoAcademicoSeleccionado != null){
            irFormulario(true, cuerpoAcademicoSeleccionado);
        }else{
            Utilidades.mostrarDiallogoSimple("Selecciona un Cuerpo Academico", 
                    "Selecciona el registro en la tabla del Cuerpo Academico para su edicion", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        irFormulario(false,null);
    }
    
    private void irFormulario(boolean esEdicion, CuerpoAcademico cuerpoAcademicoEdicion){
        try{
            FXMLLoader accesoControlador = new FXMLLoader
                    (JavaFXProyectoConstruccion.class.getResource("/javafxproyectoguiado/vistas/FXMLFormularioCuerpoAcademico.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioCuerpoAcademicoController formulario = accesoControlador.getController();
            
            formulario.inicializarInformacionFormulario(esEdicion, cuerpoAcademicoEdicion,
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
    public void notificarOperacionGuardarCuerpoAcademico() {
        cargarInformacionTabla();
    }

    @Override
    public void notificarOperacionActualizarCuerpoAcademico() {
        cargarInformacionTabla();
    }
    
}
