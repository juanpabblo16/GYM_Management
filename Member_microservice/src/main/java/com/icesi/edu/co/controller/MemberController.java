package com.icesi.edu.co.controller;

import com.icesi.edu.co.model.Member;
import com.icesi.edu.co.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/gym")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Operation(
            summary = "Registrar un nuevo miembro",
            description = "Este endpoint permite registrar un nuevo miembro en el sistema. Requiere que el solicitante tenga el rol 'ROLE_MEMBER'."
    )
    @PostMapping("/member")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        Member savedMember = memberService.registerMember(member);
        return ResponseEntity.ok(savedMember);
    }

    @Operation(
            summary = "Obtener todos los miembros",
            description = "Este endpoint permite obtener una lista de todos los miembros registrados en el sistema. Requiere que el solicitante tenga el rol 'ROLE_MEMBER'."
    )
    @GetMapping("/member")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
}
