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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Utilidades;
import javafxproyectoguiado.modelo.pojo.Singleton;

/**
 * FXML Controller class
 *
 * @author Equipo7
 */
public class FXMLMainMenuController implements Initializable {

    @FXML
    private Button btnActividadesMenu;

    @FXML
    private Button btnProyectosMenu;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnCurso;
    @FXML
    private Button btnLGAC;
    @FXML
    private Button btnCuerpoAcademico;
    @FXML
    private Button btnValidacionProyectosMenu;
    @FXML
    private Button btnProyectosMenuUsuario;

    @FXML
    void btnProyectosMenusOnAction(ActionEvent event) {
        Stage escenarioProyectos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAnteproyectosMenu.fxml");
        escenarioProyectos.setScene(esceneAdminAlumnos);
        escenarioProyectos.setTitle("Administración de anteproyectos");
        escenarioProyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioProyectos.showAndWait(); 
        escenarioProyectos.centerOnScreen();
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
            stage.centerOnScreen();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        switch(Singleton.getRol()){
            case "Responsable de academia":
                btnCurso.setVisible(false);
                btnCuerpoAcademico.setVisible(false);
                btnLGAC.setVisible(false);
                btnUsuarios.setVisible(false);
                btnProyectosMenu.setVisible(false);
                break;
            case "Estudiante":
                btnCuerpoAcademico.setVisible(false);
                btnLGAC.setVisible(false);
                btnUsuarios.setVisible(false);
                btnProyectosMenu.setVisible(false);
                btnValidacionProyectosMenu.setVisible(false);
                btnProyectosMenuUsuario.setVisible(false);
                break;
            case "Director de tesis":
                btnCurso.setVisible(false);
                btnCuerpoAcademico.setVisible(false);
                btnLGAC.setVisible(false);
                btnUsuarios.setVisible(false);
                btnValidacionProyectosMenu.setVisible(false);
                break;
            case "Profesor":
                btnCuerpoAcademico.setVisible(false);
                btnLGAC.setVisible(false);
                btnUsuarios.setVisible(false);
                btnValidacionProyectosMenu.setVisible(false);
                btnProyectosMenu.setVisible(false);
                break;
            case "Administrador":
                btnActividadesMenu.setVisible(false);
                btnProyectosMenu.setVisible(false);
                btnValidacionProyectosMenu.setVisible(false);
                btnProyectosMenuUsuario.setVisible(false);
                break;
        }
    }

    @FXML
    private void clicBtnCursos(ActionEvent event) {
        Stage escenarioCursos = new Stage();
        Scene esceneAdminCursos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminCursos.fxml");
        escenarioCursos.setScene(esceneAdminCursos);
        escenarioCursos.setTitle("Gestión de Cursos");
        escenarioCursos.initModality(Modality.APPLICATION_MODAL);
        escenarioCursos.showAndWait();
        escenarioCursos.centerOnScreen();
    }

    @FXML
    private void clicBtnLGAC(ActionEvent event) {
        Stage escenarioLGAC = new Stage();
        Scene esceneAdminLGAC = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminLGAC.fxml");
        escenarioLGAC.setScene(esceneAdminLGAC);
        escenarioLGAC.setTitle("Gestión de LGAC");
        escenarioLGAC.initModality(Modality.APPLICATION_MODAL);
        escenarioLGAC.showAndWait();
        escenarioLGAC.centerOnScreen();
    }

    @FXML
    private void clicBtnCuerpoAcademico(ActionEvent event) {
        Stage escenarioCuerpoAcademico = new Stage();
        Scene esceneAdminCuerposAcademicos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAdminCuerpoAcademico.fxml");
        escenarioCuerpoAcademico.setScene(esceneAdminCuerposAcademicos);
        escenarioCuerpoAcademico.setTitle("Gestión de Cuerpo Academico");
        escenarioCuerpoAcademico.initModality(Modality.APPLICATION_MODAL);
        escenarioCuerpoAcademico.showAndWait();
        escenarioCuerpoAcademico.centerOnScreen();
    }

    @FXML
    private void clicBtnGestionUsuarios(ActionEvent event) {
        Stage escenarioUsuarios = new Stage();
        Scene esceneAdminUsuarios = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLGestionUsuario.fxml");
        escenarioUsuarios.setScene(esceneAdminUsuarios);
        escenarioUsuarios.setTitle("Gestión de Usuarios");
        escenarioUsuarios.initModality(Modality.APPLICATION_MODAL);
        escenarioUsuarios.showAndWait();
        escenarioUsuarios.centerOnScreen();
    }

    @FXML
    private void btnValidacionProyectosMenuOnAction(ActionEvent event) {
        Stage escenarioAlumnos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAnteproyectosValidacionMenu.fxml");
        escenarioAlumnos.setScene(esceneAdminAlumnos);
        escenarioAlumnos.setTitle("Administración de validación de anteproyectos");
        escenarioAlumnos.initModality(Modality.APPLICATION_MODAL);
        escenarioAlumnos.showAndWait();  
        escenarioAlumnos.centerOnScreen();
    }

    @FXML
    private void clicCerrarSesion(MouseEvent event) {
        boolean cerrarSesion = Utilidades.mostrarDialogoConfirmacion("Cerrar Sesion", 
                "¿Esta seguro que desea cerrar sesion?");
        if(cerrarSesion){
            Stage escenarioBase = (Stage) btnActividadesMenu.getScene().getWindow();
            Singleton.setLogin(null);
                escenarioBase.setScene(Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLInicioSesion.fxml"));
                escenarioBase.setTitle("Inicio Sesión");
                escenarioBase.show();
                escenarioBase.centerOnScreen();
        }
    }

    @FXML
    private void btnProyectosMenuUsuarioOnAction(ActionEvent event) {
        Stage escenarioProyectos = new Stage();
        Scene esceneAdminAlumnos = Utilidades.inicializarEscena("/javafxproyectoguiado/vistas/FXMLAnteproyectosUsuarioMenu.fxml");
        escenarioProyectos.setScene(esceneAdminAlumnos);
        escenarioProyectos.setTitle("Administración de anteproyectos");
        escenarioProyectos.initModality(Modality.APPLICATION_MODAL);
        escenarioProyectos.showAndWait(); 
        escenarioProyectos.centerOnScreen();
    }


}
