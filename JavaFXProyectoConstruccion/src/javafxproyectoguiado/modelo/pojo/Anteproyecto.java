package javafxproyectoguiado.modelo.pojo;

public class Anteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String descripcion;
    private String fechaInicio;
    private String modalidad;
    private String duracion;
    private String estado;
    private int idUsuario;
    private int idLGAC;

    public Anteproyecto(int idAnteproyecto, String nombreAnteproyecto, String descripcion, String fechaInicio, String modalidad, String duracion, String estado, int idUsuario, int idLGAC) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.modalidad = modalidad;
        this.duracion = duracion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idLGAC = idLGAC;
    }
    public Anteproyecto(){
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }
}
