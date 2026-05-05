package com.pnc.insurance.service.impl;

import com.pnc.insurance.model.Section;
import com.pnc.insurance.repository.SectionRepository;
import com.pnc.insurance.service.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section getSectionById(Long id) {
        return sectionRepository.findById(id).orElse(null);
    }

    @Override
    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section updateSection(Long id, Section section) {
        Section existing = sectionRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setName(section.getName());
        if (section.getApplication() != null) {
            existing.setApplication(section.getApplication());
        }
        return sectionRepository.save(existing);
    }

    @Override
    public boolean deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            return false;
        }
        sectionRepository.deleteById(id);
        return true;
    }
}
