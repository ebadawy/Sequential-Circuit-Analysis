package SCAPK;


import java.util.ArrayList;

public class Gate {

	public static ArrayList<Integer> AND 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.isEmpty() || x2.isEmpty())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) & x2.get(i));
		return z;
	}
	
	public static ArrayList<Integer> AND 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) & x2.get(i) & x3.get(i));
		return z;
	}
	
	public static ArrayList<Integer> NAND 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.isEmpty() || x2.isEmpty())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) { 
			temp = x1.get(i) & x2.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> NAND 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) {
			temp = x1.get(i) & x2.get(i) & x3.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> OR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) | x2.get(i));
		return z;
	}
	
	public static ArrayList<Integer> OR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) | x2.get(i) | x3.get(i));
		return z;
	}
	
	public static ArrayList<Integer> NOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) {
			temp = x1.get(i) | x2.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> NOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) {
			temp = x1.get(i) | x2.get(i) | x3.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> XOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) ^ x2.get(i));
		return z;
	}
	
	public static ArrayList<Integer> XOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		for(int i = 0; i < x1.size(); i++) 
			z.add(x1.get(i) ^ x2.get(i) ^ x3.get(i));
		return z;
	}
	
	public static ArrayList<Integer> XNOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) {
			temp = x1.get(i) ^ x2.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> XNOR 
	(ArrayList<Integer> x1, ArrayList<Integer> x2, ArrayList<Integer> x3) {
		if(x1.size() != x2.size())
			return null;
		ArrayList<Integer> z = new ArrayList<Integer>();
		int temp;
		for(int i = 0; i < x1.size(); i++) {
			temp = x1.get(i) ^ x2.get(i) ^ x3.get(i);
			if(temp == 0)
				z.add(1);
			else
				z.add(0);
		}
		return z;
	}
	
	public static ArrayList<Integer> NOT
	(ArrayList<Integer> x) {
		ArrayList<Integer> xDash = new ArrayList<Integer>();
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i) == 0)
				xDash.add(1);
			else
				xDash.add(0);
		}
		return xDash;
	}
	
	public static ArrayList<Integer> BUFFER
	(ArrayList<Integer> x) {
		ArrayList<Integer> x2 = new ArrayList<Integer>();
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i) == 0)
				x2.add(0);
			else
				x2.add(1);
		}
		return x2;
	}
}

