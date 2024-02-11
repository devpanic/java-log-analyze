package fileAnalyze;

import java.io.IOException;

public class RunAnalyze {
    public static void main(String[] args) {
        try {
            new LogFileParsing();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
