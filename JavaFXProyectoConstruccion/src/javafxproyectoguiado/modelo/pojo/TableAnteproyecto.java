package javafxproyectoguiado.modelo.pojo;

public class TableAnteproyecto {
    private int idAnteproyecto;
    private String nombreAnteproyecto;
    private String descripcion;
    private String fechaInicio;
    private String modalidad;
    private String duracion;
    private boolean estado;
    private String usuario;
    private String LGAC;
    private int idUsuario;

    public TableAnteproyecto(int idAnteproyecto, String nombreAnteproyecto, String descripcion, String fechaInicio, String modalidad, String duracion, boolean estado, String usuario, String LGAC, int idUsuario) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreAnteproyecto = nombreAnteproyecto;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.modalidad = modalidad;
        this.duracion = duracion;
        this.estado = estado;
        this.usuario = usuario;
        this.LGAC = LGAC;
        this.idUsuario = idUsuario;
    }

    public TableAnteproyecto() {

    }

    public boolean isEstado() {
        return estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLGAC() {
        return LGAC;
    }

    public void setLGAC(String LGAC) {
        this.LGAC = LGAC;
    }
}
