package com.campuscrew;

import com.campuscrew.dto.LoginRequestDTO;
import com.campuscrew.dto.SignUpRequestDTO;
import com.campuscrew.repository.AuthRepository;
import com.campuscrew.repository.MemberRepository;
import com.campuscrew.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final String SIGNUP_URL = "/api/auth/signup";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String TEAMS_URL = "/api/teams";

    @BeforeEach
    void setUp() {
        // FK 순서상 멤버 → 팀 → 사용자 순으로 정리한다.
        memberRepository.deleteAll();
        teamRepository.deleteAll();
        authRepository.deleteAll();
    }

    @Test
    @DisplayName("팀 생성 성공")
    void createTeam_success() throws Exception {
        String token = signUpAndLogin("김민수", "20251234", "minsu@test.com", "test1234");

        mockMvc.perform(post(TEAMS_URL)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamBody("캡스톤 1팀", "웹프로그래밍", "팀플 관리용 공간"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value("캡스톤 1팀"))
                .andExpect(jsonPath("$.data.courseName").value("웹프로그래밍"))
                .andExpect(jsonPath("$.data.description").value("팀플 관리용 공간"))
                .andExpect(jsonPath("$.data.joinCode").isNotEmpty())
                .andExpect(jsonPath("$.message").value("팀이 생성되었습니다."));
    }

    @Test
    @DisplayName("팀 생성 실패 - 팀명 길이 미달")
    void createTeam_invalidName() throws Exception {
        String token = signUpAndLogin("김민수", "20251234", "minsu@test.com", "test1234");

        mockMvc.perform(post(TEAMS_URL)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamBody("A", "웹프로그래밍", "팀플 관리용 공간"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("팀명은 2자 이상 30자 이하로 입력해야 합니다."));
    }

    @Test
    @DisplayName("팀 생성 실패 - 인증 토큰 없음")
    void createTeam_noToken() throws Exception {
        mockMvc.perform(post(TEAMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamBody("캡스톤 1팀", "웹프로그래밍", "팀플 관리용 공간"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("내 팀 목록 조회 성공 - 생성한 팀이 OWNER로 보인다")
    void getMyTeams_success() throws Exception {
        String token = signUpAndLogin("김민수", "20251234", "minsu@test.com", "test1234");

        mockMvc.perform(post(TEAMS_URL)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamBody("캡스톤 1팀", "웹프로그래밍", "팀플 관리용 공간"))))
                .andExpect(status().isCreated());

        mockMvc.perform(get(TEAMS_URL)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("캡스톤 1팀"))
                .andExpect(jsonPath("$.data[0].courseName").value("웹프로그래밍"))
                .andExpect(jsonPath("$.data[0].memberCount").value(1))
                // 목록 항목에는 joinCode를 노출하지 않는다 (상세 조회에만 있음)
                .andExpect(jsonPath("$.data[0].joinCode").doesNotExist())
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @Test
    @DisplayName("내 팀 목록 조회 - 소속 팀이 없으면 빈 배열")
    void getMyTeams_empty() throws Exception {
        String token = signUpAndLogin("이서연", "20259999", "seoyeon@test.com", "test1234");

        mockMvc.perform(get(TEAMS_URL)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    /** 회원가입 후 로그인해 accessToken을 반환한다. */
    private String signUpAndLogin(String name, String studentId, String email, String password) throws Exception {
        mockMvc.perform(post(SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SignUpRequestDTO(name, studentId, email, password)))).andReturn();

        String response = mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequestDTO(email, password))))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readTree(response).path("data").path("accessToken").asText();
    }

    private Map<String, String> teamBody(String name, String courseName, String description) {
        return Map.of("name", name, "courseName", courseName, "description", description);
    }
}