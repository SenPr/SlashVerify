package me.sen2y.slashverify;

import github.scarsz.discordsrv.dependencies.jda.api.events.message.MessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import github.scarsz.discordsrv.dependencies.jda.api.interactions.commands.Command;

public class JDAListener extends ListenerAdapter {

    private SlashVerify plugin;
    public JDAListener(SlashVerify plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }
        if (e.getGuild().getOwnerId().equals(e.getAuthor().getId())) {
            if (e.getMessage().getContentRaw().equals("%slashverify delete")) {
                e.getGuild().retrieveCommands().queue(commands -> {
                    for (Command command : commands) {
                        if (command.getName().equalsIgnoreCase("link")) {
                            command.delete().queue();
                            plugin.getLogger().info("Application command /link has been deleted");
                            e.getChannel().sendMessage("Deleted the /link command").queue();
                            break;
                        }
                    }
                });
            }
        }
    }

}
