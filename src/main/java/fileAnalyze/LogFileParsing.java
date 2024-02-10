package fileAnalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LogFileParsing {
    private File logPath;
    private File[] logs;
    private FileReader fReader = null;
    private BufferedReader bufReader = null;

    public LogFileParsing() throws IOException {
        logPath = new File("./log");
        logs = logPath.listFiles();
        
        for (File log : logs) {
            openStream(log);
            // parse data
            closeStream();
        }
    }

    public void openStream(File log) throws FileNotFoundException {
        fReader = new FileReader(log);
        bufReader = new BufferedReader(fReader);
    }

    public void closeStream() throws IOException {
        bufReader.close();
        fReader.close();
    }
}
