package cn.elyar.myProject.base;



import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

/**
 * @author elyar
 * @date 2020/11/25 14:21
 * @description
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final String SUCCESS = "00";//请求成功

    private static final String FAILURE = "01";//失败

    private static final String ERROR = "99";//请求异常

    /**
     * 操作成功。
     */
    static Result success(String message) {
        Result result = new Result();
        result.put("code", SUCCESS);
        result.put("message", StrUtil.isEmpty(message) ? "操作成功" : message);
        return result;
    }

    /**
     * 操作失败，用于业务失败返回。
     */
    static Result failure(String message) {
        Result result = new Result();
        result.put("code", FAILURE);
        result.put("message", StrUtil.isEmpty(message) ? "操作失败" : message);
        return result;
    }

    /**
     * 操作异常，用于系统异常返回。
     */
    static Result error(String message) {
        Result result = new Result();
        result.put("code", ERROR);
        result.put("message", StrUtil.isEmpty(message) ? "操作异常" : message);
        return result;
    }

    /**
     * 自定义返回信息。
     */
    public Result dataResult(String code, String message, Object data) {
        super.put("code", code);
        super.put("message", StrUtil.isEmpty(message) ? "操作成功" : message);
        super.put("data", data);
        return this;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
