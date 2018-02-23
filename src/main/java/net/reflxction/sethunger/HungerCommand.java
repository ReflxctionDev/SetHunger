/*
 * * Copyright 2018 github.com/ReflxctionDev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.reflxction.sethunger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The command for the plugin, /hunger
 */

public class HungerCommand implements CommandExecutor {

    /**
     * @return Syntax of the command
     */
    private String syntax() {
        return "/hunger [player] <hunger>";
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check args
        if (args.length == 0) {
            // Show the correct syntax
            sender.sendMessage(ChatColor.RED + "Incorrect usage. Try " + syntax());
            sender.sendMessage(ChatColor.BLUE + "[] arguments are optional, <> are compulsory");
        }
        if (args.length == 1) {
            // Check if the sender is a player, since console cannot set their own hunger (unless you bring a cheese burger to the admin, i guess)
            if (sender instanceof Player) {
                // Player sender instance
                Player p = ((Player) sender);
                // Check if the sender has the permission to manage their own hunger
                if (sender.hasPermission("sethunger.self")) {

                    // Get the double the sender entered
                    try {
                        int hunger = Integer.parseInt(args[0]);
                        p.setFoodLevel(hunger);
                    } catch (NumberFormatException ex) {
                        // If the user didn't enter a number
                        sender.sendMessage(ChatColor.RED + "Expected a number value, but found " + ChatColor.BOLD + args[0]);
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command can be used by players only!");
            }
        }
        if (args.length == 2) {
            if (sender.hasPermission("sethunger.others")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Couldn't find a player with this name. Ensure that they're online and the spelling is correct");
                } else {
                    try {
                        int hunger = Integer.parseInt(args[1]);
                        target.setFoodLevel(hunger);
                    } catch (NumberFormatException ex) {
                        // If the user didn't enter a number
                        sender.sendMessage(ChatColor.RED + "Expected a number value, but found " + ChatColor.BOLD + args[0]);
                    }
                }
            }
        }
        return false;
    }
}
