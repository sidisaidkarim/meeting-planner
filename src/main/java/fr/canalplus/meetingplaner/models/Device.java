package fr.canalplus.meetingplaner.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Device {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "availableDevices")
    private Set<Room> rooms;

    @ManyToMany(mappedBy = "requiredDevices")
    private List<MeetingType> meetingTypes;

    @OneToOne(mappedBy = "device")
    private AdditionalDevice additionalDevice;
}
