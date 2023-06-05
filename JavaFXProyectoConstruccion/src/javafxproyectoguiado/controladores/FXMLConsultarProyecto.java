package javafxproyectoguiado.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.TableActivitiesDAO;
import javafxproyectoguiado.modelo.dao.TableAnteproyectoDAO;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import javafxproyectoguiado.modelo.pojo.TableAnteproyecto;
import util.Constantes;
import util.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLConsultarProyecto implements Initializable {

    @FXML
    private TableView<TableAnteproyecto> tableAnteproyecto;

    @FXML
    private TableColumn<TableAnteproyecto, String> columnDescripcion;

    @FXML
    private Label labelProfesor;

    @FXML
    private TableColumn<TableAnteproyecto, Integer> columnEstado;

    @FXML
    private TableColumn<TableAnteproyecto, String> columnDuracion;

    @FXML
    private TableColumn<TableAnteproyecto, String> columnTitulo;

    @FXML
    private Label labelFecha;

    @FXML
    private TableColumn<?, ?> columnModalidad;

    @FXML
    private ComboBox<?> comboBoxEstudiante;

    @FXML
    private TableColumn<TableAnteproyecto, String> columnFechaInicio;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<TableAnteproyecto, Void> columnBoton;

    @FXML
    private TableColumn<TableAnteproyecto, String> columnAlumno;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxEstudianteOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setActivitiesToTable();
        columnBoton.setCellFactory(column -> new TableCell<TableAnteproyecto, Void>() {
            private final Button button = new Button("Consultar");

            {
                StackPane.setAlignment(button, Pos.CENTER);
                button.setOnAction(event -> {
                    TableAnteproyecto activity = getTableView().getItems().get(getIndex());
                    System.out.println("Button clicked for activity ID: " + activity.getIdAnteproyecto());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxproyectoguiado/vistas/FXMLConsultarActividades.fxml"));
                    try {
                        Parent root = loader.load();
                        FXMLConsultarActividadesController controller = loader.getController();
                        controller.setIdAlumno(activity.getIdUsuario());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setOnShown(event2 -> {
                            // Llamar a initialize después de que se muestre la ventana
                            controller.initialize(null, null);
                        });
                        stage.show();
                        ((Stage) this.button.getScene().getWindow()).close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }
    public void setActivitiesToTable()  {
        TableAnteproyectoDAO tableAnteproyectoDAO = new TableAnteproyectoDAO();
        List<TableAnteproyecto> anteproyectoList=null;
        ObservableList<TableAnteproyecto> anteproyectoObservableList= FXCollections.observableArrayList();
        try{
            anteproyectoList = tableAnteproyectoDAO.getAnteproyecto(Singleton.getId());
            if (anteproyectoList!=null){
                anteproyectoObservableList.addAll(anteproyectoList);
            }

            columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            columnFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
            columnTitulo.setCellValueFactory(new PropertyValueFactory<>("nombreAnteproyecto"));
            columnDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
            columnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            columnModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
            tableAnteproyecto.setItems(anteproyectoObservableList);


        } catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar las actividades",  Alert.AlertType.ERROR);
            }
            else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))){
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos",  Alert.AlertType.ERROR);
            }else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido",  Alert.AlertType.ERROR);
            }
        }
    }
}