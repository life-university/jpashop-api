package com.example.jpashop.api;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse createMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse createMemberV2(@RequestBody @Valid CreateMemberRequest dto) {
        Member member = Member
            .builder()
            .name(dto.name())
            .build();
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest dto) {
        memberService.update(id, dto.name());
        Member findMember = memberService.findById(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> getMemberV1() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("/api/v2/members")
    public Result getMemberV2() {
        List<GetMemberResponse> members = memberService
            .findMembers()
            .stream()
            .map(m -> new GetMemberResponse(m.getName(), m.getAddress()))
            .toList();
        return new Result<>(members.size(), members);
    }

    record CreateMemberRequest(
        @NotEmpty
        String name
    ) {
    }

    record CreateMemberResponse(Long id) {
    }

    record UpdateMemberRequest(
        @NotEmpty
        String name
    ) {
    }

    record UpdateMemberResponse(Long id, String name) {
    }

    record Result<T>(int count, T data) {
    }

    record GetMemberResponse(String name, Address address) {
    }

}
