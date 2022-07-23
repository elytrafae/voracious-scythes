
give @s voracious_scythes:doom_disc_fragment

tellraw @s {"text":"Here's a Doom Disc Fragment!","color":"red","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false}
tellraw @s {"text":"If you want more, you know what to do!","color":"red","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false}
playsound ambient.basalt_deltas.mood player @s ~ ~ ~ 1 0.8

scoreboard players set @s CMD_Doom_Counter 0
advancement revoke @s only voracious_scythes:doom_fragment