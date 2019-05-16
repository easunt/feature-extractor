package com.ovio.extractor.utils;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.apache.commons.net.whois.WhoisClient;

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
    private final String[] badTerms = {"update", "confirm", "user", "customer", "client", "suspend", "restrict",
            "hold", "verify", "account", "login", "username", "password", "ssn", "sosical", "security", "emailID", "emailPASS",
            "phone", "signin", "hotmail", "expires", "notification", "cancellation", "immediately"}; //FIXME : maneged using enum or something
    private Logger logger = LoggerFactory.getLogger("com.ovio.extractor.utils.UrlFeatureUtil");
    private CommonUtil commonUtil = new CommonUtil();

    public String extractFeatures(String targetUrl) throws Exception {
        this.urlParser = new UrlParser(targetUrl);
        List<String> resultList = new ArrayList<>();
        this.logger.info("=======================================================================================");
        this.logger.info("Extract url features. Target url is [" + targetUrl + "].");
        resultList.add(this.numberOfSlash());
        resultList.add(this.lengthOfSubDomain());
        resultList.add(this.ttlValue());
        resultList.add(this.rankOfAlexa());
        resultList.add(this.levenDistWithGoogleSuggestion(true));
        resultList.add(this.levenDistWithGoogleSuggestion(false));
        resultList.add(this.recordOfWhois());
        resultList.add(this.numberOfBadTerms());
        resultList.add(this.hasPrefixAndSuffix());

        this.logger.info("URl feature extracting result is " + resultList.toString() + ".");
        this.logger.info("=======================================================================================");

        return resultList.toString();
    }

    //FIXME: google page rank url occur 404 not found error. Maybe service was closed or changed different url. Find new url.
    private String googlePageRank() {
        JenkinsHash jenkinsHash = new JenkinsHash();
        long hash = jenkinsHash.hash(("info:" + this.urlParser.getHost()).getBytes());
        try {

            URL url = new URL("http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&ch=6" + hash + "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + this.urlParser.getHost());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = reader.readLine();
            System.out.println(output);
            conn.disconnect();

        } catch (Exception e) {
            return "-1";
        }
        return null;
    }

    private String numberOfSlash() {
        String result = Integer.toString(this.urlParser.getTargetUrl().split("/").length - 1);
        this.logger.info("URL feature (Number of Slash in URL) result  is " + result + ".");
        return result;
    }

    private String lengthOfSubDomain() {
        String result = Integer.toString(this.urlParser.getSubDomain().length());
        this.logger.info("URL feature (Length of sub domain) result  is " + result + ".");
        return result;
    }

    private String levenDistWithGoogleSuggestion(boolean isPrimary) {
        String target = isPrimary ? urlParser.getPrimaryDomain() : urlParser.getSubDomain();
        String suggestion = "";
        String result = "0";

        if (!StringUtils.isEmpty(target)) {
            try {
                URL url = new URL("http://suggestqueries.google.com/complete/search?output=firefox&q=" + target);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String jsonString = reader.readLine();

                connection.disconnect();
                reader.close();

                JSONArray jsonArray = new JSONArray(jsonString);
                JSONArray suggestArray = (JSONArray) jsonArray.get(1);
                this.logger.debug("Google suggestion for [" + target + "] is " + suggestArray);
                suggestion = (String) suggestArray.get(0);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                result = Integer.toString(this.commonUtil.calculateLevensteinDist(target, suggestion));
            }
        }

        this.logger.info("URL feature (Levenshtein distance between google suggestion and " + (isPrimary ? "primary" : "sub") + "domain ) result is " + result + ".");
        return result;
    }

    private String ttlValue() {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        BufferedReader successBufferReader = null;
        String result = "-1";

        try {
            process = runtime.exec("ping " + this.urlParser.getHost());
            successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            int count = 0;
            String input;

            while ((input = successBufferReader.readLine()) != null) {
                if (input.toLowerCase().contains("ttl=") || count > 3) //FIXME : insert timeout logic
                    break;
                count++;
            }

            logger.debug("Ping command result is " + input);
            if (!StringUtils.isEmpty(input) && count < 3)
                result = input.split("ttl=")[1].split(" ")[0];   //FIXME : parsing logic for "icmp_seq=0 ttl=239 time=36.424 ms"

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
        this.logger.info("URL feature (Value of TTL)" + "result is " + result + ".");
        return result;
    }

    private String recordOfWhois() {
        WhoisClient whoisClient = new WhoisClient();
        String result = "0";
        try {
            whoisClient.connect(WhoisClient.DEFAULT_HOST);
            String whoisData1 = whoisClient.query("=" + this.urlParser.getHost());
            this.logger.debug("Whois record of ["+ this.urlParser.getHost()+"] " + whoisData1);

            if (!whoisData1.contains("No match for"))
                result = "1";

            whoisClient.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.logger.info("URL feature (Exist whois record)" + "result is " + result + ".");
        return result;
    }

    private String rankOfAlexa() {
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

        this.logger.info("URL feature (Value of AlexaRank) result is " + result + ".");
        return result;
    }

    private String numberOfBadTerms() {
        int result = 0;
        for (int i = 0; i < this.badTerms.length; i++) {
            String target = this.urlParser.getTargetUrl();
            result += (target.split(this.badTerms[i]).length - 1);
        }

        this.logger.info("URL feature (Count of bad terms)" + "result is " + result + ".");
        return Integer.toString(result);
    }

    private String hasPrefixAndSuffix() {
        String result = this.urlParser.getTargetUrl().contains("-") ? "1" : "0";
        this.logger.info("URL feature (Exist prefix or suffix, Count of '-' in URL) result is " + result + ".");
        return result;
    }

}
