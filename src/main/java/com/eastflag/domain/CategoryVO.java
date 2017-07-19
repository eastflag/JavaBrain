package com.eastflag.domain;

import lombok.Data;

/**
 * Created by eastflag on 2017-07-19.
 */
@Data
public class CategoryVO {
    private int category_id;
    private String name;
    private int parent_id;
    private int level;
    private String created;
    private String updated;
}
