/**
 * License: This is free do what ya want with it! Or to be more specific for all you lawyers out there... I put this into the public domain. 
 * Note: Any License which applies to any of the libraries used in the application retain their respective licensing agreements including but, not limited to the jTap utility.
 */
package lee.util.jtap.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Just a Value object for holding the users session info.
 * @author leeclarke
 */
public class JTapSession implements Serializable{
    private static final long serialVersionUID = -4089631251207710335L;
    private static final String SAVE_FILE = "jtap-session.ser";
    public static final int DEFAULT_PORT = 11210;
    public static final String DEFAULT_DUMP_FILE = "dump.txt";
    private String host;
    private int port;
    private String password;
    private String bucket;
    private String fileDumpName = DEFAULT_DUMP_FILE;
    
    /**
     * Default constructor.
     */
    public JTapSession(){
    }
    
    /**
     * Convenience constructor.
     * @param host - host ip address.
     * @param port - the port address, probably 11210
     * @param bucket - bucket name
     * @param password - bucket password
     */
    public JTapSession(String host, int port, String bucket, String password){
        this.host = host;
        this.port = port;
        this.bucket = bucket;
        this.password = password;
    }
    
    /**
     * Convenience constructor, will work on dev env where use took defaults and no password.
     * @param host
     * @param bucket
     */
    public JTapSession(String host, String bucket) {
        this.host = host;
        this.bucket = bucket;
    }

    /**
     * Saves the contents to a file for later reuse.
     */
    public void save(){
        //TODO: Add encryption to the serialization to ensure security. Use the provided password.
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(SAVE_FILE);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * De-serialize the JTapSession if one is present.
     * @return jtapSession or null if none exists.
     */
    public static JTapSession load() {
        JTapSession jtapSession = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(SAVE_FILE);
            in = new ObjectInputStream(fis);
            jtapSession = (JTapSession) in.readObject();
            in.close();
        } catch (IOException ex) {
            jtapSession = null;
        } catch (ClassNotFoundException ex) {
            jtapSession = null;
        }
        return jtapSession;
    }
    
    /**
     * Deletes the save location.
     * @return - success
     */
    public boolean delete(){
        File serFile = new File(SAVE_FILE);
        try{
            if(serFile.exists()){
                serFile.delete();
            }
        }catch(RuntimeException re){
            re.printStackTrace();
            return false;
        }
        return true;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileDumpName() {
        return this.fileDumpName;
    }

    public void setFileDumpName(String fileDumpName) {
        this.fileDumpName = fileDumpName;
    }
}
