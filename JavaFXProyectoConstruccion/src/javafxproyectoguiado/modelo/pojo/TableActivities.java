package javafxproyectoguiado.modelo.pojo;

import javafx.scene.control.Button;

public class TableActivities {
    private String titulo;
    private String descripcion;
    private String fechaInicio;
    private String fechaTermino;
    private Button btnConsultar;

    private int idActividad;
    private int codigoRespuesta;

    public TableActivities(String titulo, String descripcion, String fechaInicio, String fechaTermino, Button btnConsultar, int idActividad, int codigoRespuesta) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.btnConsultar = btnConsultar;
        this.idActividad = idActividad;
        this.codigoRespuesta = codigoRespuesta;
    }


    public TableActivities() {
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public Button getBtnConsultar() {
        return btnConsultar;
    }

    public void setBtnConsultar(Button btnConsultar) {
        this.btnConsultar = btnConsultar;
    }

    public  int setCodigoRespuesta(int codigoRespuesta){
        return this.codigoRespuesta = codigoRespuesta;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
}
