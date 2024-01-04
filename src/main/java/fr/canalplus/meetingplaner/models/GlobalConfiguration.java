package fr.canalplus.meetingplaner.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GlobalConfiguration {
    @Id
    private Long id;

    // covid = 1 but can change in the future
    private int advanceAvailabilityHours;

    //covid = 0.7 can be 1 in future
    private float percentageCapacity;


}
