package web.gpstest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.gpstest.domain.UserLocationLog;

import java.time.LocalDateTime;

public interface UserLocationLogRepository extends JpaRepository<UserLocationLog, Long> {

    @Query(value = """
        SELECT COUNT(*) FROM user_location_log
        WHERE user_id = :userId
          AND ST_DWithin(
              ST_MakePoint(longitude, latitude)::geography,
              ST_MakePoint(:longitude, :latitude)::geography,
              :radiusMeters
          )
          AND logged_at >= :since
        """, nativeQuery = true)
    long countNearbyLogs(@Param("userId") String userId,
                         @Param("longitude") Double longitude,
                         @Param("latitude") Double latitude,
                         @Param("radiusMeters") Double radiusMeters,
                         @Param("since") LocalDateTime since);
}
