package cs6253.project.server;

import cs6253.project.SpellRequest;
import cs6253.project.SpellResponse;
import cs6253.project.SpellService;
import cs6253.project.util.ShardingUtil;
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
 * CS6253 - Project 3
 * Sharding For Scalability
 * 
 * The ShardedSpellServer class is responsible for creating and 
 * starting the server thread.
 * 
 * ShardedSpellServer listens for incoming spell check requests at the 
 * designated port. When a request arrives, it invokes the processor,
 * which handles the spell checking logic.
 * 
 * ShardedSpellServer lazy loads the dictionary. When the first request
 * arrives, it determines the least significant bit of MD5 hash of the
 * first word in the list. Ex: for a list ["abc", "def", "ijk"], it
 * computes the MD5 hash of "abc", and tests it's least-significant bit.
 * If the bit is zero (0) then loads the shard_0_words, else shard_1_words
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
public class ShardedSpellServer {

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
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(ShardedSpellServer.processor));

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
class ShardedSpellHandler implements SpellService.Iface {

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
        
        ShardedSpellDictionary dictionary = 
                ShardedSpellDictionary.getInstanceForShard(ShardingUtil.getLeastSignificantBit(toCheckList.get(0)));

        for (String stringToCheck : toCheckList) {
            checkedList.add(dictionary.isWordFound(stringToCheck));         
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
class ShardedSpellDictionary {
    
    // cache for the linux wordlist
    private static final Set<String> _dictionary = new HashSet<>();
    
    private static class Holder {
        private static final ShardedSpellDictionary SSD = new ShardedSpellDictionary();
    }
    
    // static block, loads the word list at the time of class loading
    private ShardedSpellDictionary(){
        // private constructor to prevent instantiation
    }
    
    // loads the shard file, and return the ShardedSpellDictionary instance
    public static ShardedSpellDictionary getInstanceForShard(int dictionaryShard) {
        Holder.SSD.loadDictionary(dictionaryShard);
        return Holder.SSD;
    }

    //Loads the dictionary
    private void loadDictionary(int dictionaryShard) {
        if (!_dictionary.isEmpty()) {
            return;
        }
        System.out.println("Loading dictionary : " + dictionaryShard);
        BufferedReader br = null;
        try {
            // buffered reader used as it exposes the readLine() method
            br = new BufferedReader(new InputStreamReader(
                    SpellDictionary.class.getResourceAsStream("shard_"+ dictionaryShard +"_words")));
            String line = null;
            while ((line = br.readLine()) != null) {
                // converted to lower case to avoid case-sensitivity
                _dictionary.add(line); 
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

    /**
     * @param word word to be spell-checked
     * @return true, if available in dictionary, otherwise false 
     */
    public boolean isWordFound(String word) {
        word = word.charAt(0) + word.substring(1).toLowerCase();
        return _dictionary.contains(word.trim());
    }
}
