package swith.swithServer.domain.studyGroup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.ErrorResponse;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

//테스트 용
    @PostMapping()
    public ApiResponse<StudyGroup> testGroup(@RequestParam String groupId,
                                                @RequestParam String groupPw,
                                                @RequestParam int maxNum,
                                                @RequestParam int memberNum,
                                                @RequestParam String subject,
                                                @RequestParam String period,
                                                @RequestParam String communication
                                           ){
        StudyGroup createdStudyGroup = groupService.createGroup(groupId,groupPw,maxNum,memberNum,subject,period,communication);
        //return ResponseEntity.status(HttpStatus.CREATED).body(createdStudyGroup);
        return new ApiResponse<>(201, createdStudyGroup);

    }



    //스터디 가입 여부 확인
    @PostMapping("/join")
    public ApiResponse<?> joinGroup(@RequestParam String groupId,
                                    @RequestParam String groupPw,
                                    @RequestParam Long userId){

        //id와 pw가 매칭되는 group 여부 확인
        StudyGroup studyGroup = groupService.getGroupByIdPw(groupId, groupPw)
                .orElseThrow(()-> new BusinessException(ErrorCode.GROUP_LOGIN_ERROR));


        //user 조회
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        //가입 여부 확인
        boolean isUserInGroup = groupService.isUserInGroup(user, studyGroup);

        if (isUserInGroup) {
                //가입되어 있을 때
                //Map<String, Object> response = new HashMap<>();
                //response.put("message", "이미 가입되어 있음");
                //response.put("id", studyGroup.getId());

                //리다이렉트?? vs 그냥 id만??
                //return ResponseEntity.ok().body(response);
            return new ApiResponse<>(200,studyGroup.getId());
        }
        else {
                //가입되어 있지 않을 때 group 의 정원 확인
            if(studyGroup.getMemberNum()< studyGroup.getMaxNum()) {
                    //Map<String, Object> response = new HashMap<>();
                    //response.put("message", "가입 전");
                    //response.put("id", studyGroup.getId());
                    //return ResponseEntity.ok().body(response);
                return new ApiResponse<>(200,studyGroup.getId());
            }
            else{
                //return ResponseEntity.badRequest().body("정원 초과");
                return new ApiResponse<>(404,"정원을 초과했습니다.");
            }
        }

    }

    //notice 가져오기
    @GetMapping("/{id}")
    public ApiResponse<String> getNotice(
            @PathVariable Long id){
        StudyGroup studyGroup = groupService.getGroupById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));

        String notice = studyGroup.getNotice();
        //return ResponseEntity.ok().body(notice);
        return new ApiResponse<>(200,notice);

    }


    //notice 수정
    @PatchMapping("/{id}")
    public ApiResponse<String> updateNotice(
            @PathVariable Long id,
            @RequestParam String notice){
        StudyGroup updatedNotice = groupService.updateNotice(id, notice);
        //return ResponseEntity.ok().body(updatedNotice);
        return new ApiResponse<>(200, updatedNotice.getNotice());
    }


}
