package kr.co.studyhubinu.studyhubserver.apply.controller;

import kr.co.studyhubinu.studyhubserver.apply.dto.request.EnrollApplyRequest;
import kr.co.studyhubinu.studyhubserver.apply.dto.request.UpdateApplyRequest;
import kr.co.studyhubinu.studyhubserver.apply.enums.Inspection;
import kr.co.studyhubinu.studyhubserver.apply.service.ApplyService;
import kr.co.studyhubinu.studyhubserver.config.resolver.UserIdArgumentResolver;
import kr.co.studyhubinu.studyhubserver.support.controller.ControllerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(value = ApplyController.class)
class ApplyControllerTest extends ControllerRequest {

    @MockBean
    ApplyService applyService;

    @MockBean
    UserIdArgumentResolver userIdArgumentResolver;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 스터디_가입_요청_성공() throws Exception {
        // given
        doNothing().when(applyService).enroll(anyLong(), any());
        EnrollApplyRequest request = EnrollApplyRequest.builder()
                .studyId(1L)
                .build();

        // when
        ResultActions resultActions = performPostRequest("/api/v1/study/enroll", request);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 스터디_요청상태_수정() throws Exception {
        // given
        doNothing().when(applyService).update(any());
        UpdateApplyRequest request = UpdateApplyRequest.builder()
                .studyId(1L)
                .userId(1L)
                .inspection(Inspection.ACCEPT)
                .build();

        // when
        ResultActions resultActions = performPostRequest("/api/v1/study/update", request);

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}