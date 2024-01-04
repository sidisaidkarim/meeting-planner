package fr.canalplus.meetingplaner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Slf4j
public class Room {
    @Id
    private Long id;
    private String name;
    private int capacity;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<RoomBooking> reservations;

    @ManyToMany
    @JoinTable(
            name = "room_device",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id"))
    List<Device> availableDevices;

    public boolean isRoomAvailable(Date dateOfMeeting, int startHour, int endHour, int nbHourBeforeAvailability){
        boolean isAvailable = true;
        for(RoomBooking reservation: this.getReservations()){


            if( reservation.getDate().compareTo(dateOfMeeting) == 0){

                if( startHour >= reservation.getStartMeetingHour() && startHour <= (reservation.getEndMeetingHour()+nbHourBeforeAvailability)) {
                    isAvailable = false;

                }
                if( (endHour + nbHourBeforeAvailability) >= reservation.getStartMeetingHour() && (endHour + nbHourBeforeAvailability) <= reservation.getEndMeetingHour()) {
                    isAvailable = false;

                };

            }


        }
        return isAvailable;
    }

    public int getCalculatedCapacity(float percentage) {
        return (int) (this.getCapacity() * percentage);
    }
}
