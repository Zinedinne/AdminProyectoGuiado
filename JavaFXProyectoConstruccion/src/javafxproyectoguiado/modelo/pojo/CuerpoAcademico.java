package javafxproyectoguiado.modelo.pojo;

public class CuerpoAcademico {
    private int idAcademia;
    private String nombreAcademia;
    private String academiaAsignada;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idAcademia, String nombreAcademia, String academiaAsignada) {
        this.idAcademia = idAcademia;
        this.nombreAcademia = nombreAcademia;
        this.academiaAsignada = academiaAsignada;
    }

    public int getIdAcademia() {
        return idAcademia;
    }

    public void setIdAcademia(int idAcademia) {
        this.idAcademia = idAcademia;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public String getAcademiaAsignada() {
        return academiaAsignada;
    }

    public void setAcademiaAsignada(String academiaAsignada) {
        this.academiaAsignada = academiaAsignada;
    }
    
    @Override
    public String toString(){
        return nombreAcademia;
    }
}
