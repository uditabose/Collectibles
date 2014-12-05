package cs6253.project.client;

import cs6253.project.SpellRequest;
import cs6253.project.SpellResponse;
import cs6253.project.SpellService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author Udita
 */
public class FaultTolerantClient {

    private static List<String[]> serverList = null;
    private static List<String> wordList = null;
    private static int serverTimeout = 0;

    public static void main(String[] args) {

        /**
         * DEBUG ---- */
         
        args = new String[]{"/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS6253/Homeworks/servers", 
            "2", "accomplish", "sPdeR",
            "accoplish", "with", "sPendeR", "uppermost".toUpperCase(),
            "upp".toUpperCase(), "wih"};
        
        if (args.length < 2) {
            System.err.println("Invalid number of arguements "
                    + "\n Valid command is : "
                    + "FaultTolerantClient <server_ip_file> <timeout> [word_list] ");
            System.exit(1);
        }

        extractServerIps(args[0]);

        if (serverList.isEmpty()) {
            System.err.println("Server IPs not found");
            return;
        }

        // randomizing the server ip:port combination
        Collections.shuffle(serverList, new Random(System.currentTimeMillis()));

        serverTimeout = Integer.parseInt(args[1]);
        wordList = new ArrayList<String>();

        // the word list
        for (int i = 2; i < args.length; i++) {
            wordList.add(args[i]);
        }

        // will try to connect one available server
        trySpellCheckWithTimeout();
    }

    /**
     *
     * @param serverIpFilePath - a file containing server ip and port as
     * blank-space separated string, one server per line ex : <server_ip>
     * <server_port>
     * 192.168.1.2 12345 192.168.10.89 45678 ...
     */
    private static void extractServerIps(String serverIpFilePath) {
        serverList = new ArrayList<String[]>();
        File serverIpFile = new File(serverIpFilePath);
        if (serverIpFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(serverIpFile));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    serverList.add(line.trim().split(" ")); //ex: {"192.168.1.2", "12345"}
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Server IP File is not available");
            } catch (IOException ex) {
                System.err.println("Server IP File is not readable");
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    // nothing to do here
                }
            }
        }
    }

    private static void trySpellCheckWithTimeout() {
        for (String[] serverCred : serverList) {
            if(requestNextServer(serverCred)) {
                break;
            }
        }
    }

    private static boolean requestNextServer(String[] serverCred) {
        //System.out.println("requestNextServer");
        TTransport transport = null;
        try {
            // binds the server
            System.out.println("Trying ... " + serverCred[0] + ":" + serverCred[1]);
            transport = new TSocket(serverCred[0], Integer.parseInt(serverCred[1]), serverTimeout * 1000);
            transport.open();

            // data transfer protocol, simple binary protocol used
            TProtocol protocol = new TBinaryProtocol(transport);
            SpellService.Client client = new SpellService.Client(protocol);

            // the spell request wrapper
            SpellRequest request = new SpellRequest(wordList);

            // sending the request to the server
            SpellResponse checkedList = client.spellcheck(request);
            List<Boolean> isCorrectList = checkedList.getIs_correct();

            List<String> spellCheckList = new ArrayList<String>();
            for (int spCnt = 0; spCnt < isCorrectList.size(); spCnt++) {
                if (!isCorrectList.get(spCnt)) {
                    spellCheckList.add(wordList.get(spCnt));
                }
            }
            System.out.println("Final list : " + spellCheckList);
            return true;
        } catch (TException x) {
            System.err.println(" Exception : " + x.getMessage());
        } finally {
            closeTransport(transport);
        }
        
        return false;
    }

    private static void closeTransport(TTransport transport) {
        if (transport != null) {
            transport.close();
        }
    }
}
