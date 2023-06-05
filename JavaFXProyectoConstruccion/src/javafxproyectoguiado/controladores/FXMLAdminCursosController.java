package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLAdminCursosController implements Initializable {

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
    private TextField tfBusqueda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegresar(MouseEvent event) {
        Stage escearioPrincipal = (Stage) tfBusqueda.getScene().getWindow();
        escearioPrincipal.close();
    }

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
    }
    
}
