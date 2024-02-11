package fileAnalyze;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LogDataAnalyze {
    private Map<String, Integer> responseData;
    private Map<String, Integer> reqKeyData;
    private Map<String, Integer> browserData;
    private int[] timeData;

    public LogDataAnalyze() {
        responseData = new HashMap<>();
        reqKeyData = new HashMap<>();
        browserData = new HashMap<>();
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

    public String showBrowserData() {
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

    public int getMaxTimeCount() {
        int max = -1;

        for (int i = 0; i < 24; i++) {
            if (max < timeData[i]) {
                max = timeData[i];
            }
        }

        return max;
    }

    public void showTimeData() {
        for (int i = 0; i < 24; i++) {
            System.out.println(i + "시 - " + getTimeCount(i));
        }
    }
}
