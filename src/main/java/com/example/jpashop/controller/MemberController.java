package com.example.jpashop.controller;

import com.example.jpashop.controller.dto.MemberForm;
import com.example.jpashop.controller.dto.MemberList;
import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", MemberForm.empty());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = Address.builder()
            .city(memberForm.city())
            .street(memberForm.street())
            .zipcode(memberForm.zipcode())
            .build();

        Member member = Member.builder()
            .name(memberForm.name())
            .address(address)
            .build();
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<MemberList> members = memberService.findMembers()
            .stream()
            .map(m -> new MemberList(
                    m.getId(),
                    m.getName(),
                    m.getAddress()
                )
            ).toList();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
