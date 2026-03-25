package robson.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import robson.enums.OrderStatus;

@Entity
@Table(name = "service_order")
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String description;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    public OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}