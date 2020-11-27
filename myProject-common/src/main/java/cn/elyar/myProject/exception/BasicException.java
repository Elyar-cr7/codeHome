package cn.elyar.myProject.exception;

/**
 * @author elyar
 * @date 2020/11/26 16:14
 * @description
 */
public class BasicException extends RuntimeException {

    public BasicException(){
        super();
    }

    public BasicException(String meassage){
        super(meassage);
    }

    public BasicException(String message, Throwable cautse){
        super(message,cautse);
    }

    public BasicException(Throwable cause){
        super(cause);
    }

}
