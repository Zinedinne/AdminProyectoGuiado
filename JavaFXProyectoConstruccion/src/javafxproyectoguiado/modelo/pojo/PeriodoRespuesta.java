package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class PeriodoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Periodo> periodos;

    public PeriodoRespuesta() {
    }

    public PeriodoRespuesta(int codigoRespuesta, ArrayList<Periodo> periodos) {
        this.codigoRespuesta = codigoRespuesta;
        this.periodos = periodos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(ArrayList<Periodo> periodos) {
        this.periodos = periodos;
    }
}
