package com.eastflag.domain;

import lombok.Data;

@Data
public class TodoVO {
    private Integer todo_id;
    private Boolean isFinished;
    private String todo;
    private String created;
    private String updated;
}
