package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional()
/**
 *
 @Transactional()
 public method들이 기본적으로 transaction이 걸린다.
 spring transactional vs javax transactional 중에
 spring 에서 제공하는 transactional이 쓸 수 있는 것이 훨씬 많다.
 */
//@AllArgsConstructor // 생성자 자동으로 만들어준다.
@RequiredArgsConstructor // final 있는 필드만 생성자 만들어 준다. (생성자 injection)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        /*
        멀티쓰레딩 상황에서 validation을 통과하는 경우가 발생할 수 있기 때문에
        안전하게 name을 unique 제약조건을 걸어준다.
         */
    }

    /**
     * 회원 전체 조회
     */
    @Transactional(readOnly = true) // 최적화 (dirty checking 등..)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }


}
