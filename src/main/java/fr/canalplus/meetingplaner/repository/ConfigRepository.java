package fr.canalplus.meetingplaner.repository;

import fr.canalplus.meetingplaner.models.GlobalConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<GlobalConfiguration,Long> {
    GlobalConfiguration  findFirstByOrderById();
}
