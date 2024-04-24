package com.example.RecipeManager.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.RecipeManager.Model.Club;
@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findByEmail(String email);
    Club findByClubNameAndPassword(String clubName, String password);
    Club findByClubName(String clubName);

}