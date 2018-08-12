package com.example.engine;

import com.example.util.Message;

public interface CurrentLotteryInfo {

	/**
	 * 获取指定的彩种的信息
	 * @param lotteryId
	 * @return
	 */
	public Message getLottryInfoAtId(Integer lotteryId);
}
