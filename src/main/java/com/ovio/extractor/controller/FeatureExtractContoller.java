package com.ovio.extractor.controller;

import com.ovio.extractor.service.FeatureExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class FeatureExtractContoller {
    private final FeatureExtractService featureExtractService;

    @Autowired
    public FeatureExtractContoller(FeatureExtractService featureExtractService) {
        this.featureExtractService = featureExtractService;
    }

    @RequestMapping(value = "/extract", method = RequestMethod.POST)
    public String extractFeatures(@RequestParam String url) throws Exception{
        return featureExtractService.extractFeatures(url);
    }
}
