package web.gpstest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.gpstest.domain.UserLocalCertification;

public interface UserLocalCertificationRepository extends JpaRepository<UserLocalCertification, Long> {

    boolean existsByUserIdAndLocationName(String userId, String locationName);
}
