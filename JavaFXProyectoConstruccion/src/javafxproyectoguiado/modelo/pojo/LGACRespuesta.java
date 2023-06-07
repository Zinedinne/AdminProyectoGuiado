package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class LGACRespuesta {
    private int codigoRespuesta;
    private ArrayList<LGAC> lgacs;

    public LGACRespuesta() {
    }

    public LGACRespuesta(int codigoRespuesta, ArrayList<LGAC> lgacs) {
        this.codigoRespuesta = codigoRespuesta;
        this.lgacs = lgacs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<LGAC> getLgacs() {
        return lgacs;
    }

    public void setLgacs(ArrayList<LGAC> lgacs) {
        this.lgacs = lgacs;
    }
}
