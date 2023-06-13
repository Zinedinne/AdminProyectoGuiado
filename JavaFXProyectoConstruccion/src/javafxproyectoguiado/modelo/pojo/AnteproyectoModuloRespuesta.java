package javafxproyectoguiado.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class AnteproyectoModuloRespuesta {
    private int codigoRespuesta;
    private ArrayList<AnteproyectoModulo> anteproyectosModulo;

    public AnteproyectoModuloRespuesta() {
    }

    public AnteproyectoModuloRespuesta(int codigoRespuesta, ArrayList<AnteproyectoModulo> anteproyectosModulo) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectosModulo = anteproyectosModulo;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<AnteproyectoModulo> getAnteproyectosModulo() {
        return anteproyectosModulo;
    }

    public void setAnteproyectosModulo(ArrayList<AnteproyectoModulo> anteproyectosModulo) {
        this.anteproyectosModulo = anteproyectosModulo;
    }

    
    
    
}
