/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.39
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package edu.ualberta.med.scanlib;

public class ScanLibWin32Wrapper {
  public static int slIsTwainAvailable() {
    return ScanLibWin32WrapperJNI.slIsTwainAvailable();
  }

  public static int slSelectSourceAsDefault() {
    return ScanLibWin32WrapperJNI.slSelectSourceAsDefault();
  }

  public static int slConfigScannerBrightness(int brightness) {
    return ScanLibWin32WrapperJNI.slConfigScannerBrightness(brightness);
  }

  public static int slConfigScannerContrast(int contrast) {
    return ScanLibWin32WrapperJNI.slConfigScannerContrast(contrast);
  }

  public static int slConfigPlateFrame(long plateNum, double left, double top, double right, double bottom) {
    return ScanLibWin32WrapperJNI.slConfigPlateFrame(plateNum, left, top, right, bottom);
  }

  public static int slScanImage(long dpi, double left, double top, double right, double bottom, String filename) {
    return ScanLibWin32WrapperJNI.slScanImage(dpi, left, top, right, bottom, filename);
  }

  public static int slScanPlate(long dpi, long plateNum, String filename) {
    return ScanLibWin32WrapperJNI.slScanPlate(dpi, plateNum, filename);
  }

  public static int slCalibrateToPlate(long dpi, long plateNum) {
    return ScanLibWin32WrapperJNI.slCalibrateToPlate(dpi, plateNum);
  }

  public static int slDecodePlate(long dpi, long plateNum) {
    return ScanLibWin32WrapperJNI.slDecodePlate(dpi, plateNum);
  }

  public static int slDecodeImage(long plateNum, String filename) {
    return ScanLibWin32WrapperJNI.slDecodeImage(plateNum, filename);
  }

}
