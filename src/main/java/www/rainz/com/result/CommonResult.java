package www.rainz.com.result;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import static www.rainz.com.constant.ResultConstant.*;


/**
 * @author RainZ
 * @date on 2020/3/15
 */
@Data
@Accessors(chain = true)
@Component
public class CommonResult<T> {

    private int code;

    private String message;

    private boolean isSuccess;

    private T data;

    public CommonResult<T> operateSuccess() {
        return setSuccess(true).setMessage(OPERATE_SUCCESS_MESSAGE).setCode(SUCCESS_CODE);
    }

    public CommonResult<T> operateSuccess(T t) {
        return operateSuccess().setData(t);
    }

    public CommonResult<T> operateFail() {
        return setSuccess(false).setMessage(OPERATE_FAIL_MESSAGE).setCode(FAIL_CODE);
    }

    public CommonResult<T> operateFail(T t) {
        return operateFail().setData(t);
    }

    public CommonResult<T> operateNoPower() {
        return setSuccess(false).setMessage(OPERATE_NO_POWER).setCode(FORBIDDEN_CODE);
    }

    public CommonResult<T> operateNoPower(T t) {
        return operateFail().setData(t);
    }

    public <P> CommonResult<P> autoResult(boolean isSuccess) {
        if (isSuccess) {
            return new CommonResult<P>().operateSuccess();
        } else {
            return new CommonResult<P>().operateFail();
        }
    }

    public CommonResult<T> autoResult(boolean isSuccess, T data) {
        if (isSuccess) {
            return new CommonResult<T>().operateSuccess(data);
        } else {
            return new CommonResult<T>().operateFail(data);
        }
    }
}
