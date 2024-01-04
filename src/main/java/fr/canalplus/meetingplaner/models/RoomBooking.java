package fr.canalplus.meetingplaner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RoomBooking {

    @Id
    private Long id;

    private Date date;
    private int startMeetingHour;
    private int endMeetingHour;

    @ManyToOne
    @JoinColumn(name = "meeting_type_id")
    private MeetingType meetingType;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private  Room room;

    @ManyToMany
    @JoinTable(
            name = "meeting_additional_devices_reserved",
            joinColumns = @JoinColumn(name = "room_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_device_id"))
    private List<AdditionalDevice> additionalDevicesReserved;

    public List<Device> getReservedDevices(){
        return this.additionalDevicesReserved.stream().map(reserveDevice->reserveDevice.getDevice()).collect(Collectors.toList());
    }


}
