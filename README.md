# BulPearl

This Minecraft plugin modifies the behavior of the EnderPearl. Compatible with version 1.8 to the Latest Minecrat version.   
Link to download: https://www.spigotmc.org/resources/93260/ or https://builtbybit.com/resources/37040/

## Features

- Modify the damage received when using an enderpearl
- Allow to use EnderPearl in creative (for Minecraft version 1.8.8)
- Prevent player in creative mode from dying when using ender pearl (For version 1.9 or higher)
- Set a cooldown after using an EnderPearl
- Dsiplay a visual of the cooldown in the action bar
- Display a visual of the Cooldown in inventory. (for Minecraft version 1.14 or higher)
- Disable the cooldown only in specific  worlds
- Set a custom sound when player uses an EnderPearl
- Prevent players from dying due to damage received from enderpearls (if the damage of an enderpearl would kill the player, they would not be killed and their health bar would be set to 0.5 hp)
- Create a custom craft recipe for EnderPearl with a command and a custom Gui
- Fully configurable

## Configuration file

> Set the damage receveid whem a EnderPearl was used

damage: 2.0

> Define if EnderPearl can kill the players

can_kill: true

> Define the cooldown in seconds beetwen each EnderPearl (0 = disable)

cooldown: 30

>Define the worlds where the cooldown are not enable 

cooldown_blacklist_world: [world1, world2, example3]

> Define the sound who are played when a EnderPearl are use (let black to disable)

sound: LEVEL_UP

> Define all the messages and actionbar text. If you don't want a specifique message you can define nothing beetwen the two "" quotation

enable_craft_creator: true

> Control the craft creator menu. After creating your craft for EnderPearl, set this line to "false" to avoid loading unnecessary listeners,
> which will enhance the performance.

messages:   
  use_pearl: "&a[BulPearl] &eYou have used an Ender Pearl to teleport."
  on_cooldown: "&a[BulPearl] &cYou must wait %time% before using an Ender Pearl again."
  end_cooldown: "&a[BulPearl] &eYou can now use an Ender Pearl."
  craft_created: "&a[BulPearl] &eYou have created a craft for Ender Pearls."
  reload: "&a[BulPearl] Configuration reloaded."
  error_only_ingame: "&c[BulPearl] You can't use this command from the console."
  error_unknown: "&c[BulPearl] Unknown command."
  error_craft_creator_disabled: "&c[BulPearl] The craft creator is disabled in the config.yml."
  no_permission: "&c[BulPearl] You do not have permission to reload the configuration."
actionbar:   
  on_cooldown: "&cYou can reuse an enderpearl in &e%time%s"   
  end_cooldown: "&aYou can now use again an EnderPearl"   

## Commands and permissions

| Command        | Description                                                         | Permission |
|----------------|---------------------------------------------------------------------| ------|
| bulpearl craft | Open a inventory that allows the creation of a craft for EnderPearl | bulpearl.admin
| bulpearl reload | Reload the configuration file                                       | bulpearl.admin
|            | Prevent the player from dying due to damage from EnderPearl         | bulpearl.bypass.death

## Distribution

This is a public plugin. You are free to use it and create a fork to develop your own version. However you are not allowed to sell or distribute it in a private manner.