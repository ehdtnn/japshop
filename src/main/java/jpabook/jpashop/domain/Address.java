package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
/*
 값 타입은 변경 불가능하게 설계해야 한다. (immutable)
 Setter를 제거하고, 생성자에 모든 값을 초기화 하도록 설계.
 jpa 스펙상 임베디드 타입은 자바 기본생성자를 public이나 protected로 해야 한다.
 jpa가 이러한 제약을 두는 이유는
 jpa 구현 라이브러리가 객체를 생성할 때
 리플렉션이나 프록시 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.
 */
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {  //  (protected가 그나마 더 안전)
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
