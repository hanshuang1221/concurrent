package cn.foreverking.test;

import cn.foreverking.model.User;

public class StopThreadUnsafe {
	public static User user = new User();

	public static class ChangeObjectThread extends Thread {
		public void run() {
			while (true) {
				synchronized (user) {
					int v = (int) (System.currentTimeMillis() / 1000);
					user.setId(v);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					user.setName(String.valueOf(v));
				}
				Thread.yield();
			}
		}
	}

	public static class ReadObjectThread extends Thread {
		public void run() {
			while (true) {
				synchronized (user) {
					if (user.getId() != Integer.parseInt(user.getName())) {
						System.out.println(user.toString());
					}
					Thread.yield();
				}
			}
		}
	}

	public static void main(String[] args) {
		new ReadObjectThread().start();
		while (true) {
			Thread t = new ChangeObjectThread();
			t.start();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t.stop();
		}
	}
}
