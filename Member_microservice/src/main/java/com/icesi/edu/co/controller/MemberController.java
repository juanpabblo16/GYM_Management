package com.icesi.edu.co.controller;

import com.icesi.edu.co.model.Member;
import com.icesi.edu.co.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Endpoint para registrar un nuevo miembro
    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        Member savedMember = memberService.registerMember(member);
        return ResponseEntity.ok(savedMember);
    }

    // Endpoint para obtener todos los miembros
    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
}
