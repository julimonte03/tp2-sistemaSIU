package aed;

public class Estudiante {
    private int cantidadMaterias;

    public Estudiante() {
        this.cantidadMaterias = 0;
    }
    public int getCantidadMaterias(){
        return this.cantidadMaterias;
    }
    public void inscribirMateria(){
        cantidadMaterias++;
    }
    public void borrarMateria(){
        cantidadMaterias--;
    }
}
//Invariante de representacion:
//1) "cantidadMaterias" debe ser no negativo y debe reflejar correctamente el número de materias en las que el estudiante está inscripto.