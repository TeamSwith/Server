package swith.swithServer.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.repository.TaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final StudyRepository studyRepository;

    //id로 찾기
    public Task getTaskById(Long id){
        Task task=taskRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));
        return task;
    }

    //과제 생성
    @Transactional
    public Task createTask(Long id, String content){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        Task task = new Task(content, study);
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
