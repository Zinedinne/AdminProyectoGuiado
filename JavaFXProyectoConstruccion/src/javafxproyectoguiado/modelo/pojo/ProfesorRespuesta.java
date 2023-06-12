package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class ProfesorRespuesta {
    private int codigoRespuesta;
    private ArrayList<Profesor> profesores;

    public ProfesorRespuesta() {
    }

    public ProfesorRespuesta(int codigoRespuesta, ArrayList<Profesor> profesores) {
        this.codigoRespuesta = codigoRespuesta;
        this.profesores = profesores;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<Profesor> profesores) {
        this.profesores = profesores;
    }

    
}
