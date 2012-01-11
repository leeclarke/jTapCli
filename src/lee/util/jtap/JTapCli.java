/**
 * License: This is free do what ya want with it! Or to be more specific for all you lawyers out there... I put this into the public domain. 
 * Note: Any License which applies to any of the libraries used in the application retain their respective licensing agreements including but, not limited to the jTap utility.
 */
package lee.util.jtap;

import java.util.HashMap;

import lee.util.jtap.model.JTapSession;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.membase.jtap.TapStreamClient;
import com.membase.jtap.exporter.Exporter;
import com.membase.jtap.exporter.FileExporter;
import com.membase.jtap.ops.CustomStream;

/**
 * CLI tool to gather data on the contents of a membase instance.
 * @author leeclarke
 */
public class JTapCli {
    public static JTapSession session;

    /**
     * @return
     */
    protected static String dumpKeys() {
        TapStreamClient client;
        JTapCli.session = JTapSession.load();
        if (JTapCli.session == null) {
            JTapCli.session = new JTapSession("localhost", "default");
        }
        client = new TapStreamClient(JTapCli.session.getHost(), JTapCli.session.getPort(), JTapCli.session.getBucket(), JTapCli.session.getPassword());
        Exporter exporter = new FileExporter(JTapCli.session.getFileDumpName());
        CustomStream tapListener = new CustomStream(exporter, JTapCli.session.getHost());
        tapListener.keysOnly();
        tapListener.doDump();
        client.start(tapListener);
        return "Key Dump results written to file > " + JTapCli.session.getFileDumpName();
    }
    
    /**
     * @return
     */
    protected static String dumpAll() {
        TapStreamClient client;
        JTapCli.session = JTapSession.load();
        if (JTapCli.session == null) {
            JTapCli.session = new JTapSession("localhost", "default");
        }
        client = new TapStreamClient(JTapCli.session.getHost(), JTapCli.session.getPort(), JTapCli.session.getBucket(), JTapCli.session.getPassword());
        Exporter exporter = new FileExporter(JTapCli.session.getFileDumpName());
        CustomStream tapListener = new CustomStream(exporter, JTapCli.session.getHost());
        tapListener.doDump();
        client.start(tapListener);
        return "Dump results written to file > " + JTapCli.session.getFileDumpName();
    }
    
    /**
     * @param argsMap
     * @return
     */
    protected static String setSession(HashMap<String, String> argsMap) {
        if(!argsMap.containsKey("host")|| !argsMap.containsKey("bucket")){
            return "Host and bucket values are required for session to be set.";
        }
        int portInt = 0;
        try{
            if(argsMap.containsKey("port")){
                portInt = Integer.parseInt(argsMap.get("port"));
            }
            if(portInt == 0){
                portInt = JTapSession.DEFAULT_PORT;
            }
        } catch(Exception e){
            //Just use default.
            portInt = JTapSession.DEFAULT_PORT;
        }
        JTapCli.session = new JTapSession(argsMap.get("host"),portInt,argsMap.get("bucket"),argsMap.get("password"));
        JTapCli.session.save();
        return "Session set. host="+JTapCli.session.getHost() + ":"+JTapCli.session.getPort() + " bucket=" + JTapCli.session.getBucket();
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "jtapc", options );
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = constructOptions();
        
        
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Cmd Parsing Error:   " + e.getMessage());
            JTapCli.printHelp(options);
        }
        String resultMessage = "Done.";
        if(cmd.hasOption("dumpkeys")) {
            resultMessage = JTapCli.dumpKeys();
        } 
        else if(cmd.hasOption("dumpall")) {
            resultMessage = JTapCli.dumpAll();
        }
        else if(cmd.hasOption("s") || cmd.hasOption("session")) {
            HashMap<String, String> argsMap = getMappedArgs(cmd, "host","bucket","password","port");
            resultMessage = ">> "+JTapCli.setSession(argsMap);
        }
        else if(cmd.hasOption("cs") || cmd.hasOption("clearsession")) {
            boolean removed = JTapCli.session.delete();
            if(removed){
                System.out.println("Saved session data has been cleared. To set the values again call with -session");
            }
        }
        else {
            JTapCli.printHelp(options);
            resultMessage = "";
        }
        System.out.println(resultMessage);
        return;
    }

    protected static HashMap<String, String> getMappedArgs(CommandLine cmd, String... argNames) {
        HashMap<String, String> argMap = new HashMap<String, String>();
        String[] optArgs = cmd.getOptionValues("s");
        for(int a = 0;  a < optArgs.length; a++) {
            argMap.put(argNames[a], optArgs[a]);
        }
        return argMap;
    }

    public static Options constructOptions()  
    {
        final Options options = new Options();
        options.addOption("h",false, "Print help for this application");
        options.addOption("dumpkeys",false, "Dumps keys for the specified bucket");
        options.addOption("dumpall",false, "Dumps keys and values for the specified bucket");
        
        Option session = OptionBuilder.withArgName("host,bucket,[port]").hasArg().withDescription(  "host address of membase server, bucket name and optional port if not default." ).create( "s" );
        session.setLongOpt("session");
        session.setValueSeparator(',');
        session.setArgs(3);
        
        options.addOption(session);
        options.addOption("cs","clearsession",false, "Clear session info from drive.");
        return options;
    }


}
