package com.ovio.extractor.service;

import com.ovio.extractor.utils.HtmlFeatureUtil;
import com.ovio.extractor.utils.UrlFeatureUtil;
import com.ovio.extractor.utils.UrlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FeatureExtractService {

    private final UrlFeatureUtil urlFeatureUtil;
    private final HtmlFeatureUtil htmlFeatureUtil;

    @Autowired
    public FeatureExtractService(UrlFeatureUtil urlFeatureUtil, HtmlFeatureUtil htmlFeatureUtil) {
        this.urlFeatureUtil = urlFeatureUtil;
        this.htmlFeatureUtil = htmlFeatureUtil;
    }

    public String extractFeatures(String targetUrl) throws Exception {
        String urlFeatues = urlFeatureUtil.extractFeatures(targetUrl);
        //TODO: create html feature extractor code..... It's boring...
        String htmlFeatures = htmlFeatureUtil.extractFeatures(targetUrl);

        return urlFeatues;
    }


}
