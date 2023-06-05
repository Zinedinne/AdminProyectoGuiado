package javafxproyectoguiado.modelo.pojo;

public class Evaluacion {
    private String calificacion;
    private String comentario;
    private int idActividad;
    private int idEvaluacion;

    public Evaluacion(String calificacion, String comentario, int idActividad, int idEvaluacion) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.idActividad = idActividad;
        this.idEvaluacion = idEvaluacion;
    }

    public Evaluacion() {

    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }
}
