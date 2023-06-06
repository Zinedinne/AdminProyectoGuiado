package javafxproyectoguiado.modelo.pojo;

public class Curso {
    private int idCurso;
    private int bloque;
    private String nrc;
    private String seccion;
    private int idMateria;
    private String nombreMateria;
    private int idPeriodo;
    private String nombrePeriodo;

    public Curso() {
    }

    public Curso(int idCurso, int bloque, String nrc, String seccion, int idMateria, String nombreMateria, int idPeriodo, String nombrePeriodo) {
        this.idCurso = idCurso;
        this.bloque = bloque;
        this.nrc = nrc;
        this.seccion = seccion;
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    
    
}
