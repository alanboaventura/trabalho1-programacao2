package model;

import java.util.Date;

/**
 * Classe que representa um histórico de um {@link Animal}.
 */
public class Historico {

    public Historico(Date data, double peso, short altura, double temperatura) {
        this.data = data;
        this.peso = peso;
        this.altura = altura;
        this.temperatura = temperatura;
    }

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public short getAltura() {
        return altura;
    }

    public void setAltura(short altura) {
        this.altura = altura;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
}
