package javafxproyectoguiado.modelo.pojo;

public class LGAC {
    private int idLGAC;
    private String nombre;

    public LGAC() {
    }

    public LGAC(int idLGAC, String nombre) {
        this.idLGAC = idLGAC;
        this.nombre = nombre;
    }
    
    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
