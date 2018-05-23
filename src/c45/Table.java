package c45;

import java.util.List;
import java.util.ArrayList;

public class Table {
	Classes clase;
	Attribute[] atributos;
	String root;
	String name;
	// Construtor de un tabla a partir de una clase, n atributos, y un string para el nodo raiz 
	public Table(Classes clase, Attribute[] atributos, String root) {
		this.clase = clase;
		this.atributos = atributos;
		this.root = root;
		this.name = "Raiz";
	}
	// Construtor de un tabla a partir de una clase, n atributos, un string para el nodo raiz 
	// y un string del valor dado 
	public Table(Classes clase, Attribute[] atributos, String root, String name) {
		this.clase = clase;
		this.atributos = atributos;
		this.root = root;
		this.name = name;
	}
	// Construtor de un tabla a partir de un dataset
	public Table(List<List<String>> dataset) {
		// Se crea la clase de la tabla
		Classes newClase = new Classes(dataset.get(dataset.size()-1).get(0));
		List<String> claseValues = new ArrayList<String>();
		for(int i = 1; i < dataset.get(dataset.size()-1).size(); i++) {
			claseValues.add(dataset.get(dataset.size()-1).get(i));
		}
		newClase.addValues(claseValues);
		
		Attribute[] attributes = new Attribute[dataset.size()-1];
		// Aqui se crea cada atributo y se guardan los valores
		for(int i = 0; i+1<dataset.size();i++)
		{
			for(int j = 0 ; j < dataset.get(0).size(); j ++) 
			{
				if(j == 0)
				{
					attributes[i] = new Attribute(dataset.get(i).get(j));
				}
				else
				{
					attributes[i].addValues(new Value(dataset.get(i).get(j),
																dataset.get(dataset.size()-1).get(j)));
				}
			}
		}
		this.root = "Raiz";
		this.name = "Raiz";
		clase = newClase;
		atributos = attributes;
	}
	// Obtencion de la entropia global
	public double globalEntrophy() {
		return Methods.entrophy(clase.namesCount);
	}
	// Nombre del atributo n de acuerdo a un numero
	public String getAttributeName(Integer n) {
		return atributos[n].getName();
	}
	// Obtencion de la entropia de un atributo elegido
	public double getAttributeEntrophy(Integer n) {
		return atributos[n].getAttributeEntrophy();
	}
	// Obtencion del numero de atributos de la tabla
	public int getNumberOfAttributes() {
		return atributos.length;
	}
	// position de cierto atributo si es que existe
	public int getAttributePosition(String atribute){
		int n = atributos.length;
		for(int i = 0 ; i < n ; i++){
			if(atributos[i].name.equals(atribute))
				return i;
		}
		return -1;
	}
	
	public void printTable(){
		System.out.println("Nodo: "+root);
		System.out.println("Nombre: "+name);
		for(Attribute a : atributos)
			System.out.printf("%-12s",a.name);
		System.out.printf("%-12s\n",clase.name);
		int n = atributos.length;
		List<List<Value>> valores = new ArrayList<>();
		for(int i  = 0 ; i < n ; i++)
			valores.add(atributos[i].getValues()); 
		n++;
		int r = clase.numRegistros();
		for( int k = 0 ; k < r ; k++){
			for(int j = 0 ; j < n ; j++){
				if(j!=n-1){
					System.out.printf("%-12s",valores.get(j).get(k).name);
				}
				else{
					System.out.printf("%-12s\n",clase.values().get(k));
				}
			}
		}
		System.out.println();
	}
	
	public List<Table> subTableValue(String attributeName){
		List<Table> tablas = new ArrayList<Table>();
		List<Attribute> newAttribs = new ArrayList<Attribute>();
		Classes newClase = new Classes(clase.name);
		int position = getAttributePosition(attributeName);
		List<String> valNames = atributos[position].getValuesNames();
		List<List<Integer>> positions = new ArrayList<List<Integer>>();
		Attribute[] tempAttribs;
		int n = 0;
		for(String valName : valNames)
			positions.add(atributos[position].getValuePositions(valName));
		
		for(List<Integer> positionGroup : positions){
			for(Attribute newAtribute : atributos){
				newAttribs.add(newAtribute.getSubAttribute(positionGroup));				
			}
			int c = newAttribs.size();
			tempAttribs = new Attribute[c];
			for(int i = 0 ; i < c ; i++){
				tempAttribs[i] = newAttribs.get(i);
			}
			newClase = clase.getSubClase(positionGroup);
			Table temp = new Table(newClase, tempAttribs, attributeName, valNames.get(n++));
			temp.removeAttrib(position);
			tablas.add(temp);
			newAttribs.clear();
		}
		
		return tablas;
	}
	
	public void removeAttrib(Integer position){
		int n = atributos.length;
		Attribute[] newAttributes = new Attribute [n-1];
		int m = 0;
		for(int i = 0 ; i < n ; i++){
			if(i!=position){
				newAttributes[m] = atributos[i];
				m++;
			}
		}
		this.atributos = newAttributes;	
	}
	
	public String claseUnica(){
		String resultado = null;
			if(clase.names.size()==1)
				return clase.names.get(0);
		return resultado;
	}

	public String getRoot(){
		return this.root; 
	}
	
	public void calcularSiguienteRaiz(Table tabla){
		if(tabla.claseUnica()!=null)
			tabla.printTable();
		else{
			//double globalGain = tabla.globalEntrophy();
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
			for(Table tablaUnica: tablasResultado)
				calcularSiguienteRaiz(tablaUnica);
		}
	}
}
