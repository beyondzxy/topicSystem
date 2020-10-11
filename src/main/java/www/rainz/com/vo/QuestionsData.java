package www.rainz.com.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @author RainZ
 * @date 2020/10/11 13:38
 */
@Component
@Data
@Accessors(chain = true)
public class QuestionsData {
    private Integer id;
    private String question;
}
