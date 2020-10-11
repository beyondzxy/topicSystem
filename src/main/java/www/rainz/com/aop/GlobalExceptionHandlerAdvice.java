package www.rainz.com.aop;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.rainz.com.result.CommonResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author RainZ
 * @date 2019/5/11 10:24
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public CommonResult handler(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            sb.append(violation.getMessage()).append(";");
        }
        return new CommonResult().operateFail().setMessage(sb.toString());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResult handler(BindException ex) {
        StringBuilder sb = new StringBuilder();
        if (ex.getBindingResult().hasErrors()) {
            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
            allErrors.forEach(item -> sb.append(item.getDefaultMessage()).append(";"));
        }
        return new CommonResult<>().operateFail().setMessage(sb.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        if (ex.getBindingResult().hasErrors()) {
            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
            allErrors.forEach(item -> sb.append(item.getDefaultMessage()).append(";"));
        }
        return new CommonResult().operateFail().setMessage(sb.toString());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseBody
    public CommonResult handlerIllegalArgumentException(RuntimeException ex) {
        return new CommonResult().operateFail().setMessage(ex.getMessage());
    }
}
