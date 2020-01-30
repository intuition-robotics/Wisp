package com.coreoz.wisp;

import com.coreoz.wisp.time.SystemTimeProvider;
import com.coreoz.wisp.time.TimeProvider;

import org.joda.time.Duration;

/**
 * The configuration used by the scheduler
 */
public class SchedulerConfig {
	public static final TimeProvider DEFAULT_TIME_PROVIDER = new SystemTimeProvider();
	private static final Duration NON_EXPIRABLE_THREADS = Duration.millis(Long.MAX_VALUE);
	private static final String DEFAULT_THREAD_NAME_PREFIX = "Wisp Scheduler Worker ";

	private int minThreads;
	private int maxThreads;
	private Duration threadsKeepAliveTime;
	private TimeProvider timeProvider;
	private String threadNamePrefix;

	private SchedulerConfig() {

	}

	public int getMinThreads() {
		return minThreads;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public Duration getThreadsKeepAliveTime() {
		return threadsKeepAliveTime;
	}

	public TimeProvider getTimeProvider() {
		return timeProvider;
	}

	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}

	public static class Builder {
		private int minThreads = 0;
		private int maxThreads = 10;
		private Duration threadsKeepAliveTime = NON_EXPIRABLE_THREADS;
		private TimeProvider timeProvider = DEFAULT_TIME_PROVIDER;
		private String threadNamePrefix = DEFAULT_THREAD_NAME_PREFIX;

		public Builder() {
		}

		/**
		 * The minimum number of threads that will live in the jobs threads pool.
		 */
		public Builder minThreads(int minThreads) {
			this.minThreads = minThreads;

			return this;
		}

		/**
		 * The maximum number of threads that will live in the jobs threads pool.
		 */
		public Builder maxThreads(int maxThreads) {
			this.maxThreads = maxThreads;

			return this;
		}

		/**
		 * The time after which idle threads will be removed from the threads pool.
		 * By default the thread pool does not scale down (duration = infinity ~ {@link Long#MAX_VALUE}ms)
		 */
		public Builder threadsKeepAliveTime(Duration threadsKeepAliveTime) {
			this.threadsKeepAliveTime = threadsKeepAliveTime;

			return this;
		}

		/**
		 * The time provider that will be used by the scheduler
		 */
		public Builder timeProvider(TimeProvider timeProvider) {
			this.timeProvider = timeProvider;

			return this;
		}

		public Builder threadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;

			return this;
		}

		public SchedulerConfig build() {
			SchedulerConfig config = new SchedulerConfig();
			config.minThreads = minThreads;
			config.maxThreads = maxThreads;
			config.threadsKeepAliveTime = threadsKeepAliveTime;
			config.timeProvider = timeProvider;
			config.threadNamePrefix = threadNamePrefix;

			return config;
		}
	}
}
