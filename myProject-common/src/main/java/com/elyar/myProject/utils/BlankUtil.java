package com.elyar.myProject.utils;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author elyar
 * @date 2020/11/25 11:21
 * @description
 */
public class BlankUtil {
    /**
     * 判断字符串为空
     */
    public boolean isBlank(final String str) {
        return (str == null) || (str.trim().length() <= 0);
    }

    public boolean allBlank(final String... str) {
        for (String s : str) {
            if (isNotBlank(s)) {
                return false;
            }
        }
        return true;
    }

    public boolean allNotBlank(final String... str) {
        for (String s : str) {
            if (isBlank(s)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断字符串不为空
     *
     * @param str 字符串
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符是否为空
     *
     * @param cha 字符
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Character cha) {
        return (cha == null) || cha.equals(' ');
    }

    /**
     * 判断字符不为空
     *
     * @param cha 字符
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Character cha) {
        return !isBlank(cha);
    }

    /**
     * 判断对象是为空
     */
    public boolean isBlank(final Object obj) {
        if (obj instanceof String) {
            return isBlank((String) obj);
        }
        return (obj == null);
    }

    /**
     * 判断json是为空
     */
   /* public boolean isBlank(final JSONObject obj) {
        return (obj == null || obj.isEmpty());
    }*/

    /**
     * 判断对象不为空
     *
     * @param obj 对象
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Object obj) {
        return !isBlank(obj);
    }

    /**
     * 判断数组为空
     *
     * @param objs 数组
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Object[] objs) {
        return (objs == null) || (objs.length <= 0);
    }

    /**
     * 判断数组是不为空
     *
     * @param objs 对象
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Object[] objs) {
        return !isBlank(objs);
    }


    /**
     * 判断Collection为空
     *
     * @param obj 集合
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Collection<?> obj) {
        return (obj == null);
    }


    /**
     * 判断Collection不为空
     *
     * @param obj 集合
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Collection<?> obj) {
        return !isBlank(obj);
    }

    /**
     * 判断Set为空
     *
     * @param obj set
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Set<?> obj) {
        return (obj == null || obj.isEmpty());
    }

    /**
     * 判断Set不为空
     *
     * @param obj set
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Set<?> obj) {
        return !isBlank(obj);
    }


    public boolean isBlank(Integer i) {
        return i == null || i < 1;
    }

    /**
     * 判断Serializable为空
     *
     * @param obj Serializable
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Serializable obj) {
        return obj == null;
    }

    /**
     * 判断Serializable不为空
     *
     * @param obj Serializable
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Serializable obj) {
        return !isBlank(obj);
    }


    /**
     * 判断Map为空
     *
     * @param obj map
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final Map<?, ?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * 判断Map不为空
     *
     * @param obj map
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final Map<?, ?> obj) {
        return !isBlank(obj);
    }


    /**
     * 判断List为空
     *
     * @param obj list
     * @return 返回 true 或者 false
     */
    public boolean isBlank(final List<?> obj) {
        return (obj == null || (obj.size()) <= 0);
    }

    /**
     * 判断List不为空
     *
     * @param obj list
     * @return 返回 true 或者 false
     */
    public boolean isNotBlank(final List<?> obj) {
        return !isBlank(obj);
    }

}
