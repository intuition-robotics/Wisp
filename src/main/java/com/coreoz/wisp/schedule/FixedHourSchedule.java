package com.coreoz.wisp.schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

public class FixedHourSchedule implements Schedule {

	private final LocalTime executionTime;
	private final DateTimeZone zoneId;

	/**
	 * Parse time in the form of "hh:mm" or "hh:mm:ss"
	 */
	public FixedHourSchedule(String every) {
		this(
			LocalTime.parse(every)
		);
	}

	/**
	 * Parse time in the form of "hh:mm" or "hh:mm:ss"
	 */
	public FixedHourSchedule(String every, DateTimeZone zoneId) {
		this(LocalTime.parse(every), zoneId);
	}

	public FixedHourSchedule(LocalTime every) {
		this(every, DateTimeZone.getDefault());
	}

	public FixedHourSchedule(LocalTime every, DateTimeZone zoneId) {
		this.executionTime = every;
		this.zoneId = zoneId;
	}

	public LocalTime executionTime() {
		return executionTime;
	}

	public DateTimeZone zoneId() {
		return zoneId;
	}

	@Override
	public long nextExecutionInMillis(long currentTimeInMillis, int executionsCount, Long lastExecutionTimeInMillis) {
		return durationUntilNextExecutionInMillis(currentTimeInMillis, lastExecutionTimeInMillis)
				+ currentTimeInMillis;
	}

	long durationUntilNextExecutionInMillis(long currentTimeInMillis, Long lastExecutionTimeInMillis) {
		DateTime currentDateTime = org.joda.time.Instant
				.ofEpochMilli(currentTimeInMillis)
			.toDateTime(zoneId);

		return nextExecutionDateTime(
			currentDateTime,
			lastExecutionTimeInMillis != null && lastExecutionTimeInMillis == currentTimeInMillis
		).minus(
			currentDateTime.getMillis()
		).getMillis();
	}

	private DateTime nextExecutionDateTime(DateTime currentDateTime, boolean nextExecutionShouldBeNextDay) {
		if(!nextExecutionShouldBeNextDay && currentDateTime.toLocalTime().compareTo(executionTime) <= 0) {
			return currentDateTime.toLocalDate().toDateTime(executionTime, zoneId);
		}
		return currentDateTime.toLocalDate().toDateTime(executionTime, zoneId).plusDays(1);
	}

	@Override
	public String toString() {
		return "at " + executionTime + " " + zoneId;
	}

}
