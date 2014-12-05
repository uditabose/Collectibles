package cs6253.project.server;

import cs6253.project.SpellRequest;
import cs6253.project.SpellResponse;
import cs6253.project.SpellService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * CS6253 - Project 1
 * A simple spell server and client based on Apache Thrift
 * 
 * The SpellServer class is responsible for creating and 
 * starting the server thread.
 * 
 * SpellServer listens for incoming spell check requests at the 
 * designated port. When a request arrives, it invokes the processor,
 * which handles the spell checking logic.
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
public class SpellServer {

    private static SpellService.Processor processor = null; 
    private static ShardedSpellHandler handler = null;
    private static int serverPort = 9010;
    
    /**
     * Creates a simple server using the assigned port
     * If user does not provide a port then uses the default value 9010
     * @param processor implements org.apache.thrift.TProcessor handles the spell-check
     * logic
     * 
     * @see org.apache.thrift.TProcessor
     */
    public static void simple(SpellService.Processor processor) {
        try {
            // transport instance on server port
            TServerTransport serverTransport = new TServerSocket(serverPort);
            
            // simple server instance
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(SpellServer.processor));

            System.out.println("Starting the simple server with port : " + serverPort + " ...");
            
            // fire up the server
            server.serve();
        } catch (Exception e) {
            System.err.println(" Exception at server : " + e.getMessage());
        }
    }

    /**
     *
     * @param args user input, optional, accepts one integer value as server port
     */
    public static void main(String[] args) {
        // spell-check logic
        handler = new ShardedSpellHandler();
        
        // processor wrapper
        processor = new SpellService.Processor(handler);
        
        
        /* DEBUG ----
            args = new String[]{"6789"};
        */
        if (args.length >= 1) {
            // use user supplied port
            serverPort = Integer.parseInt(args[0]);
        }

        // server runner thread
        Runnable simpleServerRunner = new Runnable() {
            @Override
            public void run() {
                simple(processor);
            }
        };
        
        // start the runner thread
        new Thread(simpleServerRunner).start();
    }

}

/**
 * Spell-check handler
 * @author Udita <udita.bose@nyu.edu>
 */
class SpellHandler implements SpellService.Iface {

    /**
     * 
     * @param request wraps the user provided word list
     * @return returns a SpellResponse object, which wraps the response array list
     * @throws TException 
     */
    @Override
    public SpellResponse spellcheck(SpellRequest request) throws TException {
        
        // the return list
        List<Boolean> checkedList = new ArrayList<>();
        
        // word list to be checked
        List<String> toCheckList = request.getTo_check();
        System.out.println(toCheckList);
        
        for (String stringToCheck : toCheckList) {
            checkedList.add(SpellDictionary.isWordFound(stringToCheck));         
        }
        // response wrapper
        SpellResponse response = new SpellResponse(checkedList);
        return response;
    }

}

/**
 * Convenience class, loads and stores the Linux word dictionary.
 * Loads the dictionary just once during the lifetime of the server.
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
class SpellDictionary {
    
    // cache for the linux wordlist
    private static final Set<String> DICTIONARY = new HashSet<String>();
    
    // static block, loads the word list at the time of class loading
    static {
        // load only if empty
        if (DICTIONARY.isEmpty()) {
            loadDictionary();
        }
    }

    //Loads the dictionary
    private static void loadDictionary() {
        BufferedReader br = null;
        try {
            // buffered reader used as it exposes the readLine() method
            br = new BufferedReader(new InputStreamReader(
                    SpellDictionary.class.getResourceAsStream("dictionary")));
            String line = null;
            while ((line = br.readLine()) != null) {
                // converted to lower case to avoid case-sensitivity
                DICTIONARY.add(line.trim().toLowerCase()); 
            }
        } catch (Exception ex) {
            System.err.println(" Can't load dictionary : " + ex.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.err.println(" Exception : " + ex.getMessage());
            }
        }
    }

    private SpellDictionary() {
        // private constructor to prevent instantiation
    }
    
    /**
     * 
     * @param word word to be spell-checked
     * @return true, if available in dictionary, otherwise false 
     */
    public static boolean isWordFound(String word) {
        return DICTIONARY.contains(word.trim().toLowerCase());
    }
}
