package controller.database;

import model.Animal;
import model.Historico;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar os dados importados no sistema, os mantendo em memória durante a execução do sistema. Onde devemos consumir as listas aqui implementadas para acessar as informações
 * armazenadas.
 */
public class GerenciadorDosDadosImportados {

    /**
     * Animais importados.
     */
    private static List<Animal> animais = new ArrayList<>();

    /**
     * Históricos importados.
     */
    private static List<Historico> historicos = new ArrayList<>();

    public static List<Animal> getAnimais() {
        return animais;
    }

    public static List<Historico> getHistoricos() {
        return historicos;
    }

    public static void adicionarAnimal(Animal animal) {
        if (animal == null || animais.stream().anyMatch(a -> a.equals(animal))) return;
        animais.add(animal);
    }

    public static void adicionarHistorico(Historico historico) {
        if (historico == null || historicos.stream().anyMatch(h -> h.equals(historico))) return;
        historicos.add(historico);
    }
}
