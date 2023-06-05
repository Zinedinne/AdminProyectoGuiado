/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxproyectoguiado.modelo.pojo;

/**
 *
 * @author Zinedinne
 */
public class Singleton {
    private static Singleton login;
    private Usuario user;

    private Singleton(Usuario user) {
        this.user = user;
    }

    public static void setLogin(Usuario user) {

            login  = new Singleton(user);

    }

    public static String getName(){
        return login.user.getNombre();
    }
    
    public static String getUserName(){
        return login.user.getUsername();
    }

    public static int getId(){
        return login.user.getIdUsuario();
    }

    public static  String getRol(){ return  login.user.getTipoUsuario();}
    
}
