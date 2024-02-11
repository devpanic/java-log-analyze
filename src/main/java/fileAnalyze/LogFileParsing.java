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
            readStream();
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

    /*
     * before : [200][http://sist.co.kr/find/books?key=mongodb&query=sist][ie][2024-02-07 09:35:16]
     * After : 200 books=mongodb ie 2024-02-07 09
     */
    public void readStream() throws IOException {
        String temp = null;
        String[] parsedLine = null;

        while ((temp = bufReader.readLine()) != null) {
            parsedLine = refineString(temp);
        }
    }

    public String[] refineString(String temp) {
        System.out.println("before : " + temp);
        temp = temp.replaceAll("\\[", "")
                .replaceAll("\\]", " ")
                .replaceAll("http://sist.co.kr/find/", "")
                .replaceAll("\\?key", "")
                .replaceAll("&query=sist", "")
                .replaceAll("(\\d{2}):(\\d{2}):(\\d{2})", "$1");
        System.out.println("After : " + temp);

        return temp.split(" ");
    }
}
