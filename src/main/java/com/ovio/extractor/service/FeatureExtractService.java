package com.ovio.extractor.service;

import com.ovio.extractor.entity.Url;
import com.ovio.extractor.entity.UrlFeature;
import com.ovio.extractor.respository.UrlFeatureRepository;
import com.ovio.extractor.respository.UrlRepository;
import com.ovio.extractor.utils.HtmlFeatureUtil;
import com.ovio.extractor.utils.UrlFeatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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

    public String extractAllUrlsFeature(String start, String end) throws Exception {

        List<Url> urls = null;

        if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end))
            urls = urlRepository.findAll();
        else if (!StringUtils.isEmpty(start) && StringUtils.isEmpty(end))
            urls = urlRepository.findAllByIdGreaterThan(Long.valueOf(start));
        else
            urls = urlRepository.findAllByIdIsBetween(Long.valueOf(start), Long.valueOf(end));

        assert urls != null;
        for (Url target : urls) {
            UrlFeature urlFeature = urlFeatureUtil.extractFeatures(target.getUrl(), target.getId());
            urlFeatureRepository.save(urlFeature);
        }

        return "success";
    }
}
