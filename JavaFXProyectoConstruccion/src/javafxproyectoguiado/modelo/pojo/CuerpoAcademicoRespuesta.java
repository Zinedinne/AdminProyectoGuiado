package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademicoRespuesta {
    private int codigoRespuesta;
    private ArrayList<CuerpoAcademico> academias;

    public CuerpoAcademicoRespuesta() {
    }

    public CuerpoAcademicoRespuesta(int codigoRespuesta, ArrayList<CuerpoAcademico> academias) {
        this.codigoRespuesta = codigoRespuesta;
        this.academias = academias;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<CuerpoAcademico> getAcademias() {
        return academias;
    }

    public void setAcademias(ArrayList<CuerpoAcademico> academias) {
        this.academias = academias;
    }
}
