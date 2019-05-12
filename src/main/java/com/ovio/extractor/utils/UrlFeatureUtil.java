package com.ovio.extractor.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlFeatureUtil {
    private UrlParser urlParser;
    private final static String[] badTerms = {"aaa", "bbb", "ccc"}; //FIXME : maneged using database

    public String extractUrlFeatures(String targetUrl)throws Exception{
        this.urlParser = new UrlParser(targetUrl);
        List<String> resultList = new ArrayList<>();
        resultList.add(this.numberOfSlash());
        resultList.add(this.lengthOfSubDomain());
        resultList.add(this.ttlValue());

        System.out.println(resultList);
        return null;
    }

    private String numberOfSlash() {
        return Integer.toString(this.urlParser.getTargetUrl().split("/").length - 1);
    }

    private String lengthOfSubDomain() {
        return Integer.toString(this.urlParser.getSubDomain().length());
    }

    public String reputationOfAlexa() {
        return null;
    }

    public String levenDistWithGoogleSuggestion(boolean isPrimary) {
        return null;
    }

    public String ttlValue() {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        BufferedReader successBufferReader = null;
        String message = null;

        try {
            process = runtime.exec("ping " + this.urlParser.getHost());
            successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            int count = 0;
            while ((message = successBufferReader.readLine()) != null) {
                if (message.toLowerCase().contains("ttl=") || count > 5) //FIXME : insert timeout logic
                    break;
                count ++;
            }
            message = message.split("ttl=")[1].split(" ")[0];   //FIXME : parsing logic for "icmp_seq=0 ttl=239 time=36.424 ms"

        } catch (Exception e) {
            message = Integer.toString(-1);
        } finally {
            try {
                if (process != null) process.destroy();
                if (successBufferReader != null) successBufferReader.close();
            } finally {
                return message;
            }
        }
    }

    public String recordOfWhois() {
        return null;
    }

    public String rankOfAlexa() {
        return null;
    }

    public String numberOfBadTerms() {
        return null;
    }
}
