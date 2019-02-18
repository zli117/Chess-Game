package view;

import java.net.URL;

public interface WindowCallBack {

  void onUndo();

  void onRestart();

  void onOpenConfig(URL fileURL);

  void onForfeit();

}
