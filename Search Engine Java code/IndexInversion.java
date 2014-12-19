
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class IndexInversion {
    
    //A map to store the term_id and doc_id:position pairs for each term
    static Map<Integer,List<String>> termIdMap = new LinkedHashMap<Integer, List<String>>();
    
    // A map to store the byte offset for each term before writing to term_index.txt
    static Map<Integer, Long> offsetMap = new LinkedHashMap<Integer, Long>();
    
    public static void main(String args[]) throws IOException {
        String fileName = "doc_index.txt";
        Path path = Paths.get(fileName);
        Scanner sc = new Scanner(path);
        
        //This loop calls the createMap method for every line in doc_index.txt
        while(sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] a = str.split("\t");
            
            String doc_id = a[0];
            int term_id = Integer.parseInt(a[1]);
            List<Integer> positions = new LinkedList<Integer>();
            for(int i=2; i<a.length; i++) {
                positions.add(Integer.parseInt(a[i]));
            }
            createMap(doc_id, term_id, positions);
        }
        
        // Calling writeToFile method for writing the file term_index.txt
        writeToFile(termIdMap);
        // Calling writeTermInfo method for writing the file term_info.txt
        writeTermInfo(offsetMap);
    }
    
    /*
     * This method creats a hashmap where the key is the term_ids and the value
     * is a list of strings like doc_id:position for that term_id
     */
    public static void createMap(String doc_id, int term_id, List<Integer> positions) {
                
        List<String> value = termIdMap.get(term_id);
        //sort the list of positions in order to correctly calculate offset from
        //previous position
        Collections.sort(positions);
        //If this term_id is not present in the map yet, value will be null, so 
        //add the doc_id:position string to value and put the term_id, value in
        //the map.
        if(value == null) {
            value = new LinkedList<String>();
            for (Integer p : positions) {
                value.add(doc_id+":"+p);
            }
            termIdMap.put(term_id, value);
        } else {
            for (Integer p : positions) {
                value.add(doc_id+":"+p);
            }
        }        
    }
    
    /*
     * This method takes the hashmap containing <term_id, list of doc_id:position>
     * pairs and writes them to the file term_index.txt
     * 
     * Also creates a map which stores the term_id and the byte offset where
     * each term_id is written in the file term_index.txt
     */
    public static void writeToFile(Map<Integer, List<String>> termIdMap) 
            throws IOException {
                
        //Using RandomAccessFile object to write to file so that its 
        //getFilePointer() method can be used to get the byte offset
        RandomAccessFile out = new RandomAccessFile("term_index.txt", "rw");
        String newLine = System.getProperty("line.separator");
        
        Iterator<Map.Entry<Integer, List<String>>> it = termIdMap.entrySet().iterator();
        int temp_pos=0;
        int offset_pos=0;
        int pos=0;
        int doc_id=0;
        int temp_doc=0;
        int offset_doc=0;
        
        //Writes the termIdMap to file while calculating the doc and position offsets
        //and creating the offsetMap which contains the term_id and its byte
        //offset in the file.
        while(it.hasNext()) {
            Map.Entry<Integer, List<String>> pairs = it.next();
            int term = pairs.getKey();
            offsetMap.put(term,out.getFilePointer());
            out.writeBytes(term+"\t");
            List<String> values = pairs.getValue();
            for (String s : values) {
                String[] a= s.split(":");
                doc_id = Integer.parseInt(a[0]);
                offset_doc = doc_id - temp_doc;
                pos = Integer.parseInt(a[1]);
                if(offset_doc > 0) {
                    offset_pos = pos;
                } else {
                    offset_pos = pos - temp_pos;
                }                
                out.writeBytes(offset_doc+":"+offset_pos+"\t");
                temp_doc = doc_id;
                temp_pos = pos;
            }
            out.writeBytes(newLine);
            temp_doc = 0;
            temp_pos = 0;
        }
        out.close();
    }
    
    /*
     * This method takes the offset map and reads from the file term_index.txt
     * to write to the file term_info.txt
     */
    public static void writeTermInfo(Map<Integer, Long> offsetMap) throws IOException {
        String fileName = "term_index.txt";
        Path path = Paths.get(fileName);
        Scanner sc = new Scanner(path);
        
        BufferedWriter out = new BufferedWriter(new FileWriter("term_info.txt"));
        String newLine = System.getProperty("line.separator");
        long byteoffset = 0;
        int num_of_docs = 0;
        int num_of_pos = 0;
        int term_id = 0;
        String str = "";
        String[] a = {};
        
        //Calculates from term.index.txt the number of documents in which a term
        //occurs, and the frequency of the term in the entire corpus. 
        //Also fetches the byteoffset from the offsetMap and prints all these
        //parameters to term_info.txt
        while(sc.hasNextLine()) {
            num_of_pos=0;
            num_of_docs=0;
            str=sc.nextLine();
            a = str.split("\t");            
            term_id = Integer.parseInt(a[0]);
            byteoffset = offsetMap.get(term_id);
            out.write(term_id+"\t"+byteoffset+"\t");
           
            for(int i=1; i<a.length; i++) {
                num_of_pos++;
                String[] parts=a[i].split(":");
                if(Integer.parseInt(parts[0])>0) {
                    num_of_docs++;
                }
            }
            out.write(num_of_pos+"\t"+num_of_docs+newLine);
        }
        out.close();
    }
}
