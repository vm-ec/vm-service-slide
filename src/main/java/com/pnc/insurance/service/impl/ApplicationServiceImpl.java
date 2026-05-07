package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.SlideApplication;
import com.pnc.insurance.repository.SlideApplicationRepository;
import com.pnc.insurance.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    @Transactional
    public SlideApplication updateApplication(Long id, SlideApplication application) {
        SlideApplication existing = applicationRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        // Update application fields from request body
        if (application.getName() != null && !application.getName().isEmpty()) {
            existing.setName(application.getName());
        }
        if (application.getDescription() != null && !application.getDescription().isEmpty()) {
            existing.setDescription(application.getDescription());
        }

        // Save the updated application (changes will be persisted to database)
        SlideApplication saved = applicationRepository.save(existing);
        System.out.println("✅ Application updated successfully - ID: " + saved.getId() + ", Name: " + saved.getName());
        return saved;
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
