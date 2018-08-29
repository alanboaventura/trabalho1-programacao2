package model;

/**
 * Classe que representa um animal.
 */
public class Animal {

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

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
}
