package com.tlcsdm.gen.event;

/**
 * @author: TangLiang
 * @date: 2021/11/30 9:11
 * @since: 1.0
 */
public class User implements IListeners {

	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void receive() {
		System.out.println("\"" + this.getName() + "\"收到信息：已发货");
	}

}
