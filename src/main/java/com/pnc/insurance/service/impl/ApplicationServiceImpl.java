package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.SlideApplication;
import com.pnc.insurance.repository.SlideApplicationRepository;
import com.pnc.insurance.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final SlideApplicationRepository applicationRepository;

    public ApplicationServiceImpl(SlideApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<SlideApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public SlideApplication getApplicationById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public SlideApplication saveApplication(SlideApplication application) {
        return applicationRepository.save(application);
    }

    @Override
    public SlideApplication updateApplication(Long id, SlideApplication application) {
        SlideApplication existing = applicationRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(application.getName());
        existing.setDescription(application.getDescription());
        return applicationRepository.save(existing);
    }

    @Override
    public boolean deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            return false;
        }
        applicationRepository.deleteById(id);
        return true;
    }
}
