package fileAnalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LogFileParsing {
    private final static String LOG_PATH = "./log";

    private File logPath;
    private File[] logs;
    private FileReader fReader;
    private BufferedReader bufReader;
    private LogDataAnalyze logAnalyze;
    private LogDataAnalyze customAnalyze;

    public LogFileParsing() throws IOException {
        logAnalyze = new LogDataAnalyze();
        logPath = new File(LOG_PATH);
        logs = logPath.listFiles();

        defaultParsing();
        customizedParsing(1, 3);
        testParsing();
    }

    public LogDataAnalyze getCustomAnalyze() {
        return customAnalyze;
    }

    public void defaultParsing() throws IOException {
        for (File log : logs) {
            openStream(log);
            readStream();
            closeStream();
        }
    }

    public void customizedParsing(int start, int end) throws IOException {
        customAnalyze = new LogDataAnalyze();

        for (File log : logs) {
            openStream(log);
            readStream(start, end);
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
     * Before : [200][http://sist.co.kr/find/books?key=mongodb&query=sist][ie][2024-02-07 09:35:16]
     * After : 200 books mongodb ie 240207 09
     */
    public void readStream() throws IOException {
        String temp = null;
        String[] parsedLine = null;

        while ((temp = bufReader.readLine()) != null) {
            parsedLine = refineString(temp);
            logAnalyze.saveRespCodeData(parsedLine[0]);
            logAnalyze.saveURLData(parsedLine[1].replace("/", ""), Integer.parseInt(parsedLine[0]));
            logAnalyze.saveKeyData(parsedLine[2]);
            logAnalyze.saveBrowserData(parsedLine[3]);
            logAnalyze.saveTimeData(Integer.parseInt(parsedLine[5]));
        }
    }

    public void readStream(int start, int end) throws IOException {
        String temp = null;
        String[] parsedLine = null;

        while (start > 1 && bufReader.readLine() != null) {
            start -= 1;
        }

        while (end != 0 && (temp = bufReader.readLine()) != null) {
            parsedLine = refineString(temp);
            customAnalyze.saveRespCodeData(parsedLine[0]);
            customAnalyze.saveURLData(parsedLine[1].replace("/", ""), Integer.parseInt(parsedLine[0]));
            customAnalyze.saveKeyData(parsedLine[2]);
            customAnalyze.saveBrowserData(parsedLine[3]);
            customAnalyze.saveTimeData(Integer.parseInt(parsedLine[5]));
            end -= 1;
            System.out.println(temp);
        }
    }

    public String[] refineString(String temp) {
        temp = temp.replaceAll("\\[", "")
                .replaceAll("\\]", " ")
                .replaceAll("http://sist.co.kr/find", "")
                .replaceAll("\\?key=", " ")
                .replaceAll("key=", " ")
                .replaceAll("&query=sist", "")
                .replaceAll("\\?query=sist", " null")
                .replaceAll("(\\d{2})(\\d{2})-(\\d{2})-(\\d{2})", "$2$3$4")
                .replaceAll("(\\d{2}):([a-zA-Z0-9]+):([a-zA-Z0-9]+)", "$1");

        return temp.split(" ");
    }

    public void testParsing() {
        System.out.println(
                "200 : " + logAnalyze.getRespCodeCount("200") + " ratio : " + logAnalyze.getRespCodeRatio("200"));
        System.out.println(
                "403 : " + logAnalyze.getRespCodeCount("403") + " ratio : " + logAnalyze.getRespCodeRatio("403"));
        System.out.println(
                "404 : " + logAnalyze.getRespCodeCount("404") + " ratio : " + logAnalyze.getRespCodeRatio("404"));
        System.out.println(
                "500 : " + logAnalyze.getRespCodeCount("500") + " ratio : " + logAnalyze.getRespCodeRatio("500"));

        logAnalyze.showKeys();
        System.out.println("res " + logAnalyze.getKeyRatio("res"));
        System.out.println("jsp " + logAnalyze.getKeyRatio("jsp"));
        System.out.println("HTML " + logAnalyze.getKeyRatio("HTML"));
        System.out.println("javascript " + logAnalyze.getKeyRatio("javascript"));
        System.out.println("java " + logAnalyze.getKeyRatio("java"));
        System.out.println("d8 " + logAnalyze.getKeyRatio("d8"));
        System.out.println("hadoop " + logAnalyze.getKeyRatio("hadoop"));
        System.out.println("front " + logAnalyze.getKeyRatio("front"));
        System.out.println("mongodb " + logAnalyze.getKeyRatio("mongodb"));
        System.out.println("AWS " + logAnalyze.getKeyRatio("AWS"));
        System.out.println("ora " + logAnalyze.getKeyRatio("ora"));
        System.out.println(
                "Max : " + logAnalyze.getMaxCountKey() + ", " + logAnalyze.getKeyCount(logAnalyze.getMaxCountKey()));

        System.out.println(logAnalyze.getBrowserData());
        System.out.println(logAnalyze.showTimeData());
        System.out.println(logAnalyze.showURLData());
        System.out.println(logAnalyze.getURLRespData("books", 500));
    }
}
