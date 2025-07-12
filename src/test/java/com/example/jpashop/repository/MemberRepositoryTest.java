package com.example.jpashop.repository;

import com.example.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void testMember() {
        // given
        Member member = Member.builder()
            .name("testUser")
            .build();

        // when
        memberRepository.save(member);

            // then
        Member findMember = memberRepository.findById(member.getId());

        Assertions.assertThat(findMember).isEqualTo(member);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    }

}
