package swith.swithServer.domain.Group.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.Group.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

//테스트 용
    @PostMapping()
    public ResponseEntity<Group> testGroup(@RequestParam String groupId,
                                           @RequestParam String groupPw,
                                           @RequestParam int maxNum,
                                           @RequestParam int memberNum,
                                           @RequestParam String subject,
                                           @RequestParam String period,
                                           @RequestParam String communication
                                           ){
        Group createdGroup = groupService.createGroup(groupId,groupPw,maxNum,memberNum,subject,period,communication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);

    }



    //스터디 가입 여부 확인
    @PostMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestParam String groupId,
                                            @RequestParam String groupPw,
                                            @RequestParam Long userId){

        //id와 pw가 매칭되는 group 여부 확인
        Optional<Group> groupExist = groupService.getGroupByIdPw(groupId, groupPw);

        if(groupExist.isPresent()) {
            //아이디와 비밀번호가 일치하는 그룹이 있음

            //user 조회
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id"));

            Group group = groupExist.get();

            //가입 여부 확인
            boolean isUserInGroup = groupService.isUserInGroup(user, group);

            if (isUserInGroup) {
                //가입되어 있을 때
                Map<String, Object> response = new HashMap<>();
                response.put("message", "이미 가입되어 있음");
                response.put("id", group.getId());

                //리다이렉트?? vs 그냥 id만??
                return ResponseEntity.ok().body(response);
            }
            else {
                //가입되어 있지 않을 때 group 의 정원 확인
                if(group.getMemberNum()<group.getMaxNum()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "가입 전");
                    response.put("id", group.getId());
                    return ResponseEntity.ok().body(response);
                }
                else{
                    return ResponseEntity.badRequest().body("정원 초과");
                }
            }
        }
        else{
            //아이디와 비밀번호가 일치하는 그룹이 없음
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    //notice 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<String> getNotice(
            @PathVariable Long id){
        Optional<Group> groupOptional = groupService.getGroupById(id);

        if (groupOptional.isPresent()) {
            String notice = groupOptional.get().getNotice();
            return ResponseEntity.ok().body(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //notice 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Group> updateNotice(
            @PathVariable Long id,
            @RequestParam String notice){
        Group updatedNotice = groupService.updateNotice(id, notice);
        return ResponseEntity.ok().body(updatedNotice);
    }


}
