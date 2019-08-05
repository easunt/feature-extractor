package com.ovio.extractor.controller;

import com.ovio.extractor.service.FeatureExtractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class FeatureExtractContoller {
    private final FeatureExtractService featureExtractService;

    @GetMapping("urls/{urlId}")
    public String getUrl(@PathVariable String urlId) {
        return featureExtractService.getUrl(urlId);
    }

    @PostMapping("urlFeatures/extract")
    public String extractFeatures(@RequestParam String url) throws Exception{
        return featureExtractService.extractFeatures(url);
    }

    @PostMapping("urlFeatures/extractByRange")
    public String extractAllUrlsFeature(@RequestParam String start, @RequestParam String end) throws Exception {
        return featureExtractService.extractUrlFeaturesByRange(start, end);
    }

    @GetMapping("urlFeatures/{urlFeatureId}")
    public String getUrlFeatures(@PathVariable String urlFeatureId) {
        return featureExtractService.getUrlFeatures(urlFeatureId);
    }
}
