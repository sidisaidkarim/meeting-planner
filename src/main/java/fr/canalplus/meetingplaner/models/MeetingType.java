package fr.canalplus.meetingplaner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MeetingType {

    @Id
    private String name;

    @OneToMany(mappedBy = "meetingType", cascade = CascadeType.ALL)
    List<RoomBooking> reservations;


    @ManyToMany
    @JoinTable(
            name = "meeting_type_required_devices",
            joinColumns = @JoinColumn(name = "meeting_type_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id"))
    private List<Device> requiredDevices;

}
