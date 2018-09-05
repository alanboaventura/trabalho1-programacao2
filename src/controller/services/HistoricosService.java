package controller.services;

import controller.database.GerenciadorDosDadosImportados;
import model.Animal;
import model.Historico;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviços referentes a entidade {@link Historico}.
 */
public class HistoricosService {

    /**
     * Retorna os históricos do {@link Animal} informado via parâmetro.
     *
     * @param animal {@link Animal} que deve ter seus históricos retornados.
     * @return Uma lista contendo os {@link Historico} referentes ao animal informado.
     */
    public static List<Historico> encontrarHistoricosDeAnimal(Animal animal) {
        return GerenciadorDosDadosImportados.getHistoricos().stream().filter(h -> h.getAnimal().getIdentificador() == animal.getIdentificador()).collect(Collectors.toList());
    }
}
