package fr.canalplus.meetingplaner.repository;

import fr.canalplus.meetingplaner.models.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingTypeRepository extends JpaRepository<MeetingType,String> {
}
