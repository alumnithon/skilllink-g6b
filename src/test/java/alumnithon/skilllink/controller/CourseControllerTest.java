package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseDetailDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseUpdateDTO;
import alumnithon.skilllink.domain.learning.course.service.CourseService;
import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<CourseCreateDTO> courseCreateDTOJacksonTester;

    @Autowired
    private JacksonTester<CourseUpdateDTO> courseUpdateDTOJacksonTester;

    @Autowired
    private JacksonTester<CourseDetailDTO> courseDetailDTOJacksonTester;

    @Autowired
    private JacksonTester<CoursePreviewDTO> coursePreviewDTOJacksonTester;

    private CourseDetailDTO courseDetailDTO;
    private CoursePreviewDTO coursePreviewDTO1;
    private CoursePreviewDTO coursePreviewDTO2;

    @BeforeEach
    void setUp() {
        courseDetailDTO = new CourseDetailDTO( 1L,"Curso 1","Description curso 1", Boolean.TRUE,"mentor", LocalDateTime.now());
        coursePreviewDTO1 = new CoursePreviewDTO(1L,"Curso 1", "Description curso 1", Boolean.TRUE);
        coursePreviewDTO2 = new CoursePreviewDTO(2L,"Curso 2", "Description curso 2", Boolean.TRUE);
    }


    @Test
    @DisplayName("GET /courses/{id} debería devolver CourseDetailDTO con 200 OK")
    @WithMockUser
    void shouldReturnCourseById() throws Exception {
        // Given
        Long id = 1L;
        when(courseServiceMock.getEnabledCourseById(id)).thenReturn(courseDetailDTO);

        // When
        var response = mockMvc.perform(get("/api/courses/{id}", id))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = courseDetailDTOJacksonTester.write(courseDetailDTO).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("GET /courses debería devolver página de PreviewCourseDTO con 200 OK")
    @WithMockUser
    void shouldReturnAllCourse() throws Exception {
        List<CoursePreviewDTO> cursos = List.of(coursePreviewDTO1, coursePreviewDTO2);

        when(courseServiceMock.getAllEnabledCourses()).thenReturn(cursos);

        // When
        var response = mockMvc.perform(get("/api/courses"))
                .andReturn().getResponse();

        // Then
        String jsonEsperado = objectMapper.writeValueAsString(cursos);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("GET /courses/mentor/{id} debería devolver CourseDetailDTO con 200 OK para rol MENTOR")
    void shouldReturnCourseByIdForMentor() throws Exception {
        Long id = 1L;

        Mockito.when(courseServiceMock.getEnabledCourseByIdForMentor(id)).thenReturn(courseDetailDTO);

        mockMvc.perform(get("/api/courses/mentor/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = {"MENTOR"})
    @DisplayName("POST /courses/mentor sin body debería devolver 400 BAD REQUEST")
    void shouldReturn400WhenRequestBodyIsMissing() throws Exception {
        mockMvc.perform(post("/api/courses/mentor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("POST /courses/mentor debería crear el curso y devolver 201 CREATED")
    void shouldCreateCourse() throws Exception {
        // Given
        CourseCreateDTO request = new CourseCreateDTO(
                "Curso 1",
                "Description curso 1",
                true
        );

        when(courseServiceMock.createCourseByMentor(any(CourseCreateDTO.class)))
                .thenReturn(coursePreviewDTO1);

        // When
        var response = mockMvc.perform(post("/api/courses/mentor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseCreateDTOJacksonTester.write(request).getJson()))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = coursePreviewDTOJacksonTester.write(coursePreviewDTO1).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("PUT /courses/mentor/{id} debería actualizar y devolver 200 OK")
    void shouldUpdateCourse() throws Exception {
        // Given
        Long id = 1L;
        CourseUpdateDTO request = new CourseUpdateDTO(
                "Curso 2",
                "Description curso 1",
                true
        );

        when(courseServiceMock.updateCourseByMentor(id, request)).thenReturn(courseDetailDTO);

        // When
        var response = mockMvc.perform(put("/api/courses/mentor/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseUpdateDTOJacksonTester.write(request).getJson()))
                .andReturn().getResponse();

        // Then
        var jsonEsperado = courseDetailDTOJacksonTester.write(courseDetailDTO).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser(roles = "MENTOR")
    @DisplayName("DELETE /courses/mentor/{id} debería eliminar y devolver 204 NO CONTENT")
    void shouldDeleteCourse() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/courses/mentor/{id}", id))
                .andExpect(status().isNoContent());
    }
}
