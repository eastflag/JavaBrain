/*
 *
 * S G C P                  I N T E R F A C E                  S E R V E R
 *
 * LG CNS Co., Ltd.
 *
 * All rights reserved.     No part of this publication may be reproduced,
 * stored in a retrieval system or transmitted in any form or by any means
 * -   electronic,  mechanical,  photocopying,  recording,  or  otherwise,
 * without the prior written permission of LG CNS Co., Ltd.
 *
 * 파 일 명 : TcpResult.java
 * 기능설명 :
 * 수정이력 :
 * 수 정 일    성    명                       수정내용
 * ----------  ---------  ------------------------------------------------
 * 2014. 4. 16.     Administrator    Initial Release
 * 2014. 4. 16.     Administrator    M0001 - XXX를 수정
 *
 */
package com.eastflag.result;

/**
 * TcpResult
 */
public class Result<T> {

	private int result = 0;

	private T value;

	public Result(T value) {
		this.value = value;
	}

	public Result(int result, T value) {
		this.result = result;
		this.value = value;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            to set the value
	 */
	public void setValue(T value) {
		this.value = value;
	}

}
