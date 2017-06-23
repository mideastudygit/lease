package com.ercar.consts;

/**
 * 数据字典.
 */
public final class DataDict {

	private DataDict() {

	}

	/**
	 * 状态：0-禁用，1-启用
	 */
	public enum Status {

		DISABLED(0), ENABLED(1);

		private Integer value;

		Status(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 盒子状态：1-正常，0-故障
	 */
	public enum BoxStatus {

		NOMAL(1), FAIL(0);

		private Integer value;

		BoxStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 联网状态：1-在线，0-离线
	 */
	public enum NetworkStatus {

		ONLINE(1), OFFLINE(0);

		private Integer value;

		NetworkStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 车辆状态:1-使用中，2-故障，3-空闲，4-其他
	 */
	public enum CarStatus {

		USE(1), FAULT(2),FREE(3),OTHER(4);

		private Integer value;

		CarStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return this.value;
		}
	}

}
