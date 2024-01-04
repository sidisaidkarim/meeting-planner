package fr.canalplus.meetingplaner.dto;
import fr.canalplus.meetingplaner.models.MeetingType;
import fr.canalplus.meetingplaner.models.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Date date;
    private int startMeetingHour;
    private int endMeetingHour;
    private MeetingType meetingType;
    private Room room;
}
