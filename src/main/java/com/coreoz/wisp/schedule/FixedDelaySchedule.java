package com.coreoz.wisp.schedule;

import org.joda.time.Duration;

public class FixedDelaySchedule implements Schedule {

	private final Duration frequency;

	public FixedDelaySchedule(Duration frequency) {
		this.frequency = frequency;
	}

	@Override
	public long nextExecutionInMillis(long currentTimeInMillis, int executionsCount, Long lastExecutionTimeInMillis) {
		return currentTimeInMillis + frequency.getMillis();
	}

	@Override
	public String toString() {
		return "every " + frequency.getMillis() + "ms";
	}

}
