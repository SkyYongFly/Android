package com.example.engine;

import com.example.util.Message;

public interface CurrentLotteryInfo {

	/**
	 * ��ȡָ���Ĳ��ֵ���Ϣ
	 * @param lotteryId
	 * @return
	 */
	public Message getLottryInfoAtId(Integer lotteryId);
}
