/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    public static boolean correoValido(String correo){
        if(correo != null && !correo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static boolean soloLetras(String campo){
        if(campo != null && !campo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("(^[a-zA-Z]+$)");
            Matcher matchPatron = patronCorreo.matcher(campo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static boolean soloNumeros(String campo){
        if(campo != null && !campo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("(^[0-9]+$)");
            Matcher matchPatron = patronCorreo.matcher(campo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
}
