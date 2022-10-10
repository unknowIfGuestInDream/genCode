package com.tlcsdm.gen.event;

/**
 * @author: TangLiang
 * @date: 2021/11/30 9:11
 * @since: 1.0
 */
public class Courier implements IListeners {

	private String name;

	public Courier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void receive() {
		System.out.println("\"" + this.getName() + "\"收到信息： 有快递需要发送");
	}

}
