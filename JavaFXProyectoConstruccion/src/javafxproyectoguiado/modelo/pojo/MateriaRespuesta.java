package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class MateriaRespuesta {
    private int codigoRespuesta;
    private ArrayList<Materia> materias;

    public MateriaRespuesta() {
    }

    public MateriaRespuesta(int codigoRespuesta, ArrayList<Materia> materias) {
        this.codigoRespuesta = codigoRespuesta;
        this.materias = materias;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }
    
    
}
