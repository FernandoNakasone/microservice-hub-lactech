package br.com.lactech.performanceWebsite.repositories;

import br.com.lactech.performanceWebsite.entities.PerformanceWebsite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceWebsiteRepository extends JpaRepository<PerformanceWebsite, Long> {
}
