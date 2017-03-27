TFC Metalworking at 5GPM (Glitches Per Minute) -- Fixed!
========================================================

While playing TFC, version 0.79.29 ([1.7.10]TerraFirmaCraft-0.79.29.922.jar), I was combining some melted metals in partially filled ingot molds.  

I right-clicked with a completely filled (100 units full) Sterling Silver mold and then accidentally shift-clicked another completely filled (100 units full) Sterling Silver mold into the metal pouring GUI and…  the source slot ingot mold became partially filled (98 units full) [<--Glitch#1].  

Thinking this was either a one-time glitch or maybe some mistake on my part, I put another completely filled (100 units full) Sterling Silver mold into the input slot and… the ingot mold in the source slot with 98 units became 97 units full [<--Glitch#2].  

So now, I needed to make 3 more units of Sterling Silver, therefore I poured exactly 1 unit of liquid copper out of a completely filled (100 units full) copper mold into an empty crucible…  The crucible received 1 unit of copper, while the source copper mold lost 2 units and became just 98 units full [<--Glitch#3].  

I proceeded to pour exactly 2 units of liquid silver out of a completely filled (100 units full) silver mold into the crucible containing 1 unit of copper…  The crucible now contained 1+2=3 units of Sterling Silver (33.33% coper, 66 .67% silver), while the source silver mold had only 97 units of silver left [<--Glitch#4].  

And when I put an empty ingot mold into the output slot of the crucible which had 3 units of Sterling Silver the mold received… only 2 units of Sterling Silver and the crucible was empty [<--Glitch#5].

At this point, I took a long deep breath and… went straight for the TFC source code!

----------------------------------------

Now, it is my firm belief that I had fixed all the above-described glitches without introducing any new ones.  (I have rebuilt the entire TFC jar and tested the game, trying to break it in every way imaginable for about an hour; and then I played for fun for 3 more hours without a problem.)

I have clearly marked all my edits in the code (search for [Precision Smelter]).  The modified files are:  ItemMeltedMetal.java, ContainerMold.java, ContainerLiquidVessel.java, TECrucible.java.

I love Minecraft.  TerraFirmaCraft is by far my favorite Minecraft mod, so it pains me to see that the last officially released TFC version (0.79.29) turned out to be so glitchy.  I hope you will incorporate my fixes into the next official release.

Best regards,
Precision Smelter

