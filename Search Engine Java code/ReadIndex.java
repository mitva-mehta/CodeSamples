
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.tartarus.snowball.SnowballStemmer;


public class ReadIndex {
        
    public static void main(String args[]) throws IOException, Throwable {
        if(args.length == 2) {
            if (args[0].equals("--term")) {
                listTermInfo(args[1]);
            } else if (args[0].equals("--doc")) {
                listDocInfo(args[1]);
            } else {
                System.out.println("Incorrect usage. Correct usage is : ");
                System.out.println("java ReadIndex --term <term>");
                System.out.println("OR");
                System.out.println("java ReadIndex --doc <document>");
                System.out.println("OR");
                System.out.println("java ReadIndex --term <term> --doc <document>");
            }
        } else if (args.length == 4) {
            if (args[0].equals("--term") && args[2].equals("--doc")) {
                termIndexInfo(args[1],args[3]);
            } else if(args[0].equals("--doc") && args[2].equals("--term")) {
                termIndexInfo(args[3],args[1]);
            } else {
                System.out.println("Incorrect usage. Correct usage is : ");
                System.out.println("java ReadIndex --term <term> --doc <document>");
            }
        } else {
            System.out.println("Incorrect usage. Correct usage is : ");
            System.out.println("java ReadIndex --term <term>");
            System.out.println("OR");
            System.out.println("java ReadIndex --doc <document>");
            System.out.println("OR");
            System.out.println("java ReadIndex --term <term> --doc <document>");
        }
    }
    
    /*
     * This method takes the document name as an argument and reads from the 
     * files docids.txt and doc_index.txt to print the corresponding information
     * about the document
     */
    public static void listDocInfo(String docName) throws IOException {
        
        System.out.println("Listing for document: "+docName);
        Path path = Paths.get("doc_index.txt");
        Scanner inDocIndex = new Scanner(path);
                   
        String docid=getDocId(docName);
        
        if(docid.equals("")) {
            System.out.println("No match found for document: "+docName);
        } else {
            int dist_terms = 0;
            int total_terms = 0;
            mainloop:
            while(inDocIndex.hasNextLine()) {
                String str = inDocIndex.nextLine();
                String[] a = str.split("\t");
                if(a[0].equals(docid)) {
                    dist_terms++;
                    total_terms+= a.length - 2;
                }
                if(Integer.parseInt(a[0]) > Integer.parseInt(docid)) {
                    break mainloop;
                }
            }
            System.out.println("DOCID: "+docid);
            System.out.println("Distinct terms : " +dist_terms);
            System.out.println("Total terms : " +total_terms);
        }    
    }
    
    /*
     * This method takes the document name as an argument and reads through
     * the file docids.txt to return the doc_id for this document
     */
    public static String getDocId(String doc) throws IOException {
        Path path = Paths.get("docids.txt");
        Scanner inDocIds = new Scanner(path);
        String docid="";
        
        while(inDocIds.hasNextLine()) {
            String str = inDocIds.nextLine();
            String[] a = str.split("\t");
            if(a[1].equals(doc)) {
                docid = a[0];
                break;
            }
        }
        return docid;
    }
    
    /*
     * This method takes the term as an argument and reads through the
     * file termids.txt to return the term_id corresponding to this term
     */
    public static String getTermId(String term) throws IOException {
        
        Path path = Paths.get("termids.txt");
        Scanner inTermIds = new Scanner(path);
        String termid = "";
        
        while(inTermIds.hasNextLine()) {
            String str = inTermIds.nextLine();
            String[] a = str.split("\t");
            if(a[1].equals(term)) {
                termid = a[0];
                break;
            }
        }
        return termid;
    }
    
    /*
     * This method takes the term_id as an argument and reads through the file
     * term_info.txt to return the byte offset for that term in term_index.txt
     */
    public static long getByteOffset(String term_id) throws IOException {
        Path path5 = Paths.get("term_info.txt");
        Scanner inTermInfo = new Scanner(path5);
        
        long byteoffset =0;
        while(inTermInfo.hasNextLine()) {
            String str = inTermInfo.nextLine();
            String[] a = str.split("\t");
            if(a[0].equals(term_id)) {
                byteoffset = Long.parseLong(a[1]);
                break;
            }
        }
        return byteoffset;
    }
    
