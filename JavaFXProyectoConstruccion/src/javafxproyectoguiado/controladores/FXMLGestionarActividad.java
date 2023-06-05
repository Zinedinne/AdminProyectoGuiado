package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.dao.EntregaDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.Evaluacion;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.Utilidades;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLGestionarActividad extends Stage implements Initializable {

    @FXML
    private Button btnAdjuntarArchivo;

    @FXML
    private WebView webViewDescripcion;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnDescargarDocumento;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelAlumno;

    @FXML
    private Label labelCalificacion;

    @FXML
    private Label labelFechaFin;

    @FXML
    private Label labelArchivo;

    @FXML
    private Label labelFechaInicio;
    private int idActividad;

    private Actividades actividad;
    private final Evaluacion evaluacion = new Evaluacion();
    private File selectedFile = null;
    private static Entrega entrega = new Entrega();

    @FXML
    void btnRegresarOnAction(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLConsultarActividades.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnRegresar.getScene().getWindow();
            stage.setTitle("Consultar Actividades");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }    }

    @FXML
    void btnEnviarOnAction(ActionEvent event) throws FileNotFoundException {
        if (entrega.getIdEntrega()!=0) {
            modificarActividad();
        }else {
            enviarActividad();
        }
    }

    private void enviarActividad() throws FileNotFoundException{
        if (selectedFile != null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String formattedDateTime = now.format(formatter);

            Entrega entrega = new Entrega();
            entrega.setArchivo(selectedFile);
            entrega.setFechaEntrega(formattedDateTime);
            entrega.setIdActividad(idActividad);
            entrega.setNombreArchivo(selectedFile.getName());
            EntregaDAO entregaDAO = new EntregaDAO();
            try {
                if (entregaDAO.entregarActividad(entrega)) {
                    Utilidades.mostrarDiallogoSimple("Exito", "La actividad ha sido subida con exito", Alert.AlertType.CONFIRMATION);
                }
            } catch (SQLException throwables) {
                if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error al subir la actividad", Alert.AlertType.ERROR);
                } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Erro de conexion con la base de datos", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarDiallogoSimple("Error", "No se ha seleccionado ningun archivo", Alert.AlertType.ERROR);
        }
    }

    private void modificarActividad()throws FileNotFoundException{
        if (selectedFile != null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String formattedDateTime = now.format(formatter);
            entrega.setArchivo(selectedFile);
            entrega.setFechaEntrega(formattedDateTime);
            entrega.setIdActividad(idActividad);
            entrega.setNombreArchivo(selectedFile.getName());
            EntregaDAO entregaDAO = new EntregaDAO();
            try {
                if (entregaDAO.modificarActividad(entrega)) {
                    Utilidades.mostrarDiallogoSimple("Actividad Modificada", "La actividad ha sido modificada con exito", Alert.AlertType.CONFIRMATION);
                }
            } catch (SQLException throwables) {
                if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error al subir la actividad", Alert.AlertType.ERROR);
                } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Erro de conexion con la base de datos", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utilidades.mostrarDiallogoSimple("Error", "No se ha seleccionado ningun archivo", Alert.AlertType.ERROR);
        }
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idActividad != 0) {
            configurarVentana();
            verificarEntrega();
        }
    }

    public void configurarVentana() {

        obtenerActividad();
        labelAlumno.setText(Singleton.getName());
        labelFechaInicio.setText(actividad.getFechaInicio());
        labelFechaFin.setText(actividad.getFechaFin());
        labelCalificacion.setText(evaluacion.getCalificacion());
        labelTitulo.setText(actividad.getTitulo());
        WebEngine webEngine = webViewDescripcion.getEngine();
        webEngine.loadContent(actividad.getDescripcion());
    }

    public void obtenerActividad() {
        ActividadesDAO actividadesDAO = new ActividadesDAO();
        try {
            actividad = actividadesDAO.getActividad(idActividad);

        } catch (SQLException throwables) {
            if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al consultar las actividades", Alert.AlertType.ERROR);
            } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            } else {
                Utilidades.mostrarDiallogoSimple("Error", "Error desconocido", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnAdjuntarArchivoOnAction(ActionEvent event) {
        Utilidades.mostrarDiallogoSimple("Adjuntar Archivo", "Adjuntar Archivo", Alert.AlertType.INFORMATION);
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            labelArchivo.setText(selectedFile.getName());
            entrega.setArchivo(selectedFile);
        } else {
            labelArchivo.setText("No se ha seleccionado ningún archivo");
            entrega.setArchivo(null);
        }
    }
    public void verificarEntrega() {
        EntregaDAO entregaDAO = new EntregaDAO();
        try {
            entrega =entregaDAO.verificarEntrega(idActividad);
            if (entrega.getIdEntrega()!=0) {
                labelArchivo.setText(entrega.getNombreArchivo());
                btnDescargarDocumento.setVisible(true);
                btnEnviar.setText("Guardar Cambios");
            }
        } catch (SQLException throwables) {
            if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al verificar la entrega", Alert.AlertType.ERROR);
            }else if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnDescargarDocumentoOnAction(ActionEvent event) throws IOException {
        EntregaDAO entregaDAO = new EntregaDAO();
        InputStream inputStream = entregaDAO.obtenerDocumento(idActividad);
        seleccionarRuta(inputStream);
    }

    private static void saveFile(InputStream inputStream, String filePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();
    }
    private static void seleccionarRuta(InputStream inputStream) throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(entrega.getNombreArchivo());

        fileChooser.setTitle("Guardar archivo");

        // Establecer filtros para limitar la selección a archivos PDF
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el diálogo de selección de archivo
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Obtener el nombre y la ruta del archivo seleccionado
            String fileName = file.getName();
            String filePath = file.getAbsolutePath();
            try{
                saveFile(inputStream, filePath);
            }catch (Exception e){
                Utilidades.mostrarDiallogoSimple("Error", "Error al guardar el archivo", Alert.AlertType.ERROR);
            }
            Utilidades.mostrarDiallogoSimple("Archivo", "Archivo guardado con exito", Alert.AlertType.CONFIRMATION);
        }
    }
}