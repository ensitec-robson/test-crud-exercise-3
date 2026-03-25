package robson.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import robson.entity.ServiceOrder;
import robson.enums.OrderStatus;

@ApplicationScoped
public class ServiceOrderRepository implements PanacheRepository<ServiceOrder> {

    public long countOpenOrdersByUserId(Long userId) {
        return count("user.id = ?1 and status = ?2", userId, OrderStatus.OPEN);
    }
    //aqui eu conto quantas ordens OPEN o usuário tem
    public long countNonFinishedOrdersByUserId(Long userId) {
        return count(
            "user.id = ?1 and (status = ?2 or status = ?3)",
            userId,
            OrderStatus.OPEN,
            OrderStatus.IN_PROGRESS
        );
    }
    //aqui eu conto quantas ordens esse usuário tem que ainda estão abertas ou em andamento
}