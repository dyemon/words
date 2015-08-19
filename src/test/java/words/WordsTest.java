package words;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class WordsTest {

	@Test
	public void testCheckWords() {
		Words w = new Words();
		
		assertTrue(w.checkWords("qwe", "qwr"));
		assertTrue(w.checkWords("qwet", "qwrt"));
		assertTrue(w.checkWords("twe", "qwe"));
		assertFalse(w.checkWords("qwe", "qwry"));
		assertFalse(w.checkWords("qwe", "qyr"));
		assertFalse(w.checkWords("qwe", "123"));
		assertFalse(w.checkWords("qwe", "qwe"));
	}

	@Test
	public void testFind() {
		Words w = new Words();
		List<String> dictionary = new LinkedList<String>();
		dictionary.add("abc");
		dictionary.add("abd");
		dictionary.add("abe");
		dictionary.add("abd");
		dictionary.add("abc");
		
		List<String> res = new LinkedList<String>();
		res.add("abc");
		res.add("abe");
		
		String start = "abc";
		String end = "abe";
		
		w.setData(start, end);
		w.setDictionary(dictionary);
		w.find();
		
		assertNotNull(w.getResult());
		assertArrayEquals( w.getResult().toArray(), res.toArray() );
	}
}
