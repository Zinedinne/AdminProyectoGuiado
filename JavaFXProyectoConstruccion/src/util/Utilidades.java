/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafxproyectoconstruccion.JavaFXProyectoConstruccion;

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
}
