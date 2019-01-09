package com.sentiment.analysis.sentimentanalysis.service;

import com.sentiment.analysis.sentimentanalysis.model.ResponseBase;
import com.sentiment.analysis.sentimentanalysis.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youngwa on 2018/12/07. 20:26
 */
@Slf4j
public abstract class AbstractAnalysisAction implements ActualAction<List<String>, String> {

    public ResponseResult<List<String>> execute(String content) {
        ResponseResult<List<String>> responseBase = new ResponseResult<List<String>>();
        responseBase.setCode("200");
        List<String> result = null;
        String newContent;
        if (verify(content)) {
            try{
                newContent = doFilter(content);
            } catch (Exception e) {
                log.error("filter content occured an exception: ", e);
                result = new ArrayList<>();
                result.add("gg! \uD83D\uDC7B\uD83D\uDC7B\uD83D\uDC7B");
                responseBase.setData(result);
                responseBase.setSuccess(false);
                responseBase.setMessage("sry，解析失败，输入中含有非法的字符！");
                return responseBase;
            }
            result = action(newContent);

            responseBase.setData(result);
            responseBase.setSuccess(true);
            responseBase.setMessage("successful analysis，best wish to you！");
            return responseBase;

        } else {
            result = new ArrayList<>();
            result.add("gg! \uD83D\uDC7B\uD83D\uDC7B\uD83D\uDC7B");
            responseBase.setData(result);
            responseBase.setSuccess(false);
            responseBase.setMessage("sry，校验失败，中文数字不足！");
            return responseBase;
        }
    }

    public abstract boolean verify(String content);

    public abstract String doFilter(String content);

}
