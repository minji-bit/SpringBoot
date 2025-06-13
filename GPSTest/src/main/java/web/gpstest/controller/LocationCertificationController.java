package web.gpstest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.gpstest.dto.LocationRequestDto;
import web.gpstest.service.LocationService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationCertificationController {

    private final LocationService locationService;

    @PostMapping("/certify-location")
//    public ResponseEntity<String> certify(@RequestBody LocationRequestDto request, @AuthenticationPrincipal UserDetails user) {
    public ResponseEntity<String> certify(@RequestBody LocationRequestDto request) {
        String userId =  "test-user"; // 임시 사용자 ID (나중에 Security 적용 시 교체)
        boolean certified = locationService.recordLocation(userId, request);

        String message = certified ? "현지인 인증 완료 🎉" : "현 위치 인증 기록 저장 완료";
        return ResponseEntity.ok(message);
    }
}
