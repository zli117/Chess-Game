package view;

import static org.junit.Assert.fail;

/**
 * Manual test environment for the GUI. Checks whether the cmd argument SKIP_GUI
 * is set. If so, skip the test. Otherwise run the GUI in a separate thread.
 */
public abstract class ManualTestEnv {

  private Thread mContainerThread;
  private boolean mFailed;

  /**
   * Create an environment and run it.
   */
  public ManualTestEnv() {
    String skipGui = System.getenv("SKIP_GUI");
    mFailed = false;
    if (skipGui == null) {
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

  /**
   * Implement this to actually run the GUI code.
   */
  abstract void runTest(ManualTestEnv env);

  /**
   * Get the thread that's the parent of the GUI thread.
   */
  Thread getContainerThread() {
    return mContainerThread;
  }

  /**
   * Indicate the test has failed (human tester clicked on "fail" button.
   */
  void setFailed() {
    mFailed = true;
  }

}
