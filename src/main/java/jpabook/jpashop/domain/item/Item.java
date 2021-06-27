package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter

public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 실무에서는 이렇게 잘 쓰지 않지만
    // 이렇게도 사용 가능하다는 것을 보여주기 위한 예제
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /**
     * 재고를 늘리고 줄이는 로직
     * 도메인 주도 설계에서 엔티티 자체가 해결할 수 있는 것들은 엔티티 안에 로직을 넣는 것이 객체지향적임
     * stockQuantity 를 갖고 있기 때문에 직접 로직을 수행하는 것이 응집도가 더 높다.
     *
     * 값의 변경이 필요할 때
     * 엔티티 바깥에서 값을 변경하고 set 메서드를 사용하는 것이 아니라
     * "비즈니스 메서드" 를 사용하여 변경하는 것이 좋다.
     */

    /**
        stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
