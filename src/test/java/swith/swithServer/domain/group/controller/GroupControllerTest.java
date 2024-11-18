//package swith.swithServer.domain.group.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import swith.swithServer.domain.group.dto.GroupCreateRequest;
//import swith.swithServer.domain.group.service.GroupService;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(GroupController.class)
//public class GroupControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private GroupService groupService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private GroupCreateRequest validRequest;
//
//    @BeforeEach
//    void setUp() {
//        validRequest = new GroupCreateRequest();
//        validRequest.setGroupId("testGroup"); // ID 설정
//        validRequest.setGroupPw("testPassword"); // PW 설정
//    }
//
//    @Test
//    void testCreateGroupSuccess() throws Exception {
//        // Mocking the service call to return true (group successfully created)
//        Mockito.when(groupService.createGroup(Mockito.any())).thenReturn(true);
//
//        mockMvc.perform(post("/api/groups/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("스터디 그룹 생성 완료"));
//    }
//
//    @Test
//    void testCreateGroupFailureDueToDuplicateId() throws Exception {
//        // Mocking the service call to return false (group ID already exists)
//        Mockito.when(groupService.createGroup(Mockito.any())).thenReturn(false);
//
//        mockMvc.perform(post("/api/groups/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("스터디 그룹 생성 실패: 아이디 중복"));
//    }
//}