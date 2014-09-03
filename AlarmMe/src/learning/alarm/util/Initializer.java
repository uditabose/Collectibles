/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.alarm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ubose
 */
public class Initializer {
    
    public void initialize() throws IOException {
    
        // TODO : Take an input folder path
        String configFolderPath = "D:\\Learning\\AlarmMe";
        
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(configFolderPath, Constants.INIT.CONFIG_FILE)));
        
        Map<String, Object> initMap = new HashMap<String, Object>();
        
        initMap.put(Constants.INIT.FINISH_TIME, properties.getProperty(Constants.INIT.FINISH_TIME));
        
        properties.clear();
        
        //properties.load(new FileInputStream(new File(configFolderPath, Constants.INIT.TIME_LOG)));
        
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
        
        System.out.println(sdf.format(today));
        
        
    }
    
    public static void main(String[] args) {
        try {
            new Initializer().initialize();
        } catch (IOException ex) {
            Logger.getLogger(Initializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
