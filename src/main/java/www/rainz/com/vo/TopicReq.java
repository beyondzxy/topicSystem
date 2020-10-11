package www.rainz.com.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author RainZ
 * @date 2020/10/11 13:52
 */
@Component
@Data
public class TopicReq {
    private Integer topicCount;
    private Integer numLimit;
}
