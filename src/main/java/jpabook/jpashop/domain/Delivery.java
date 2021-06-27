package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)  // 연관관계 거울 (Order 클래스의 delivery 필드)
    private Order order;

    @Embedded
    private Address address;

    // @Enumerated(EnumType.ORDINAL) // 1,2,3,4,... (순서대로 되기 때문에 중간에 변경되면 밀려서 꼬여서 쓰지말자)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
