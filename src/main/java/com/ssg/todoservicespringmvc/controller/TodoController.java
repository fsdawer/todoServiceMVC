package com.ssg.todoservicespringmvc.controller;

import com.ssg.todoservicespringmvc.dto.TodoDTO;
import com.ssg.todoservicespringmvc.service.TodoService;
import com.ssg.todoservicespringmvc.service.TodoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class TodoController {


    private final TodoService todoService;



    // todo/list
    @RequestMapping("/list")
    public void list(Model model) {
        log.info("list.........");

        // dtoList = serviceImpl에 있는 dtoList
        model.addAttribute("dtoList", todoService.getAll());
    }


    // todo/register
    @GetMapping("/register")
    public void registerGET() {
        log.info("register.......");
    }


    // insert
    // todo/register
    @PostMapping("/register")
    public String registerPost(@Valid TodoDTO todoDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        log.info("post TODO register.......");

        // 유효성 검사 에러가 있을 경우 다시 등록 페이지로 리다이렉트
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }

        log.info("등록할 todoDTO: " + todoDTO);

        // 실제 서비스 계층 호출 → DB insert 실행
        todoService.register(todoDTO);

        // 등록 완료 후 알림 메시지 전달 (선택사항)
        redirectAttributes.addFlashAttribute("msg", "등록 완료!");

        // 등록 후 목록 페이지로 리다이렉트
        return "redirect:/todo/list";
    }


    // 수정인데 get?
    @GetMapping({"/read", "/modify"})
    public void read(Long tno, Model model){
        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);
        model.addAttribute("dto", todoDTO );
    }


    // 삭제하면 다시 list로 가야되니까 redirect
    @PostMapping("/remove")
    public String remove(Long tno, RedirectAttributes redirectAttributes) {
        log.info("==============remove.......================");
        log.info("tno" + tno);

        todoService.remove(tno);

        return "redirect:/todo/list";
    }


    @PostMapping("/modify")
    public String modify(@Valid TodoDTO todoDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            log.info("@@@@@@@@@@@has eroors@@@@@@@@@");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno", todoDTO.getTno());
            return "redirect:/todo/modify";
        }

        log.info(todoDTO);

        todoService.modify(todoDTO);

        return "redirect:/todo/list";
    }



}
