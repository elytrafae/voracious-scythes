
# Adds 1 to the kill count of at least one mob was killed this tick
scoreboard players operation @s CMD_NYCTBA_Counter += @s CMD_Trades

# Give Advancement if required count is reached!
execute as @s[scores={CMD_NYCTBA_Counter=50..}] at @s run advancement grant @s only voracious_scythes:nyctba 