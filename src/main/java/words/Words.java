package words;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Words {

	private List<String> dictionary;
	private Map<String, ?> sequence;
	private String start;
	private String end;
	
	public static void main(String[] s) {
	//	System.out.println("Start");
		if(s.length < 2) {
			System.out.println("Invalid arguments");
			return;
		}
		
		Words w = new Words();
		try {
			w.loadData(s[0]);
			w.loadDictionary(s[1]);
		} catch (IOException | InvalidParameterException  e) {
			e.printStackTrace();
			return;
		}
		
		w.find();
		w.printResult();
		
	//	System.out.println("End");
	}

	public Collection<String> getResult() {
		return (sequence == null)? null : sequence.keySet();
	}
	
	public void setDictionary(List<String> dictionary) {
		this.dictionary = dictionary;
	}
	
	public void setData(String start, String end) {
		this.start = start;
		this.end = end;
	}
	
	private void printResult() {
		if(sequence == null) {
			System.out.println("Can not find sequence");
		} else {
			printCollection(getResult());
		}
	}

	public void find() {
		for(String word : dictionary) {
			if(start.equals(word)) {
				Map<String, ?> path = new LinkedHashMap<String, Object>();
				path.put(word, null);
				findNext(word, path);
				return;
			}
		}
	}

	private void findNext(String currentWord, Map<String, ?> path) {
		for(String word : dictionary) {
			if(path.containsKey(word)) {
				continue;
			}
			
			if(checkWords(currentWord, word)) {
				Map<String, ?> newPath = (Map<String, ?>) ((LinkedHashMap<String, Object>)path).clone();
				newPath.put(word, null);
				if(word.equals(end)) {
					if(sequence == null || sequence.size() > newPath.size()) {
						sequence = newPath;
					}
					return;
				}
				findNext(word, newPath);
			}
		}
	}

	private void loadData(String file) throws IOException {
		List<String> strings = readFile(file);
		if(strings.size() < 2) {
			throw new InvalidParameterException("Invalid data file format");
		}
		
		setData(strings.get(0), strings.get(1));
	}

	private void loadDictionary(String file) throws IOException {
		setDictionary(readFile(file));
	}
	
	private List<String> readFile(String file) throws IOException {
		BufferedReader reader = null;
		List<String> lines = new LinkedList<String>();
		
		try {
			reader = Files.newBufferedReader(Paths.get(file), Charset.forName("UTF-8"));
	        String line;      
	        while ((line = reader.readLine()) != null) {
	            lines.add(line);
	        }
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
        
        return lines;
	}
	
	public void printCollection(Collection<String> strings) {
		for(String s : strings) {
			System.out.println(s);
		}
	}
	
	public boolean checkWords(String w1, String w2) {
		if(w1 == null || w2 == null || w1.length() != w2.length()) {
			return false;
		}
		
		int i = 0, k = 0;
		for(char c : w1.toCharArray()) {
			if(c != w2.charAt(i++)) {
				if(++k > 1) {
					return false;
				}
			}
		}
		
		return k == 1;
	}
}
