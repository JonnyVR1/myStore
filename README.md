myStore custom store for all Sense base ROMs including Sense 5. 

Now myStore using Fragments instead of Activities which allows to use application in Sense 5 as well. 

Now myStore will get links for secions from single file in System ROOT folder. 

Create myStore.txt (follow exact name) and place it to /system/ (where you have build.prop file) and paste this data:

ro.news=http://yoursite.com/news/news.xml
<n>ro.skins=http://yoursite.com/skins/skins.xml</n>
ro.battery=http://yoursite.com/battery/battery.xml
ro.kb=http://yoursite.com/keyboard/keyboard.xml
ro.mods=http://yoursite.com/skins/skins.xml
ro.kernels=http://yoursite.com/kernels/kernels.xml


Change to your real links and you finish with configuration. 

