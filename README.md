<center>
    <h1>TO-DO : Banner image</h1>
    Ever wanted to experience the thrill of being a fireman in Minecraft? We got the bars and ladders to start!
</center>

<br>

## About

Bars and Ladders adds a few new ways to get around in Minecraft.

### Bars

By building a loose standing pillar of (Iron, Copper) Bars or End Rods, you create a 'pole'.
<br> When you jump into a pole, you will be slightly slowed down and not take fall damage.
> By default, fall damage is only reset if you've been slowed down to the 'target speed', this means you can not 'clutch' your fall.

### Ladders

When right-clicking a placed ladder with a ladder in your hand, it will be extended downwards.

Ladders can also be supported by other ladders above them, meaning you can build a ladder downwards from a platform without needing a wall for support.

### Chains

Hanging chains can be grabbed onto and hung still from by crouching next to them.
> By default, this will do half the fall damage you would've normally gotten if any.

### Jumping

While sliding down a pole or holding on to chains, you can press your jump key to jump in the direction you're looking.

<br>

## Configuration

On its own, the mod will not create a config file.
To change settings, you can install [Fzzy Config](https://modrinth.com/mod/fzzy-config).

To load changes to the config file, you can use the vanilla ``/reload`` command.

```toml
# The speed a player will be slowed to when sliding down a bar.
targetSpeed = -0.75
# The value by which the player's speed will be multiplied per tick while sliding down a bar.
dragModifier = 0.85
# How long a ladder can be to still be extendable.
ladderReach = 16
```