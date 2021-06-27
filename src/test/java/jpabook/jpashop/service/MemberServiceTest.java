package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.invocation.RealMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
/**
 * @RunWith
 * junit과 spring 같이 실행
 */
@SpringBootTest
/**
 * @SpringBootTest
 * spring boot를 띄운 상태로 테스트 하려면 있어야 한다.
 * 없으면 autowired 다 실패 (스프링컨테이너 안에서 테스트 수행)
 */
@Transactional
/**
 * @Transactional
 * spring @Transactional의 기본 동작은
 * commit 하지 않고 rollback 시킨다.
 * (영속성 컨텍스트의 실제 쿼리를 수행하지 않는다.)
 * em.flush()를 통해 쿼리 수행할 수 있다.
 */
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false) // commit하고 rollback을 수행하지 않음
    // 테스트를 반복적으로 하기 위해서는 rollback 시킨다
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트에 있는 쿼리를 날린다.
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member);
        memberService.join(member2);

        // then
        fail("에외가 발생행야 한다."); // 여기로 오면 안되는데 넘어오는지 확인할 때 쓰면 좋다.
    }


}