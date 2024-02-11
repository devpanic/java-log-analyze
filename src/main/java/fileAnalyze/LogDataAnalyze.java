package fileAnalyze;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LogDataAnalyze {
    private Map<String, Integer> responseData;
    private Map<String, Integer> reqKeyData;
    private Map<String, Integer> browserData;
    private Map<String, int[]> urlRespCodeData;
    private int[] timeData;

    public LogDataAnalyze() {
        responseData = new HashMap<>();
        reqKeyData = new HashMap<>();
        browserData = new HashMap<>();
        urlRespCodeData = new HashMap<String, int[]>();
        timeData = new int[24];
    }

    public void saveRespCodeData(String respCode) {
        if (!responseData.containsKey(respCode)) {
            responseData.put(respCode, 1);
            return;
        }
        responseData.put(respCode, responseData.get(respCode) + 1);
    }

    public int getRespCodeCount(String respCode) {
        if (responseData.containsKey(respCode)) {
            return responseData.get(respCode);
        }
        return -1;
    }

    public double getRespCodeRatio(String respCode) {
        Collection<Integer> counts = responseData.values();
        double sum = 0;

        for (Integer count : counts) {
            sum += count;
        }

        return getRespCodeCount(respCode) / sum;
    }

    public void saveKeyData(String key) {
        if (key.equals("null")) {
            return;
        }

        if (!reqKeyData.containsKey(key)) {
            reqKeyData.put(key, 1);
            return;
        }
        reqKeyData.put(key, reqKeyData.get(key) + 1);
    }

    public int getKeyCount(String key) {
        if (reqKeyData.containsKey(key)) {
            return reqKeyData.get(key);
        }
        return -1;
    }

    public double getKeyRatio(String key) {
        Collection<Integer> counts = reqKeyData.values();
        double sum = 0;

        for (Integer count : counts) {
            sum += count;
        }

        return getKeyCount(key) / sum;
    }

    public String getMaxCountKey() {
        Iterator<String> keyCount = reqKeyData.keySet().iterator();
        int max = -1;
        int temp = 0;
        String ret = "";

        while (keyCount.hasNext()) {
            String key = keyCount.next();
            temp = reqKeyData.get(key);
            if (max < temp) {
                max = temp;
                ret = key;
            }
        }

        return ret;
    }

    public void showKeys() {
        Iterator<String> keyCount = reqKeyData.keySet().iterator();

        while (keyCount.hasNext()) {
            String temp = keyCount.next();
            System.out.println(temp + " : " + reqKeyData.get(temp));
        }
    }

    public void saveBrowserData(String browser) {
        if (!browserData.containsKey(browser)) {
            browserData.put(browser, 1);
            return;
        }
        browserData.put(browser, browserData.get(browser) + 1);
    }

    public int getBrowserCount(String browser) {
        if (browserData.containsKey(browser)) {
            return browserData.get(browser);
        }
        return -1;
    }

    public double getBrowserRatio(String browser) {
        Collection<Integer> counts = browserData.values();
        double sum = 0;

        for (Integer count : counts) {
            sum += count;
        }

        return getBrowserCount(browser) / sum;
    }

    public String getBrowserData() {
        StringBuilder returnStr = new StringBuilder();
        Iterator<String> keyCount = browserData.keySet().iterator();

        while (keyCount.hasNext()) {
            String temp = keyCount.next();
            returnStr.append(temp + " - " + getBrowserCount(temp) + " (" + getBrowserRatio(temp) + "%)\n");
        }

        return returnStr.toString();
    }

    public void saveTimeData(int time) {
        timeData[time] += 1;
    }

    public int getTimeCount(int time) {
        return timeData[time];
    }

    public int getMaxCountTime() {
        int max = -1;
        int maxTime = -1;

        for (int i = 0; i < 24; i++) {
            if (max < timeData[i]) {
                max = timeData[i];
                maxTime = i;
            }
        }

        return maxTime;
    }

    public String showTimeData() {
        StringBuilder timeData = new StringBuilder();

        for (int i = 0; i < 24; i++) {
            timeData.append(i + "시 : " + getTimeCount(i) + "\n");
        }
        
        return timeData.toString();
    }

    public void saveURLData(String url, int respCode) {
        if (url.length() == 0) {
            return;
        }

        if (!urlRespCodeData.containsKey(url)) {
            urlRespCodeData.put(url, new int[4]);
        }

        saveURLRespData(urlRespCodeData.get(url), respCode);
    }

    public void saveURLRespData(int[] urlRespCodeArr, int respCode) {
        switch (respCode) {
            case 200:
                urlRespCodeArr[0] += 1;
                break;
            case 403:
                urlRespCodeArr[1] += 1;
                break;
            case 404:
                urlRespCodeArr[2] += 1;
                break;
            case 500:
                urlRespCodeArr[3] += 1;
                break;
        }
    }

    public int getURLRespData(String url, int respCode) {
        if (!urlRespCodeData.containsKey(url)) {
            return -1;
        }

        int[] urlRespCodeArr = urlRespCodeData.get(url);

        switch (respCode) {
            case 200:
                return urlRespCodeArr[0];
            case 403:
                return urlRespCodeArr[1];
            case 404:
                return urlRespCodeArr[2];
            case 500:
                return urlRespCodeArr[3];
            default:
                return -1;
        }
    }

    public String showURLData() {
        Iterator<String> urlDataIter = urlRespCodeData.keySet().iterator();
        StringBuilder urlData = new StringBuilder();

        while (urlDataIter.hasNext()) {
            String url = urlDataIter.next();
            int[] tempArr = urlRespCodeData.get(url);
            urlData.append(url + ",");
            for (int data : tempArr) {
                urlData.append(data + ",");
            }
            urlData.deleteCharAt(urlData.length() - 1);
            urlData.append("\n");
        }

        return urlData.toString();
    }
}
