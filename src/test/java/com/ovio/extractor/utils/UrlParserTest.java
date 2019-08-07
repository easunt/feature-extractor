package com.ovio.extractor.utils;

import org.junit.Assert;
import org.junit.Test;

public class UrlParserTest {

    @Test
    public void generate_url_parser_with_normal_url() {
        String expect = "https://www.google.com";
        String actual = "";
        UrlParser urlParser;
        try {
            urlParser = new UrlParser("https://www.google.com");
            actual = urlParser.getTargetUrl();
        } catch (Exception error) { }

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void generate_url_parser_with_not_url() {
        String expect = "";
        String actual = "";
        UrlParser urlParser;
        try {
            urlParser = new UrlParser("I'm not url");
            actual = urlParser.getTargetUrl();
        } catch (Exception error) { }

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void get_subdomain_when_give_url_that_start_www() {
        String expect = "";
        String actual = "";
        UrlParser urlParser;
        try {
            urlParser = new UrlParser("https://www.google.com");
            actual = urlParser.getSubDomain();
        } catch (Exception error) { }

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void get_subdomain_when_give_url_that_not_start_www() {
        String expect = "mail";
        String actual = "";
        UrlParser urlParser;
        try {
            urlParser = new UrlParser("https://mail.google.com");
            actual = urlParser.getSubDomain();
        } catch (Exception error) { }

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void get_subdomain_when_give_url_that_empty_subdomain() {
        String expect = "";
        String actual = "";
        UrlParser urlParser;
        try {
            urlParser = new UrlParser("https://gmail.com");
            actual = urlParser.getSubDomain();
        } catch (Exception error) { }

        Assert.assertEquals(expect, actual);
    }
}
