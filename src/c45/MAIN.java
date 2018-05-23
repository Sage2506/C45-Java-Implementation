package c45;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MAIN {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<List<String>> dataset = new ArrayList<>();
		// Generar una lista de listas para guardar los valores de los atributos y la clase
		dataset = Methods.generateDatasetFromFile("datasets/outlooktestmsdos.csv");
		// Crear la tabla a partir del dataset
		Table table = new Table(dataset);
		// Comenzar algoritmo C45
		Methods.calculaC45(table);
	}
}
