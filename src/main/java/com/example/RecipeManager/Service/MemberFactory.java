package com.example.RecipeManager.Service;

import com.example.RecipeManager.Model.Member;

public class MemberFactory {
    public static Member createMember(String email, String password, String domain, String srn, String name,
                                      int sem, String dept, String phoneno, String gender) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setDomain(domain);
        member.setSrn(srn);
        member.setName(name);
        member.setSem(sem);
        member.setDept(dept);
        member.setPhoneno(phoneno);
        member.setGender(gender);
        return member;
    }
}