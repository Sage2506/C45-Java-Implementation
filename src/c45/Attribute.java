package c45;

import java.util.List;
import java.util.ArrayList;

public class Attribute {
	String name;
	List<String> nameValues;
	List<Integer> countValues;
	List<Values> values;
	
	public Attribute (String name) {
		this.name = name;
		nameValues = new ArrayList<String>();
		countValues = new ArrayList<Integer>();
		values = new ArrayList<Values>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addValues (Values val) {
		values.add(val);
		Methods.addNamesAndCount(nameValues, countValues, val.name);
	}
	
	public double atributeEntrophy() {
		 return Methods.entrophy(countValues);
	 }
	
	public double getAttributeEntrophy() {
		List<String> nameOfValues = new ArrayList<String>();
		List<Integer> countOfValues = new ArrayList<Integer>();
		double sumOfEntrophyTimesPerc = 0;
		int tempCount;
		
		for(String val : nameValues) {
			tempCount = 0;
			nameOfValues.clear();
			countOfValues.clear();
			for(Values value : values) {
				if(value.name().equals(val)) {
					Methods.addNamesAndCount(nameOfValues, countOfValues, value.clase);
					tempCount++;
				}
			}
			sumOfEntrophyTimesPerc += ((double)tempCount/values.size())*(Methods.entrophy(countOfValues));
			
		}
		return sumOfEntrophyTimesPerc;
	}
	
	public int getNumValues(){
		return nameValues.size();
	}

	public List<String> getValuesNames(){
		return nameValues;
	}
	
	public List<Values> getValues(){
		return values;
	}
	
	public Attribute getSubAttribute(List<Integer> positions){
		Attribute result = new Attribute(this.name);
		for(Integer position : positions)
			result.addValues(this.values.get(position));
		return result;
	}
	
	public List<Integer> getValuePositions(String valueName){
		List<Integer> result = new ArrayList<Integer>();
		int n = values.size();
		for(int i = 0 ; i < n ; i++)
			if(values.get(i).name.equals(valueName))
				result.add(i);
		return result;
 	}
}
