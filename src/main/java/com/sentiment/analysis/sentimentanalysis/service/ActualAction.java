package com.sentiment.analysis.sentimentanalysis.service;

import com.sentiment.analysis.sentimentanalysis.model.ResponseBase;

public interface ActualAction<T, R> {
    T action(R content);
}
