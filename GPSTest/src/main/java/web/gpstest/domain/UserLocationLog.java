package web.gpstest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_location_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLocationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private String userId;

    private String locationName;  // Kakao API 주소도 같이 저장

    private Double latitude;      // 위도
    private Double longitude;     // 경도

    @CreationTimestamp
    private LocalDateTime loggedAt;
}
