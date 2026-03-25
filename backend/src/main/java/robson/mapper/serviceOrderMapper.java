package robson.mapper;

import java.time.LocalDateTime;
// importa a classe LocalDateTime ela será usada para preencher, createdAt, updatedAt

import robson.dto.ServiceOrderRequestDto;
// importa o dto de entrada da ordem de serviço
import robson.dto.ServiceOrderResponseDto;
import robson.entity.ServiceOrder;
import robson.entity.User;
import robson.enums.OrderStatus;

public class serviceOrderMapper {

    public static ServiceOrder toEntity(ServiceOrderRequestDto dto, User user) {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.description = dto.description;
        serviceOrder.createdAt = LocalDateTime.now();
        serviceOrder.updatedAt = LocalDateTime.now();
        serviceOrder.status = OrderStatus.OPEN;
        serviceOrder.user = user;
        return serviceOrder;
    }

    public static ServiceOrderResponseDto toResponseDto(ServiceOrder serviceOrder) {
        ServiceOrderResponseDto dto = new ServiceOrderResponseDto();
        dto.id = serviceOrder.id;
        dto.description = serviceOrder.description;
        dto.createdAt = serviceOrder.createdAt;
        dto.updatedAt = serviceOrder.updatedAt;
        dto.status = serviceOrder.status != null ? serviceOrder.status.name() : null;
        dto.userId = (Long) (serviceOrder.user != null ? serviceOrder.user.id : null);
        dto.userName = serviceOrder.user != null ? serviceOrder.user.name : null;
        return dto;
    }
}