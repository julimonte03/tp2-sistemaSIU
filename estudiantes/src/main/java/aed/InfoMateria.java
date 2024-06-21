package aed;

public class InfoMateria{

    private ParCarreraMateria[] paresCarreraMateria;

    public InfoMateria(ParCarreraMateria[] paresCarreraMateria){
        this.paresCarreraMateria = paresCarreraMateria;
    }

    public ParCarreraMateria[] getParesCarreraMateria() {
        return this.paresCarreraMateria;
    }
}
//Invariante de representacion:
//1) "paresCarreraMateria" debe ser no nulo y debe contener solo instancias válidas de "ParCarreraMateria".