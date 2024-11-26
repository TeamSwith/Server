package swith.swithServer.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.studyGroup.dto.StringRequest;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.repository.TaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final StudyRepository studyRepository;

    //study로 과제 찾기
    public List<Task> getTaskByStudy(Study study){
        List<Task> task=taskRepository.findByStudy(study);
        return task;
    }

    //과제 생성
    @Transactional
    public Task createTask(Long id, StringRequest stringRequest){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        Task task = new Task(stringRequest.getMessage(), study);
        return taskRepository.save(task);

    }

    //과제 삭제
    @Transactional
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));
        taskRepository.delete(task);
    }
}
