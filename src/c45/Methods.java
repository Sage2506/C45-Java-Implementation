package c45;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Methods {

	public static double entrophy(List<Integer> atributeCount) {
			double result = 0;
			Integer total = 0;
			double temp = 0.0;
			
			for(int i : atributeCount) {
				total += i;
			}
			
			for(Integer val : atributeCount){
				temp = (-((double)val/total)*Math.log((double)val/total)/Math.log(2.0) );
				if(!Double.isNaN(temp))
					result+=temp;
			}
			return result;
	}

	public static void addNamesAndCount(List<String> names, List<Integer> count, String Value) {
		if(names.isEmpty()) {
			names.add(Value);
			count.add(1);
		}
		else if (!names.contains(Value)) {
			names.add(Value);
			count.add(1);
		}
		else {
			count.set(names.lastIndexOf(Value), 
					count.get(names.lastIndexOf(Value))+1);
		}
	}

	public static List<Value> createValues(List<String> Clases, List<String> Attributes){
		List<Value> resultado = new ArrayList<Value>();
		int n = Clases.size();
		for(int i = 1; i < n; i++) 
			resultado.add(new Value(Attributes.get(i),Clases.get(i)));
		return resultado;
	}
	
	public static List<List<String>> generateDatasetFromFile(String fileLocation)  throws IOException{
		List<List<String>> result = new ArrayList<>();
		Scanner scan = new Scanner(new File(fileLocation));
		
		String headerLine = scan.nextLine();
		String headers[]  = headerLine.split(",");
		for(int i = 0; i < headers.length; i++) {
			List<String> nueva = new ArrayList<String>();
			nueva.add(headers[i]);
			result.add(nueva);
		}
		
		while(scan.hasNextLine()) {
			String inLine = scan.nextLine();
			String values[] = inLine.split(",");
			for(int i = 0; i < values.length; i++)
				result.get(i).add(values[i]);
		}
		scan.close();
		return result;
	}

	public static void calculaC45(Table tabla){
		tabla.printTable();
		calcularSiguienteRaiz(tabla);
		
	}

	public static void calcularSiguienteRaiz(Table tabla){
		if(tabla.claseUnica()==null){
			double minorGain = 0.0;
			String minorGainName = "";
			int n = tabla.getNumberOfAttributes();
			minorGain = tabla.getAttributeEntrophy(0);
			minorGainName = tabla.getAttributeName(0);
			for ( int i = 1 ; i < n ; i++){
				if(tabla.getAttributeEntrophy(i)< minorGain){
					minorGain = tabla.getAttributeEntrophy(i);
					minorGainName = tabla.getAttributeName(i);
				}
			}
			List<Table> tablasResultado = tabla.subTableValue(minorGainName);
			for(Table tablaUnica: tablasResultado){
				System.out.println("Nodo: "+tablaUnica.root);
				System.out.println("Nombre del valor: "+tablaUnica.name);
				calcularSiguienteRaiz(tablaUnica);
			}
				
		}			
		else{			
			tabla.printTable();
		}
	}
}