    /*
     * Takes a term as an argument and returns the term after stemming it.
     */
    public static String stemTerm(String term) throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException {
        
        Class stemClass = Class.forName("org.tartarus.snowball.ext.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        
        String stemmed_term = "";
        if (term.length() > 0) {
            stemmer.setCurrent(term);
            stemmer.stem();
	    stemmed_term = stemmer.getCurrent();		   
        }
       return stemmed_term;
    }
    
    /*
     * This method takes a term as an argument, calls the stemTerm method to 
     * stem this term, and reads through the file term_info.txt to print the 
     * following information about that term : term_id, number of documents in
     * which the term appears, frequency of the term in the entire corpus.
     */
    public static void listTermInfo(String raw_term) throws IOException, Throwable {
        
        String term = stemTerm(raw_term);
        String termid = getTermId(term);
        
        Path path = Paths.get("term_info.txt");
        Scanner inTermInfo = new Scanner(path);
                
        int num_of_docs = 0;
        long freq_in_corpus = 0;
        long byteoffset =0;
        
        while(inTermInfo.hasNextLine()) {
            String str = inTermInfo.nextLine();
            String[] a = str.split("\t");
            if(a[0].equals(termid)) {
                byteoffset = Long.parseLong(a[1]);
                num_of_docs = Integer.parseInt(a[3]);
                freq_in_corpus = Long.parseLong(a[2]);
                break;
            }
        }
        System.out.println("\nListing for term: "+term);
        if (termid.equals("")) {
            System.out.println("No match found for term: "+term);
        } else {
            System.out.println("TERMID : "+termid);
            System.out.println("Number of documents containing term: "+num_of_docs);
            System.out.println("Term frequency in corpus: "+freq_in_corpus);
            System.out.println("Inverted list offset: "+byteoffset);
        }
    }
    
    /*
     * This method takes a term and a document name, calls the stemTerm method
     * to stem the term, calls the getByteOffset method to get the byte offset
     * of this term in the term_index file and then jumps to this offset in the 
     * file term_index.txt and prints the termid, docid, term frequency of this 
     * term in this document and positions of this term in this document. 
     */
    public static void termIndexInfo(String raw_term, String doc) throws 
            IOException, Throwable {
        
        String term = stemTerm(raw_term);
        String termid = getTermId(term);
        String docid = getDocId(doc);
        
        if(termid.equals("")) {
            System.out.println("Term "+term+" not found in corpus");
            System.out.println("Please try another term");
        } else if(docid.equals("")) {
            System.out.println("Document not found: "+doc);
            System.out.println("Please try another document");
        } else {
            int doc_id = Integer.parseInt(docid);
            long offset = getByteOffset(termid);
                
            RandomAccessFile raf = new RandomAccessFile("term_index.txt","r");
            raf.seek(offset);
        
            String str = raf.readLine();
           // System.out.println(str);
            String[] a = str.split("\t");
            int d = 0;
            int p = 0;
            boolean found = false;
            int freq_in_doc=0;
            String positions = "";
        
            for(int i=1; i<a.length; i++) {
                String[] s= a[i].split(":");
                d+=Integer.parseInt(s[0]);
            
                if(d == doc_id) {
                    p+= Integer.parseInt(s[1]);
                    freq_in_doc++;
                    positions = positions+", "+p;
                    found = true;
                }
            }
            System.out.println("Inverted list for term: "+term);
            System.out.println("In document: "+doc);
        
            if(found == false) {
                System.out.println("TERMID: "+termid);
                System.out.println("DOCID: "+docid);
                System.out.println("Term "+term+" not found in document "+doc);
            } else {
                System.out.println("TERMID: "+termid);
                System.out.println("DOCID: "+docid);
                System.out.println("Term frequency in document: "+freq_in_doc);
                //Remove the extra comma from the beginning of positions and print 
                //the rest
                System.out.println("Positions: "+positions.replaceFirst(", ", "")); 
            }
    }
}
}