package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class DirectorTesisRespuesta {
    private int codigoRespuesta;
    private ArrayList<DirectorTesis> directores;

    public DirectorTesisRespuesta() {
    }

    public DirectorTesisRespuesta(int codigoRespuesta, ArrayList<DirectorTesis> directores) {
        this.codigoRespuesta = codigoRespuesta;
        this.directores = directores;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<DirectorTesis> getDirectores() {
        return directores;
    }

    public void setDirectores(ArrayList<DirectorTesis> directores) {
        this.directores = directores;
    }
    
    
}
