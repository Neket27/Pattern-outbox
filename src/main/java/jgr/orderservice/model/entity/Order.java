package jgr.orderservice.model.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Order extends BaseEntity{
    private Long customerId;

    private List<Long> productIds;

    private String deliveryAddress;

    private String paymentMethod;

    private String orderNotes;

    private String customerEmail;
}
