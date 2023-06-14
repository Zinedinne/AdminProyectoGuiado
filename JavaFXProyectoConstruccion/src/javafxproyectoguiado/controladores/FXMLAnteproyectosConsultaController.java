/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxproyectoguiado.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Alvaro
 */
public class FXMLAnteproyectosConsultaController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbNombreAnteproyecto;
    @FXML
    private Label lbModalidadTrabajo;
    @FXML
    private Label lbFechainicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbDirector;
    @FXML
    private Label lbEncargado;
    @FXML
    private Label lbEstadoAnteproyecto;
    @FXML
    private Label lbLGAC;
    @FXML
    private Label lbAlumnoAsignado;
    
    private AnteproyectoModulo anteproyectoConsulta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarInformacionConsulta(AnteproyectoModulo anteproyectoConsulta){
        this.anteproyectoConsulta = anteproyectoConsulta;
        
        lbTitulo.setText("Consultar información del anteproyecto: "+anteproyectoConsulta.getNombreAnteproyecto());
        cargarInformacionConsulta();
    }
    
    public void cargarInformacionConsulta(){
        lbNombreAnteproyecto.setText(anteproyectoConsulta.getNombreAnteproyecto());
        lbLGAC.setText(anteproyectoConsulta.getNombreLGAC());
        lbModalidadTrabajo.setText(anteproyectoConsulta.getModalidad());
        lbFechainicio.setText(anteproyectoConsulta.getFechaInicio());
        lbFechaFin.setText(anteproyectoConsulta.getDuracion());
        lbAlumnoAsignado.setText(anteproyectoConsulta.getEstudianteAsignado());
        lbDescripcion.setText(anteproyectoConsulta.getDescripcion());
        lbDirector.setText(anteproyectoConsulta.getNombreEncargadoDeTesis());
        lbEncargado.setText(anteproyectoConsulta.getNombreCreador());
        lbEstadoAnteproyecto.setText(anteproyectoConsulta.getEstado());
        Utilidades.asignarTextoLabelEstado(lbEstadoAnteproyecto, anteproyectoConsulta.getEstado());
    }

    @FXML
    private void clicCerrarVentana(MouseEvent event) {
        Stage escearioPrincipal = (Stage) lbNombreAnteproyecto.getScene().getWindow();
        escearioPrincipal.close();
    }
    
}
