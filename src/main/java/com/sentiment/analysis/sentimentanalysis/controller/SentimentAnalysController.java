package com.sentiment.analysis.sentimentanalysis.controller;

import com.alibaba.fastjson.JSONObject;
import com.sentiment.analysis.sentimentanalysis.model.ResponseResult;
import com.sentiment.analysis.sentimentanalysis.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youngwa on 2018/12/06. 23:44
 */
@Slf4j
@RestController
public class SentimentAnalysController {

    @Autowired
    public AnalysisService analysisService;

    @RequestMapping(value = "/analysis" , method = RequestMethod.POST)
    public ResponseResult<List<String>> get(@RequestBody String sentence) {
        String contnet = (String) JSONObject.parseObject(sentence).get("sentence");
        log.info("a request was made, content is：" + contnet);
        return analysisService.execute(contnet);
    }
}
