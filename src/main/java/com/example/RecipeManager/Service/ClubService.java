package com.example.RecipeManager.Service;

import org.springframework.stereotype.Service;
import com.example.RecipeManager.Model.Club;
import com.example.RecipeManager.Repository.ClubRepository;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Club createClub(String clubName, String email, String password, String facultyId,
                           String clubType, String headSrn, String name, int sem, String dept,
                           String phoneno, String gender) {
        Club club = ClubFactory.createClub(clubName, email, password, facultyId, clubType, headSrn,
                                           name, sem, dept, phoneno, gender);
        return clubRepository.save(club);
    }

    public Club findByEmail(String email) {
        return clubRepository.findByEmail(email);
    }

    public Club findByClubNameAndPassword(String clubName, String password) {
        try {
            Club club = clubRepository.findByClubNameAndPassword(clubName, password);
            System.out.println("Found club: " + club);
            return club;
        } catch (Exception e) {
            System.err.println("Exception while finding club by name and password: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Club getClubDetailsByName(String clubName) {
        return clubRepository.findByClubName(clubName);
    }
}