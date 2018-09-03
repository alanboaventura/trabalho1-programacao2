package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Entidade que representa um histórico de um {@link Animal}.
 */
public class Historico implements Serializable {

    public Historico(Animal animal, Date data, double peso, short altura, double temperatura) {
        this.animal = animal;
        this.data = data;
        this.peso = peso;
        this.altura = altura;
        this.temperatura = temperatura;
    }

    /**
     * Animal que o histórico em questão remete.
     */
    private Animal animal;

    /**
     * Data do histórico.
     */
    private Date data;

    /**
     * Peso do animal na data do histórico.
     */
    private double peso;

    /**
     * Altura do animal na data do histórico.
     */
    private short altura;

    /**
     * Temperatura do animal na data do histórico.
     */
    private double temperatura;

    public Animal getAnimal() {
        return animal;
    }

    public Date getData() {
        return data;
    }

    public double getPeso() {
        return peso;
    }

    public short getAltura() {
        return altura;
    }

    public double getTemperatura() {
        return temperatura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Historico)) return false;
        Historico historico = (Historico) o;
        return Double.compare(historico.getPeso(), getPeso()) == 0 &&
                getAltura() == historico.getAltura() &&
                Double.compare(historico.getTemperatura(), getTemperatura()) == 0 &&
                Objects.equals(getAnimal(), historico.getAnimal()) &&
                Objects.equals(getData(), historico.getData());
    }
}
