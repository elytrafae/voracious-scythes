
give @s voracious_scythes:terminal_existence_music_disc 1

particle minecraft:damage_indicator ~ ~0.8 ~ 0.5 0.5 0.5 0.5 40 force @a

tellraw @s {"text":"Not bad for a start.","color":"red","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false}
tellraw @s {"text":"You seem to be worthy to be kept alive after all.","color":"red","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false}
tellraw @s {"text":"Here's a gift to keep you focused on your purpose!","color":"red","bold":true,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false,"hoverEvent":{"action":"show_text","contents":[{"text":"You got a special Music Disc and a Blood Beer!","color":"dark_red"}]}}

# effect give @s resistance 5 5 true
# effect give @s strength 30 2 true
# effect give @s regeneration 30 1 true
# effect give @s speed 30 1 true

function voracious_scythes:give_blood_beer
function voracious_scythes:give_blood_beer

scoreboard players set @s CMD_TermExt_Counter 0