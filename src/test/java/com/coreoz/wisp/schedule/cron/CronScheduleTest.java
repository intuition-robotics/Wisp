package com.coreoz.wisp.schedule.cron;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import org.joda.time.LocalTime;
import org.junit.Test;

public class CronScheduleTest {

	@Test
	public void should_calcule_the_next_execution_time_based_on_a_unix_cron_expression() {
		CronSchedule everyMinuteScheduler = CronSchedule.parseUnixCron("* * * * *");

		assertThat(everyMinuteScheduler.nextExecutionInMillis(0, 0, null))
		.isEqualTo(Duration.standardMinutes(1).getMillis());
	}

	@Test
	public void should_calcule_the_next_execution_time_based_on_a_quartz_cron_expression() {
		CronSchedule everyMinuteScheduler = CronSchedule.parseQuartzCron("0 * * * * ? *");

		assertThat(everyMinuteScheduler.nextExecutionInMillis(0, 0, null))
		.isEqualTo(Duration.standardMinutes(1).getMillis());
	}

	@Test
	public void should_not_executed_daily_jobs_twice_a_day() {
		CronSchedule everyMinuteScheduler = CronSchedule.parseQuartzCron("0 0 12 * * ? *");

		DateTime augustMidday = new LocalDate(2016, 8, 31)
			.toLocalDateTime(new LocalTime(12,0))
			.toDateTime(DateTimeZone.getDefault());
		long midday = augustMidday.getMillis();

		assertThat(everyMinuteScheduler.nextExecutionInMillis(midday-1, 0, null))
		.isEqualTo(midday);
		assertThat(everyMinuteScheduler.nextExecutionInMillis(midday, 0, null))
		.isEqualTo(midday + Duration.standardDays(1).getMillis());
	}

}
