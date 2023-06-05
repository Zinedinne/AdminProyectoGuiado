package javafxproyectoguiado.modelo.pojo;

import java.io.File;
import java.io.FileInputStream;

public class Entrega {
    private int idEntrega;
    private String fechaEntrega;
    private File archivo;
    private String nombreArchivo;
    private int idActividad;

    public Entrega(int idEntrega, String fechaEntrega, File archivo, String nombreArchivo, int idActividad) {
        this.idEntrega = idEntrega;
        this.fechaEntrega = fechaEntrega;
        this.archivo = archivo;
        this.nombreArchivo = nombreArchivo;
        this.idActividad = idActividad;
    }

    public Entrega() {

    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
}
