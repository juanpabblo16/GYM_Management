package com.icesi.edu.co.service;

import com.icesi.edu.co.model.Member;
import com.icesi.edu.co.repository.MemberRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public Member addMember(Member member) {
        Member savedMember = memberRepository.save(member);
        amqpTemplate.convertAndSend("notification-exchange", "notification.routing.key", "New member registered: " + savedMember.getName());
        return savedMember;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
