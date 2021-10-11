package com.example.demo;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public void setName(String name) {
        this.name = name;
    }

    //==생성 메서드==//
    public static Member createMember(String name, Grade grade) {
        Member member = new Member();
        member.name = name;
        member.grade = grade;

        return member;
    }

}
