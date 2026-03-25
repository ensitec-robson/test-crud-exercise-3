package robson.dto;

import java.time.LocalDateTime;

public class ServiceOrderResponseDto {
    public Long id;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String status;
    public Long userId;
    public String userName;
}
