package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * 1. MemberForm을 쓰는 이유
     *  - 실제 화면에서 넘어올때의 validation과
     *  Member 엔티티 validation이 다를 수 있음
     *  억지로 맞추지 않고 화면에 fit한 form 객체를 만들어서 사용하는 것이 깔끔하다.
     *
     *  - 실제로 form객체와 엔티티의 차이가 발생하게 되는데 엔티티만 쓸 경우
     *  화면 종속적인 코드가 계속해서 삽입이 되게 된다.
     *
     *  - 화면 종속적인 부분들은 form 객체나 dto를 사용하는 것이 바람직하다.
     *
     *  - 엔티티는 최대한 순수하게 비즈니스 로직에만 의존성이 있도록 설계하는 것이 중요
     *
     * 2. Valid 뒤에 BindingResult가 있으면 메소드에서 튕겨내지 않고 안쪽 코드를 수행한다.
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        /**
         * 스프링과 타임리프의 인터그리티가 잘 연계되어 있음
         */
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        /**
         * 더 복잡해지면 memeber 엔티티를 그대로 화면에 뿌리지 않고,
         * dto 등을 이용하여 필요한 데이터만 뽑아서 전달하는 것이 좋다.
         *
         * 서버사이드 렌더링을 하는 경우 필요한 데이터만 자동으로 처리하여 보내지만,
         * API를 만들 때는 절대 엔티티로 보내면 안된다.
         * 패스워드가 노출될 수도 있고
         * member 엔티티는 하나의 스펙이기 때문에 API 스펙이 변할 수 있음.
         *
         */
        return "members/memberList";
    }
}
