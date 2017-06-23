package com.common.consts;

/**
 * 数据字典.
 */
public final class DataDict {

	private DataDict() {

	}

	/**
	 * 分页状态：0-分页,1-不分页
	 */
	public enum PageStatus {

		PAGE(0), NOTPAGE(1);

		private int value;

		PageStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}
}
