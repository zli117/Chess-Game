package view;

import java.net.URL;

public interface WindowCallBack {

  void onUndo();

  void onRestart(boolean isTie);

  void onOpenConfig(URL fileURL);

  void onForfeit();

}
