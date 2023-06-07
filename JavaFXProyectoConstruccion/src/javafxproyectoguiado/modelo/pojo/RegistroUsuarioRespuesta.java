package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class RegistroUsuarioRespuesta {
    private int codigoRespuesta;
    private ArrayList<RegistroUsuario> usuarios;

    public RegistroUsuarioRespuesta() {
    }

    public RegistroUsuarioRespuesta(int codigoRespuesta, ArrayList<RegistroUsuario> usuarios) {
        this.codigoRespuesta = codigoRespuesta;
        this.usuarios = usuarios;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<RegistroUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<RegistroUsuario> usuarios) {
        this.usuarios = usuarios;
    }
}
