package com.ovio.extractor.utils;


import org.apache.commons.lang3.StringUtils;

class CommonUtil {
    public static int calculateLevensteinDist(String str1, String str2) {
        if (StringUtils.isBlank(str1) && StringUtils.isBlank(str2))
            return 0;
        if (StringUtils.isBlank(str1))
            return str2.length();
        if (StringUtils.isBlank(str2))
            return str1.length();

        if (str1.length() > str2.length()) {
            String tempStr = str1;
            str1 = str2;
            str2 = tempStr;
        }
        int[] str1Array1 = new int[str1.length() + 1];
        int[] str1Array2 = new int[str1.length() + 1];

        for (int i = 1; i <= str1.length(); i++) str1Array1[i] = i;
        str1Array2[0] = 1;

        for (int j = 1; j <= str2.length(); j++) {
            for (int i = 1; i <= str1.length(); i++) {
                int c = (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1);
                str1Array2[i] = Math.min(str1Array1[i - 1] + c, Math.min(str1Array1[i] + 1, str1Array2[i - 1] + 1));
            }

            int[] temp = str1Array1;
            str1Array1 = str1Array2;
            str1Array2 = temp;
            str1Array2[0] = str1Array1[0] + 1;
        }
        return str1Array1[str1.length()];
    }
}


