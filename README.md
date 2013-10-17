smart-meal
==========

You need to update SDK building-tools ^^ it took me 3 hours to complete downloading and installing. Have fun ^^


HOW TO RUN
==========

0. Go to <SDK>/tools/ant/build.xml. Replace 

1. target name="-dex" depends="-compile, -post-compile, -obfuscate"
2. by
3. target name="-dex" depends="-jarjar"

4. You need to install Ant (plugin or standalone)
5. Then, clean your project
6. Choose ant debug or run $ant debug
7. Go to the bin/ directory of the project
8. Execute adb install -r MainActivity-debug.apk
9. The result on the device is 3 tabs with my type BMI: 1 (little overweight)
