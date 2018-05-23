package c45;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MAIN {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<List<String>> dataset = new ArrayList<>();
		dataset = Methods.generateDatasetFromFile("datasets/outlooktestmsdos.csv");
		Table table = new Table(dataset);
		Methods.calculaC45(table);
	}
}
