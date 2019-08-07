package com.ovio.extractor.service;

import com.ovio.extractor.entity.Url;
import com.ovio.extractor.entity.UrlFeature;
import com.ovio.extractor.respository.UrlFeatureRepository;
import com.ovio.extractor.respository.UrlRepository;
import com.ovio.extractor.utils.UrlFeatureUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FeatureExtractService {

    private final UrlFeatureUtil urlFeatureUtil;
    private final UrlRepository urlRepository;
    private final UrlFeatureRepository urlFeatureRepository;

    public String extractFeatures(String targetUrl) throws Exception {
        Url url = urlRepository.save(new Url(targetUrl));
        UrlFeature urlFeature = urlFeatureUtil.extractFeatures(targetUrl, url.getId());

        urlFeatureRepository.save(urlFeature);
        return urlFeature.toString();
    }

    public String extractUrlFeaturesByRange(String start, String end) throws Exception {
        List<Url> urls;
        if (StringUtils.isBlank(start) && StringUtils.isBlank(end))
            urls = urlRepository.findAll();
        else if (!StringUtils.isBlank(start) && StringUtils.isBlank(end))
            urls = urlRepository.findAllByIdGreaterThan(Long.valueOf(start));
        else
            urls = urlRepository.findAllByIdIsBetween(Long.valueOf(start), Long.valueOf(end));

        assert urls != null;

        for (Url target : urls) {
            UrlFeature urlFeature = urlFeatureUtil.extractFeatures(target.getUrl(), target.getId());
            urlFeatureRepository.save(urlFeature);
        }

        return Integer.toString(urls.size());
    }

    public String getUrl(String urlId) {
        Url url = urlRepository.findById(Long.valueOf(urlId))
                .orElseThrow(() -> new RuntimeException("URL NOT FOUND"));

        return url.getUrl();
    }

    public String getUrlFeatures(String urlFeatureId) {
        UrlFeature urlFeature = urlFeatureRepository.findById(Long.valueOf(urlFeatureId))
                .orElseThrow(() -> new RuntimeException("URL FEATURE NOT FOUND."));

        return urlFeature.getFeature();
    }
}
