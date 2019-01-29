package com.xpgaming.PokedexRewards;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Remaining implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player player = (Player) src;
            EntityPlayerMP entity = (EntityPlayerMP) src;
            Optional<PlayerPartyStorage> optstorage = Optional.ofNullable(Pixelmon.storageManager.getParty(entity));
            double percent = Utils.getInstance().calcPercent((EntityPlayerMP) player);
            if(optstorage.isPresent()) {
                if (percent < 100) {
                    List<Text> contents = new ArrayList<>();
                    for (EnumSpecies e : EnumSpecies.values()) {
                        if (e.toString().contentEquals("PorygonZ")) {
                            String name = "Porygon-Z";
                            int id = Pokedex.nameToID(name);
                            if (!optstorage.get().pokedex.hasCaught(id)) {
                                contents.add(Text.of("\u00A76" + name));
                            }
                        } else if (e.toString().contentEquals("Hooh")) {
                            String name = "Ho-Oh";
                            int id = Pokedex.nameToID(name);
                            if (!optstorage.get().pokedex.hasCaught(id)) {
                                contents.add(Text.of("\u00A7e" + name));
                            }
                        } else {
                            int id = Pokedex.nameToID(e.toString());
                            if (!optstorage.get().pokedex.hasCaught(id)) {
                                if (EnumSpecies.legendaries.contains(e.toString())) {
                                    contents.add(Text.of("\u00A7e" + e.toString()));
                                } else contents.add(Text.of("\u00A76" + e.toString()));
                            }
                        }
                    }
                    PaginationList.builder()
                            .title(Text.builder("Pokémon Remaining").color(TextColors.GOLD).build())
                            .contents(contents)
                            .padding(Text.builder("-").color(TextColors.YELLOW).build())
                            .sendTo(player);
                }
            } else {
                src.sendMessage(Text.of("\u00A7f[\u00A7bPokeDex\u00A7f] \u00A7bYou have no more Pokémon to catch, well done!"));
            }

        } else {
            src.sendMessage(Text.of("\u00A7f[\u00A7cPokeDex\u00A7f] \u00A7cYou need to be a player to run this command!"));
        }
        return CommandResult.success();
    }
}
