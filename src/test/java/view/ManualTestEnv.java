package view;

import static org.junit.Assert.fail;

public abstract class ManualTestEnv {

  private Thread mContainerThread;
  private boolean mFailed;

  public ManualTestEnv() {
    String skipGUI = System.getenv("SKIP_GUI");
    mFailed = false;
    if (skipGUI == null) {
      ManualTestEnv env = this;
      mContainerThread = new Thread(new Runnable() {
        @Override
        public void run() {
          runTest(env);
          try {
            // Wait for the GUI to run.
            Thread.sleep(Long.MAX_VALUE);
          } catch (InterruptedException interrupt) {
            // Do nothing. Just exit.
          }
        }
      });
      mContainerThread.start();
      try {
        mContainerThread.join();
      } catch (InterruptedException interrupt) {
        // Do nothing.
      }
      if (mFailed) {
        fail();
      }
    }
  }

  abstract void runTest(ManualTestEnv env);

  Thread getContainerThread() {
    return mContainerThread;
  }

  void setFailed() {
    mFailed = true;
  }

}
