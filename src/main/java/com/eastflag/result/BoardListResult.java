package com.eastflag.result;

public class BoardListResult<T> extends Result<T> {

	private int total;
	
	public BoardListResult(T value) {
		super(value);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
