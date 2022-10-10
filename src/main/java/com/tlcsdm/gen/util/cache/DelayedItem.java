package com.tlcsdm.gen.util.cache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列对象
 *
 * @author: TangLiang
 * @date: 2021/6/30 9:12
 * @since: 1.0
 */
public class DelayedItem<T> implements Delayed {

	private T t;

	private long liveTime;

	private long removeTime;

	public DelayedItem(T t, long liveTime) {
		this.setT(t);
		this.liveTime = liveTime;
		this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime, TimeUnit.NANOSECONDS) + System.nanoTime();
	}

	@Override
	public int compareTo(Delayed o) {
		if (o == null)
			return 1;
		if (o == this)
			return 0;
		if (o instanceof DelayedItem) {
			DelayedItem<T> tmpDelayedItem = (DelayedItem<T>) o;
			return Long.compare(liveTime, tmpDelayedItem.liveTime);
		}
		long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return diff > 0 ? 1 : diff == 0 ? 0 : -1;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(removeTime - System.nanoTime(), unit);
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Override
	public int hashCode() {
		return t.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof DelayedItem) {
			return object.hashCode() == hashCode();
		}
		return false;
	}

}
