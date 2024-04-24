package com.example.RecipeManager.Service;

import org.springframework.stereotype.Service;
import com.example.RecipeManager.Model.Member;
import com.example.RecipeManager.Repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findBySrn(String srn) {
        return memberRepository.findBySrn(srn);
    }

    public Member findByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public Member findBySrnAndPassword(String srn, String password) {
        return memberRepository.findBySrnAndPassword(srn, password);
    }

    public Member createMember(String email, String password, String domain, String srn, String name,
                               int sem, String dept, String phoneno, String gender) {
        Member member = MemberFactory.createMember(email, password, domain, srn, name, sem, dept, phoneno, gender);
        return memberRepository.save(member);
    }
}