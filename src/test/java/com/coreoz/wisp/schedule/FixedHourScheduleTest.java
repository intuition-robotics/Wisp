package com.coreoz.wisp.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import org.junit.Test;

public class FixedHourScheduleTest {

	@Test
	public void should_parse_time_without_seconds() {
		LocalTime executionTime = new FixedHourSchedule("12:31").executionTime();

		assertThat(executionTime.getHourOfDay()).isEqualTo(12);
		assertThat(executionTime.getMinuteOfHour()).isEqualTo(31);
		assertThat(executionTime.getSecondOfMinute()).isEqualTo(0);
	}

	@Test
	public void should_parse_time_with_seconds() {
		LocalTime executionTime = new FixedHourSchedule("03:31:09").executionTime();

		assertThat(executionTime.getHourOfDay()).isEqualTo(3);
		assertThat(executionTime.getMinuteOfHour()).isEqualTo(31);
		assertThat(executionTime.getSecondOfMinute()).isEqualTo(9);
	}

	@Test
	public void should_calcule_next_execution_from_epoch() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime augustMidnight = new LocalDate(2016, 8, 31)
			.toLocalDateTime(LocalTime.MIDNIGHT)
			.toDateTime(ectZone);
		long midnight = augustMidnight.getMillis();

		assertThat(
			new FixedHourSchedule("00:00:00", ectZone).nextExecutionInMillis(midnight, 0, null)
		).isEqualTo(midnight);
	}

	@Test
	public void should_calcule_next_execution_from_midnight() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime augustMidnight = new LocalDate(2016, 8, 31)
			.toLocalDateTime(LocalTime.MIDNIGHT)
			.toDateTime(ectZone);
		long midnight = augustMidnight.getMillis();

		assertThat(
			new FixedHourSchedule("00:00:00", ectZone).durationUntilNextExecutionInMillis(midnight, null)
		).isEqualTo(0);
		assertThat(
			new FixedHourSchedule("00:00:01", ectZone).durationUntilNextExecutionInMillis(midnight, null)
		).isEqualTo(1000);
	}

	@Test
	public void should_calcule_next_execution_from_midday() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime augustMidday = new LocalDate(2016, 8, 31)
			.toLocalDateTime(new LocalTime(12, 0))
			.toDateTime(ectZone);
		long midday = augustMidday.getMillis();

		assertThat(
			new FixedHourSchedule("12:00:00", ectZone).durationUntilNextExecutionInMillis(midday, null)
		).isEqualTo(0);
		assertThat(
			new FixedHourSchedule("12:00:01", ectZone).durationUntilNextExecutionInMillis(midday, null)
		).isEqualTo(1000);
		assertThat(
			new FixedHourSchedule("11:59:59", ectZone).durationUntilNextExecutionInMillis(midday, null)
		).isEqualTo(24 * 60 * 60 * 1000 - 1000);
		assertThat(
			new FixedHourSchedule("00:00:00", ectZone).durationUntilNextExecutionInMillis(midday, null)
		).isEqualTo(12 * 60 * 60 * 1000);
	}

	@Test
	public void should_calcule_next_execution_with_dst() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime augustMidnight = new LocalDate(2016, 8, 31)
			.toLocalDateTime(LocalTime.MIDNIGHT)
			.toDateTime(ectZone);
		long midnight = augustMidnight.getMillis();

		assertThat(new FixedHourSchedule("02:00:00", ectZone).durationUntilNextExecutionInMillis(midnight, null)).isEqualTo(2 * 60 * 60 * 1000);
		assertThat(new FixedHourSchedule("03:00:00", ectZone).durationUntilNextExecutionInMillis(midnight, null)).isEqualTo(3 * 60 * 60 * 1000);
	}

	@Test
	public void should_calcule_next_execution_during_time_change() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime oneSecBeforeTimeChange = new LocalDate(2016, 8, 31)
			.toLocalDateTime(new LocalTime(1, 59, 59))
			.toDateTime(ectZone);
		long oneSecBeforeTimeChangeMillis = oneSecBeforeTimeChange.getMillis();
		long oneSecAfterTimeChangeMillis = oneSecBeforeTimeChange.plusSeconds(2).getMillis();

		assertThat(
			new FixedHourSchedule("02:00:00", ectZone)
				.durationUntilNextExecutionInMillis(oneSecBeforeTimeChangeMillis, null)
		).isEqualTo(1000);
		assertThat(
			new FixedHourSchedule("02:00:00", ectZone)
				.durationUntilNextExecutionInMillis(oneSecAfterTimeChangeMillis, null)
		).isEqualTo(24 * 60 * 60 * 1000 - 1000);
	}

	@Test
	public void should_not_return_current_time_if_last_execution_equals_current_time() {
		DateTimeZone ectZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		DateTime augustMidnight = new LocalDate(2016, 8, 31)
			.toLocalDateTime(LocalTime.MIDNIGHT)
			.toDateTime(ectZone);
		long midnight = augustMidnight.getMillis();

		assertThat(
			new FixedHourSchedule("00:00:00", ectZone).durationUntilNextExecutionInMillis(midnight, midnight)
		).isEqualTo(24 * 60 * 60 * 1000);
	}

}
