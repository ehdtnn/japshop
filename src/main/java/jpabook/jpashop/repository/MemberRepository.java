package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
//    private EntityManager em;

//    @Autowired (spring jpa 라이브러리 autowired 가능)
//    private EntityManager em;

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
        /**
         * db마다 다르지만, generateValue 전략에서는 기본적으로
         * persist 하는 시점에 insert 문이 수행 되지 않고,
         * commit 하는 시점에 flush 가 되면서 insert 문이 수행된다.
         */
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        /*
         JPQL
         sql은 테이블을 대상으로 쿼리하지만
         jpql은 엔티티 객체를 대상으로 쿼리한다.
         */
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
