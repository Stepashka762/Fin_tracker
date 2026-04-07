package com.skillbox.data.repository;

import com.skillbox.data.model.Analytic;
import java.io.IOException;

public interface AnalyticRepository {
    void save(Analytic analytic) throws IOException;
}