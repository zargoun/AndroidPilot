package com.barbermot.pilot;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import de.mindpipe.android.logging.log4j.LogCatAppender;

public class Log4jInitializer {
    
    static {
        
        PatternLayout terse = new PatternLayout("%c %r %m%n");
        PatternLayout verbose = new PatternLayout("%r %-5p %c{2} - %m%n");
        
        ConsoleAppender console = new ConsoleAppender(verbose);
        console.setImmediateFlush(true);
        console.setThreshold(Level.DEBUG);
        
        LogCatAppender logcat = new LogCatAppender(terse);
        logcat.setThreshold(Level.DEBUG);
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(logcat);
        
        FileAppender data;
        try {
            String dataFile = "/sdcard/barbermot/data.txt";
            data = new FileAppender(terse, dataFile);
            data.setBufferedIO(true);
            data.setImmediateFlush(false);
            
            String[] logNames = { "AutoControl", "GpsSignal",
            "ThrottleControl", "ThrottleGpsControl", "AileronControl",
            "AileronGpsControl", "RudderControl", "ElevatorControl",
            "ElevatorGpsControl", "Signal" };
            
            for (String name : logNames) {
                Logger logger = Logger.getLogger(name);
                logger.addAppender(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        FileAppender state;
        try {
            state = new FileAppender(terse, "/sdcard/barbermot/state.txt");
            state.setBufferedIO(false);
            state.setImmediateFlush(true);
            Logger logger = Logger.getLogger("FlightState");
            logger.addAppender(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}