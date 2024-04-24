package com.example.RecipeManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.RecipeManager.Model.Member;
import com.example.RecipeManager.Service.MemberService;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import com.example.RecipeManager.Model.Event;

@Controller
public class MemDashboardCon {
    private final MemberService memberService;
    private final JdbcTemplate jdbcTemplate;
    public MemDashboardCon(MemberService memberService, JdbcTemplate jdbcTemplate) {
        this.memberService = memberService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/member/{srn}")
    public String memberDashboard(@PathVariable String srn, Model model) {
        Member member = memberService.findBySrn(srn);
        if (member != null) {
            model.addAttribute("member", member);

            String clubsSql = "SELECT clubname FROM ClubMember WHERE SRN = ?";
            List<String> clubs = jdbcTemplate.queryForList(clubsSql, String.class, srn);
            model.addAttribute("clubs", clubs);

            String eventsSql = "SELECT * FROM event WHERE clubname IN (SELECT clubname FROM ClubMember WHERE SRN = ?)";
            @SuppressWarnings("deprecation")
            List<Event> upcomingEvents = jdbcTemplate.query(eventsSql, new Object[]{srn}, 
                                                         (rs, rowNum) -> {
                                                             Event event = new Event();
                                                                 event.setEventName(rs.getString("eventname"));
                                                                 event.setClubName(rs.getString("clubname"));
                                                                 event.setDescription(rs.getString("description"));
                                                                 event.setLocation(rs.getString("loc"));
                                                                 event.setType(rs.getString("type"));
                                                                 event.setTimestamp(rs.getTimestamp("timestamp"));
                                                                 event.setBudget(rs.getDouble("budget"));
                                                                 event.setRegistrationLink(rs.getString("registrationlink"));
                                                                 event.setBanner(rs.getBytes("banner"));
                                                                 return event;
                                                             });

            model.addAttribute("upcomingEvents", upcomingEvents);

            return "member"; 
        } else {
            return "error";
        }
    }
    @PostMapping("/members/{srn}/applyClub")
public String applyClub(@PathVariable String srn, @RequestParam String clubName, @RequestParam String message, Model model) {
    Member member = memberService.findBySrn(srn);
    if (member != null) {
        String insertSql = "INSERT INTO requests (srn, memberName, memberEmail, clubName, message) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, srn, member.getName(), member.getEmail(), clubName, message);
        return "redirect:/member/" + srn; 
    } else {
        model.addAttribute("errorMessage", "Member not found");
        return "error"; 
    }
}


}
