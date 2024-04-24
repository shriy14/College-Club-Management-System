package com.example.RecipeManager.Service;

import com.example.RecipeManager.Model.Club;

public class ClubFactory {
    public static Club createClub(String clubName, String email, String password, String facultyId,
                                  String clubType, String headSrn, String name, int sem, String dept,
                                  String phoneno, String gender) {
        Club club = new Club();
        club.setClubName(clubName);
        club.setEmail(email);
        club.setPassword(password);
        club.setFacultyId(facultyId);
        club.setClubType(clubType);
        club.setHeadSrn(headSrn);
        club.setName(name);
        club.setSem(sem);
        club.setDept(dept);
        club.setPhoneno(phoneno);
        club.setGender(gender);
        return club;
    }
}