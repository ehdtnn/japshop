package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    /**
     * item 은 저장하기 전까지 id 값이 없음. -> 완전히 새로 생성한 객체를 의미한다. -> save를 호출하면 null이고 persist 수행
     * null 이 아니면 값이 db에 등록되어 있거나 어디서 가져오는 경우
     * merge는 실제 update와는 다르지만 뒤에 후술
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            /**
             * merge 란
             * 영속성 컨텍스트에서 식별자를 통해 찾은 item을
             * 파라미터의 값으로 변경 시킨다.
             *
             * 실무에서 사용하면 위험하다.
             * 업데이트 지정하지 않은 값은 null로 들어가기 때문에
             * 변경감지로 명확하게 업데이트 시키는 것이 낫다.
             *
             */
            Item merge = em.merge(item);
            // 이후에 사용하려면 merge를 사용한다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
