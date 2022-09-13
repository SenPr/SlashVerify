package me.sen2y.slashverify;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.commands.PluginSlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommand;
import github.scarsz.discordsrv.api.commands.SlashCommandProvider;
import github.scarsz.discordsrv.dependencies.jda.api.events.interaction.SlashCommandEvent;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.OptionType;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.build.CommandData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class SlashVerify extends JavaPlugin implements SlashCommandProvider {

    private String alias;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        alias = this.getConfig().getString("command-alias");
    }

    @Override
    public Set<PluginSlashCommand> getSlashCommands() {
        return new HashSet<>(Arrays.asList(
                new PluginSlashCommand(this, new CommandData(alias, this.getConfig().getString("command-description"))
                        .addOption(OptionType.STRING, this.getConfig().getString("code-option-name"), this.getConfig().getString("code-option-description"), true))
        ));
    }

    @SlashCommand(path = "*")
    public void verifyCommand(SlashCommandEvent event) {
        if (!event.getCommandPath().equalsIgnoreCase(alias)) {
            return;
        }
        String code = Objects.requireNonNull(event.getOption("code")).getAsString();
        event.reply(DiscordSRV.getPlugin().getAccountLinkManager().process(code, event.getUser().getId()))
                .setEphemeral(this.getConfig().getBoolean("ephemeral-reply")).queue();
    }
}
