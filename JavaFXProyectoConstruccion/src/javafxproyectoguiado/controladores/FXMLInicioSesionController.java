/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxproyectoguiado.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxproyectoguiado.modelo.dao.SesionDAO;
import javafxproyectoguiado.modelo.pojo.Singleton;
import javafxproyectoguiado.modelo.pojo.Usuario;
import util.Constantes;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author Zinedinne
 */
public class FXMLInicioSesionController implements Initializable {

     @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContra;
    @FXML
    private Label lbErrorContra;
    @FXML
    private Label lbErrorUsuario;

    @FXML
    private Button ButtonIngresar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ClicIngresar(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorContra.setText("");
        validarCampos();
        
    }
    
    private void validarCampos(){
        String usuario = tfUsuario.getText();
        String password = tfContra.getText();
        boolean sonValidos = true;
        if(usuario.isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El campo usuario es requerido.");
        }
        if(password.length() == 0){
            sonValidos = false;
            lbErrorContra.setText("El campo contraseña es requerido.");
        }
        if(sonValidos){
            validarCredencialesUsuario(usuario, password);
        }
    }
    
    private void validarCredencialesUsuario(String usuario, String password){
        Usuario usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        switch(usuarioRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDiallogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, por favor inténtelo más tarde.", Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDiallogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación.", Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                if(usuarioRespuesta.getIdUsuario() > 0){
                    Utilidades.mostrarDiallogoSimple("Usuario verificado",
                            "Bienvenido(a) "+ Singleton.getName()+" al sistema...", Alert.AlertType.INFORMATION);
                    irPantallaPrincipal();
                }else{
                    Utilidades.mostrarDiallogoSimple("Credenciales incorrectas", 
                            "El usuario y/o contrsaeña no son correctos, por favor verifica la información", Alert.AlertType.WARNING);
                }
                break;
            default:
                    Utilidades.mostrarDiallogoSimple("Error de petición",
                            "El sistema no esta disponible por el momento", Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaPrincipal(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/javafxproyectoguiado/vistas/FXMLMainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.ButtonIngresar.getScene().getWindow();
            stage.setTitle("Menú principal");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }catch(IOException ioException){
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }  
}