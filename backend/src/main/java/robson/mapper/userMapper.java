package robson.mapper;

import robson.dto.UserRequestDto;
import robson.dto.UserResponseDto;
import robson.entity.User;
import robson.enums.UserStatus;

public class userMapper {
    public static User toEntity(UserRequestDto dto) {
        User user = new User();
        user.name = dto.name;
        user.status = UserStatus.ACTIVE;
        return user;
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.id;
        dto.name = user.name;
        dto.status = user.status != null ? user.status.name() : null;
    // essa minha lógica  significa: se user.status existir, pega o nome dele como texto, se não, devolve null
    // exemplo, se user.status for UserStatus.Active, o resultado retornado será "active" o método .name() pega o texto do enum
        return dto;
    }
}