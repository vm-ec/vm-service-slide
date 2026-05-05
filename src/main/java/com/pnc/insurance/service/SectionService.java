package com.pnc.insurance.service;

import com.pnc.insurance.model.Section;

import java.util.List;

public interface SectionService {

    List<Section> getAllSections();

    Section getSectionById(Long id);

    Section saveSection(Section section);

    Section updateSection(Long id, Section section);

    boolean deleteSection(Long id);
}
