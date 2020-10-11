package www.rainz.com.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author RainZ
 * @date 2020/10/11 18:43
 */
@Component
@Data
public class Page {
    private Integer pageCount;
    private Integer pageSize;
}
