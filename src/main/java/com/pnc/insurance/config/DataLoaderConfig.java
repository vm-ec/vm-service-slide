package com.pnc.insurance.config;

import com.pnc.insurance.model.Section;
import com.pnc.insurance.model.SlideApplication;
import com.pnc.insurance.model.SlideEnvironment;
import com.pnc.insurance.model.Tile;
import com.pnc.insurance.repository.SlideEnvironmentRepository;
import com.pnc.insurance.repository.SlideApplicationRepository;
import com.pnc.insurance.repository.SectionRepository;
import com.pnc.insurance.repository.TileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoaderConfig {

    @Bean
    public CommandLineRunner loadData(SlideEnvironmentRepository envRepo, SlideApplicationRepository appRepo, SectionRepository sectionRepo, TileRepository tileRepo) {
        return args -> {

            // Load environments if not present
            if (envRepo.count() == 0) {
                SlideEnvironment dev = new SlideEnvironment();
                dev.setId(1L);
                dev.setName("DEV");
                dev.setRegion("US-East");

                SlideEnvironment qa = new SlideEnvironment();
                qa.setId(2L);
                qa.setName("QA");
                qa.setRegion("US-East");

                SlideEnvironment prod = new SlideEnvironment();
                prod.setId(3L);
                prod.setName("PROD");
                prod.setRegion("US-East");

                envRepo.save(dev);
                envRepo.save(qa);
                envRepo.save(prod);

                System.out.println("✅ Environments seeded successfully");
            }

            // Load applications if not present
            SlideApplication app1 = null;
            SlideApplication app2 = null;
            if (appRepo.count() == 0) {
                app1 = new SlideApplication();
                app1.setName("PNC Insurance App");
                app1.setDescription("Main insurance application");

                app2 = new SlideApplication();
                app2.setName("Slide Tool");
                app2.setDescription("Slide management tool");

                appRepo.save(app1);
                appRepo.save(app2);

                System.out.println("✅ Applications seeded successfully");
            } else {
                // If already exist, fetch them
                app1 = appRepo.findAll().get(0);
                if (appRepo.count() > 1) app2 = appRepo.findAll().get(1);
            }

            // Load sections if not present
            Section section1 = null;
            Section section2 = null;
            if (sectionRepo.count() == 0) {
                section1 = new Section();
                section1.setName("Dashboard");
                section1.setApplication(app1);

                section2 = new Section();
                section2.setName("Reports");
                section2.setApplication(app2 != null ? app2 : app1);

                sectionRepo.save(section1);
                sectionRepo.save(section2);

                System.out.println("✅ Sections seeded successfully");
            } else {
                // Update existing sections if application is null
                for (Section sec : sectionRepo.findAll()) {
                    if (sec.getApplication() == null) {
                        sec.setApplication(app1);
                        sectionRepo.save(sec);
                    }
                }
                section1 = sectionRepo.findAll().get(0);
                if (sectionRepo.count() > 1) section2 = sectionRepo.findAll().get(1);
                System.out.println("✅ Sections updated successfully");
            }

            // Load tiles if not present
            if (tileRepo.count() == 0) {
                Tile tile1 = new Tile();
                tile1.setTileId("dashboard-tile");
                tile1.setName("Dashboard Tile");
                tile1.setSection(section1);

                Tile tile2 = new Tile();
                tile2.setTileId("reports-tile");
                tile2.setName("Reports Tile");
                tile2.setSection(section2);

                tileRepo.save(tile1);
                tileRepo.save(tile2);

                System.out.println("✅ Tiles seeded successfully");
            }
        };
    }
}
