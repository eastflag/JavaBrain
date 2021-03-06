package com.eastflag.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TodoVO extends SearchVO {
    private Integer todo_id;
    private Boolean isFinished;
    private String todo;
    private String created;
    private String updated;
}
