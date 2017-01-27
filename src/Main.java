import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {

	static ArrayList<Node> list = new ArrayList<Node>() ;
	static HashMap<String,String> listCode = new HashMap<String,String>() ;
	static HashMap<String,String> listCodeRe = new HashMap<String,String>() ;
	
	public static void main(String[] args) throws IOException {
		
	}
	
	public static void Compression(String input) throws IOException{
//		FileInputStream f = new FileInputStream("input.txt");
//		DataInputStream d = new DataInputStream(f);
//		@SuppressWarnings("deprecation")
//		String input = d.readLine();
//		d.close();
		
		boolean found = false ;
		for (int i = 0; i < input.length(); i++) {
			Node p = new Node() ;
			String s = input.charAt(i) + "" ;
			for (int j = 0; j < list.size(); j++) {
				if (s.equals(list.get(j).s)){
					found = true ;
					list.get(j).p ++ ;
					break ;
				}
				else 
					found = false ;
			}
			if (found == false){
				p.s = s ;
				p.p = 1.0 ;
				list.add(p) ;
			}
		}
		for (int i = 0; i < list.size(); i++) list.get(i).p /= input.length() ;
		
		while(list.size() > 1){
			Collections.sort(list);
			
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i).s + " " + list.get(i).p);
//			}
//			System.out.println("--------------------------");
			
			Node p = new Node() ;
			Node p1 = new Node() ;
			Node p2 = new Node() ;
			
			p1 = list.get(list.size()-2) ;
			p2 = list.get(list.size()-1) ;
			p.s = list.get(list.size()-2).s + list.get(list.size()-1).s ;
			p.p = list.get(list.size()-2).p + list.get(list.size()-1).p ;
			p.left = p1 ;
			p.right = p2 ;
			
			list.remove(p1) ;
			list.remove(p2) ;
			list.add(p) ;
		}
		String code = "" ;
		dfs(list.get(0) , code) ;
		
		System.out.println(listCode);
		
		String binaryRep = "" ;
		for (int i = 0; i < input.length(); i++) {
			String a = input.charAt(i) + "" ;
			binaryRep += listCode.get(a) ;
		}
		FileOutputStream f1 = new FileOutputStream("compressed.txt") ;
		DataOutputStream d1 = new DataOutputStream(f1);
		d1.writeBytes(binaryRep);
		d1.close();
	}
	public static void dfs(Node node , String code){
		 if(node.s.length() == 1){
			 node.code = code ;
			 listCode.put(node.s , node.code) ;
			 listCodeRe.put(node.code, node.s) ;
			 return ;
		 }
		 dfs(node.left,code+'0') ;
		 dfs(node.right,code+'1') ;
	}
	
	public static void Decompression() throws IOException{
		FileReader fr = new FileReader("compressed.txt");
		BufferedReader br = new BufferedReader(fr);
		String binaryRep = "" ;
		binaryRep = br.readLine() ;
		
		String output = "" ;
		String s = "" ;
		for (int i = 0; i < binaryRep.length() ; i++) {
			s += binaryRep.charAt(i) + "" ;
			if (listCodeRe.get(s) != null){
				output += listCodeRe.get(s) ;
				s = "" ;
			}
		}
		System.out.println(output);
		FileOutputStream f3 = new FileOutputStream("output.txt") ;
		DataOutputStream d3 = new DataOutputStream(f3) ;
		d3.writeBytes(output);
		d3.close(); 
	}
}