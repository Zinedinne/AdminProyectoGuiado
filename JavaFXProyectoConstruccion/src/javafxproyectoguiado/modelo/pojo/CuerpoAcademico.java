package javafxproyectoguiado.modelo.pojo;

public class CuerpoAcademico {
    private int idAcademia;
    private String nombreAcademia;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idAcademia, String nombreAcademia) {
        this.idAcademia = idAcademia;
        this.nombreAcademia = nombreAcademia;
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
}
