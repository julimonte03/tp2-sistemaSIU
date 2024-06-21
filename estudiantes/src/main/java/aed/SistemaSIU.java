package aed;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import aed.Materia.ParNombres;

public class SistemaSIU {
    private Trie<Trie<Materia>> carreras;
    private Trie<Estudiante> estudiantes;
    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        carreras = new Trie<>(); //O(1)
        estudiantes = new Trie<>(); //O(1)

        for (InfoMateria listaDePares : infoMaterias) { //O(|infoMaterias|)
            Materia materia = new Materia();//O(1)
            for (ParCarreraMateria par : listaDePares.getParesCarreraMateria()) { //O(|listaDePares|)
                Trie<Materia> materias = carreras.buscar(par.getCarrera());//O(|c|)
                if (materias == null) { //O(1)
                    materias = new Trie<>(); //O(1)
                    carreras.insertarClave(par.getCarrera(), materias);//O(|c|)
                }
                    materias.insertarClave(par.getNombreMateria(), materia); //O(|m|)
                    materia.añadirPar(par.getNombreMateria(), materias);//O(|m|)
            }
        }

        for (String estudiante : libretasUniversitarias) { //O(1) estudiante es acotado
            estudiantes.insertarClave(estudiante, new Estudiante()); //O(1)
        }
    }
    //Para todo c en carreras: acceder a cada infoMateria  cuesta O(|c|) por cada materia con distinto nombre de esa materia = O(|c|. |m|). Cumple con la primera parte. Dado que hay E estudiantes y es un término constante que representa la cantidad total de estudiantes y hay que inscribirlos en n nombres distintos de la materia, es decir el ultimo bucle depende de la cantidad de nombres distintos de la materia -> O(|n| + E) es la complejidad para cada n en nombre de materias. Cumple la segunda parte.

    public void inscribir(String estudiante, String carrera, String materia) {
        Materia materiaInscribir = carreras.buscar(carrera).buscar(materia); //O(|c| + |m|)
        Estudiante estudianteInscribir = estudiantes.buscar(estudiante); //O(|n|)
        estudianteInscribir.inscribirMateria(); //O(|1|)
        materiaInscribir.añadirInscripto(estudianteInscribir); //O(|1|)
    }
    //complejidad total : O(|c| + |m| ) + O(|1|) = O(|c| + |m|)

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        Trie<Materia> materias = carreras.buscar(carrera);//O(|c|)
        if (materias == null) { //O(|1|)
            throw new NoSuchElementException("Carrera no encontrada: " + carrera);
        }
    
        Materia materiaAgregar = materias.buscar(materia);//O(|m|)
        if (materiaAgregar == null) {//O(|1|)
            throw new NoSuchElementException("Materia no encontrada: " + materia);
        }
    
        switch (cargo) {//O(|1|)
            case PROF:
                materiaAgregar.añadirPROF();
                break;
            case JTP:
                materiaAgregar.añadirJTP();
                break;
            case AY1:
                materiaAgregar.añadirAY1();
                break;
            case AY2:
                materiaAgregar.añadirAY2();
                break;
            default:
                throw new NoSuchElementException("No se encontró el cargo");
        }
    }
    //complejidad total : O(|c| + |m| ) + O(|1|) = O(|c| + |m|)

    public int[] plantelDocente(String materia, String carrera) {
        Materia materiaDocentes = carreras.buscar(carrera).buscar(materia); //O(|c| + |m|)
        if (materiaDocentes == null) {//O(|1|)
            throw new NoSuchElementException("Materia no encontrada: " + materia);
        }
        return materiaDocentes.cantidadDocentes();
    }
    //complejidad total : O(|c| + |m| )

    public void cerrarMateria(String materia, String carrera){
        Trie<Materia> materias = carreras.buscar(carrera);//O(|c|)
        Materia materiaObj = materias.buscar(materia);//O(|m|)
        materias.borrar(materia);//O(|m|)
        for(ParNombres par : materiaObj.getParesNombres()){ //O(k) donde k es el numero de pares 
            String nombre = par.nombre; //O(1)
            materias = par.carrera; //O(1)
            materias.borrar(nombre);//O(|m|)
        }
        ArrayList<Estudiante> listaAlumnos = materiaObj.listaAlumnos(); //O(E) cantidad de estudiantes 
        for(Estudiante alumno : listaAlumnos){ //O(E)
            alumno.borrarMateria();
        }
    }
    //luego cumple la complejidad porque tengo O(|c| + |m| + k|n| + E) donde k|n| es la sumatoria de todos los nombres de materias distintos. en el codigo:
    // for(ParNombres par : materiaObj.getParesNombres()){ O(k) donde k es el numero de pares 
    //materias.borrar(nombre); O(|m|)


    public int inscriptos(String materia, String carrera){
        Trie<Materia> materias = carreras.buscar(carrera); //O(|c|)
        if (materias == null) {//O(1)
            return 0; // La carrera no existe
        }
    
        Materia materia2 = materias.buscar(materia);//O(|m|)
        if (materia2 == null) {//O(1)
            return 0; // La materia no existe en la carrera dada
        }
    
        int inscriptos = materia2.cantidadInscriptos();//O(1)
        return inscriptos;
    }
    //complejidad total : O(|c| + |m| )

    public boolean excedeCupo(String materia, String carrera){
        Trie<Materia> materias = carreras.buscar(carrera); //O(|c|)
        Materia materia2 = materias.buscar(materia);//O(|m|)
        int capacidadAlumnos = materia2.getCapacidadAlumnos();//O(1)
        int cantidadAlumnos = materia2.cantidadInscriptos();//O(1)
    
        if(capacidadAlumnos >= cantidadAlumnos){//O(1)
                return false;
        }else{
                return true;
        }
    
        }
    //complejidad total : O(|c| + |m| )

    public String[] carreras(){

        ArrayList<String> listaCarreras = carreras.obtenerClaves();//O(|c|) por cada carrera
        return listaCarreras.toArray(new String[0]);
    }

    public String[] materias(String carrera) {
        Trie<Materia> materias = carreras.buscar(carrera);//O(|c|)
        if (materias == null) { //O(1)
            return new String[0];
        }

        ArrayList<String> listaMaterias = materias.obtenerClaves(); //O(|m|) por cada materia
        return listaMaterias.toArray(new String[0]);
    }


    public int materiasInscriptas(String estudiante) { //O(1) porque estudiante es acotado y en un trie cuesta O(1)
        Estudiante e = estudiantes.buscar(estudiante); 
        return e.getCantidadMaterias();
        }

}
//Invariante de representacion:
//1) "carreras" y "estudiantes" deben ser no nulos después de la inicialización.
//2) Cada carrera en "Trie<Trie<Materia>> carreras" debe ser una instancia válida de "Trie<Materia>".
//3) Cada estudiante en "Trie<Estudiante> estudiantes" debe ser una instancia válida de "Estudiante".
//4) Todos los elementos en "carreras" deben ser cadenas no nulas y no vacías.
//5) Todos los elementos en "estudiantes" deben ser cadenas no nulas y no vacías.
