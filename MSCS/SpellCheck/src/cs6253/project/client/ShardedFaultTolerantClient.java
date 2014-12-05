

package cs6253.project.client;

import cs6253.project.SpellRequest;
import cs6253.project.SpellResponse;
import cs6253.project.SpellService;
import cs6253.project.util.ShardingUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * CS6253 - Project 3
 * Sharding For Scalability
 * 
 * The client accepts 2 server credentials file, and request timeout
 * and a list of words.
 * 
 * Client shards the word list and initiates threads, connecting the servers
 * one for each shard. The also creates a monitor to
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
public class ShardedFaultTolerantClient {

    public static void main(String[] args) {

        /**
         * DEBUG ----
         
        args = new String[]{"/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS6253/Homeworks/servers0",
            "/Users/michaelgerstenfeld/Google Drive/MSCS/FALL14/CS6253/Homeworks/servers1",
            "2", "accomplish", "sPdeR",
            "accoplish", "with", "sPendeR", "uppermost".toUpperCase(),
            "upp".toUpperCase(), "wih"};
         */
        
        if (args.length < 3) {
            System.err.println("Invalid number of arguements "
                    + "\n Valid command is : "
                    + "java -cp <path_to>/SpellCheck.jar cs6253.project.client.ShardedFaultTolerantClient "
                    + "<shard0_server_ip_file> <shard1_server_ip_file> <timeout> [word_list] ");
            System.exit(1);
        }

        // server ips and ports
        Queue<String[]> shard0ServerCredentials = extractServerIps(args[0]);
        Queue<String[]> shard1ServerCredentials = extractServerIps(args[1]);

        // randomizing the server ip:port combination
        Collections.shuffle((LinkedList)shard0ServerCredentials, new Random(System.currentTimeMillis()));
        Collections.shuffle((LinkedList)shard1ServerCredentials, new Random(System.currentTimeMillis()));

        int serverTimeout = Integer.parseInt(args[2]) * 1000;
        
        List<String> wordList = new ArrayList<>();
        // the word list
        for (int i = 3; i < args.length; i++) {
            wordList.add(args[i]);
        }

        // starts the requests to servers
        requestServers(shard0ServerCredentials, shard1ServerCredentials, wordList, serverTimeout);
    }

    /**
     *
     * @param serverIpFilePath - a file containing server ip and port as
     *     blank-space separated string, one server per line ex : 
     *     <server_ip> <server_port>
     *     192.168.1.2 12345 
     *     192.168.10.89 45678 
     *     ...
     * @return returns array of server ip-port queues
     * 
     */
    private static Queue<String[]> extractServerIps(String serverIpFilePath) {
        Queue<String[]> serverQueue = new LinkedList<>();
        File serverIpFile = new File(serverIpFilePath);
        if (serverIpFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(serverIpFile));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    serverQueue.add(line.trim().split(" ")); //ex: {"192.168.1.2", "12345"}
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
       return serverQueue; 
        
    }

    private static void requestServers(Queue<String[]> shard0ServerCredentials, 
            Queue<String[]> shard1ServerCredentials, List<String> wordList, int serverTimeout) {
        
        ClientStateMonitor clientStateMonitor = new ClientStateMonitor(wordList);
        clientStateMonitor.initiateClients(new Queue[]{shard0ServerCredentials, shard1ServerCredentials}, serverTimeout);
    }
}

/**
 * Monitors the client state, that is if 
 * a server communication is failed, if new connection should 
 * be initiated, and if all the servers are communicated and
 * combines the result from servers 
 * @author Udita <udita.bose@nyu.edu>
 */
class ClientStateMonitor { 
    // state of the client it is monitoring
        // 0 = at begining
        // 1 = done
        // 2 = start new
    private List<ClientRequestCommand> clientRequestCommands = null;
    private int noOfFinished = 0; // no of completed shards
    private List<String> wordList = null; // the full word list

    ClientStateMonitor(List<String> wordList) {
        this.wordList = wordList;
    }
    
    /**
     * 
     * @param clientState sets the state of the server communication
     * @param commandId which shard
     */
    void setClientState(int clientState, int commandId) {
        
        if (clientState == 1) {
            // shard has finished, may successful or failed
            noOfFinished++;
        } else if (clientState == 2) {
            // either failed so start new, or just start
            setClientState(0, commandId);
            runCommand(commandId);
        }
        
        // if all shards are done, removes the correct words from the word list
        if (noOfFinished == clientRequestCommands.size()) {
            for (ClientRequestCommand clientRequestCommand : clientRequestCommands) {
                this.wordList.removeAll(clientRequestCommand.getCorrectWordList());
            }
            System.out.println("Final list : " + this.wordList);
            System.exit(0);
        }
    }
    
