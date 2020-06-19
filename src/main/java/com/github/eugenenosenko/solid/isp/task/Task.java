package com.github.eugenenosenko.solid.isp.task;

interface BatteryPoweredDevice {
  double getBatteryAmount();
}

interface Phone extends BatteryPoweredDevice {

  void call(String number);

  void text(String number);

  void unlockPhone();

  void restart();

  void takeAPhoto();

  void recordAVideo();
}

public class Task {

  // refactor code below to not violate ISP

}

class iPhone implements Phone {

  @Override
  public void call(String number) {
    System.out.println("Calling " + number);
  }

  @Override
  public void text(String number) {
    System.out.println("Texting " + number);
  }

  @Override
  public void unlockPhone() {
    System.out.println("Unlocking phone");
  }

  @Override
  public void restart() {
    System.out.println("Restarting phone");
  }

  @Override
  public void takeAPhoto() {
    System.out.println("Taking a photo");
  }

  @Override
  public void recordAVideo() {
    System.out.println("Recording a video");
  }

  @Override
  public double getBatteryAmount() {
    return 100;
  }
}

class Nokia3310 implements Phone {

  @Override
  public void call(String number) {
    System.out.println("Calling " + number);
  }

  @Override
  public void text(String number) {
    System.out.println("Texting " + number);
  }

  @Override
  public void unlockPhone() {
    throw new UnsupportedOperationException("Unsupported feature");
  }

  @Override
  public void restart() {
    throw new UnsupportedOperationException("Unsupported feature, can only turn off phone");
  }

  @Override
  public void takeAPhoto() {
    throw new UnsupportedOperationException("Unsupported feature");
  }

  @Override
  public void recordAVideo() {
    throw new UnsupportedOperationException("Unsupported feature");
  }

  @Override
  public double getBatteryAmount() {
    return 100;
  }
}
