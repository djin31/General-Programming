//To run the program give a dictionary file alongwith a word for which anagram has to be found as arguments
// #javac Anagram,java
// #java Anagram <name of dictionary file> <word for which anagram has to be found>
import java.util.*;
import java.io.*;
import java.math.BigInteger;
class entries{
	BigInteger key;
	ArrayList<String> values;
}

public class Anagram {

	static entries[] hashTable;
	static int [] alpha_look_up = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,157,0,0,0,0,0,0,0,0,103,107,109,113,127,131,137,139,149,151,0,0,0,0,0,0,0,2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,0,0,0,0,0,0,2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
	static BigInteger mod_val = BigInteger.valueOf(52057);
	static int mod_val_int = 52057;

	private static void makeVocab(String filename){
		String temp;
		BigInteger key;
		ArrayList<String> v;
		int final_key;
		try{
			
			FileInputStream fi =new FileInputStream(filename);
			Scanner s = new Scanner(fi);
			int index=s.nextInt();
			while(index>0){
				index--;
				temp=s.next();
				key=BigInteger.valueOf(1);
				if (temp.length()<13){
					for (int j=0;j<temp.length();j++){
						key=key.multiply(BigInteger.valueOf(alpha_look_up[(int)temp.charAt(j)]));
						if (key.equals(0))
							break;
					}
					final_key=key.mod(mod_val).intValue();
					if (hashTable[final_key]==null){
						entries new_entry=new entries();
						new_entry.key=key;
						new_entry.values=new ArrayList<String>() ;
						new_entry.values.add(temp);
						hashTable[final_key]=new_entry;
					}
					else if(hashTable[final_key].key.equals(key)){
						hashTable[final_key].values.add(temp);
					}
					else{
						while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(key)){
							final_key++; 
							
							if (final_key==mod_val_int)
								final_key=0;
						}
						if (hashTable[final_key]!=null)
							hashTable[final_key].values.add(temp);
						else{
							entries new_entry=new entries();
							new_entry.key=key;
							new_entry.values=new ArrayList<String>() ;
							new_entry.values.add(temp);
							hashTable[final_key]=new_entry;	
						}
					}
				}
			}

		}
		catch (FileNotFoundException f){
			System.out.println(f);

		}
	}


	private static ArrayList<ArrayList<Integer>> findsubset(ArrayList<Integer> numbers){
		ArrayList<ArrayList<Integer>> subsets= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> subset;
		for(long i = 0; i < (1<<numbers.size()); i++){
		    subset = new ArrayList<Integer>();
		    for(int j = 0; j < numbers.size(); j++){
		        if(((i>>j) & 1) == 1){ // bit j is on
		            subset.add(numbers.get(j));
		        }
		    }
	        if ((subset.size()>2)&&(subset.size()<numbers.size()-2)){
	        	subsets.add(subset);
	        }
		}
		return subsets;
	}


	private static void findAnagram(String s){
		BigInteger key=BigInteger.valueOf(1);
		ArrayList<String> v,w,x,y; 
		ArrayList<Integer> factors = new ArrayList<Integer>();
		int final_key;
		v = new ArrayList<String>();
		for(int i=0;i<s.length();i++){
			key=key.multiply(BigInteger.valueOf(alpha_look_up[(int)s.charAt(i)]));
			factors.add(alpha_look_up[(int)s.charAt(i)]);
			//System.out.println(key);
		}
		final_key=key.mod(mod_val).intValue();
		
		while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(key)){
			final_key++;
			if (final_key==mod_val_int)
				final_key=0;
		}
		if (hashTable[final_key]!=null)
			v.addAll(hashTable[final_key].values);		

	
		if (s.length()>5){
			ArrayList<ArrayList<Integer>> factorsets= findsubset(factors);
			BigInteger i,j,k,z;
			ArrayList<Integer> compliment;
			for(ArrayList<Integer> f:factorsets){
				i=BigInteger.ONE;
				for (Integer p:f)
					i=i.multiply(BigInteger.valueOf(p));
				final_key=i.mod(mod_val).intValue();
				while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(i)){
					final_key++;
					if (final_key==mod_val_int)
						final_key=0;
				}
				if (hashTable[final_key]!=null){
					w=hashTable[final_key].values;
					z=key.divide(i);
					final_key=z.mod(mod_val).intValue();
					while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(z)){
						final_key++;//chain++;
						if (final_key==mod_val_int)
							final_key=0;
					}
					if (hashTable[final_key]!=null){
						x=hashTable[final_key].values;
						for (String a:w)
							for(String b:x)
								v.add(a+" "+b);
					}
					if ((factors.size()- f.size())>5){
						compliment=new ArrayList<Integer>();
						compliment.addAll(factors);
						for (Integer p:f)
							compliment.remove(p);
						ArrayList<ArrayList<Integer>> subfactorset = findsubset(compliment);
						for (ArrayList<Integer> sf : subfactorset){
							j=BigInteger.valueOf(1);
							for (Integer p:sf)
								j=j.multiply(BigInteger.valueOf(p));
							final_key=j.mod(mod_val).intValue();
							while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(j)){
								final_key++;//chain++;
								if (final_key==mod_val_int)
									final_key=0;
							}
							if (hashTable[final_key]!=null){
								x=hashTable[final_key].values;
								k=z.divide(j);
								final_key=k.mod(mod_val).intValue();
								while (hashTable[final_key]!=null && !hashTable[final_key].key.equals(k)){
									final_key++;//chain++;
									if (final_key==mod_val_int)
										final_key=0;
								}
								if (hashTable[final_key]!=null){
									y=hashTable[final_key].values;
									for (String a:w)
										for(String b:x)
											for(String c:y)
												v.add(a+" "+b+" "+c);
								}
							}
						}
					}
				}
			}		
		}
		Collections.sort(v);
		v.add("-1");
		StringBuilder printer = new StringBuilder(v.get(0));
		for (int i=1;i<v.size();i++){
			if (!v.get(i).equals(v.get(i-1))){
				printer.append("\n");
				printer.append(v.get(i));
			}
		}
		System.out.println(printer);
	}

	public static void main(String[] args) {
		hashTable = new entries[mod_val_int];
		
		//long t=System.currentTimeMillis();
		makeVocab(args[0]);	
		findAnagram(args[1]);		
		//System.out.println(System.currentTimeMillis()-t);
	}
}
