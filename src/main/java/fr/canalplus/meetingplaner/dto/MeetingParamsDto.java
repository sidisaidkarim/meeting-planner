package fr.canalplus.meetingplaner.dto;

import fr.canalplus.meetingplaner.enums.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingParamsDto {
    private Date date;
    private int nbPeople;
    private String type;
    private int startHour;
    private int endHour;
}
