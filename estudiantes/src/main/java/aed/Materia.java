package aed;

import java.util.ArrayList;

public class Materia {
    private ArrayList<Estudiante> alumnos;
    private int inscriptos;
    private ArrayList<ParNombres> nombresEquivalentes;
    private int[] cantidadDocentes; // Orden: 0 PROF, 1 JTP, 2 AY1, 3 AY2

    public Materia() {
        this.alumnos = new ArrayList<>();
        this.inscriptos = 0;
        this.nombresEquivalentes = new ArrayList<ParNombres>();
        this.cantidadDocentes = new int[4];
    }

    public class ParNombres{
        String nombre;
        Trie<Materia> carrera;
        public ParNombres(String nombre, Trie<Materia> carrera){
            this.nombre = nombre;
            this.carrera = carrera;
        }
    }
    
    public void añadirPar(String nombre, Trie<Materia> carrera){
        ParNombres par = new ParNombres(nombre, carrera);
        nombresEquivalentes.add(par);
    }
    
    public ArrayList<ParNombres> getParesNombres(){
        return this.nombresEquivalentes;
    }

    public void añadirInscripto(Estudiante alumno) {
        this.inscriptos++;
        this.alumnos.add(alumno);
    }
    public ArrayList<Estudiante> listaAlumnos(){
        return this.alumnos;
    }
    public int cantidadInscriptos(){
        return this.inscriptos;
    }

    public int[] cantidadDocentes() {
        return this.cantidadDocentes;
    }

    public void añadirPROF() {
        this.cantidadDocentes[0] = this.cantidadDocentes[0] + 1;
    }

    public void añadirJTP() {
        this.cantidadDocentes[1] = this.cantidadDocentes[1] + 1;
    }

    public void añadirAY1() {
        this.cantidadDocentes[2] = this.cantidadDocentes[2] + 1;
    }

    public void añadirAY2() {
        this.cantidadDocentes[3] = this.cantidadDocentes[3] + 1;
    }

    public int getCapacidadAlumnos() {
        int[] maxCapacidad = {cantidadDocentes[0]*250, cantidadDocentes[1]*100, cantidadDocentes[2]*20, cantidadDocentes[3]*30};
        int minimo = maxCapacidad[0];
        for(int docente : maxCapacidad){
            if(minimo > docente){
                minimo = docente;
            }
        }
        return minimo;
    }


}
//Invariante de representacion:
//1) "alumnos" debe ser no nulo y debe contener solo instancias válidas de "Estudiante".
//2) "inscriptos" debe ser igual al tamaño de la lista "alumnos".
//3) "nombresEquivalentes" debe ser no nulo y debe contener solo instancias válidas de "ParNombres".
//4) "cantidadDocentes" debe ser un arreglo de tamaño 4 y cada entrada debe ser no negativa.
