package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // 이렇게 쓰면 final 붙은 필드를 모아서 생성자를 만들어줌 -> 코드가 간결해진다
public class OrderServiceImpl implements OrderService{

    // 다양한 의존 관계 주입 방법 1 : 생성자 주입
    // 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다.
    // 불변, 필수 의존관계에서 사용
    // 생성자가 1개만 있다면 @Autowired 없어도 알아서 설정된다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // 이렇게 하면 Interface 에만 의존할 수 있다

    // DiscountPolicy 가 있기 때문에 OrderService 에서는 할인에 대한 구현을 신경쓸 필요 없다.
    // 단일 책임의 원칙이 잘 지켜진것처럼 보이지만
    // 결국 FixDiscountPolicy, RateDiscountPolicy 등 구현객체에 의존한다.
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 이런 코드는 DIP, OCP 위반

    // 조회 빈이 2개 이상일때
    // 타입으로 빈을 가져왔을때, 여러개가 있다면 필드명, 파라미터명 을 확인한다. DiscountPolicy -> rate, fix -> 변수명 확인 -> 이때도 여러개면 오류
    // @Qualifier 이름으로 조회 Qualifier 이름끼리 매칭 -> 빈 이름 매칭 -> 오류
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
    // @Primary 가 우선순위
    // Primary 랑 Qualifier 중에서는 Qualifier 가 우선순위가 높다
//    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        // 이렇게 구현하면 MemberRepository, DiscountPolicy interface 에만 의존한다.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 생성자 주입을 사용해야되는 이유
    // final 사용 가능
    // 불변
    // 누락된 부분 바로 확인 가능
    // 누락된 경우 컴파일 과정에서 확인 가능
    // 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법
    // 가끔 옵션이 필요할때만 수정자 주입

    // 다양한 의존 관계 주입 방법 2 : 수정자 주입
    // setter 를 사용해서 주입
    // 선택, 변경 가능성이 있는 의존관계에서 사용
    // @Autowired 가 꼭 있어야된다
    /*
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
     */

    // 다양한 의존 관계 주입 방법 3 : 필드 주입
    // Spring 이 아닌 환경에서 Test 만들기가 어려움 -> setter 가 필요함 -> 수정자 주입...?
    // 별로 효율적이지않음...쓰지마!
    // DI 프레임워크가 없으면 아무것도 할수가 없다(Spring 환경에서만 사용가능)
    // Test 코드내에서 확인할때는 사용 가능
    // Configuration test 할때도 사용 가능 -> 근데 하지마
    /*
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DiscountPolicy discountPolicy;
     */

    // 다양한 의존 관계 주입 방법 4 : 일반 메서드 주입
    // 일반 메서드를 통해서 주입 받을 수 있다.
    // 한번에 여러 필드를 주입 받을 수 있다.
    // 일반적으로 잘 사용하지 않는다.
    /*
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
     */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
