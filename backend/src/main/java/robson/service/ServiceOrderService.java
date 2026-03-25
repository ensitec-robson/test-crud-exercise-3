package robson.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import robson.dto.ServiceOrderRequestDto;
import robson.dto.ServiceOrderResponseDto;
import robson.dto.UpdateServiceOrderStatusDto;
import robson.entity.ServiceOrder;
import robson.entity.User;
import robson.enums.OrderStatus;
import robson.enums.UserStatus;
import robson.exception.BusinessException;
import robson.mapper.serviceOrderMapper;
import robson.repository.ServiceOrderRepository;
import robson.repository.UserRepository;

@ApplicationScoped
public class ServiceOrderService {

    @Inject
    ServiceOrderRepository serviceOrderRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public ServiceOrderResponseDto create(ServiceOrderRequestDto dto) {
        User user = userRepository.findById(dto.userId);

        if (user == null) {
            throw new BusinessException("Usuário não encontrado.");
        }

        if (user.status == UserStatus.INACTIVE) {
            throw new BusinessException("Usuário inativo não pode receber nova ordem de serviço.");
        }

        long openOrders = serviceOrderRepository.countOpenOrdersByUserId(user.id);
        if (openOrders >= 5) {
            throw new BusinessException("Limite de 5 ordens OPEN atingido para este usuário.");
        }
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(dto, user);
        serviceOrderRepository.persist(serviceOrder);
        return serviceOrderMapper.toResponseDto(serviceOrder);
    }

    public List<ServiceOrderResponseDto> findAll() {
        return serviceOrderRepository.listAll()
                .stream()
                .map(serviceOrderMapper::toResponseDto)
                .collect(Collectors.toList());
    //aqui eu busco entidades, converto uma por uma e devolvo a lista de DTO
    }

    public ServiceOrderResponseDto findById(Long id) {
    // busco uma ordem específica
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);
        if (serviceOrder == null) {
            throw new BusinessException("Ordem de serviço não encontrada.");
        }

        return serviceOrderMapper.toResponseDto(serviceOrder);
    }


    @Transactional
    public ServiceOrderResponseDto updateStatus(Long id, UpdateServiceOrderStatusDto dto) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);
        if (serviceOrder == null) {
            throw new BusinessException("Ordem de serviço não encontrada.");
        }

        OrderStatus newStatus;

        try {
            newStatus = OrderStatus.valueOf(dto.status);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido.");
        }
    // aqui transformo a string recebida em enum, exemplo, { "status:" "In_progress"} vira: orderStatus.In_progress, se o texto vir errado, retorna um erro

        OrderStatus currentStatus = serviceOrder.status;
        boolean validTransition =
                (currentStatus == OrderStatus.OPEN && newStatus == OrderStatus.IN_PROGRESS) ||
                (currentStatus == OrderStatus.IN_PROGRESS && newStatus == OrderStatus.FINISHED) ||
                (currentStatus == OrderStatus.OPEN && newStatus == OrderStatus.CANCELED);
    // aqui eu permito apenas 3 mudanças, Open que é in_progress, In_Progress que é finished e open que é canceled, qualquer outra combinação é inválida.
        if (!validTransition) {
            throw new BusinessException("Transição de status inválida.");
        }
    // se a transição não estiver na lista permitida, barra a atualização, para impedir coisas como: Finished -> Open, Finished -> Open....
        serviceOrder.status = newStatus;
        serviceOrder.updatedAt = LocalDateTime.now();
    // se a troca for válida, muda o status, atualiza a data de modificação

        return serviceOrderMapper.toResponseDto(serviceOrder);
    // devolve a ordem atualizada
    }

    // metodo delete
    @Transactional
    public void delete(Long id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id);
    // busco a ordem

        if (serviceOrder == null) {
            throw new BusinessException("Ordem de serviço não encontrada.");
        }
    // se não existir, retorna o erro "Ordem de serviço não encontrada."
        serviceOrderRepository.delete(serviceOrder);
    // se existir, deleta
    }
}