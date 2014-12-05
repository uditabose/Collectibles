SpellCheck 
-----------------------------------------------------------
Implemented by Udita Bose (NetID : ub298, udita.bose@nyu.edu)
Fault tolerant Spell Check client 


Build 
-----------------------------------------------------------
Modify hbuild.xml 
1. Modify the value of property "thrift.lib" to the local thrift jar location
2. Modify the value of property "lib.dir" to the dependent library directory

Build command : ant -f hbuild.xml

Run
-----------------------------------------------------------
Run server -
java -jar SpellCheck.jar  cs6253.project.server.SpellServer [optional_port_number]

Run client -
java -jar SpellCheck.jar  cs6253.project.client.FaultTolerantClient <server_list_file> <timeout> [word_list] 

or 

java -jar SpellCheck.jar  cs6253.project.client.SpellClient <server_list_file> <timeout> [word_list] 


