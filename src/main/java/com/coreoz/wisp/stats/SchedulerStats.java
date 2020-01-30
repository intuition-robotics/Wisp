package com.coreoz.wisp.stats;

public class SchedulerStats {
	private final ThreadPoolStats threadPoolStats;

	public SchedulerStats(ThreadPoolStats threadPoolStats) {
		this.threadPoolStats = threadPoolStats;
	}

	public ThreadPoolStats getThreadPoolStats() {
		return threadPoolStats;
	}
}
