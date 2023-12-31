package kr.co.studyhubinu.studyhubserver.apply.controller;


import kr.co.studyhubinu.studyhubserver.apply.dto.request.EnrollApplyRequest;
import kr.co.studyhubinu.studyhubserver.apply.service.ApplyService;
import kr.co.studyhubinu.studyhubserver.user.dto.data.UserId;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping("/v1/study/enroll")
    public ResponseEntity<HttpStatus> approveUser(UserId userId, EnrollApplyRequest request) {
        applyService.enroll(userId.getId(), request);
        return ResponseEntity.ok().build();
    }
//
//    @PostMapping("/v1/study/refuse")
//    public ResponseEntity<HttpStatus> refuseUser(RefuseUserStudyRequestDto refuseUserStudyRequestDto) {
//        applyService.refuse(refuseUserStudyRequestDto.getUserId(), refuseUserStudyRequestDto.getStudyId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
