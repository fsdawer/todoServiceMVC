package com.ssg.todoservicespringmvc.service;

import com.ssg.todoservicespringmvc.domain.TodoVO;
import com.ssg.todoservicespringmvc.dto.TodoDTO;
import com.ssg.todoservicespringmvc.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper; // DTO -> VO , VO -> DTO 변환 장치

    @Override
    public void register(TodoDTO todoDTO) {
        log.info(modelMapper);
        // Insert니까 DTO -> VO 변환 해서 VO에 저장
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);

        log.info(todoVO);
        todoMapper.insert(todoVO);

    }


    @Override
    public List<TodoDTO> getAll() {
        log.info(modelMapper);
        // VO -> DTO로 변환하는데 select니까 VO 타입으로 먼저 가져오고 변환
        List<TodoDTO> dtoList = todoMapper.selectAll().stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());

        return dtoList;
    }


    // modelMapper.map(1, 2) -> 1번이 2번으로 변환
    @Override
    public TodoDTO getOne(Long tno) {
        TodoVO todoVO = todoMapper.selectOne(tno);
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }


    @Override
    public void remove(Long tno) {
        todoMapper.delete(tno);
    }


    @Override
    public void modify(TodoDTO todoDTO) {
        TodoVO todoVO = modelMapper.map(todoDTO , TodoVO.class);
        todoMapper.update(todoVO);
    }
}
