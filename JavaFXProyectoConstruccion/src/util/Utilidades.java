package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import modelo.ConexionBD;

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
            Pattern patronCorreo = Pattern.compile("([a-z0-9A-Z]+(\\.?[a-z0-9A-Z])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static boolean correoValidoEstudiante(String correo){
        if(correo != null && !correo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("([zS]+(\\.?[a-z0-9A-Z])*)+@(([estudiantes]+)\\.([uv]+))\\.(([mx]))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    
    public static boolean correoValidoAcademico(String correo){
        if(correo != null && !correo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("([a-z0-9A-Z]+(\\.?[a-z0-9A-Z])*)+@(([uv]+))\\.(([mx]))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        }else{
            return false;
        }
    }
    

    public static boolean soloLetras(String campo){
        if(campo != null && !campo.isEmpty()){

            Pattern patronCorreo = Pattern.compile("(^[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]+$)");

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
    
    public static boolean matricula(String campo){
        if(campo != null && !campo.isEmpty()){
            Pattern patronCorreo = Pattern.compile("(([zS]+))+(([0-9]))+");
            Matcher matchPatron = patronCorreo.matcher(campo);
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
    
    public static String convertirFecha(String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    public static String obtenerNombreCompletoPorIdUsuario(int idUsuario) {
    String nombreCompleto = null;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    
    if (conexionBD != null) {
        try {
            String consulta = "SELECT CONCAT(nombre, ' ', apellidoPaterno, ' ', apellidoMaterno) AS nombreCompleto " +
                              "FROM usuario " +
                              "WHERE idUsuario = ?";
            
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idUsuario);
            
            ResultSet resultado = prepararSentencia.executeQuery();
            
            if (resultado.next()) {
                nombreCompleto = resultado.getString("nombreCompleto");
            }
            
            conexionBD.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return nombreCompleto;
    }
   
}