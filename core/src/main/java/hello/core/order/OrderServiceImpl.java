package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    // DiscountPolicy 가 있기 때문에 OrderService 에서는 할인에 대한 구현을 신경쓸 필요 없다.
    // 단일 책임의 원칙이 잘 지켜진것처럼 보이지만
    // 결국 FixDiscountPolicy, RateDiscountPolicy 등 구현객체에 의존한다.
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 이런 코드는 DIP, OCP 위반
    private final DiscountPolicy discountPolicy; // 이렇게 하면 Interface 에만 의존할 수 있다

    // 이렇게 구현하면 MemberRepository, DiscountPolicy interface 에만 의존한다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
