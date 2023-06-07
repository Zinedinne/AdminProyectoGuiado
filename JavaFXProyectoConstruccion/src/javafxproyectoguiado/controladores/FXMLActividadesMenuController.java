package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.pojo.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLActividadesMenuController implements Initializable {
    @FXML
    private Button btnVerActividades;
    @FXML
    private Button btnRegistrarActividad;

    @FXML
    void btnVerActividadesOnAction(ActionEvent event) {

        String path ;
        if (Singleton.getRol().equals("Estudiante")){
            path = "/javafxproyectoguiado/vistas/FXMLConsultarActividades.fxml";
        }else {
            path = "/javafxproyectoguiado/vistas/FXMLConsultarProyecto.fxml";
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(path));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnVerActividades.getScene().getWindow();
            stage.setTitle("Consultar Actividades");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    @FXML
    void btnRegistrarActividadOnAction(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLRegistrarActividad.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnVerActividades.getScene().getWindow();
            stage.setTitle("Registrar Actividad");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Singleton.getRol().equals("Estudiante")){
            btnRegistrarActividad.setVisible(false);
        }
    }
}