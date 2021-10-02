package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        // 이렇게 구현하면 MemberRepository, DiscountPolicy interface 에만 의존한다.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
