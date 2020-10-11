package www.rainz.com.service;

import www.rainz.com.vo.QuestionsData;
import www.rainz.com.vo.TopicReq;

import java.util.List;

/**
 * @author RainZ
 * @date 2020/10/11 14:08
 */
public interface TopicService {
    /**
     * 根据数值范围生成题目
     * @param req 生题目需要参数：数值范围、题目数量
     * @return 是否生成成功
     */
    boolean createTopics(TopicReq req);

    /**
     * 根据page获取题目
     * @param pageCount 页数
     * @param pageSize 页面大小
     * @return 题目
     */
    List<QuestionsData> getTopicByPage(Integer pageCount, Integer pageSize);
}
