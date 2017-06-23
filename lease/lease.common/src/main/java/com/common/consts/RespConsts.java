package com.common.consts;

/**
 * 接口响应状态.
 */
public final class RespConsts {

	private RespConsts() {

	}

	/**
	 * 返回对象键值
	 */
	public enum RespKey {

		STATUS("status"),STATE("state"), NUM("num"), CODE("code"), MSG("msg"), DATA("data");

		private String value;

		RespKey(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	/**
	 * 接口返回State值：0-系统内部错误,1-成功,2-失败
	 */
	public enum RespState {

		ERROR(0), SUCCESS(1), FAIL(2);

		private int value;

		RespState(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}
}
