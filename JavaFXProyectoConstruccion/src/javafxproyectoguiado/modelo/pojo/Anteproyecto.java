package javafxproyectoguiado.modelo.pojo;

public class Anteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String descripcion;
    private String fechaInicio;
    private String modalidad;
    private String duracion;
    private boolean estado;
    private int idUsuario;
    private int idLGAC;
    private int idAnteproyecto_idAnteproyecto;
    private int idEncargadoDeTesis;

    public Anteproyecto(int idAnteproyecto, String nombreAnteproyecto, String descripcion, String fechaInicio, String modalidad, String duracion, boolean estado, int idUsuario, int idLGAC, int idAnteproyecto_idAnteproyecto, int idEncargadoDeTesis) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.modalidad = modalidad;
        this.duracion = duracion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idLGAC = idLGAC;
        this.idAnteproyecto_idAnteproyecto = idAnteproyecto_idAnteproyecto;
        this.idEncargadoDeTesis = idEncargadoDeTesis;
    }

    public Anteproyecto(){
    }

    public boolean isEstado() {
        return estado;
    }

    public int getIdAnteproyecto_idAnteproyecto() {
        return idAnteproyecto_idAnteproyecto;
    }

    public void setIdAnteproyecto_idAnteproyecto(int idAnteproyecto_idAnteproyecto) {
        this.idAnteproyecto_idAnteproyecto = idAnteproyecto_idAnteproyecto;
    }

    public int getIdEncargadoDeTesis() {
        return idEncargadoDeTesis;
    }

    public void setIdEncargadoDeTesis(int idEncargadoDeTesis) {
        this.idEncargadoDeTesis = idEncargadoDeTesis;
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

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
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

    @Override
    public String toString() {
        return nombreAnteproyecto;
    }
}
