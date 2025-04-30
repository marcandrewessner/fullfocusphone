# T9 Keyboard for the QIN F22 PRO

## How to install the keyboard

The settings are found under `Settings > System > Language & Input > On-Screen Keyboard`
But there is a problem: You cannot select the only the T9 Keyboard!

Do it using the command line:
``
adb.exe shell
$ ime help # The ime command is used to change the Input Method Settings
$ ime disable com.android.inputmethod.latin/.LatinIME # Disable the AOSP Keyboard
$ ime enable com.marcandre.ma9keyboard/.inputmethodservice.KeyboardMain # Enable the T9 Keyboard
``