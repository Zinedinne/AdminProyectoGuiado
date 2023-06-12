package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

public class EstudianteRespuesta {
    private int codigoRespuesta;
    private ArrayList<Estudiante> estudiantes;

    public EstudianteRespuesta() {
    }

    public EstudianteRespuesta(int codigoRespuesta, ArrayList<Estudiante> estudiantes) {
        this.codigoRespuesta = codigoRespuesta;
        this.estudiantes = estudiantes;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    
}
