package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLConsultarActividadesController {
    @FXML
    private Button btnRegresar;
    @FXML
    private TableColumn<?, ?> columnFechaInicio;

    @FXML
    private TableColumn<?, ?> columnDescripcion;

    @FXML
    private TableView<?> tableViewActivites;

    @FXML
    private TableColumn<?, ?> columnFechaTermino;

    @FXML
    private TableColumn<?, ?> columnTitulo;

    @FXML
    private Label labelFecha;

    @FXML
    private Label labelNombre;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {
        openWindow();
    }
    public void openWindow(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLActividadesMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnRegresar.getScene().getWindow();
            stage.setTitle("Actividades Menu");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

}
