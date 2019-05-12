package com.ovio.extractor.utils;

import java.net.URL;

public class UrlParser {
    private String targetUrl;
    private URL urlObj;

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

        String[] dotSplitArray = this.targetUrl.split("://")[1].split("\\.");
        StringBuilder subDomain = new StringBuilder();

        for (int idx = 0; idx < dotSplitArray.length - 2; idx++) {
            subDomain.append(".").append(dotSplitArray[idx]);
        }

        return subDomain.substring(1);
    }

    public String getPrimaryDomain() {
        String subDomain = this.getSubDomain();
        return this.targetUrl.split(this.getSubDomain()+"|\\.")[0];
    }

    public String getHost() {
        return this.urlObj.getHost();
    }
}
