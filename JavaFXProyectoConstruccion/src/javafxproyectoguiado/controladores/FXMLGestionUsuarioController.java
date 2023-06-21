package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Utilidades;

public class FXMLGestionUsuarioController implements Initializable {

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) lbTitulo.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnEstudiantes(MouseEvent event) {
        Stage escenarioEstudiante = new Stage();
        Scene esceneAdminEstudiante = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminEstudiantes.fxml");
        escenarioEstudiante.setScene(esceneAdminEstudiante);
        escenarioEstudiante.setTitle("Administracion de Estudiantes");
        escenarioEstudiante.initModality(Modality.APPLICATION_MODAL);
        escenarioEstudiante.showAndWait();
        escenarioEstudiante.centerOnScreen();
    }

    @FXML
    private void clicBtnProfesores(MouseEvent event) {
        Stage escenarioProfesor = new Stage();
        Scene esceneAdminProfesor = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminProfesores.fxml");
        escenarioProfesor.setScene(esceneAdminProfesor);
        escenarioProfesor.setTitle("Administracion de Profesores");
        escenarioProfesor.initModality(Modality.APPLICATION_MODAL);
        escenarioProfesor.showAndWait();
    }

    @FXML
    private void clicBtnDirectores(MouseEvent event) {
        Stage escenarioDirectorTesis = new Stage();
        Scene esceneAdminDirectorTesis = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminDirectorTesis.fxml");
        escenarioDirectorTesis.setScene(esceneAdminDirectorTesis);
        escenarioDirectorTesis.setTitle("Administracion de Directores de Tesis");
        escenarioDirectorTesis.initModality(Modality.APPLICATION_MODAL);
        escenarioDirectorTesis.showAndWait();
    }

    @FXML
    private void clicBtnResponsables(MouseEvent event) {
        Stage escenarioResponsable = new Stage();
        Scene esceneAdminResponsable = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminResponsables.fxml");
        escenarioResponsable.setScene(esceneAdminResponsable);
        escenarioResponsable.setTitle("Administracion de Responsable de Tesis");
        escenarioResponsable.initModality(Modality.APPLICATION_MODAL);
        escenarioResponsable.showAndWait();
    }
    
}
