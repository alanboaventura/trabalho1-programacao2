package model;

import java.io.Serializable;

/**
 * Entidade que representa um animal.
 */
public class Animal implements Serializable {

    /**
     * Identificação numerica do animal.
     */
    private int identificador;

    public Animal(int identificador) {
        this.identificador = identificador;
    }

    public int getIdentificador() {
        return identificador;
    }
}
