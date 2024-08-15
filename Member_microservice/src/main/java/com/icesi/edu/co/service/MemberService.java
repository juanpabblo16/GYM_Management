package com.icesi.edu.co.service;

import com.icesi.edu.co.model.Member;
import com.icesi.edu.co.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Método para registrar un nuevo miembro
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    // Método para obtener todos los miembros
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
