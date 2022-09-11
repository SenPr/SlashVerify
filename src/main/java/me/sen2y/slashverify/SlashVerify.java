package me.sen2y.slashverify;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommandProvider;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.SlashCommandEvent;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.OptionType;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class SlashVerify extends JavaPlugin implements SlashCommandProvider {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public Set<PluginSlashCommand> getSlashCommands() {
        return new HashSet<>(Arrays.asList(
                new PluginSlashCommand(this, new CommandData("link", this.getConfig().getString("command-description"))
                        .addOption(OptionType.STRING, this.getConfig().getString("code-option-name"), this.getConfig().getString("code-option-description"), true))
        ));
    }

    @SlashCommand(path = "link", deferEphemeral = true)
    public void verifyCommand(SlashCommandEvent event) {
        String code = Objects.requireNonNull(event.getOption("code")).getAsString();
        event.reply(DiscordSRV.getPlugin().getAccountLinkManager().process(code, event.getUser().getId())).queue();
    }
}