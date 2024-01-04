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
public class AdditionalDevice {

    @Id
    private  Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id",referencedColumnName = "id")
    private Device device;
    private  int numberOfAdditionalDevices;

    @ManyToMany(mappedBy = "additionalDevicesReserved")
    private List<RoomBooking> reservations;
}
