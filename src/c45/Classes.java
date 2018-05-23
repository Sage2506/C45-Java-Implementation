package c45;

import java.util.ArrayList;
import java.util.List;

public class Classes {
	String name;
	String root;
	List<String> names;
	List<Integer> namesCount;
	List<String> values;

	public Classes(String name) {
		names = new ArrayList<String>();
		namesCount = new ArrayList<Integer>();
		this.name = name;
	}

	public void addValues(List<String> valores) {
		for(String val : valores) {
			Methods.addNamesAndCount(names, namesCount, val);
		}
		this.values = valores;
	}

	public List<String> names(){
		return names;
	}

	public Integer conteo(String name){
		return namesCount.get(names.lastIndexOf(name));
	}

	public Integer total() {
		int totalC = 0;
		for(Integer n : namesCount) {
			totalC += n;
		}
		return totalC;
	}

	public double clasEntrophy() {
		return Methods.entrophy(namesCount);
	}

	public List<String> values(){
		return this.values;
	}

	public int numRegistros(){
		return values.size();
	}

	public Classes getSubClase(List<Integer> positions){
		Classes newClass = new Classes(this.name);
		List<String> newValues = new ArrayList<String>();
		for(Integer position : positions)
			newValues.add(values.get(position));
		newClass.addValues(newValues);
		return newClass;
	}
}
