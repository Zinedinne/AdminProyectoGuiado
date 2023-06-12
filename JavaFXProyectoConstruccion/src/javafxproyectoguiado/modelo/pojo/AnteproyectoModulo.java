/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxproyectoguiado.modelo.pojo;

/**
 *
 * @author Alvaro
 */
public class AnteproyectoModulo {
   private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String fechaInicio;
    private String duracion;
    private String modalidad;
    private String estado;
    private String descripcion;
    private int idLGAC;
    private String nombreLGAC;
    private int idUsuario;
    private String nombreCreador;
    private int idEncargadoDeTesis;
    private String nombreEncargadoDeTesis;
    private int idEstudianteAsignado;
    private String estudianteAsignado;

    public AnteproyectoModulo() {
    }

    public AnteproyectoModulo(int idAnteproyecto, String nombreAnteproyecto, String fechaInicio, String duracion, String modalidad, String estado, String descripcion, int idLGAC, String nombreLGAC, int idUsuario, String nombreCreador, int idEncargadoDeTesis, String nombreEncargadoDeTesis, int idEstudianteAsignado, String estudianteAsignado) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.modalidad = modalidad;
        this.estado = estado;
        this.descripcion = descripcion;
        this.idLGAC = idLGAC;
        this.nombreLGAC = nombreLGAC;
        this.idUsuario = idUsuario;
        this.nombreCreador = nombreCreador;
        this.idEncargadoDeTesis = idEncargadoDeTesis;
        this.nombreEncargadoDeTesis = nombreEncargadoDeTesis;
        this.idEstudianteAsignado = idEstudianteAsignado;
        this.estudianteAsignado = estudianteAsignado;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getNombreAnteproyecto() {
        return nombreAnteproyecto;
    }

    public void setNombreAnteproyecto(String nombreAnteproyecto) {
        this.nombreAnteproyecto = nombreAnteproyecto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public String getNombreLGAC() {
        return nombreLGAC;
    }

    public void setNombreLGAC(String nombreLGAC) {
        this.nombreLGAC = nombreLGAC;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public int getIdEncargadoDeTesis() {
        return idEncargadoDeTesis;
    }

    public void setIdEncargadoDeTesis(int idEncargadoDeTesis) {
        this.idEncargadoDeTesis = idEncargadoDeTesis;
    }

    public String getNombreEncargadoDeTesis() {
        return nombreEncargadoDeTesis;
    }

    public void setNombreEncargadoDeTesis(String nombreEncargadoDeTesis) {
        this.nombreEncargadoDeTesis = nombreEncargadoDeTesis;
    }

    public int getIdEstudianteAsignado() {
        return idEstudianteAsignado;
    }

    public void setIdEstudianteAsignado(int idEstudianteAsignado) {
        this.idEstudianteAsignado = idEstudianteAsignado;
    }

    public String getEstudianteAsignado() {
        return estudianteAsignado;
    }

    public void setEstudianteAsignado(String estudianteAsignado) {
        this.estudianteAsignado = estudianteAsignado;
    }

}
