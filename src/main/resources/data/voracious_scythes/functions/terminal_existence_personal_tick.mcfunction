
# Adds 1 to the kill count of at least one mob was killed this tick
scoreboard players add @s[scores={CMD_VC_MKills=1..}] CMD_TermExt_Counter 1

# Give Advancement if required count is reached!
execute as @s[scores={CMD_TermExt_Counter=666..},advancements={voracious_scythes:terminal_existence=false}] at @s run advancement grant @s only voracious_scythes:terminal_existence
execute as @s[scores={CMD_TermExt_Counter=66..},advancements={voracious_scythes:terminal_existence=true}] at @s run advancement grant @s only voracious_scythes:blood_alcoholism