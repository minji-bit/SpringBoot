package web.gpstest.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_local_certification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLocalCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String locationName;

    private Double latitude;
    private Double longitude;

    private LocalDateTime certifiedAt;
    private LocalDateTime expiredAt;
}
