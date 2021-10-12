package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch) {
        // 동적 쿼리 만드는건 쉽지 않은데 어떻게 만들거냐
        // 1. 조건문으로 문자열 조작해서 jpql 생성 -> 번거롭고, 버그잡기 이렇게 하지마
        // 2. JPA Criteria (findAllByCriteria 참고) -> JPA 표준스펙이긴 함, 근데 만들기도 어렵고, 읽기도 어려워서 유지보수 힘듬 -> 실무에서 못써
        // 3. Querydsl 로 처리 -> 처음에 좀 복잡해보이지만 실무에서 쓰기 제일 좋다. -> 나중에 설명
//    }


    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        // fetch join
        // 재사용 가능
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    // repository 에 API 스펙이 들어와있다 -> 이건 의도랑 맞지 않는다.
    // repository 는 Entity 를 조회하는 용도 => OrderSimpleQueryRepository 에 따로 구현한다.
//    public List<OrderSimpleQueryDto> findOrderDtos() {
//        // sql 처럼 원하는 값을 선택해서 조회
//        // 재사용 X
//        return em.createQuery(
//                        "select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
//                                " from Order o" +
//                                " join o.member m" +
//                                " join o.delivery d", OrderSimpleQueryDto.class)
//                .getResultList();
//    }
}
