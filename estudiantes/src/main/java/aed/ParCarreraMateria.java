package aed;

public class ParCarreraMateria {
    String carrera;
    String nombreMateria;

    public ParCarreraMateria(String carrera, String nombreMateria) {
        this.carrera = carrera;
        this.nombreMateria = nombreMateria;
    }

    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public String getCarrera() {
        return this.carrera;
    }
}
//Invariante de representacion:
//1) "carrera" y "nombreMateria" deben ser no nulos y no vacíos. 