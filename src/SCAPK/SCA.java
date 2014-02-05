package SCAPK;


import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JTextPane;

public class SCA {
	
	private static ArrayList<Integer>[] X;
	private static ArrayList<Integer>[] Z;
	private static ArrayList<Integer>[] J;
	private static ArrayList<Integer>[] K;
	private static ArrayList<Integer>[] D;
	private static ArrayList<Integer>[] T;
	private static ArrayList<Integer>[] S;
	private static ArrayList<Integer>[] R;
	private static ArrayList<Integer>[] Q;
	private static ArrayList<Integer>[] QPlus;
	public static String FF_TYPE = "";
	
	private static TreeMap<String, ArrayList<Integer>> internals;
	private static TreeMap<String, String> inputs;
	private static TreeMap<String, String> outputs;
	
	static boolean noInput = false;
	private int val;
	public SCA(int x, int z, int f) {
		if(x == 0)
			noInput = true;
		else
			X = (ArrayList<Integer>[]) new ArrayList[x];
		Z = (ArrayList<Integer>[]) new ArrayList[z];
		J = (ArrayList<Integer>[]) new ArrayList[f];
		K = (ArrayList<Integer>[]) new ArrayList[f];
		D = (ArrayList<Integer>[]) new ArrayList[f];
		T = (ArrayList<Integer>[]) new ArrayList[f];
		S = (ArrayList<Integer>[]) new ArrayList[f];
		R = (ArrayList<Integer>[]) new ArrayList[f];
		Q = (ArrayList<Integer>[]) new ArrayList[f];
		QPlus = (ArrayList<Integer>[]) new ArrayList[f];
		internals = new TreeMap<String, ArrayList<Integer>>();
		inputs = new TreeMap<String, String>();
		outputs = new TreeMap<String, String>();
		
		if(!noInput) {
			for(int i = 0; i < x; i++) {
				X[i] =  new ArrayList<Integer>();
				for(int c = 1; c <= Math.pow(2, f+x); c++) 
					if(c % Math.pow(2, i+f) == 0) {
						X[i].add(val);
						val = change(val);
					} else
						X[i].add(val);	
				internals.put("X" + i, X[i]);
			}
		}
		for(int i = 0; i < z; i++) {
			Z[i] = new ArrayList<Integer>();
			internals.put("Z" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			J[i] = new ArrayList<Integer>();
			internals.put("J" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			K[i] = new ArrayList<Integer>();
			internals.put("K" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			D[i] = new ArrayList<Integer>();
			internals.put("D" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			T[i] = new ArrayList<Integer>();
			internals.put("T" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			S[i] = new ArrayList<Integer>();
			internals.put("S" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			R[i] = new ArrayList<Integer>();
			internals.put("R" + i, new ArrayList<Integer>());
		}
		for(int i = 0; i < f; i++) {
			Q[i] = new ArrayList<Integer>();
			val = 0;
			for(int c = 1; c <= Math.pow(2, f+x); c++) 
				if(c % Math.pow(2, i) == 0) {
					Q[i].add(val);
					val = change(val);
				} else
					Q[i].add(val);
			internals.put("Q" + i, Q[i]);
		}
		for(int i = 0; i < f; i++) 
			QPlus[i] = new ArrayList<Integer>();
	}
	
	public static void setInternals(String[] arr) {
		for(String i : arr) {
			if(!internals.containsKey(i))
				internals.put(i, new ArrayList<Integer>());
		}
	}
	
	public static void setInputs(String[] arr) {
		int count = 0;
		for(String i : arr) {
			inputs.put(i, "X" + count);
			count++;
		}
	}
	
	public static void setOutpuuts(String[] arr) {
		int count = 0;
		
		for(String i : arr) {
			outputs.put(i, "Z" + count);
			count++;
		}
	}
	
	public static void appandToErrors(String s) {
		if(!Main.errors.contains("   " + s))
			Main.errors.add("   " + s);
	}

	public static void eval(String str) {
			
		str = removeSpaces(str);
		if(!str.matches("(?i)((gate|ff:){1}.*)")) 
			appandToErrors("\"" + str + "\" Can't be resolved.");	
		String str2 = "";
		try {
			str2 = str.substring(str.indexOf("(")+1, str.indexOf(")"));
		} catch(StringIndexOutOfBoundsException e) {
			appandToErrors("\"" + str + "\" Can't be resolved.");			
		}
		str2 = Main.clean(str2);
		String[] io = str2.split(",");
		try {
			if(str.substring(0, 4).toLowerCase().equals("gate")) {
				ArrayList<Integer> i0 = new ArrayList<Integer>();
				ArrayList<Integer> i1 = new ArrayList<Integer>();
				ArrayList<Integer> i2 = new ArrayList<Integer>();
				String output = "";
				if(io.length == 2) {
					i0.addAll(gateInput(io[0]));
					output = gateOutput(io[1]);
				}
				else if(io.length == 3) {
					i0.addAll(gateInput(io[0]));
					i1.addAll(gateInput(io[1]));
					output = gateOutput(io[2]);
				}
				else if(io.length == 4) {
					i0.addAll(gateInput(io[0]));
					i1.addAll(gateInput(io[1]));
					i2.addAll(gateInput(io[2]));
					output = gateOutput(io[3]);
				}
				
				String gate = 
						str.substring(str.indexOf(":")+1,
								str.indexOf("(")).toLowerCase();
				if(gate.length() > 4 &&
						gate.substring(4).matches(Main.blueWords)) {
					String appanedTOErrors = 
							"   \"" + str +"\" Missing colons.";
					if(!Main.errors.contains(appanedTOErrors))
						Main.errors.add(appanedTOErrors);
				}
				else if(!gate.matches(Main.blueWords)) 
					appandToErrors("\"" + str +"\" check the name of the gate.");
				 else if(!str2.matches("[\\w|,*|'*]+")) 
					appandToErrors("\"" + str +"\" Missing comma.");
				
				
				switch(gate){
				case "inverter":
					internals.put(output, Gate.NOT(i0));
					break;
				case "buffer":
					internals.put(output, Gate.BUFFER(i0));
					break;
				case "and" :
					if(io.length == 3)
						internals.put(output, Gate.AND(i0,i1));
					else {
						internals.put(output, Gate.AND(i0,i1,i2));
					}
					break;
				case "or" :
					if(io.length == 3) 
						internals.put(output, Gate.OR(i0,i1));
					else
						internals.put(output, Gate.OR(i0,i1,i2));
					break;
				case "nand" :
					if(io.length == 3) {
						internals.put(output, Gate.NAND(i0,i1));
					}
					else
						internals.put(output, Gate.NAND(i0,i1,i2));
					break;
				case "nor" :
					if(io.length == 3) {
						internals.put(output, Gate.NOR(i0,i1));
					}
					else
						internals.put(output, Gate.NOR(i0,i1,i2));
					break;
				case "xor" :
					if(io.length == 3) {
						internals.put(output, Gate.XOR(i0,i1));
					}
					else
						internals.put(output, Gate.XOR(i0,i1,i2));
					break;
				case "xnor" :
					if(io.length == 3) {
						internals.put(output, Gate.XNOR(i0,i1));
					}
					else
						internals.put(output, Gate.XNOR(i0,i1,i2));
					break;					
				}
			} else {
				if(!str.matches("(?i)(FF:[JK|SR|D|T]+.{1,})")) {
					if(str.substring(0, 1).matches("(?i)ff"))
						appandToErrors("\"" + str +"\" Missing colons.");
					 else 
						appandToErrors("\"" + str +"\" Can't be resolved.");
				} 
				else if(!str.substring(3, 5).matches("(?i)(jk|sr|(d\\()|(t\\())")) 
					appandToErrors("\"" + str +"\" Check FF type.");
				
				else if(!str2.matches("[\\w|,*|'*]+")) 
					appandToErrors("\"" + str +"\" Missing comma.");

				updateValues();
				int indx = 0;
				try {
				indx = Integer.parseInt(str.substring(str.length()-2,str.length()-1));
				} catch (NumberFormatException e) {
					appandToErrors("\"" + str + "\" Can't be resolved.");
				}
				switch(FF_TYPE){
				case "JK":
					if(io[0].equals("1") && io[1].equals("1")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildJKFF(onesArray, onesArray, Q[indx]));
					}
					else if((io[0].equals("1"))) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildJKFF(onesArray, internals.get(io[1]), Q[indx]));
					}
					else if((io[1].equals("1"))) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildJKFF(internals.get(io[0]), onesArray, Q[indx]));
					}
					if(io[0].equals("0") && io[1].equals("0")){
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildJKFF(zerosArray, zerosArray, Q[indx]));
					}
					else if((io[0].equals("0"))) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildJKFF(zerosArray, internals.get(io[1]), Q[indx]));
					}
					else if((io[1].equals("0"))) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildJKFF(internals.get(io[0]), zerosArray, Q[indx]));
					}
					else if(io[0].equals("1") && io[1].equals("0")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildJKFF(onesArray, zerosArray, Q[indx]));
					}
					else if(io[0].equals("1") && io[1].equals("1")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildJKFF(zerosArray, onesArray, Q[indx]));
					}
					else 
						QPlus[indx].addAll(buildJKFF(internals.get(io[0]), internals.get(io[1]), Q[indx]));
					break;
				case "D" :
					if(io[0].equals("1")) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(onesArray);
					}
					else if(io[0].equals("0")) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildDFF(zerosArray));
					}
					else 
						QPlus[indx].addAll(buildDFF(internals.get(io[0])));
					break;
				case "T" :
					if(io[0].equals("1")) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildTFF(onesArray, Q[indx]));
					}
					else if(io[0].equals("0")) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildTFF(zerosArray, Q[indx]));
					}
					else 
						QPlus[indx].addAll(buildTFF(internals.get(io[0]), Q[indx]));
					break;
				case "SR":
					if(io[0].equals("1") && io[1].equals("1")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildSRFF(onesArray, onesArray, Q[indx]));
					}
					else if((io[0].equals("1"))) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildSRFF(onesArray, internals.get(io[1]), Q[indx]));
					}
					else if((io[1].equals("1"))) {
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						QPlus[indx].addAll(buildSRFF(internals.get(io[0]), onesArray, Q[indx]));
					}
					if(io[0].equals("0") && io[1].equals("0")){
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildSRFF(zerosArray, zerosArray, Q[indx]));
					}
					else if((io[0].equals("0"))) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildSRFF(zerosArray, internals.get(io[1]), Q[indx]));
					}
					else if((io[1].equals("0"))) {
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildSRFF(internals.get(io[0]), zerosArray, Q[indx]));
					}
					else if(io[0].equals("1") && io[1].equals("0")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildSRFF(onesArray, zerosArray, Q[indx]));
					}
					else if(io[0].equals("1") && io[1].equals("1")){
						ArrayList<Integer> onesArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							onesArray.add(1);
						ArrayList<Integer> zerosArray = new ArrayList<Integer>();
						for(int i = 0; i < Q[indx].size(); i++)
							zerosArray.add(0);
						QPlus[indx].addAll(buildSRFF(zerosArray, onesArray, Q[indx]));
					}
					else
						QPlus[indx].addAll(buildSRFF(internals.get(io[0]), internals.get(io[1]), Q[indx]));
					break;
				}
		}
		} catch(NullPointerException e) {}
		 catch (StringIndexOutOfBoundsException|ArrayIndexOutOfBoundsException e) {
				appandToErrors("\"" + str + "\" Can't be resolved.");
		 }
	}
	
	private static ArrayList<Integer> gateInput(String str) {
		if(inputs.containsKey(str))
			return internals.get(inputs.get(str));
		else if(outputs.containsKey(str)) 
			return internals.get(outputs.get(str));
		else {
			return internals.get(str);
		}
	}
	
	private static String gateOutput(String str) {
		if(inputs.containsKey(str)) {
			return inputs.get(str);
		}
		else if(outputs.containsKey(str)) {
			return outputs.get(str);
		}
		else {
			return str;
		}
	}
	
	private static void updateValues() {
		// Z  J  K D T S R
		try {
			for(int i = 0; i < Z.length; i++) {
				Z[i].clear();
				Z[i].addAll(internals.get("Z" + i));
			}
			for(int i = 0; i < K.length; i++) {
				J[i].clear();
				K[i].clear();
				J[i].addAll(internals.get("J" + i));
				K[i].addAll(internals.get("Q" + i));
			}
		} catch(NullPointerException e) {}
	}
	
	private static ArrayList<Integer> buildJKFF(ArrayList<Integer> j, ArrayList<Integer> k,ArrayList<Integer> q) {
		try {
			ArrayList<Integer> rslt = new ArrayList<Integer>();
			for(int i = 0; i < j.size(); i++)
				rslt.add(JKFF(j.get(i), k.get(i), q.get(i)));
			return rslt;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private static int JKFF(int j, int k, int q) {
		if(j == 0 && k == 0)
			return q;
		else if(j == 0 && k == 1)
			return 0;
		else if(j == 1 && k == 0)
			return 1;
		else if(q == 1)
			return 0;
		return 1;
	}

	private static ArrayList<Integer> buildDFF(ArrayList<Integer> d) {
		try {
			ArrayList<Integer> rslt = new ArrayList<Integer>();
			for(int i = 0; i < d.size(); i++)
				rslt.add(DFF(d.get(i)));
			return rslt;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private static int DFF(int d) {
		switch(d) {
		case 0 :
			return 0;
		case 1 :
			return 1;		
		}
		return -1;
	}
	
	private static ArrayList<Integer> buildTFF(ArrayList<Integer> t, ArrayList<Integer> q) {
		try {
			ArrayList<Integer> rslt = new ArrayList<Integer>();
			for(int i = 0; i < t.size(); i++)
				rslt.add(TFF(t.get(i), q.get(i)));
			return rslt;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private static int TFF(int d, int q) {
		switch(d) {
		case 0 :
			return q;
		case 1 :
			if(q == 0)
				return 1;
			return 0;
		}
		return -1;
	}
	
	private static ArrayList<Integer> buildSRFF
	(ArrayList<Integer> s, ArrayList<Integer> r,ArrayList<Integer> q) {
		try {
			ArrayList<Integer> rslt = new ArrayList<Integer>();
			for(int i = 0; i < s.size(); i++)
				rslt.add(SRFF(s.get(i), r.get(i), q.get(i)));
			return rslt;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private static int SRFF(int s, int r, int q) {
		if(s == 0 && r == 0)
			return q;
		else if(s == 0 && r == 1)
			return 0;
		else if(s == 1 && r == 0)
			return 1;
		appandToErrors("Forbiddent satate found in SRFF");
		return -1;
	}
	private static String removeSpaces(String str) {
		String[] arr = str.split(" ");
		str = "";
		for(String i : arr)
			str += i;
		return str;
	}

	private int change(int num) {
		if(num == 0)
			return 1;
		return 0;
	}
	
	private static String getX(int n) {
		String str = "";
		for(int i = 0; i < X.length; i++)
			str += X[i].get(n); 
		return str;
	}
	
	private static String getZ(int n) {
		String str = "";
		try {
		for(int i = Z.length-1; i >= 0; i--)
				str += Z[i].get(n);
		
		} catch(IndexOutOfBoundsException e) {
			appandToErrors("Check outputs equtions.");
		}
		return str;
	}
	
	private static String getState(int n) {
		String rslt = "";
		try {
		String str = "";
		for(int i = 0; i < QPlus.length; i++)
			str += QPlus[i].get(n); 
		for(int i = str.length()-1; i >= 0; i--) 
			rslt += str.charAt(i) + "";
		rslt = Main.clean(rslt);
		return Integer.toString(Integer.parseInt(rslt, 2));
		} catch(IndexOutOfBoundsException e) {
			appandToErrors("Check FF equtions.");
		}
		return null;
	}
	
	public static void appendToTextPane(JTextPane tp) {
		
		String rslt = "";
		getZ(0);
		getState(0);

		if(Main.errors.size() == 0) {
		int n = (int) Math.pow(2, Q.length);
		rslt = "STATES_NO:" + n + "\n";
		rslt += "STATES: ";
		for(int i = 0; i < n; i++) 
			if(n-i == 1)
				rslt += "S" + i + "\n";
			else
				rslt += "S" + i + ", ";
		rslt += "STATE_DIAGRAM:" + "\n";
		ArrayList<String>[] s = (ArrayList<String>[]) new ArrayList[n];
		int shift = (int) Math.pow(2, Q.length);
		for(int i = 0; i < s.length; i++) {
			s[i] = new ArrayList<String>();
			if(noInput) {
				s[i].add("noInputFound");
				s[i].add("S" + getState(i));
				s[i].add(getZ(i));
			} else {
				for(int c = 0; c < X.length*2; c++) {
					s[i].add(getX(i+shift*c));
					s[i].add("S" + getState(i+shift*c));
					s[i].add(getZ(i+shift*c));
				}
			}
		}
		String currentIN = "", currentOUT = "";
		for(String i : Main.inputs)
			currentIN += i;
		for(int i = Main.outputs.length-1; i >= 0; i--)
			currentOUT += Main.outputs[i];
		for(int i = 0; i < s.length; i++) {
			rslt += "S" + i + ": \n";
			for(int c = 0; c < s[i].size(); c += 3)
				rslt +=
				currentIN + "=" + s[i].get(c) + "->" 
				+  s[i].get(c+1) + "," + currentOUT + "=" 
						+ s[i].get(c+2) + "\n";
		}
		
		tp.setText(rslt);
		} else {
			int size = Main.errors.size();
			rslt = "Error, Can't find a solution.\n\n";
			if(size == 1) 
				rslt += size + " Quick Fix found: \n";
			else
				rslt += size + " Quick Fixs found: \n";
			for(String i : Main.errors) 
				rslt += i + "\n";
			tp.setText(rslt);
		}
	}
	
	public static void eval() {	
		int n = (int) Math.pow(2, Q.length);
		System.out.println("STATES_NO:" + n);
		System.out.print("STATES: ");
		for(int i = 0; i < n; i++) 
			if(n-i == 1)
				System.out.println("S" + i);
			else
				System.out.print("S" + i + ", ");
		System.out.println("STATE_DIAGRAM:");
		ArrayList<String>[] s = (ArrayList<String>[]) new ArrayList[n];
		int shift = (int) Math.pow(2, Q.length);
		for(int i = 0; i < s.length; i++) {
			s[i] = new ArrayList<String>();
			if(noInput) {
				s[i].add("noInputFound");
				s[i].add("S" + getState(i));
				s[i].add(getZ(i));
			} else {
				for(int c = 0; c < X.length*2; c++) {
					s[i].add(getX(i+shift*c));
					s[i].add("S" + getState(i+shift*c));
					s[i].add(getZ(i+shift*c));
				}
			}
		}
		
		String currentIN = "", currentOUT = "";
		for(String i : Main.inputs)
			currentIN += i;
		for(int i = Main.outputs.length-1; i >= 0; i--)
			currentOUT += Main.outputs[i];
		for(int i = 0; i < s.length; i++) {
			System.out.println("S" + i + ": ");
			for(int c = 0; c < s[i].size(); c += 3)
			System.out.println
			(currentIN + "=" + s[i].get(c) + "->" 
			+  s[i].get(c+1) + "," + currentOUT + "=" + s[i].get(c+2));
		}
	} 
}
