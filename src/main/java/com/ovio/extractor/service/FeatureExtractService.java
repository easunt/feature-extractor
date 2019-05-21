package com.ovio.extractor.service;

import com.ovio.extractor.entity.Url;
import com.ovio.extractor.entity.UrlFeature;
import com.ovio.extractor.respository.UrlFeatureRepository;
import com.ovio.extractor.respository.UrlRepository;
import com.ovio.extractor.utils.HtmlFeatureUtil;
import com.ovio.extractor.utils.UrlFeatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeatureExtractService {

    private final UrlFeatureUtil urlFeatureUtil;
    private final HtmlFeatureUtil htmlFeatureUtil;
    private final UrlRepository urlRepository;
    private final UrlFeatureRepository urlFeatureRepository;

    @Autowired
    public FeatureExtractService(UrlFeatureUtil urlFeatureUtil, HtmlFeatureUtil htmlFeatureUtil, UrlRepository urlRepository, UrlFeatureRepository urlFeatureRepository) {
        this.urlFeatureUtil = urlFeatureUtil;
        this.htmlFeatureUtil = htmlFeatureUtil;
        this.urlRepository = urlRepository;
        this.urlFeatureRepository = urlFeatureRepository;
    }

    public String extractFeatures(String targetUrl) throws Exception {
        Url url = new Url();
        url.setUrl(targetUrl);
        urlRepository.save(url);

        UrlFeature urlFeature = urlFeatureUtil.extractFeatures(targetUrl, url.getId());
        urlFeatureRepository.save(urlFeature);
        //TODO: create html feature extractor code..... It's boring...
        String htmlFeatures = htmlFeatureUtil.extractFeatures(targetUrl);
        return urlFeature.toString();
    }

    public String extractAllUrlsFeature() throws Exception {
        List<Url> urls = urlRepository.findAll();

        UrlFeature urlFeature = urlFeatureUtil.extractFeatures()
    }

}
