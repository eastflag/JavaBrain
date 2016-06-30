package com.eastflag.domain;

import lombok.Data;

@Data
public class BoardVO {
	private int board_id;
	private String title;
	private String content;
	private byte[] image;
	private String create_date;
	private String mod_date;
	private String user_name;
	private String user_password;
}
