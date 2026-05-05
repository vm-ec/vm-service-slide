package com.pnc.insurance.service;

import com.pnc.insurance.model.SlideEnvironment;

import java.util.List;

public interface EnvironmentService {

    List<SlideEnvironment> getAllEnvironments();

    SlideEnvironment getEnvironmentById(Long id);

    SlideEnvironment saveEnvironment(SlideEnvironment environment);

    SlideEnvironment updateEnvironment(Long id, SlideEnvironment environment);

    boolean deleteEnvironment(Long id);
}
