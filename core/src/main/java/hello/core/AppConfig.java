package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Application 에 대한 환경 구성
// 생성자 주입
// DI(Dependency Injection) : 의존관계 주입, 의존성 주입

@Configuration
public class AppConfig {
    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()
    // MemoryMemberRepository 가 두번 생성된다 -> 싱글톤 깨지나?
    // => 안깨진다. ConfigurationSingletonTest 확인
    // 왜?

    // 각각의 역할과 구현 클래스가 한눈에 보임
    @Bean // Bean : Spring Container 에 등록이 됨, method 이름으로 등록됨
    public MemberService memberService() {
//        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
//        System.out.println("AppConfig.orderService");

        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null; // 필드 주입 사용할 때
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // 할인 정책을 변경할때 이곳의 코드만 변경하면 된다.
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
