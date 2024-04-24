package com.example.RecipeManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  com.example.RecipeManager.Model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findBySrn(String srn);
    Member findByEmailAndPassword(String email, String password);
    Member findBySrnAndPassword(String srn, String password);
}