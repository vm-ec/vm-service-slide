package com.pnc.insurance.service;

import com.pnc.insurance.model.SlideApplication;

import java.util.List;

public interface ApplicationService {

    List<SlideApplication> getAllApplications();

    SlideApplication getApplicationById(Long id);

    SlideApplication saveApplication(SlideApplication application);

    SlideApplication updateApplication(Long id, SlideApplication application);

    boolean deleteApplication(Long id);
}
