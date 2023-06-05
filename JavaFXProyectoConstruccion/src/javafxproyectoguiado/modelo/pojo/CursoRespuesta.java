package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class CursoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Curso> cursos;

    public CursoRespuesta() {
    }

    public CursoRespuesta(int codigoRespuesta, ArrayList<Curso> cursos) {
        this.codigoRespuesta = codigoRespuesta;
        this.cursos = cursos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }
    
    
}
