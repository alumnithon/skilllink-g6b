package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.challenge.service.ChallengeService;
import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ChallengerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChallengeService challengeServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<ChallengeCreateDto> challengeCreateDtoJacksonTester;

    @Autowired
    private JacksonTester<ChallengeUpdateDto> challengeUpdateDtoJacksonTester;

    @Autowired
    private JacksonTester<ChallengeDetailDto> challengeDetailDtoJacksonTester;

    @Autowired
    private JacksonTester<ChallengePreviewDto> challengePreviewDtoJacksonTester;

    private ChallengeDetailDto challengeDetailDto;
    private ChallengePreviewDto challengePreviewDto1;
    private ChallengePreviewDto challengePreviewDto2;
    private final List<String> tags = List.of("React", "Spring boot");
    @BeforeEach
    void setUp() {
        challengeDetailDto = new ChallengeDetailDto( 1L,"Desafio 1","Description Desafio 1", DifficultyLevel.BEGINNER, LocalDate.now(), LocalDate.now(), 1L, "User", tags);
        challengePreviewDto1 = new ChallengePreviewDto(1L,"Desafio 1", DifficultyLevel.BEGINNER, LocalDate.now(), tags);
        challengePreviewDto2 = new ChallengePreviewDto( 2L, "Desafio 2", DifficultyLevel.BEGINNER, LocalDate.now(), tags);
    }

    @Test
    @DisplayName("GET /challenges/{id} debería devolver ChallengeDetailDto con 200 OK")
    @WithMockUser
    void shouldReturnChallengeById() throws Exception {
        // Given
        Long id = 1L;
        when(challengeServiceMock.getChallengeById(id)).thenReturn(challengeDetailDto);

        // When
        var response = mockMvc.perform(get("/api/challenges/{id}", id))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = challengeDetailDtoJacksonTester.write(challengeDetailDto).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("GET /challenges debería devolver página de PreviewChallengeDto con 200 OK")
    @WithMockUser
    void shouldReturnAllChallenges() throws Exception {
        List<ChallengePreviewDto> list = List.of(challengePreviewDto1, challengePreviewDto2);
        Page<ChallengePreviewDto> page = new PageImpl<>(list, PageRequest.of(0, 10), list.size());

        Mockito.when(challengeServiceMock.getAllChallenges(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/challenges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("GET /challenges/mentor/{id} debería devolver ChallengeDetailDto con 200 OK para rol MENTOR")
    void shouldReturnChallengeByIdForMentor() throws Exception {
        Long id = 1L;

        Mockito.when(challengeServiceMock.getChallengeByIdForMentor(id)).thenReturn(challengeDetailDto);

        mockMvc.perform(get("/api/challenges/mentor/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = {"MENTOR"})
    @DisplayName("POST /challenges/mentor sin body debería devolver 400 BAD REQUEST")
    void shouldReturn400WhenRequestBodyIsMissing() throws Exception {
        mockMvc.perform(post("/api/challenges/mentor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("POST /challenges/mentor debería crear challenge y devolver 201 CREATED")
    void shouldCreateChallenge() throws Exception {
        // Given
        ChallengeCreateDto request = new ChallengeCreateDto(
                "Desafio 1",
                "Description Desafio 1",
                DifficultyLevel.BEGINNER,
                LocalDate.now(),
                tags
        );

        when(challengeServiceMock.createChallengeByMentor(any(ChallengeCreateDto.class)))
                .thenReturn(challengePreviewDto1);

        // When
        var response = mockMvc.perform(post("/api/challenges/mentor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(challengeCreateDtoJacksonTester.write(request).getJson()))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = challengePreviewDtoJacksonTester.write(challengePreviewDto1).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("PUT /challenges/mentor/{id} debería actualizar y devolver 200 OK")
    void shouldUpdateChallenge() throws Exception {
        // Given
        Long id = 1L;
        ChallengeUpdateDto request = new ChallengeUpdateDto(
                "Desafio 2",
                "Description",
                DifficultyLevel.BEGINNER,
                LocalDate.now()
        );

        when(challengeServiceMock.updateChallengeByMentor(id, request)).thenReturn(challengeDetailDto);

        // When
        var response = mockMvc.perform(put("/api/challenges/mentor/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(challengeUpdateDtoJacksonTester.write(request).getJson()))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = challengeDetailDtoJacksonTester.write(challengeDetailDto).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("DELETE /challenges/mentor/{id} debería eliminar y devolver 204 NO CONTENT")
    void shouldDeleteChallenge() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/challenges/mentor/{id}", id))
                .andExpect(status().isNoContent());
    }

}
