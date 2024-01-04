package fr.canalplus.meetingplaner.resources;

import fr.canalplus.meetingplaner.dto.MeetingParamsDto;
import fr.canalplus.meetingplaner.dto.NoRoomAvailableDto;
import fr.canalplus.meetingplaner.dto.RoomDto;
import fr.canalplus.meetingplaner.models.Room;
import fr.canalplus.meetingplaner.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MeetingResource {

    private final RoomService roomService;

    public MeetingResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/get-room")
    ResponseEntity getRoomForMeeting(@RequestBody MeetingParamsDto body){
        try {
            Room room = roomService.getRoomForMeeting(body);
            RoomDto roomDto = RoomDto.builder()
                    .name(room.getName())
                    .capacity(room.getCapacity())
                    .build();
            return  ResponseEntity.ok(roomDto);

        }
        catch (Exception e){
            log.info("EXception = "+e);
            NoRoomAvailableDto erroMsgDto = NoRoomAvailableDto.builder()
                    .errorMessage(e.getMessage())
                    .build();
           return ResponseEntity.status(404).body(erroMsgDto);
        }

    }

    @GetMapping("/test")
    String test(){
        return roomService.TestEndPoint();
    }
}
