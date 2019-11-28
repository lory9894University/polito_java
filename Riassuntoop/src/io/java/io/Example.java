package io.java.io;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

public class Example {

	public static void main(String[] args) throws IOException {
		/*Read text file line-by-line*/
		String filename = null;
		List<String> lines = null;
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			lines = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		if (lines == null)
			return;
		Iterator<String> iterator = lines.iterator();
		String line = iterator.next();	//remove this if you DON'T want to skip the first line
		while(iterator.hasNext()) {
			line = iterator.next();
			//usage example
			System.out.println(line); //print each line
			String splitLine[] = line.split(";");
			System.out.println(splitLine[0]);	//print the first word of each line
		}
		
		/*Don't bother studying below this for the exam*/
		
		/*Copy text file with buffer (faster than copying single chars)*/
		Reader src = new FileReader("src-filename");
		Writer dest = new FileWriter("dest-filename");
		char[] buffer = new char[4096];
		int n;
		while((n=src.read(buffer))!=-1) {
			dest.write(buffer,0,n);
		}
		src.close();
		dest.close();
		
		/*Download text File*/
		URL home=new URL("localhost");
		URLConnection con = home.openConnection();
		String ctype = con.getContentType();
		if(ctype.equals("text/html")) {
			Reader r = new InputStreamReader(con.getInputStream());
			Writer w= new OutputStreamWriter(System.out);
			char[] buff = new char[4096];
			while(true) {
				int n1=r.read(buff);
				if(n1==-1) break;
				w.write(buff,0,n1);
			}
			r.close();
			w.close();
		}
	}
}
