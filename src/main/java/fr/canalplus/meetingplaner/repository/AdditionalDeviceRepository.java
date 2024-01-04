package fr.canalplus.meetingplaner.repository;

import fr.canalplus.meetingplaner.models.AdditionalDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalDeviceRepository extends JpaRepository<AdditionalDevice, Long> {
    Optional<AdditionalDevice> findByDeviceName(String name);
}
