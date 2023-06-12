/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxproyectoguiado.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Utilidades;
import javafxproyectoguiado.modelo.pojo.Singleton;

/**
 * FXML Controller class
 *
 * @author MB2
 */
public class FXMLMainMenuController implements Initializable {

    @FXML
    private Button btnActividadesMenu;

    @FXML
    private Button btnProyectosMenu;
    @FXML
    private Button btnValidacionProyectosMenu;


    void btnUsuariosMenuOnAction(ActionEvent event) {
        

    }

    @FXML
    void btnProyectosMenusOnAction(ActionEvent event) {
        Stage escenarioProyectos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAnteproyectosMenu.fxml");
        escenarioProyectos.setScene(esceneAdminAlumnos);
        escenarioProyectos.setTitle("Administración de anteproyectos");
        escenarioProyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioProyectos.showAndWait(); 
    }
    
    @FXML
    private void btnValidacionProyectosMenuOnAction(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAnteproyectosValidacionMenu.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Administración de validación de anteproyectos");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();  
    }
    
    @FXML
    void btnActividadesMenuOnAction(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLActividadesMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnActividadesMenu.getScene().getWindow();
            stage.setTitle("Menú de Actividades");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        switch (Singleton.getRol()) {
            case "Responsable de academia":
                //TODO
                break;
            case "Encargado de tesis":
                btnValidacionProyectosMenu.setVisible(false);
                break;
            case "Profesor":
                btnValidacionProyectosMenu.setVisible(false);
                break;
            case "Estudiante":
                btnProyectosMenu.setVisible(false);
                btnValidacionProyectosMenu.setVisible(false);
                break;
            case "TODO ROLES": 
                break;
            default:
                // Código por defecto si ninguno de los casos anteriores coincide
                break;
        }
    }

    @FXML
    private void clicBtnCursos(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminCursos.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Gestión de Cursos");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();
    }

    @FXML
    private void clicBtnLGAC(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminLGAC.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Gestión de LGAC");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();
    }

    @FXML
    private void clicBtnCuerpoAcademico(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminCuerpoAcademico.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Gestión de Cuerpo Academico");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();
    }

    @FXML
    private void clicBtnGestionUsuarios(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminUsuario.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Gestión Usuarios");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();
    }

    


}
