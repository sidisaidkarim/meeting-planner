package fr.canalplus.meetingplaner.service;

import fr.canalplus.meetingplaner.dto.MeetingParamsDto;
import fr.canalplus.meetingplaner.models.*;
import fr.canalplus.meetingplaner.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;

import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final ConfigRepository configRepository;

    private final MeetingTypeRepository meetingTypeRepository;
    private final AdditionalDeviceRepository additionalDeviceRepository;

    private final ReservationRepository reservationRepository;

    public RoomService(RoomRepository roomRepository, ConfigRepository configRepository, MeetingTypeRepository meetingTypeRepository, AdditionalDeviceRepository additionalDeviceRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.configRepository = configRepository;
        this.meetingTypeRepository = meetingTypeRepository;
        this.additionalDeviceRepository = additionalDeviceRepository;
        this.reservationRepository = reservationRepository;
    }

    public String TestEndPoint(){
        Room testRoom = roomRepository.findById(9l).get();
        MeetingParamsDto testMeeting = MeetingParamsDto.builder()
                .date(Date.valueOf("2024-05-08"))
                .type("VC")
                .nbPeople(5)
                .startHour(8)
                .endHour(10)
                .build();
        return "is available =  "+testRoom.isRoomAvailable(testMeeting.getDate(),testMeeting.getStartHour(),testMeeting.getEndHour(),1);
    }
    public Room getRoomForMeeting(MeetingParamsDto params) throws Exception{




        GlobalConfiguration RoomMeetingConfiguration = configRepository.findFirstByOrderById();

        List<Room> rooms = roomRepository.findByOrderByCapacityAsc();


        List<Device> requiredDeviceForMeeting = meetingTypeRepository.findById(params.getType()).get().getRequiredDevices();

        List<RoomBooking> reservations = reservationRepository.getReservationForGivenPeriod(
                params.getDate(),
                params.getStartHour(),
                params.getEndHour()
        );

        List<Device> AlreadyReservedDevices = new ArrayList<>();

        for(RoomBooking reservation : reservations){

                for( Device device:reservation.getReservedDevices()){
                    AlreadyReservedDevices.add(device);
                }
        }



        rooms = rooms.stream()
                .filter(room ->
                                room.getCalculatedCapacity(RoomMeetingConfiguration.getPercentageCapacity())>= params.getNbPeople()
                )
                .filter( room ->
                        room.isRoomAvailable(params.getDate(),params.getStartHour(), params.getEndHour(),RoomMeetingConfiguration.getAdvanceAvailabilityHours())
                ).collect(Collectors.toList());

        if(rooms.isEmpty())  throw new BadRequestException("there is no room available for this period");

        Room roomToBook = rooms.get(0);

        List<AdditionalDevice> devicesToReserveForMeeting = new ArrayList<>();

        for(Device requiredDevice: requiredDeviceForMeeting){

            if(! roomToBook.getAvailableDevices().contains(requiredDevice)){

                int countOfReservedDeviceOfThisType = Collections.frequency(AlreadyReservedDevices, requiredDevice.getName());
                //log.info("nb occurrence of "+requiredDevice.getName()+" is "+countOfReservedDeviceOfThisType);
                AdditionalDevice deviceToReserve = additionalDeviceRepository.findByDeviceName(requiredDevice.getName()).get();
                if(countOfReservedDeviceOfThisType == deviceToReserve.getNumberOfAdditionalDevices()){
                    throw new BadRequestException("there is no room available for this period");
                }
                else {

                    devicesToReserveForMeeting.add(deviceToReserve);

                }
            }
        }


        //generate random id for reservation to save, coz i forget to set GeneratedValue on Reservation Entity
        //so to not drop table and loose all data ....
        Long leftLimit = 20L;
        Long rightLimit = 5000L;
        Long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        RoomBooking bookingToSave = RoomBooking.builder()
                .id(generatedLong)
                .date(params.getDate())
                .startMeetingHour(params.getStartHour())
                .endMeetingHour((params.getEndHour()))
                .meetingType(meetingTypeRepository.findById(params.getType()).get())
                .room(roomToBook)
                .additionalDevicesReserved(devicesToReserveForMeeting)
                .build();
        reservationRepository.save(bookingToSave);

        return roomToBook;
    }


}
