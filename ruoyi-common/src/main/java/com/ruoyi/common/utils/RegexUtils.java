package com.ruoyi.common.utils;

/**
 * 各种正则表达式
 */
public class RegexUtils {

    //匹配整数
    public static final String INTEGER_REGEX = "^([0-9]{1,})$";

    //匹配两位有效小数
    public static final String DECIMAL_REGEX = "^([0-9]{1,}[.][0-9]{1,2})$";

    //身份证正则
    public static final String IDENTIFY_REGEX = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

}
