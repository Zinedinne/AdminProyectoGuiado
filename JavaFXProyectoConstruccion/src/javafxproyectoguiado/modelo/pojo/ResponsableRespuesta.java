package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class ResponsableRespuesta {
    private int codigoRespuesta;
    private ArrayList<Responsable> responsables;

    public ResponsableRespuesta() {
    }

    public ResponsableRespuesta(int codigoRespuesta, ArrayList<Responsable> responsables) {
        this.codigoRespuesta = codigoRespuesta;
        this.responsables = responsables;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(ArrayList<Responsable> responsables) {
        this.responsables = responsables;
    }
    
    
}
