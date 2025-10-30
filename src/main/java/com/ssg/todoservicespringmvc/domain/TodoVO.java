package com.ssg.todoservicespringmvc.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoVO {
    private long tno;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String writer;
    private boolean finished;
}
