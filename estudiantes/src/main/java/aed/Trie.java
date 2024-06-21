package aed;

import java.util.ArrayList;

public class Trie<T> {
    private Nodo raiz;

    private static final int NUMERO_CARACTERES = 256; // Cantidad de caracteres en ASCII

    private class Nodo {
        T valor;
        ArrayList<Nodo> hijos;

        public Nodo() {
            hijos = new ArrayList<>(NUMERO_CARACTERES);
            for (int i = 0; i < NUMERO_CARACTERES; i++) {
                hijos.add(null);
            }
        }
    }

    public Trie() {
        raiz = new Nodo();
    }

    public void insertarClave(String clave, T valor) {
        Nodo actual = raiz;
        for (char c : clave.toCharArray()) {
            int index = (int) c; // Obtiene el valor ASCII del caracter
            if (actual.hijos.get(index) == null) {
                actual.hijos.set(index, new Nodo());
            }
            actual = actual.hijos.get(index);
        }
        actual.valor = valor;
    }

    public T buscar(String clave) {
        Nodo actual = raiz;
        for (char c : clave.toCharArray()) {
            int index = (int) c;
            actual = actual.hijos.get(index);
            if (actual == null) {
                return null;
            }
        }
        return actual.valor;
    }

    public void borrar(String clave) {
        delete(raiz, clave, 0);
    }

    private boolean delete(Nodo actual, String clave, int index) {
        if (index == clave.length()) {
            actual.valor = null; // Elimina el valor asociado a la clave
            return sonHijosNulos(actual);
        }

        char c = clave.charAt(index);
        int ascii = (int) c;

        Nodo nodo = actual.hijos.get(ascii);
        if (nodo == null) {
            return false;
        }

        boolean deberiaBorrar = delete(nodo, clave, index + 1);

        if (deberiaBorrar) {
            actual.hijos.set(ascii, null);
            return sonHijosNulos(actual);
        }

        return false;
    }

    private boolean sonHijosNulos(Nodo nodo) {
        for (Nodo hijo : nodo.hijos) {
            if (hijo != null) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> obtenerClaves() {
    ArrayList<String> claves = new ArrayList<>();
    obtenerClavesRecursivo(raiz, "", claves);
    return claves;
    }

    private void obtenerClavesRecursivo(Nodo actual, String prefijo, ArrayList<String> claves) {
        if (actual == null) return;
        if (actual.valor != null) {
            claves.add(prefijo);
        }
        for (int i = 0; i < NUMERO_CARACTERES; i++) {
            if (actual.hijos.get(i) != null) {
                obtenerClavesRecursivo(actual.hijos.get(i), prefijo + (char) i, claves);
            }
    }
    }
}
//Invariante de representacion:
//1) "raiz" debe ser no nulo.
//2) "raiz.hijos" debe ser una lista de tamaño "NUMERO_CARACTERES" (256), y cada entrada debe ser o bien nula o bien una instancia válida de "Nodo".
//3) Para cualquier "nodo" en el trie, "nodo.hijos" debe ser una lista de tamaño "NUMERO_CARACTERES" (256), y cada entrada debe ser o bien nula o bien una instancia válida de "Nodo".
//4) Cada clave insertada en el trie debe ser una cadena no nula y no vacía.
//5) La estructura del trie debe ser tal que todas las claves pueden ser derivadas correctamente de la raíz hasta el nodo correspondiente a la clave.