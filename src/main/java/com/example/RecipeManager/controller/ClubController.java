package com.example.RecipeManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.RecipeManager.Model.Club;
import com.example.RecipeManager.Service.ClubService;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Controller
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/club/{clubName}")
    public String clubDashboard(@PathVariable String clubName, Model model) {
        Club club = clubService.getClubDetailsByName(clubName);
        
        if (club == null) {
            return "club-not-found";
        }
        List<Map<String, Object>> clubMembers = getClubMembersByName(clubName);
        List<Map<String, Object>> clubEvents = getClubEvents(clubName);

        model.addAttribute("club", club);
        model.addAttribute("clubMembers", clubMembers);
        model.addAttribute("clubEvents", clubEvents);

        return "club";
    }

    @GetMapping("/event")
    public String showEventForm() {
        return "event";
    }

    @GetMapping("/faculty/{faculty_id}")
public String getFacultyClubs(@PathVariable("faculty_id") int facultyId, Model model) {
    try {
        String selectFacultySql = "SELECT * FROM faculty WHERE id = ?";
        Map<String, Object> faculty = jdbcTemplate.queryForMap(selectFacultySql, facultyId);
        String facultyName = (String) faculty.get("faculty_name");

        String selectClubsSql = "SELECT c.*, COUNT(cm.SRN) AS memberCount, SUM(e.budget) AS totalBudget " +
                                "FROM club c " +
                                "LEFT JOIN ClubMember cm ON c.clubName = cm.clubName " +
                                "LEFT JOIN event e ON c.clubName = e.clubname " +
                                "WHERE c.faculty_id = ? " +
                                "GROUP BY c.id";
        List<Map<String, Object>> clubs = jdbcTemplate.queryForList(selectClubsSql, facultyId);

        model.addAttribute("facultyName", facultyName);
        model.addAttribute("clubs", clubs);

        return "faculty";
    } catch (Exception e) {
        e.printStackTrace();
        return "error";
    }
}





@PostMapping("/addEvent")
public String addEvent(@RequestParam("clubname") String clubName,
                       @RequestParam("eventname") String eventName,
                       @RequestParam("description") String description,
                       @RequestParam("location") String location,
                       @RequestParam("type") String type,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("budget") double budget,
                       @RequestParam("registrationlink") String registrationLink,
                       @RequestParam("banner") MultipartFile banner,
                       Model model) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime parsedTimestamp = LocalDateTime.parse(timestamp, formatter);

    byte[] bannerBytes = null;
    try {
        bannerBytes = banner.getBytes();
    } catch (IOException e) {
        e.printStackTrace();
    }

    String insertSql = "INSERT INTO event (clubname, eventname, description, loc, type, timestamp, budget, registrationlink, banner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
        jdbcTemplate.update(insertSql, clubName, eventName, description, location, type, Timestamp.valueOf(parsedTimestamp), budget, registrationLink, bannerBytes);
    } catch (Exception e) {
        e.printStackTrace();
    }

    return "redirect:/event";
}


    
    @PostMapping("/club/{clubName}/requests")
    public String handleClubRequests(@PathVariable String clubName, @RequestParam String action,
                                     @RequestParam String srn) {
                                        if ("accept".equals(action)) {
                                            String acceptRequestQuery = "INSERT INTO ClubMember (SRN, clubName) VALUES (?, ?)";
                                            jdbcTemplate.update(acceptRequestQuery, srn, clubName);
                                    
                                            String updateRequestStatusQuery = "UPDATE requests SET status = 'accepted' WHERE srn = ? AND clubName = ?";
                                            jdbcTemplate.update(updateRequestStatusQuery, srn, clubName);
                                        } else if ("reject".equals(action)) {
                                            String rejectRequestQuery = "UPDATE requests SET status = 'rejected' WHERE srn = ? AND clubName = ?";
                                            jdbcTemplate.update(rejectRequestQuery, srn, clubName);
                                        }

        return "redirect:/club/{clubName}";
    }

    @GetMapping("/club/{clubName}/requests")
public String viewClubRequests(@PathVariable String clubName, Model model) {
    String pendingRequestsQuery = "SELECT * FROM requests WHERE clubName = '" + clubName + "' AND status='pending'";
    List<Map<String, Object>> pendingRequests = jdbcTemplate.queryForList(pendingRequestsQuery);

    model.addAttribute("pendingRequests", pendingRequests);
    System.out.print(clubName);
    return "club-requests";
}

private List<Map<String, Object>> getClubMembersByName(String clubName) {
    String query = "SELECT DISTINCT * FROM clubmember WHERE clubname = ?";
    return jdbcTemplate.queryForList(query, clubName);
}

public List<Map<String, Object>> getClubEvents(String clubName) {
    String query = "SELECT * FROM event WHERE clubname = ?";
    return jdbcTemplate.queryForList(query, clubName);
}

}
