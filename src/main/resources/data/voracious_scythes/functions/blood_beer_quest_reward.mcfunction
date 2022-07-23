
function voracious_scythes:give_blood_beer

tellraw @s {"text":"Here's another Blood Beer! Now keep going!","color":"red","bold":false,"italic":false,"underlined":false,"strikethrough":false,"obfuscated":false}
playsound entity.witch.drink player @s ~ ~ ~ 1 0.8

scoreboard players set @s CMD_TermExt_Counter 0
advancement revoke @s only voracious_scythes:blood_alcoholism