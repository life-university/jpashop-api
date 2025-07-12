package com.example.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import com.example.jpashop.domain.Member;
import com.example.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    void memberJoin() {
        // given
        Member member = Member.builder()
            .name("test user")
            .build();

        // when
        Long savedId = memberService.join(member);

        // then
        assertThat(member).isEqualTo(memberRepository.findById(savedId));
    }

    @Test()
    void memberDuplicate() {
        // given
        Member member1 = Member.builder().name("test user 1").build();
        Member member2 = Member.builder().name("test user 1").build();

        // when & then
        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2))  // here should throw an exception
            .isInstanceOf(IllegalStateException.class);
    }

}
