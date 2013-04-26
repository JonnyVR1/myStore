myStore custom store for all Sense base ROMs including Sense 5. 

Now myStore using Fragments instead of Activities which allows to use application in Sense 5 as well. 

Now myStore will get links for secions from single file in System ROOT folder. 

Create <b>myStore.txt</b> (follow exact name) and place it to /system/ (where you have build.prop file) and paste this data:

<code>ro.news=http://yoursite.com/news/news.xml</code>

<code>ro.skins=http://yoursite.com/skins/skins.xml</code>

<code>ro.battery=http://yoursite.com/battery/battery.xml</code>

<code>ro.kb=http://yoursite.com/keyboard/keyboard.xml</code>

<code>ro.mods=http://yoursite.com/skins/skins.xml</code>

<code>ro.kernels=http://yoursite.com/kernels/kernels.xml</code>


Change to your real links and you finish with configuration. 

