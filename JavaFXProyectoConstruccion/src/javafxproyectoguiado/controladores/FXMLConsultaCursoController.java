package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafxproyectoguiado.modelo.dao.MateriaDAO;
import javafxproyectoguiado.modelo.dao.PeriodoDAO;
import javafxproyectoguiado.modelo.pojo.Curso;
import javafxproyectoguiado.modelo.pojo.Materia;
import javafxproyectoguiado.modelo.pojo.MateriaRespuesta;
import javafxproyectoguiado.modelo.pojo.Periodo;
import javafxproyectoguiado.modelo.pojo.PeriodoRespuesta;
import util.Constantes;
import util.Utilidades;

public class FXMLConsultaCursoController implements Initializable {

    @FXML
    private ComboBox<Periodo> cbPeriodo;
    private ObservableList<Periodo> periodos;
    @FXML
    private ComboBox<String> cbSeccion;
    private ObservableList<String> secciones;
    @FXML
    private ComboBox<Materia> cbMateria;
    private ObservableList<Materia> materias;
    @FXML
    private TextField tfNRC;
    @FXML
    private TextField tfBloque;
    @FXML
    private Label lbTitulo;
    private Curso cursoConsulta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionMateria();
        cargarInformacionPeriodo();
        cargarInformacionSeccion();
    } 
    
    public void inicializarInformacionConsulta(Curso cursoConsulta){
        this.cursoConsulta = cursoConsulta;
        
        lbTitulo.setText("Consultar información del Curso");
        cargarInformacionConsulta();
    }
    
    private void cargarInformacionConsulta(){
        tfBloque.setText(String.valueOf(cursoConsulta.getBloque()));
        tfBloque.setEditable(false);
        tfNRC.setText(cursoConsulta.getNrc());
        tfNRC.setEditable(false);
        String seccion = cursoConsulta.getSeccion();
        cbSeccion.getSelectionModel().select(seccion);
        cbSeccion.setDisable(true);
        cbSeccion.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
        int posicionMateria = obtenerPosicionComboMateria(cursoConsulta.getIdMateria());
        cbMateria.getSelectionModel().select(posicionMateria);
        cbMateria.setDisable(true);
        cbMateria.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
        int posicionPeriodo = obtenerPosicionComboPeriodo(cursoConsulta.getIdPeriodo());
        cbPeriodo.getSelectionModel().select(posicionPeriodo); 
        cbPeriodo.setDisable(true);
        cbPeriodo.setStyle("-fx-opacity: 1; -fx-background-color: #ffffff;");
    }
    
    private int obtenerPosicionComboMateria(int idMateria){
        for(int i = 0; i < materias.size(); i++){
            if(materias.get(i).getIdMateria() == idMateria)
                return i;
        }
        return 0;
    }
    
    private int obtenerPosicionComboPeriodo(int idPeriodo){
        for(int i = 0; i < periodos.size(); i++){
            if(periodos.get(i).getIdPeriod() == idPeriodo)
                return i;
        }
        return 0;
    }
    
    private void cargarInformacionSeccion(){
        secciones = FXCollections.observableArrayList("Matutino","Vespertino");
        cbSeccion.setItems(secciones);
    }
    
    private void cargarInformacionMateria(){
        materias = FXCollections.observableArrayList();
        MateriaRespuesta materiasBD = MateriaDAO.obtenerInformacionMateria();
        switch(materiasBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de Conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de Consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    materias.addAll(materiasBD.getMaterias());
                    cbMateria.setItems(materias);
                break;
        }
    }
    
    private void cargarInformacionPeriodo(){
        periodos = FXCollections.observableArrayList();
        PeriodoRespuesta periodosBD = PeriodoDAO.obtenerInformacionPeriodo();
        switch(periodosBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDiallogoSimple("Error de Conexión", 
                            "Error de conexion con la base de datos.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDiallogoSimple("Error de Consulta", 
                            "Por el momento no se puede obtener la información.", Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    periodos.addAll(periodosBD.getPeriodos());
                    cbPeriodo.setItems(periodos);
                break;
        }
    }
    
}
