package controller.services;

import controller.database.GerenciadorDosDadosImportados;
import model.Animal;

/**
 * Serviços referentes a entidade {@link Animal}.
 */
public class AnimalService {

    /**
     * Encontra um animal com base em seu código identificador.
     *
     * @param identificador Identificação do animal que está sendo buscado.
     * @return O {@link Animal} que possuí o código identificador informado.
     */
    public static Animal encontrarAnimal(int identificador) {
        Animal animalEncontrado = null;

        for (Animal animal : GerenciadorDosDadosImportados.getAnimais()) {
            if (animal.getIdentificador() == identificador) animalEncontrado = animal;
        }
        if (animalEncontrado == null) {
            animalEncontrado = new Animal(identificador);
            GerenciadorDosDadosImportados.adicionarAnimal(animalEncontrado);
        }

        return animalEncontrado;
    }
}
