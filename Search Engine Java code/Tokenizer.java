import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.tartarus.snowball.SnowballStemmer;

public class Tokenizer {
  
   static long term_id = 1;
   static String newLine = System.getProperty("line.separator");
   static Map<String, Long> vocab = new HashMap<String, Long>();
   
   public Tokenizer() {}
   /*
    * Method for parsing document text, ignoring html tags, tokenizing the text,
    * converting all tokens to lowercase and assigning position to each token.
    * 
    * Returns a multimap containing all tokens, including stop words, and their
    * positions
    */
   public static Multimap extractText(Reader reader) throws IOException,
           Throwable,ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String line;
        
        //Construct a StringBuilder containing all the text in the file
        while((line=br.readLine()) != null) {
            sb.append(line);
        }
        
        //Remove http headers, then parse the remaining content using jsoup
        //to extract the document text, ignoring all html tags
        String textOnly = Jsoup.parse(removeHeaders(sb)).text();
               
        Pattern pattern = Pattern.compile("\\w+(\\.?\\w+)");
        Matcher match = pattern.matcher(textOnly);
        
        Multimap<String, Integer> mm = LinkedListMultimap.create();
       
        //Split the text into tokens that match \w+(\.?\w+)* , convert all
        //tokens to lowercase and put them in a multimap along with their
        //positions
        String matching_regex = "";
        int pos=1;
        while(match.find()){
            matching_regex = match.group();
            mm.put(matching_regex.toLowerCase(),pos);
            pos++;
        }
        return mm;
   }
   
   /*
    * This method stems each token from a multimap, and returns another multimap
    * that contains the stemmed token and its positions. 
    * 
    * The multimap merges the positions of two tokens that have the same stemmed
    * root.
    */
   public static Multimap<String, List> stemming(Multimap al) throws 
           ClassNotFoundException,Throwable
   {   
       Class stemClass = Class.forName("org.tartarus.snowball.ext.englishStemmer");
       SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
       Multiset<String> keys = al.keys();
       Multimap<String, List> hm = LinkedListMultimap.create();
       int pos=1;
       String k;
       List list;
       for(Object key: al.keySet()) {
            k = key.toString();
            if (k.length() > 0) {
		    stemmer.setCurrent(k);
                    stemmer.stem();
                    list = new ArrayList(al.get(key));
                    hm.put(stemmer.getCurrent(), list);   
                   }
	    }
       return hm;
   }
   
   /*
    * This method writes the file doc_index.txt
    */
   public static void writeToFile(Multimap<String,List> sm, BufferedWriter index_writer,
                                  BufferedWriter term_writer, int doc_id) throws IOException {
       
       Set keySet = sm.keySet();
       Iterator it = keySet.iterator();
       while(it.hasNext()) {
           String m = "";
           String term = (String)it.next();
           if(vocab.containsKey(term)) {
               m = Integer.toString(doc_id) + "\t" + Long.toString(vocab.get(term)) + "\t";
           }
           else {
               vocab.put(term,term_id);
               m = Integer.toString(doc_id) + "\t" + Long.toString(term_id) + "\t";
               term_writer.write(Long.toString(term_id) + "\t" + term + newLine);
               term_id++;
           }
           List list_of_pos = (List)sm.get(term);
       
           for (Object l : list_of_pos) {
               List pos = (List) l;
               for (Object p : pos) {
                   String position = p.toString();
                   m = m + position + "\t";
               }
           }
           index_writer.write(m.trim() + newLine);
       }
   }
   
    /*
     * Method for removing HTTP headers from the document
     * Returns a String containing the contents of the document after removing
     * the http headers
     */
    public static String removeHeaders(StringBuilder sb) {
        String content = "";
     
        if(sb.toString().contains("<!doctype") || sb.toString().contains("<!DOCTYPE")) {
            content = sb.toString().replaceAll("(WARC|warc).*?(<!doctype|<!DOCTYPE)","<!DOCTYPE");
        }
        else if(sb.toString().contains("<html") || sb.toString().contains("<HTML")) {
            content = sb.toString().replaceAll("(WARC|warc).*?(<html|<HTML)","<html");
        }
        else if(sb.toString().contains("<head") || sb.toString().contains("<HEAD")) {
            content = sb.toString().replaceAll("(WARC|warc).*?(<head|<HEAD)","<head");
        }
        else if(sb.toString().contains("<script") || sb.toString().contains("<SCRIPT")) {
            content = sb.toString().replaceAll("(WARC|warc).*?(<script|<SCRIPT)","<script");
        }
        else if(sb.toString().contains("<a") || sb.toString().contains("<A")) {
            content = sb.toString().replaceAll("(WARC|warc).*?(<a|<A)","<a");
        }
        return content;
    }
    
    /*
     * Method for removing the stop words from the tokenized list of words
     */
    public static Multimap stopping(Multimap a) throws FileNotFoundException, 
                                                       IOException {
        BufferedReader stop = new BufferedReader(new FileReader("stoplist.txt"));
        String line = "";
        while((line = stop.readLine()) != null){
            if (a.containsKey(line)) {
                a.removeAll(line);   //remove all instances of this stop word 
                                     //from the multimap
            }
        }
        return a;
    }
    
    public static void main(String args[]) throws Throwable,ClassNotFoundException{
        try {            
            File dir = new File(args[0]);
            File[] listoffiles = dir.listFiles();
            
            BufferedWriter doc_writer = new BufferedWriter(new FileWriter("docids.txt"));
            BufferedWriter index_writer = new BufferedWriter(new FileWriter("doc_index.txt"));
            BufferedWriter term_writer = new BufferedWriter(new FileWriter("termids.txt"));
                
            int doc_id=1;
           
            for (File f : listoffiles) {
                doc_writer.write(doc_id+"\t"+f.getName());
                doc_writer.write(newLine);
                FileReader reader = new FileReader(f);
               // System.out.println("*****New file : " +doc_id+"******");
                Multimap mm = extractText(reader);
                Multimap tokens_after_stopping = stopping(mm);
                Multimap<String, List> stemmed_map = stemming(tokens_after_stopping);
                writeToFile(stemmed_map, index_writer, term_writer, doc_id);
                doc_id++;
            }
            doc_writer.close();
            index_writer.close();
            term_writer.close();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }   
}
