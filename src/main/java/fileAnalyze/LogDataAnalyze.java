package fileAnalyze;

import java.io.IOException;

public class LogDataAnalyze {
    public LogDataAnalyze(){
        try {
            new LogFileParsing();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
