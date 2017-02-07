package com.mobile.younthcanteen.util;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
	private static Object lock = new Object();
	private static ThreadPool threadPool;
	public static ThreadPool getThreadPool(){
		synchronized (lock) {
			if(threadPool == null){
				threadPool = new ThreadPool( 
						10, 10, 0L);
			}
			return threadPool;
		}
	} 
	
	public static class ThreadPool{
		private ThreadPoolExecutor threadPoolExecutor;
		private int corePoolSize;
		private int maximumPoolSize;
		private long keepAliveTime;
		public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
			super();
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		//执行任务的方法
		public void execute(Runnable runnable){
			if(runnable == null){
				return;
			}
			
			if(threadPoolExecutor == null || threadPoolExecutor.isShutdown()){
				threadPoolExecutor= new ThreadPoolExecutor(
						corePoolSize, 
						maximumPoolSize, 
						keepAliveTime, 
						TimeUnit.MILLISECONDS, 
						new LinkedBlockingQueue<Runnable>(), 
						Executors.defaultThreadFactory(),
						new AbortPolicy());
			}
			threadPoolExecutor.execute(runnable);
		}
	}
}
