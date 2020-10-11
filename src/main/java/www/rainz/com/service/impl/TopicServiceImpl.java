package www.rainz.com.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import www.rainz.com.service.TopicService;
import www.rainz.com.vo.QuestionsData;
import www.rainz.com.vo.TopicReq;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author RainZ
 * @date 2020/10/11 14:08
 */
@Service
public class TopicServiceImpl implements TopicService {
    List<QuestionsData> topics = new ArrayList<>();
    String allTopic = "";
    @Override
    public boolean createTopics(TopicReq req) {
        Assert.notNull(req.getTopicCount(), "生成题目条件:题目数量为空！");
        Assert.notNull(req.getNumLimit(), "生成题目条件:数字范围为空！");
        Assert.isTrue(req.getNumLimit() > 0, "生成题目条件:数字范围小于等于0！");
        Assert.isTrue(req.getTopicCount() > 0, "生成题目条件:题目数量小于等于0！");
        int flag = 1;
        while(flag <= req.getTopicCount()){
            String topic = createTopic(req.getNumLimit(), req.getTopicCount());
            if(!checkTopic(topic)){
                continue;
            }
            QuestionsData questionsData = new QuestionsData().setId(flag).setQuestion(topic);
            flag++;
            topics.add(questionsData);
        }
        Assert.isTrue(creatTopicTxt(), "生成文件题目失败！");
        return true;
    }

    @Override
    public List<QuestionsData> getTopicByPage(Integer pageNum, Integer pageSize) {
        Assert.notNull(pageSize, "获取题目条件:页面题目数量为空！");
        Assert.notNull(pageNum, "获取题目条件:页数为空！");
        Assert.isTrue(pageNum > 0, "获取题目条件:页数小于等于0！");
        Assert.isTrue(pageSize > 0, "获取题目条件:页面题目数量小于等于0！");

        List<QuestionsData> questionsDataList = new ArrayList<>();
        int flag = 0;
        while(flag < pageSize){
            questionsDataList.add(topics.get((pageNum-1)*pageSize+flag));
            flag++;
        }
        return questionsDataList;
    }

    private boolean creatTopicTxt(){
        File file = new File("./src/Exercises.txt");
        if(!file.exists()){
            try {
                Assert.isTrue(file.createNewFile(), "创建题目文件失败！");
            } catch (IOException e) {
                throw new RuntimeException("创建题目文件异常！");
            }
        }


        topics.stream().forEach(topic->{
            allTopic = allTopic + topic.toString() + "\n";
        });
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter("./src/Exercises.txt", false);
            fwriter.write(allTopic);
            allTopic = "";
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    private String createTopic(Integer numLimit, Integer count){
        int numFlag = 3;
        String topic = "";
        while(numFlag > 0){
            String num = getNumber(numLimit);
            numFlag--;
            if(numFlag>0){
                topic = topic + num + getOperator();
            }else if(numFlag == 0){
                topic = topic + num;
            }
        }
        return topic;
    }
    private String getNumber(Integer numLimit){
        Random random = new Random();
        int numerator = checkNum(random.nextInt(numLimit), numLimit);
        int denominator = checkNum(random.nextInt(numLimit), numLimit);
        if(numerator >= denominator){
            if(numerator%denominator == 0){
                return String.valueOf(numerator/denominator);
            }
        }
        return "("+numerator+"/"+denominator+")";
    }

    private int checkNum(int num, int numLimit){
        Random random = new Random();
        while(num <= 0){
            num = random.nextInt(numLimit);
        }
        return num;
    }
    private String getOperator(){
        String[] operators = {"+","-","*","/"};
        Random random = new Random();
        int index = random.nextInt(100);
        index %= 3;
        return operators[index];
    }

    private boolean checkTopic(String topic){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        String stringResult = null;
        try {
            stringResult = String.valueOf(scriptEngine.eval(topic));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        double result = Double.parseDouble(stringResult);
        return result >= 0;
    }


}
