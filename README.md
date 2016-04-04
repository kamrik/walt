## WALT Latency Timer ##

**DISCLAIMER:** This is not an official Google product.

WALT is designed to measure the latency of physical sensors and outputs on phones and computers. It can currently perform the following measurements:

 * Tap latency - time from the moment a finger-like probe touches down (or up) on the screen
   until the kernel timestamps an ACTION_DOWN (or ACTION_UP) event. This physical contact with
   the screen is timed using an accelerometer mounted on the probe
 * Drag latency (scroll)
 * Screen draw latency - using a photodiode that detects whether the screen is black or white.
 * Audio input / output latencies. Input latency will be usually referred as microphone latency.

Hardware build instructions can be found in this repository under hardware/

TODO: Longer description + principle of operation (link to time sync doc).

## Notes

* The Android device and Teensy clocks have a tendency to diverge due to
  differing clock frequencies. This means they will go out of sync after
  approximately 5 minutes. The workaround is to use the app to re-sync the
  clocks.
* Python code used to communicate with WALT from ChromeOS can be found
  [here](https://chromium.googlesource.com/chromiumos/platform/touchbot/+/master/quickstep/)

## TODO
 * Convert most of the C code to C++
 * Use smaller buffers for audio input (mic)