package fr.canalplus.meetingplaner.integration;

import fr.canalplus.meetingplaner.dto.MeetingParamsDto;
import fr.canalplus.meetingplaner.dto.RoomDto;
import fr.canalplus.meetingplaner.models.Room;
import fr.canalplus.meetingplaner.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class getRoomForMeetingResourceTest {

    @LocalServerPort
    int port;

    private String basUrl;

    @Autowired
    private TestRestTemplate restTemplate;



    @BeforeEach
    void setUp(){
        this.basUrl = "http://localhost:"+port+"/api/v1/";
    }

    @Test
    public void itShouldGetOkResponse (){
        MeetingParamsDto meetingParamsDto = MeetingParamsDto.builder()
                .date(Date.valueOf("2024-01-08"))
                .type("VC")
                .nbPeople(5)
                .startHour(8)
                .endHour(10)
                .build();

        HttpEntity<MeetingParamsDto> request = new HttpEntity<>(meetingParamsDto);


        ResponseEntity<Room> responseEntity = restTemplate.exchange(
                basUrl+"get-room",
                HttpMethod.POST,
                request,
                Room.class
                );
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    public void itShouldGetRoomWithTheRightCapacity(){
        //GIVEN

        //WHEN

        //THEN

    }

    @Test
    public void BookRooms(){


            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/meetings.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            }
            catch (Exception e){
                log.info("____________Exception "+e);
            }

            for(List<String> line: records){

                MeetingParamsDto newMeeting = MeetingParamsDto.builder()
                        .date(Date.valueOf("2024-05-21"))
                        .startHour(Integer.valueOf(line.get(0)))
                        .endHour(Integer.valueOf(line.get(1)))
                        .nbPeople(Integer.valueOf(line.get(3)))
                        .type(line.get(2))
                        .build();

                HttpEntity<MeetingParamsDto> requestBody = new HttpEntity<>(newMeeting);
                ResponseEntity<Room> responseEntity = restTemplate.exchange(
                        basUrl+"get-room",
                        HttpMethod.POST,
                        requestBody,
                        Room.class
                );

                log.info("---------------------- "+responseEntity.getStatusCode());


            }
        }





    }
