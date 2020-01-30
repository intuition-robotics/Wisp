package com.coreoz.wisp.stats;

public class ThreadPoolStats {
	private final int minThreads;
	private final int maxThreads;
	private final int activeThreads;
	private final int idleThreads;
	private final int largestPoolSize;

	public ThreadPoolStats(int minThreads, int maxThreads, int activeThreads, int idleThreads,
		int largestPoolSize) {
		this.minThreads = minThreads;
		this.maxThreads = maxThreads;
		this.activeThreads = activeThreads;
		this.idleThreads = idleThreads;
		this.largestPoolSize = largestPoolSize;
	}

	public int getMinThreads() {
		return minThreads;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public int getActiveThreads() {
		return activeThreads;
	}

	public int getIdleThreads() {
		return idleThreads;
	}

	public int getLargestPoolSize() {
		return largestPoolSize;
	}
}
