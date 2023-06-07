package javafxproyectoguiado.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.ActividadesDAO;
import javafxproyectoguiado.modelo.dao.EntregaDAO;
import javafxproyectoguiado.modelo.dao.EvaluacionDAO;
import javafxproyectoguiado.modelo.pojo.Actividades;
import javafxproyectoguiado.modelo.pojo.Entrega;
import javafxproyectoguiado.modelo.pojo.Evaluacion;
import javafxproyectoguiado.modelo.pojo.Singleton;
import util.Constantes;
import util.Utilidades;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLGestionarActividadProfesor implements Initializable {

    @FXML
    private HTMLEditor htmlEditorDescripcion;
    @FXML
    private HTMLEditor htmlEditorRetroalimentacion;
    @FXML
    private Button btnCalificar;

    @FXML
    private Button btnRegresar;
    @FXML
    private ComboBox<String> comboBoxCalificacion;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelAlumno;

    @FXML
    private Label labelFechaInicio;

    @FXML
    private Label labelCalificacion;

    @FXML
    private Label labelFechaFin;

    @FXML
    private Label labelArchivo;

    @FXML
    private Button btnDescargarDocumento;
    private int idActividad;
    private String nombreAlumno;
    private Actividades actividad;
    private static Evaluacion evaluacion = new Evaluacion();
    private File selectedFile = null;
    private static Entrega entrega = new Entrega();
    @FXML
    private Label labelNota;
    private int idAlumno;

    @FXML
    void btnRegresarOnAction(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxproyectoguiado/vistas/FXMLConsultarActividades.fxml"));
        try {
            Parent root = loader.load();
            FXMLConsultarActividadesController controller = loader.getController();
            Utilidades.mostrarDiallogoSimple("Exito", idActividad + nombreAlumno, Alert.AlertType.INFORMATION);
            controller.setIdAlumno(idAlumno, nombreAlumno);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnShown(event2 -> {
                // Llamar a initialize después de que se muestre la ventana
                controller.initialize(null, null);
            });
            stage.show();
            ((Stage) this.btnRegresar.getScene().getWindow()).close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnCalificarOnAction(ActionEvent event) {
        Evaluacion nuevaEvaluacion = new Evaluacion();
        nuevaEvaluacion.setCalificacion(String.valueOf(comboBoxCalificacion.getValue()));
        nuevaEvaluacion.setComentario(htmlEditorRetroalimentacion.getHtmlText());
        if (evaluacion.getIdEvaluacion()!=0){
            nuevaEvaluacion.setIdEvaluacion(evaluacion.getIdEvaluacion());
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
            try {
                if(evaluacionDAO.modificarRevision(nuevaEvaluacion)){
                    Utilidades.mostrarDiallogoSimple("Exito", "Evaluacion actualizada", Alert.AlertType.INFORMATION);
                    verificarRevision();
                }
            } catch (SQLException throwables) {
                if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error al actualizar la evaluacion", Alert.AlertType.ERROR);
                } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarDiallogoSimple("Error", "Error desconocido", Alert.AlertType.ERROR);
                }
            }
        }else{
            nuevaEvaluacion.setIdActividad(idActividad);
            EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
            try {
                if (evaluacionDAO.revisarActividad(nuevaEvaluacion)) {
                    Utilidades.mostrarDiallogoSimple("Exito", "Evaluacion registrada", Alert.AlertType.INFORMATION);
                    verificarRevision();
                }
            } catch (SQLException throwables) {
                if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error al registrar la evaluacion", Alert.AlertType.ERROR);
                } else if (throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                    Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarDiallogoSimple("Error", "Error desconocido", Alert.AlertType.ERROR);
                }
            }
        }
    }
    @FXML
    void btnDescargarDocumentoOnAction(ActionEvent event) throws IOException {
        EntregaDAO entregaDAO = new EntregaDAO();
        InputStream inputStream = entregaDAO.obtenerDocumento(idActividad);
        seleccionarRuta(inputStream);
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


    public void setIdActividad(int idActividad, String nombre, int idAlumno) {
        this.idActividad = idActividad;
        this.nombreAlumno = nombre;
        this.idAlumno = idAlumno;
    }
    public void obtenerActividad() {
        ActividadesDAO actividadesDAO = new ActividadesDAO();
        try {
            actividad = actividadesDAO.getActividad(idActividad);
            htmlEditorDescripcion.setHtmlText(actividad.getDescripcion());
            labelTitulo.setText(actividad.getTitulo());

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (idActividad != 0) {
            configurarVentana();
            verificarEntrega();
        }
    }
    public void verificarEntrega() {
        EntregaDAO entregaDAO = new EntregaDAO();
        try {
            entrega =entregaDAO.verificarEntrega(idActividad);
            if (entrega.getIdEntrega()!=0) {
                labelArchivo.setText(entrega.getNombreArchivo());
                btnDescargarDocumento.setVisible(true);
            }
        } catch (SQLException throwables) {
            if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al verificar la entrega", Alert.AlertType.ERROR);
            }else if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
    }
    public void verificarRevision(){
        EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
        try {
            evaluacion =evaluacionDAO.verificarEvaluacion(idActividad);
            if (evaluacion.getIdEvaluacion()!=0) {
                btnCalificar.setText("Actualizar calificacion");
                htmlEditorRetroalimentacion.setHtmlText(evaluacion.getComentario());
                labelCalificacion.setText(evaluacion.getCalificacion());
                if (comboBoxCalificacion.getValue()==null){
                    btnCalificar.setDisable(true);
                }
            }
        } catch (SQLException throwables) {
            if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONSULTA))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error al verificar la revision", Alert.AlertType.ERROR);
            }else if(throwables.getMessage().equals(String.valueOf(Constantes.ERROR_CONEXION))) {
                Utilidades.mostrarDiallogoSimple("Error", "Error de conexion con la base de datos", Alert.AlertType.ERROR);
            }
        }
    }
    public void configurarVentana() {
        if (Singleton.getRol().equals("Profesor")){
            btnCalificar.setDisable(true);
            comboBoxCalificacion.setDisable(true);
            htmlEditorRetroalimentacion.setDisable(true);
        }else {
            for (int i = 1; i <= 10; i++) {
                comboBoxCalificacion.getItems().add(String.valueOf(i));
            }
            labelNota.setVisible(false);
        }
        obtenerActividad();
        verificarRevision();
        labelAlumno.setText(nombreAlumno);
        labelFechaInicio.setText(actividad.getFechaInicio());
        labelFechaFin.setText(actividad.getFechaFin());
        labelTitulo.setText(actividad.getTitulo());
    }

    @FXML
    void comboBoxCalificacionOnAction(ActionEvent event) {
        if (comboBoxCalificacion.getValue()!=null){
            btnCalificar.setDisable(false);
        }else{
            btnCalificar.setDisable(true);
        }
    }
}