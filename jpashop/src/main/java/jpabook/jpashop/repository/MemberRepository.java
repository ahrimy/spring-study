package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // Spring 이 Entity Manager 를 만들어서 주입해준다.
//    private EntityManager em;
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속성 context 에 member 를 넣는다.
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // jpql: SQL 은 Table 을 대상으로 쿼리 , jpql 은 Entity 객체를 대상으로 쿼리
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
