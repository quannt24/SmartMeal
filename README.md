smart-meal
==========

You need to update SDK building-tools ^^ it took me 3 hours to complete downloading and installing. Have fun ^^


HOW TO RUN
==========

0. Go to <SDK>/tools/ant/build.xml
    Replace
  <target name="-dex" depends="-compile, -post-compile, -obfuscate">
    by
	<target name="-dex" depends="-jarjar">

1. You need to install Ant (plugin or standalone)
2. Then, clean your project
3. Choose ant debug or run $ant debug
4. Go to the bin/ directory of the project
5. Execute adb install -r MainActivity-debug.apk
6. The result on the device is 3 tabs with my type BMI: 1 (little overweight)
