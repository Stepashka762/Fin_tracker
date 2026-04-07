package com.skillbox.data.repository;

import com.skillbox.data.model.Analytic;
import com.skillbox.data.repository.AnalyticRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class AnalyticRepositoryImpl implements AnalyticRepository {
    private final String filename;

    public AnalyticRepositoryImpl(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(Analytic analytic) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filename), analytic);
    }


    public String getOutputFilename() {
        return filename;
    }
}