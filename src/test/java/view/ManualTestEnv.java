package view;

import static org.junit.Assert.fail;

/**
 * Manual test environment for the GUI. Checks whether the cmd argument SKIP_GUI
 * is set. If so, skip the test. Otherwise run the GUI in a separate thread.
 */
abstract class ManualTestEnv {

  private Thread mContainerThread;
  private boolean mFailed;

  /**
   * Create an environment and run it.
   */
  ManualTestEnv() {
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
   * Interrupt the thread containing GUI.
   */
  void interruptContainerThread() {
    mContainerThread.interrupt();
  }

  /**
   * Indicate the test has failed (human tester clicked on "fail" button.
   */
  void setFailed() {
    mFailed = true;
  }

}
