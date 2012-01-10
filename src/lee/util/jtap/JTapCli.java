/**
 * License: This is free do what ya want with it!
 */
package lee.util.jtap;

import lee.util.jtap.model.JTapSession;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.membase.jtap.TapStreamClient;
import com.membase.jtap.exporter.Exporter;
import com.membase.jtap.exporter.FileExporter;
import com.membase.jtap.ops.CustomStream;

/**
 * 
 * @author leeclarke
 */
public class JTapCli {

    //TODO: add ability to set ip/port and bucket for easier cli calls later.
    public static JTapSession session;
    
    
    public static void dumpKeys() {
        TapStreamClient client;
        if (session == null) {
            client = new TapStreamClient("172.16.62.178", 11210, "PEPCOM_Cart", "pepcom_cart");
        } else {
            //TODO: change after the 
            client = new TapStreamClient("172.16.62.178", 11210, "PEPCOM_Cart", "pepcom_cart");
        }
        Exporter exporter = new FileExporter("results.txt");
        CustomStream tapListener = new CustomStream(exporter, "172.16.62.178");
        tapListener.keysOnly();
        tapListener.doDump();
        client.start(tapListener);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Options options = new Options();
        options.addOption("h",false, "Print help for this application");
        options.addOption("s",true, "Sets the session params for ease of use. [host-ip, port, bucket, password]");
        options.addOption("session",true, "Sets the session params for ease of use. [host-ip, port, bucket, password]");
        options.addOption("c",true, "Clear session");
        options.addOption("clear",true, "Clear session");
        options.addOption("dumpkeys",true, "Dumps keys for the specified bucket");
        options.addOption("keys",true, "Dumps keys for the specified bucket");
        options.addOption("dumpkeys",true, "Dumps keys for the specified bucket");
        
        
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = null;
        
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO: default to help
        }
        
        //http://commons.apache.org/cli/usage.html
        //TODO:
        
        //just do it.
        JTapCli.dumpKeys();
        return;
        
    }
    


}
