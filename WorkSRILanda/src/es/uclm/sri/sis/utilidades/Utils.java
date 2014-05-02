package es.uclm.sri.sis.utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;


public class Utils {

	private Utils() { }
	
	public static String sustituirEspaciosBlanco(String cadena) {
		cadena = cadena.trim();
		for (int i=0; i < cadena.length(); i++) {
			if (cadena.charAt(i) == ' ') {
				cadena += '+';
			} else {
				cadena += cadena.charAt(i);
			}
		}
		return cadena;
	}
	
	public static ArrayList<Object> convertirHashMapEnArrayList(HashMap<String, Object> hash) {
		ArrayList<Object> arrayList = new ArrayList<Object>();
		Iterator<Entry<String, Object>> it = hash.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
			arrayList.add(e.getValue());
		}
		return arrayList;
	}
	
	public static Double[] inicializarArrayDoble(Double[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0.0;
		}
		return array;
	}
	
	public static String[] removeElements(String[] input, String deleteMe) {
	    List<String> result = new LinkedList<String>();

	    for(String item : input)
	        if(!deleteMe.equals(item))
	            result.add(item);

	    return result.toArray(input);
	}
	
	public static double[] removeElements(double[] input, int pos1, int pos2) {
		
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < input.length; i++) {
			if (i != pos1 && i != pos2) {
				result.add(new Double(input[i]));
			}
		}
		double[] d = new double[result.size()];
		int index = 0;
		Iterator<Double> it = result.iterator();
		while(it.hasNext()) {
			d[index] = it.next().doubleValue();
			index++;
		}
		return d;
	}
	
	public static double[] toDoubleArray(Double[] arr) {
	    List<Double> list = new ArrayList<Double>(Arrays.asList(arr));
	    list.removeAll(Collections.singleton(null));
	    return ArrayUtils.toPrimitive(list.toArray(new Double[list.size()]));
	}
	
	public static String convertirFecha(Date date, String formato) {
	    DateFormat fecha = null;
	    if (formato == null || formato.length() == 0) {
	        fecha = new SimpleDateFormat("ddMMyyy");
	    } else {
	        fecha = new SimpleDateFormat(formato);
	    }
	    
	    return fecha.format(date);
	}

}