    /**
     * initiates the client-server communication
     * @param queue server ip port
     * @param serverTimeout request timeout
     */
    void initiateClients(Queue[] queue,  int serverTimeout) {

        List[] shardLists = ShardingUtil.populateWordLists(wordList);
        
        // will try to connect one available server
        if (clientRequestCommands == null) {
            clientRequestCommands = new ArrayList<>();
        }
        
        for (int i = 0; i < queue.length; i++) {
            initiateClient(queue[i], shardLists[i], serverTimeout, i + 1);
        }
        
    }
    
    private void initiateClient(Queue<String[]> shardServerCredentials,  
            List<String> shardList, int serverTimeout, int commandId) {
        
        ClientRequestTimer clientRequestTimer = 
                new ClientRequestTimer(this, serverTimeout, commandId);
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        ClientRequestCommand clientRequestCommand = 
                new ClientRequestCommand(shardServerCredentials, shardList, this, commandId);
        this.clientRequestCommands.add(clientRequestCommand);

        scheduledExecutorService.scheduleAtFixedRate(clientRequestTimer, 0, 
                        serverTimeout/1000, TimeUnit.MILLISECONDS);
    }

    private void runCommand(final int commandId) {
        Runnable commandRunner = new Runnable() {

            @Override
            public void run() {
                clientRequestCommands.get(commandId - 1).closeTransport();
                clientRequestCommands.get(commandId - 1).command();
            }
        };
        new Thread(commandRunner).start();
    }
}

/**
 * Executes spellcheck operation and updates the client monitor
 * status
 * @author Udita <udita.bose@nyu.edu>
 */
class ClientRequestCommand {
    private int id = 0;
    private Queue<String[]> serverCredentials = null;
    private List<String> wordList = null;
    private ClientStateMonitor clientStateListener = null;
    private TTransport transport = null;

    public ClientRequestCommand(Queue<String[]> serverCredentials, 
            List<String> wordList, ClientStateMonitor clientStateListener, int id) {
        this.id = id;
        this.serverCredentials = serverCredentials;
        this.wordList = wordList;
        this.clientStateListener = clientStateListener;
    }

    /**
     * connects to next server,
     * updates the client state monitor
     */
    void command() {        
        try {
            // binds the server
            String[] serverCred = serverCredentials.poll();
            if (serverCred == null) {
                this.clientStateListener.setClientState(1, this.id);
                return;
            }
            System.out.println("Trying ... " + serverCred[0] + ":" + serverCred[1]);
            transport = new TSocket(serverCred[0], Integer.parseInt(serverCred[1]));
            transport.open();

            // data transfer protocol, simple binary protocol used
            TProtocol protocol = new TBinaryProtocol(transport);
            SpellService.Client client = new SpellService.Client(protocol);

            // the spell request wrapper
            SpellRequest request = new SpellRequest(wordList);

            // sending the request to the server
            SpellResponse checkedList = client.spellcheck(request);
            List<Boolean> isCorrectList = checkedList.getIs_correct();

            List<String> correctList = new ArrayList<>();
            for (int spCnt = 0; spCnt < isCorrectList.size(); spCnt++) {
                if (isCorrectList.get(spCnt)) {
                    correctList.add(wordList.get(spCnt));
                }
            }
            this.wordList = correctList;
            this.clientStateListener.setClientState(1, this.id);
        } catch (TException x) {
            System.err.println(" Exception : " + x.getMessage());
        } finally {
            closeTransport();
        }
    }
    
    /**
     * 
     * @return only the correct words
     */
    List<String> getCorrectWordList() {
        return this.wordList;
    }
    
    void stopCommand() {
        closeTransport();
        this.serverCredentials.clear();
    }

    void closeTransport() {
        if (transport != null) {
            transport.close();
        }
    }  
}

/**
 * This is background thread for each shard server communication,
 * keeps on checking if the request is taking more time than server timeout,
 * if so then updates the state of Client State Monitor, so that new server
 * can be communicated
 * 
 * @author Udita <udita.bose@nyu.edu>
 */
class ClientRequestTimer implements Runnable {
    private ClientStateMonitor clientStateListener = null;
    private int timeout = 0;
    private int commandId = 0;
    private static long startTime = 0l;
    
    public ClientRequestTimer(ClientStateMonitor clientStateListener, int timeout, int commandId) {
        this.clientStateListener = clientStateListener;
        this.timeout = timeout;
        startTime = System.currentTimeMillis();
        this.commandId = commandId;
    }

    @Override
    public void run() {        
        long thisTime = System.currentTimeMillis();
        if (thisTime - startTime >= timeout) {
            startTime = System.currentTimeMillis();
            clientStateListener.setClientState(2, this.commandId);
        }
    }
}
