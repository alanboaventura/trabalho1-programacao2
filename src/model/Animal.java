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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Animal)) return false;
        Animal animal = (Animal) obj;
        return getIdentificador() == animal.getIdentificador();
    }
}
