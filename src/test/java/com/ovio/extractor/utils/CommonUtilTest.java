package com.ovio.extractor.utils;

import org.junit.Assert;
import org.junit.Test;

public class CommonUtilTest {

    @Test
    public void calculdate_levenstein_distance_when_str1_exist_and_str2_exist_and_diff() {
        String str1 = "levenstein";
        String str2 = "lavensteindist";
        int expect = 5;
        int actual = CommonUtil.calculateLevensteinDist(str1, str2);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_exist_and_str2_exist_and_same() {
        String str1 = "levenstein";
        String str2 = "levenstein";
        int expect = 0;
        int actual = CommonUtil.calculateLevensteinDist(str1, str2);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_exist_and_str2_null() {
        String str1 = "levenstein";
        int expect = 10;
        int actual = CommonUtil.calculateLevensteinDist(str1, null);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_null_and_str2_exist() {
        String str2 = "lavensteindist";
        int expect = 14;
        int actual = CommonUtil.calculateLevensteinDist(null, str2);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_null_and_str2_null() {
        int expect = 0;
        int actual = CommonUtil.calculateLevensteinDist(null, null);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_exist_and_str2_blank() {
        String str1 = "levenstein";
        String str2 = " ";
        int expect = 10;
        int actual = CommonUtil.calculateLevensteinDist(str1, str2);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_blank_and_str2_exist() {
        String str1 = " ";
        String str2 = "lavensteindist";
        int expect = 14;
        int actual = CommonUtil.calculateLevensteinDist(str1, str2);

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void calculdate_levenstein_distance_when_str1_blank_and_str2_blank() {
        String str1 = " ";
        String str2 = " ";
        int expect = 0;
        int actual = CommonUtil.calculateLevensteinDist(str1, str2);

        Assert.assertEquals(expect, actual);
    }

}
