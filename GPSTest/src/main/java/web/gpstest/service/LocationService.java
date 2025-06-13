package web.gpstest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.gpstest.domain.UserLocalCertification;
import web.gpstest.domain.UserLocationLog;
import web.gpstest.dto.LocationRequestDto;
import web.gpstest.repository.UserLocalCertificationRepository;
import web.gpstest.repository.UserLocationLogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserLocationLogRepository logRepo;
    private final UserLocalCertificationRepository certRepo;

    public boolean recordLocation(String userId, LocationRequestDto request) {
        // 1. 위치 인증 기록 저장
        logRepo.save(UserLocationLog.builder()
                .userId(userId)
                .locationName(request.getLocationName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build());

        // 2. 반경 N미터 (예: 300m) 이내 30일 내 인증 횟수 확인
        long count = logRepo.countNearbyLogs(
                userId,
                request.getLongitude(),
                request.getLatitude(),
                300.0,  // 반경 300m
                LocalDateTime.now().minusDays(30)
        );

        // 3. 3회 이상이면 인증 테이블에 등록
        if (count >= 3 && !certRepo.existsByUserIdAndLocationName(userId, request.getLocationName())) {
            certRepo.save(UserLocalCertification.builder()
                    .userId(userId)
                    .locationName(request.getLocationName())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .certifiedAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusDays(60))  // 인증 60일 유효
                    .build());
            return true;
        }

        return false;
    }
}


