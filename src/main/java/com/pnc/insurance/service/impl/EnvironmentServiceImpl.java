package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.SlideEnvironment;
import com.pnc.insurance.repository.SlideEnvironmentRepository;
import com.pnc.insurance.service.EnvironmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    private final SlideEnvironmentRepository environmentRepository;

    public EnvironmentServiceImpl(SlideEnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    @Override
    public List<SlideEnvironment> getAllEnvironments() {
        return environmentRepository.findAll();
    }

    @Override
    public SlideEnvironment getEnvironmentById(Long id) {
        return environmentRepository.findById(id).orElse(null);
    }

    @Override
    public SlideEnvironment saveEnvironment(SlideEnvironment environment) {
        return environmentRepository.save(environment);
    }

    @Override
    public SlideEnvironment updateEnvironment(Long id, SlideEnvironment environment) {
        SlideEnvironment existing = environmentRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(environment.getName());
        existing.setRegion(environment.getRegion());
        return environmentRepository.save(existing);
    }

    @Override
    public boolean deleteEnvironment(Long id) {
        if (!environmentRepository.existsById(id)) {
            return false;
        }
        environmentRepository.deleteById(id);
        return true;
    }
}
