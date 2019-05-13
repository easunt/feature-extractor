package com.ovio.extractor.utils;

import net.minidev.json.JSONArray;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlFeatureUtil {
    private UrlParser urlParser;
    private final static String[] badTerms = {"aaa", "bbb", "ccc"}; //FIXME : maneged using enum

    public String extractUrlFeatures(String targetUrl) throws Exception {
        this.urlParser = new UrlParser(targetUrl);
        List<String> resultList = new ArrayList<>();
        resultList.add(this.numberOfSlash());
        resultList.add(this.lengthOfSubDomain());
        resultList.add(this.ttlValue());
        resultList.add(this.rankOfAlexa());
        resultList.add(this.levenDistWithGoogleSuggestion(true));

        System.out.println(resultList);
        return null;
    }

    private String numberOfSlash() {
        return Integer.toString(this.urlParser.getTargetUrl().split("/").length - 1);
    }

    private String lengthOfSubDomain() {
        return Integer.toString(this.urlParser.getSubDomain().length());
    }

    public String levenDistWithGoogleSuggestion(boolean isPrimary) throws Exception{
        String target = isPrimary ? urlParser.getPrimaryDomain() : urlParser.getSubDomain();
        URL url = new URL("http://suggestqueries.google.com/complete/search?output=firefox&q=" + target);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        System.out.println(reader.readLine());
        //JSONParser jsonParser = new JSONParser(reader.readLine());


        return null;
    }

    public String ttlValue() {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        BufferedReader successBufferReader = null;
        String message = "-1";

        try {
            process = runtime.exec("ping " + this.urlParser.getHost());
            successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            int count = 0;
            while ((message = successBufferReader.readLine()) != null) {
                if (message.toLowerCase().contains("ttl=") || count > 5) //FIXME : insert timeout logic
                    break;
                count++;
            }
            if (!StringUtils.isEmpty(message))
                message = message.split("ttl=")[1].split(" ")[0];   //FIXME : parsing logic for "icmp_seq=0 ttl=239 time=36.424 ms"

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (process != null) process.destroy();
                if (successBufferReader != null) successBufferReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public String recordOfWhois() {
        return null;
    }

    public String rankOfAlexa() {
        //use Document builder when parse xml file. Because xml file is very small. SAX is useful when xml file is big.
        String result = "-1";
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("http://data.alexa.com/data?cli=10&dat=snbamz&url=" + urlParser.getHost());
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node rank = (Node) xpath.evaluate("/ALEXA//SD/POPULARITY", document, XPathConstants.NODE);
            if (rank != null) {
                result = rank.getAttributes().getNamedItem("TEXT").getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String numberOfBadTerms() {
        return null;
    }

    private int calculateLevensteinDist(String str1, String str2) {
        if (StringUtils.isEmpty(str1))
            return str2.length();
        if (StringUtils.isEmpty(str2))
            return str1.length();
        if (str1.length() > str2.length()) {
            String tempStr = str1;
            str1 = str2;
            str2 = tempStr;
        }
        int[] str1Array1 = new int[str1.length() + 1];
        int[] str1Array2 = new int[str1.length() + 1];

        for (int i = 1; i <= str1.length(); i++) str1Array1[i] = i;
        str1Array2[0] = 1;

        for (int j = 1; j <= str2.length(); j++) {
            for (int i = 1; i <= str1.length(); i++) {
                int c = (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1);
                str1Array2[i] = Math.min(str1Array1[i - 1] + c, Math.min(str1Array1[i] + 1, str1Array2[i - 1] + 1));
            }

            int[] temp = str1Array1;
            str1Array1 = str1Array2;
            str1Array2 = temp;
            str1Array2[0] = str1Array1[0] + 1;
        }
        return str1Array1[str1.length()];
    }
}
