package robson.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import robson.dto.UserRequestDto;
import robson.dto.UserResponseDto;
import robson.entity.User;
import robson.enums.UserStatus;
import robson.exception.BusinessException;
import robson.mapper.userMapper;
import robson.repository.ServiceOrderRepository;
import robson.repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    ServiceOrderRepository serviceOrderRepository;

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        userRepository.persist(user);
        return userMapper.toResponseDto(user);

    }

    public List<UserResponseDto> findAll() {
        // aqui eu irei listar todos os usuários
        return userRepository.listAll()
        // busco no banco de dados todos os usuarios
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
        // aqui acontece a conversão em massa, pelo a lista entidades, o "userRepository" e transformo cada User em UserResponseDto, e por fim devolvo tudo em lista, exatamente como fala aqui: List<UserResponseDto>
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new BusinessException("Usuário não encontrado.");
        }

        return userMapper.toResponseDto(user);
    }

    @Transactional
    public UserResponseDto update(long id, UserRequestDto dto) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BusinessException("Usuário não encontrado.");
        }
        if (user.status == UserStatus.INACTIVE) {
            throw new BusinessException("Usuário inativo não pode ser atualizado.");
        }

        user.name = dto.name;

        return userMapper.toResponseDto(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BusinessException("Usuário não encontrado com id: " + id);
        }
    //se não existir o usuário, retorno o erro "Usuário não encontrado."    
        long nonFinishedOrders = serviceOrderRepository.countNonFinishedOrdersByUserId(id);
    // aqui pergunto, quantas ordens desse usuario achado ainda não estão finalizadas

        if (nonFinishedOrders > 0) {
            throw new BusinessException("Não é permitido deletar usuário com ordens não finalizadas.");
        }
        userRepository.delete(user);
    }
}