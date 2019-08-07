package com.ovio.extractor.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlParser {
    private String targetUrl;
    private URL urlObj;
    private String primaryDomain = null;
    private String subDomain = null;
    private String host = null;

    UrlParser(String targetUrl) throws MalformedURLException {
        this.targetUrl = targetUrl;
        this.urlObj = new URL(targetUrl);
    }

    public URL getUrlObj() {
        return urlObj;
    }

    public void setUrlObj(URL urlObj) {
        this.urlObj = urlObj;
    }

    String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    String getSubDomain() {
        if (this.subDomain == null) {
            this.getPrimaryDomain();
            String host = this.urlObj.getHost();
            String[] splitArray = host.split(this.primaryDomain + "\\.");

            if (splitArray.length > 0 && !StringUtils.isBlank(splitArray[0]) && !splitArray[0].equals("www.")) {
                if (splitArray[0].contains("."))
                    this.subDomain = splitArray[0].substring(0, splitArray[0].lastIndexOf('.'));
                else
                    this.subDomain = splitArray[0];
            } else
                this.subDomain = "";
        }
        return this.subDomain;
    }

    String getPrimaryDomain() {
        if (StringUtils.isBlank(this.primaryDomain)) {
            String host = this.urlObj.getHost().replaceAll(".*\\.(?=.*\\.)", "");
            this.primaryDomain = host.split("\\.")[0];
        }
        return this.primaryDomain;
    }

    String getHost() {
        if (StringUtils.isBlank(this.host)) {
            this.host = this.urlObj.getHost().replace(this.getSubDomain() + ".", "").replace("www.", "");
        }
        return this.host;
    }
}
