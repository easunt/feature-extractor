package com.ovio.extractor.service;

import com.ovio.extractor.utils.HtmlFeatureUtil;
import com.ovio.extractor.utils.UrlFeatureUtil;
import com.ovio.extractor.utils.UrlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeatureExtractService {

    private final UrlFeatureUtil urlFeatureUtil;
    private final HtmlFeatureUtil htmlFeatureUtil;
    private UrlParser urlParser;

    @Autowired
    public FeatureExtractService(UrlFeatureUtil urlFeatureUtil, HtmlFeatureUtil htmlFeatureUtil) {
        this.urlFeatureUtil = urlFeatureUtil;
        this.htmlFeatureUtil = htmlFeatureUtil;
    }

    public String extractFeatures(String targetUrl) throws Exception {
        return urlFeatureUtil.extractUrlFeatures(targetUrl);
    }


}
