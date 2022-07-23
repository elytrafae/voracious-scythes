
# Adds 1 to the kill count of at least one mob was killed this tick
scoreboard players add @s[scores={CMD_VC_MKills=1..}] CMD_FierceW_Counter 1

# Give Advancement if required count is reached!
execute as @s[scores={CMD_FierceW_Counter=25..}] at @s run advancement grant @s only voracious_scythes:fiercest_warrior