package www.rainz.com.controller;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.rainz.com.result.CommonResult;
import www.rainz.com.service.TopicService;
import www.rainz.com.vo.Page;
import www.rainz.com.vo.QuestionsData;
import www.rainz.com.vo.TopicReq;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RainZ
 * @date 2020/10/11 13:32
 */
@RestController
public class TopicController {

    @Resource
    TopicService topicService;

    @RequestMapping("/createTopic")
    public CommonResult<Void> getTopic(@RequestBody TopicReq req) {
        Assert.notNull(req, "生成题目条件：topicReq为空！");
        return new CommonResult<>().autoResult(topicService.createTopics(req));
    }

    @RequestMapping("/getTopic")
    public CommonResult<List<QuestionsData>> getTopics(@RequestBody Page page) {
        Assert.notNull(page, "获取题目条件：page为空！");
        return new CommonResult<List<QuestionsData>>().operateSuccess(topicService.getTopicByPage(page.getPageCount(), page.getPageSize()));
    }
}
