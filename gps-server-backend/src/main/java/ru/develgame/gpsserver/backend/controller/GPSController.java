package ru.develgame.gpsserver.backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsserver.backend.dto.GPSDataRequestDto;
import ru.develgame.gpsserver.backend.dto.GPSDataResponseDto;
import ru.develgame.gpsserver.backend.mapper.GPSDataMapper;
import ru.develgame.gpsserver.backend.service.GPSDataService;

import static ru.develgame.gpsserver.backend.configuration.security.SecurityConfiguration.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/data")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@RequiredArgsConstructor
public class GPSController {
    private final GPSDataService gpsDataService;
    private final GPSDataMapper gpsDataMapper;

    @PostMapping
    public ResponseEntity<GPSDataResponseDto> create(@RequestBody GPSDataRequestDto data) {
        return ResponseEntity.ok(gpsDataMapper.toDto(gpsDataService.createData(data)));
    }
//
//    @GetMapping("/data")
//    public ResponseEntity<List<GPSReceivedData>> data(@RequestParam(name = "date") java.sql.Date date, @RequestParam(name = "user_id") Long userId) throws SQLException {
//        List<GPSReceivedData> res = new ArrayList<>();
//
//        try (Connection connection = dataSource.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement("SELECT latitude, longitude, timestamp FROM gpsdata WHERE date=? AND user_id=?")) {
//                statement.setDate(1, date);
//                statement.setLong(2, userId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        res.add(new GPSReceivedData(
//                                resultSet.getDouble(1),
//                                resultSet.getDouble(2),
//                                new Date(resultSet.getLong(3))));
//                    }
//                }
//            }
//        }
//
//        return ResponseEntity.ok(res);
//    }
//
//    @GetMapping("/getAllDates")
//    public ResponseEntity<List<java.sql.Date>> getAllDates(@RequestParam(name = "user_id") Long userId) throws SQLException {
//        List<java.sql.Date> res = new ArrayList<>();
//
//        try (Connection connection = dataSource.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement("SELECT date FROM gpsdata WHERE user_id=? GROUP BY date")) {
//                statement.setLong(1, userId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        res.add(resultSet.getDate(1));
//                    }
//                }
//            }
//        }
//
//        return ResponseEntity.ok(res);
//    }
}
