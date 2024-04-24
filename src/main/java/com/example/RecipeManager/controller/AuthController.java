package com.example.RecipeManager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.RecipeManager.Model.Member;
import com.example.RecipeManager.Model.Club;
import com.example.RecipeManager.Service.MemberService;
import com.example.RecipeManager.Service.ClubService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;
    private final ClubService clubService;
    private final JdbcTemplate jdbcTemplate;

    public AuthController(MemberService memberService, ClubService clubService, JdbcTemplate jdbcTemplate) {
        this.memberService = memberService;
        this.clubService = clubService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> requestBody) {
        String userType = (String) requestBody.get("userType");

        if (userType.equals("member")) {
            Member savedMember = memberService.createMember((String) requestBody.get("email"),
                                                            (String) requestBody.get("password"),
                                                            (String) requestBody.get("domain"),
                                                            (String) requestBody.get("srn"),
                                                            (String) requestBody.get("name"),
                                                            Integer.parseInt((String) requestBody.get("sem")),
                                                            (String) requestBody.get("dept"),
                                                            (String) requestBody.get("phoneno"),
                                                            (String) requestBody.get("gender"));

            String srn = (String) requestBody.get("srn");
            String clubName = (String) requestBody.get("clubName");
            String sql = "INSERT INTO ClubMember (SRN, clubname) VALUES (?, ?)";
            jdbcTemplate.update(sql, srn, clubName);

            return ResponseEntity.ok().body(savedMember);
        } else if (userType.equals("club")) {
            Club savedClub = clubService.createClub((String) requestBody.get("clubName"),
                                                    (String) requestBody.get("email"),
                                                    (String) requestBody.get("password"),
                                                    (String) requestBody.get("facultyId"),
                                                    (String) requestBody.get("clubType"),
                                                    (String) requestBody.get("headSrn"),
                                                    (String) requestBody.get("name"),
                                                    Integer.parseInt((String) requestBody.get("sem")),
                                                    (String) requestBody.get("dept"),
                                                    (String) requestBody.get("phoneno"),
                                                    (String) requestBody.get("gender"));

            String srn = (String) requestBody.get("headSrn");
            String clubName = (String) requestBody.get("clubName");
            String sql = "INSERT INTO ClubMember (SRN, clubname) VALUES (?, ?)";
            jdbcTemplate.update(sql, srn, clubName);

            return ResponseEntity.ok().body(savedClub);
        }

        return ResponseEntity.badRequest().body("Invalid user type");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String userType = loginData.get("userType");
        String emailOrClubName = loginData.get("emailOrClubName");
        String password = loginData.get("password");

        if (userType.equals("member")) {
            Member member = memberService.findByEmailAndPassword(emailOrClubName, password);
            if (member != null) {
                return ResponseEntity.ok().body(new LoginResponse("/member/" + member.getSrn(), member.getSrn()));
            }
        } else if (userType.equals("clubHead")) {
            Club club = clubService.findByClubNameAndPassword(emailOrClubName, password);
            if (club != null) {
                return ResponseEntity.ok().body(new LoginResponse("/club/" + club.getClubName(), club.getClubName()));
            }
        } else if (userType.equals("faculty")) {
            // Perform authentication for faculty
            String facultyEmail = emailOrClubName;
            String facultyPassword = password;
        
            String sql = "SELECT * FROM faculty WHERE email = ? AND password = ?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, facultyEmail, facultyPassword);
        
            if (!result.isEmpty()) {
                int facultyId = (int) result.get(0).get("id");
                return ResponseEntity.ok().body(new LoginResponse("/faculty/" + facultyId, String.valueOf(facultyId)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    public class LoginResponse {
        private String redirectUrl;
        private String userIdentifier;

        public LoginResponse(String redirectUrl, String userIdentifier) {
            this.redirectUrl = redirectUrl;
            this.userIdentifier = userIdentifier;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public String getUserIdentifier() {
            return userIdentifier;
        }
    }
}