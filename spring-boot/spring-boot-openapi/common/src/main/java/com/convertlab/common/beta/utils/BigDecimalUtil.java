package com.convertlab.common.beta.utils;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 *
 * @author LIUJUN
 * @date 2021-01-26 10:13
 */
public class BigDecimalUtil {

    /** 除法运算默认精度 */
    private static final int DEF_DIV_SCALE = 6;

    private BigDecimalUtil() {

    }

    /**
     * Integer转BigDecimal
     *
     * @param v Integer
     * @return BigDecimal
     */
    public static BigDecimal valueOf(Integer v) {
        if (v == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(v.longValue());
    }

    /**
     * Long转BigDecimal
     *
     * @param v Long
     * @return BigDecimal
     */
    public static BigDecimal valueOf(Long v) {
        if (v == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(v);
    }

    /**
     * Long转BigDecimal
     *
     * @param v Long
     * @return BigDecimal
     */
    public static BigDecimal valueOf(Double v) {
        if (v == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(v);
    }

    /**
     * 精确加法
     *
     * @param v1 Double
     * @param v2 Double
     */
    public static BigDecimal add(Double v1, Double v2) {
        if (v1 == null) {
            v1 = 0d;
        }
        if (v2 == null) {
            v2 = 0d;
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2);
    }

    /**
     * 精确加法
     *
     * @param v1 Double
     * @param v2 Double
     */
    public static BigDecimal add(Integer v1, Integer v2) {
        if (v1 == null) {
            v1 = 0;
        }
        if (v2 == null) {
            v2 = 0;
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2);
    }

    /**
     * 精确减法
     *
     * @param v1 Double
     * @param v2 被减的值
     */
    public static BigDecimal sub(Double v1, Double v2) {
        if (v1 == null) {
            v1 = 0d;
        }
        if (v2 == null) {
            v2 = 0d;
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2);
    }


    /**
     * 精确乘法
     *
     * @param v1 Double
     * @param v2 double
     */
    public static BigDecimal mul(Double v1, Double v2) {
        if (v1 == null) {
            v1 = 0d;
        }
        if (v2 == null) {
            v2 = 0d;
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2);
    }


    /**
     * 精确除法 使用默认精度
     *
     * @param v1 Double
     * @param v2 被除的值
     */
    public static BigDecimal div(Double v1, Double v2) {
        if (v1 == null) {
            v1 = 0d;
        }
        if (v2 == null) {
            v2 = 1d;
        }
        return div(v1, v2, DEF_DIV_SCALE);
    }


    /**
     * 精确除法
     *
     * @param scale 精度
     * @param v1    Double
     * @param v2    被除的值
     */
    public static BigDecimal div(Double v1, Double v2, int scale) {
        if (v1 == null) {
            v1 = 0d;
        }
        if (v2 == null) {
            v2 = 1d;
        }
        if (scale < 0) {
            throw new RuntimeException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 四舍五入计算
     *
     * @param v     Double
     * @param scale 小数点后保留几位
     */
    public static BigDecimal round(Double v, int scale) {
        if (v == null) {
            return BigDecimal.ZERO;
        }

        return div(v, 1d, scale);
    }

    /**
     * 精确加法
     *
     * @param v1 Double
     * @param v2 Double
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }


    /**
     * 精确减法
     *
     * @param v1 Double
     * @param v2 被减的值
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }


    /**
     * 精确乘法
     *
     * @param v1 Double
     * @param v2 double
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ZERO;
        }
        return v1.multiply(v2);
    }


    /**
     * 精确除法 使用默认精度
     *
     * @param v1 Double
     * @param v2 被除的值
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ONE;
        }
        return div(v1, v2, DEF_DIV_SCALE);
    }


    /**
     * 精确除法
     *
     * @param scale 精度
     * @param v1    Double
     * @param v2    被除的值
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        if (v2 == null) {
            v2 = BigDecimal.ONE;
        }
        if (scale < 0) {
            throw new RuntimeException("精确度不能小于0");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

}