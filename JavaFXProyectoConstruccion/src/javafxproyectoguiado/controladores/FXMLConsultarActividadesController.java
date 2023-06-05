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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.TableActivitiesDAO;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.TableActivities;
import util.Constantes;
import util.Utilidades;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLConsultarActividadesController implements Initializable {
    @FXML
    private Button btnRegresar;

    @FXML
    private TableColumn<String, String> columnFechaInicio;

    @FXML
    private TableColumn<TableActivities, String> columnDescripcion;

    @FXML
    private TableView<TableActivities> tableViewActivites;

    @FXML
    private TableColumn<TableActivities, String> columnFechaTermino;

    @FXML
    private TableColumn< TableActivities, String> columnTitulo;
    @FXML
    private TableColumn<TableActivities, Void> columnButton;
    @FXML
    private Label labelFecha;

    @FXML
    private Label labelNombre;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {
        mostrarActividadesMenu();
    }
    public void mostrarActividadesMenu(){
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarVentana();
        columnButton.setCellFactory(column -> new TableCell<TableActivities, Void>() {
            private final Button button = new Button("Consultar");

            {
                StackPane.setAlignment(button, Pos.CENTER);
                button.setOnAction(event -> {
                    TableActivities activity = getTableView().getItems().get(getIndex());
                    System.out.println("Button clicked for activity ID: " + activity.getIdActividad());


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxproyectoguiado/vistas/FXMLGestionarActividad.fxml"));
                    try {
                        Parent root = loader.load();
                        FXMLGestionarActividad controller = loader.getController();
                        controller.setIdActividad(activity.getIdActividad());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setOnShown(event2 -> {
                            // Llamar a initialize después de que se muestre la ventana
                            controller.initialize(null, null);
                        });
                        stage.show();
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

    public void configurarVentana(){
        this.labelNombre.setText(Singleton.getName());
        this.labelFecha.setText(LocalDate.now().toString() );
        this.columnTitulo.setText("Titulo");
        this.columnDescripcion.setText("Descripcion");
        this.columnFechaInicio.setText("Fecha Inicio");
        this.columnFechaTermino.setText("Fecha Termino");
        setActivitiesToTable();
    }



    public void setActivitiesToTable()  {
        TableActivitiesDAO tableActivitiesDAO = new TableActivitiesDAO();
        List<TableActivities> activitiesList=null;
        ObservableList<TableActivities> activitiesObservableList= FXCollections.observableArrayList();

        try{
            activitiesList = tableActivitiesDAO.getActivities(Singleton.getId());
            if (activitiesList!=null){
                activitiesObservableList.addAll(activitiesList);
            }

            columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            columnFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
            columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            columnFechaTermino.setCellValueFactory(new PropertyValueFactory<>("fechaTermino"));
            tableViewActivites.setItems(activitiesObservableList);

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