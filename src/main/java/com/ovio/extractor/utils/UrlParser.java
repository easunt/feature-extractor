package com.ovio.extractor.utils;

import org.thymeleaf.util.StringUtils;

import java.net.URL;

public class UrlParser {
    private String targetUrl;
    private URL urlObj;
    private String primaryDomain = null;
    private String subDomain = null;
    private String host = null;

    public UrlParser(String targetUrl) throws Exception {
        this.targetUrl = targetUrl;
        this.urlObj = new URL(targetUrl);
    }

    public URL getUrlObj() {
        return urlObj;
    }

    public void setUrlObj(URL urlObj) {
        this.urlObj = urlObj;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSubDomain() {
        if (this.subDomain == null) {
            String[] dotSplitArray = this.targetUrl.split("://")[1].split("\\.");
            StringBuilder subDomain = new StringBuilder();

            for (int idx = 0; idx < dotSplitArray.length - 2; idx++) {
                subDomain.append(".").append(dotSplitArray[idx]);
            }
            if (subDomain.length() == 0) {
                this.subDomain = "";
            } else {
                this.subDomain = subDomain.substring(1).equals("www") ? "" : subDomain.substring(1);
            }
        }
        return this.subDomain;
    }

    public String getPrimaryDomain() {
        if (StringUtils.isEmpty(this.primaryDomain)) {
            String subDomain = this.getSubDomain();
            this.primaryDomain = this.targetUrl.split(subDomain + "\\.")[1];
        }
        return this.primaryDomain;
    }

    public String getHost() {
        if (StringUtils.isEmpty(this.host)) {
            this.host = this.urlObj.getHost().replace(this.getSubDomain()+".", "").replace("www.", "");
        }
        return this.host;
    }
}
