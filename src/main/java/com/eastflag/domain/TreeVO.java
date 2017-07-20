package com.eastflag.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by eastflag on 2017-07-20.
 */
@Data
public class TreeVO {
    private Integer id;
    private String name;
    private List<TreeVO> children;
}
