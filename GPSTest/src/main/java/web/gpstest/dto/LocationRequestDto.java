package web.gpstest.dto;

import lombok.Data;

@Data
public class LocationRequestDto {
    private String locationName;
    private Double latitude;
    private Double longitude;
}
