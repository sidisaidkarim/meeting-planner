package fr.canalplus.meetingplaner.unit;

import fr.canalplus.meetingplaner.dto.MeetingParamsDto;
import fr.canalplus.meetingplaner.models.Room;
import fr.canalplus.meetingplaner.models.RoomBooking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

public class RoomIsAvailableTest {

    @Test
    public void shouldTestIfRoomIsAvailable(){

        //given
        Room room = Room.builder()
                .name("test")
                .capacity(10)
                .build();
        //and
        RoomBooking reservation1 = RoomBooking.builder()

                .startMeetingHour(8)
                .endMeetingHour(10)
                .build();

        RoomBooking reservation2 = RoomBooking.builder()
                .startMeetingHour(14)
                .endMeetingHour(16)
                .build();
        room.setReservations(List.of(reservation1, reservation2));
        //and
        int nbHoursOfAvailability = 1;




        //then
        /*Assertions.assertEquals(true,room.isRoomAvailable(11,12,1));
        Assertions.assertEquals(true,room.isRoomAvailable(17,19,1));
        Assertions.assertEquals(false,room.isRoomAvailable(10,12,1));
        Assertions.assertEquals(false,room.isRoomAvailable(16,17,1));*/

    }
}
