package cn.elyar.myProject.base;

import cn.hutool.core.util.StrUtil;
import cn.elyar.myProject.enums.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author elyar
 * @date 2020/11/25 14:20
 * @description
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String KEY_CODE = "code";

    public static final String KEY_MESSAGE = "message";

    public static final String KEY_DATA = "data";

    protected Result successMessage() {
        Result result = Result.success(null) ;
        return result;
    }

    protected Result successMessage(String message) {
        Result result = Result.success(message);
        return result ;
    }

    protected Result success(String code, String message) {
        Result result = new Result();
        result.put(KEY_CODE, code);
        result.put(KEY_MESSAGE, StrUtil.isEmpty(message) ? "操作成功" : message);
        result.put(KEY_DATA, null);
        return result;
    }

    protected Result success(String code, String message, Object data) {
        Result result = new Result();
        result.put(KEY_CODE, code);
        result.put(KEY_MESSAGE, StrUtil.isEmpty(message) ? "操作成功" : message);
        result.put(KEY_DATA, data);
        return result;
    }

    protected Result errorMessage() {
        Result result = Result.error(null) ;
        return result;
    }

    protected Result errorMessage(String message) {
        Result result = Result.error(message);
        return result ;
    }

    protected Result errorParams() {
        Result result = Result.error(ReturnCodeEnum.ERROR_PARAMS.getDesc());
        return result ;
    }

    protected Result error(String code, String message) {
        Result result = new Result();
        result.put(KEY_CODE, code);
        result.put(KEY_MESSAGE, StrUtil.isEmpty(message) ? "请求异常" : message);
        return result;
    }

    protected Result failMessage() {
        Result result = Result.failure(null);
        return result ;
    }

    protected Result failMessage(String message) {
        Result result = Result.failure(message);
        return result ;
    }

    protected Result fail(String code, String message) {
        Result result = new Result();
        result.put(KEY_CODE, code);
        result.put(KEY_MESSAGE, StrUtil.isEmpty(message) ? "请求错误" : message);
        return result;
    }

    protected Result objectResult(Object data) {
        Result result = Result.success(null);
        result.put(KEY_DATA, data);
        return result;
    }

    protected int[] getPageParams(HttpServletRequest request) {
        int[] pageParams = new int[2];
        pageParams[0] = Integer.parseInt(StrUtil.isEmpty(request.getParameter("pageIndex")) ? (StrUtil.isEmpty(request.getParameter("pageNum")) ? "1" : request.getParameter("pageNum")) : request.getParameter("pageIndex"));
        pageParams[1] = Integer.parseInt(StrUtil.isEmpty(request.getParameter("pageSize")) ? "10" : request.getParameter("pageSize"));
        return pageParams;
    }
}
