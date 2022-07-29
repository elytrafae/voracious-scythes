
# Sets mob kills counter to total kills
scoreboard players operation @s CMD_VC_MKills = @s CMD_VC_TKills

# Deducts player kills from total kills
scoreboard players operation @s CMD_VC_MKills -= @s CMD_VC_PKills

# Do stuff here!
execute as @s[scores={CMD_VC_KillCD=..0}] run function voracious_scythes:terminal_existence_personal_tick
execute as @s[scores={CMD_VC_KillCD=..0},nbt={Dimension:"minecraft:the_nether"}] run function voracious_scythes:doom_personal_tick
execute as @s[scores={CMD_VC_KillCD=..0},nbt=!{SelectedItem:{}},nbt=!{Inventory:[{Slot:-106b}]}] run function voracious_scythes:fiercest_warrior_personal_tick
execute as @s run function voracious_scythes:nyctba_personal_tick

# Process Kill Cooldowns!
scoreboard players set @s[scores={CMD_VC_TKills=1..,CMD_VC_KillCD=..0}] CMD_VC_KillCD 10
scoreboard players remove @s CMD_VC_KillCD 1

# Resets all automatic objectives
scoreboard players reset @s CMD_VC_PKills
scoreboard players reset @s CMD_VC_TKills
scoreboard players reset @s CMD_VC_MKills
scoreboard players reset @s CMD_Trades