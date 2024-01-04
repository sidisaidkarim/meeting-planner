package fr.canalplus.meetingplaner.repository;

import fr.canalplus.meetingplaner.models.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<RoomBooking,Long> {

    @Query("select r from RoomBooking r where r.date = cast (?1 as date) and " +
            "?2 between r.startMeetingHour and r.endMeetingHour and " +
            "?3 between r.startMeetingHour and r.endMeetingHour")
    public List<RoomBooking> getReservationForGivenPeriod(Date date, int startHour, int endHour);
}
