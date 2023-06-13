/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;
import javafxproyectoguiado.modelo.pojo.AnteproyectoModulo;

/**
 *
 * @author Zinedinne
 */
public class Utilidades {

    public static void mostrarDiallogoSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();    
    }
    
    public static Scene inicializarEscena(String ruta){
        Scene escena = null;
        try {
            Parent vista = FXMLLoader.load(JavaFXProyectoConstruccion.class.getResource(ruta));
            escena = new Scene(vista);
           }catch(IOException ex){
            System.err.println("ERROR: "+ex.getMessage());
        }
        return escena;
    }
    
    public static boolean correoValido(String correo){
        if(correo != null && !correo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static void asignarTextoEstado(TableColumn<AnteproyectoModulo, String> colEstado) {
    colEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AnteproyectoModulo, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<AnteproyectoModulo, String> data) {
            String estado = data.getValue().getEstado();
            String textoEstado; 
            switch (estado) {
                case "0":
                    textoEstado = "Postulado";
                    break;
                case "1":
                    textoEstado = "Validado";
                    break;
                case "2":
                    textoEstado = "Denegado";
                    break;
                default:
                    textoEstado = "Desconocido";
                    break;
            }
            return new SimpleStringProperty(textoEstado); 
        }});
    }
    
    public static void asignarTextoLabelEstado(Label label, String estado) {
        String textoEstado;
        switch (estado) {
            case "0":
                textoEstado = "Postulado";
                break;
            case "1":
                textoEstado = "Validado";
                break;
            case "2":
                    textoEstado = "Denegado";
                    break;
            default:
                textoEstado = "Desconocido";
                break;
        }
        label.setText(textoEstado);
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        Optional<ButtonType> botonClic = alertaConfirmacion.showAndWait();
        return (botonClic.get() == ButtonType.OK);
    }
}
