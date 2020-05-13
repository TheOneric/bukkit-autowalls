package oneric.bukkit.walls.commands;

import oneric.bukkit.walls.src.WallsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ComArmor extends WallsCommand {


	public ComArmor(WallsPlugin plugin) {
		super(plugin, 1, true, WallsPlugin.PERMISSION_MANIPULATE);
		/// Permission geht NICHT D:
		/// Internal Error wenn zuwenige / keine Args
	}

	@Override
	public boolean onSafeCommand(CommandSender sender, Command com, String comLabel, String[] arg) {
		
		Player player = (Player)sender;
		
		// Checking WHICH Argument
		if (arg[0].equalsIgnoreCase("chain")) {
			player.getInventory().setBoots(
					new ItemStack(Material.CHAINMAIL_BOOTS, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
			player.getInventory().setHelmet(
					new ItemStack(Material.CHAINMAIL_HELMET, 1));

			player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED
					+ " You're a CHEATER");

			return true;
		} else if (arg[0].equalsIgnoreCase("leather")) {
			player.getInventory().setBoots(
					new ItemStack(Material.LEATHER_BOOTS, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.LEATHER_LEGGINGS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.LEATHER_CHESTPLATE, 1));
			player.getInventory().setHelmet(
					new ItemStack(Material.LEATHER_HELMET, 1));

			player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED
					+ " You're a CHEATER");

			return true;
		} else if (arg[0].equalsIgnoreCase("gold")) {
			player.getInventory().setBoots(
					new ItemStack(Material.GOLD_BOOTS, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.GOLD_LEGGINGS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.GOLD_CHESTPLATE, 1));
			player.getInventory().setHelmet(
					new ItemStack(Material.GOLD_HELMET, 1));

			player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED
					+ " You're a CHEATER");

			return true;
		} else if (arg[0].equalsIgnoreCase("iron")) {
			player.getInventory().setBoots(
					new ItemStack(Material.IRON_BOOTS, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.IRON_LEGGINGS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.IRON_CHESTPLATE, 1));
			player.getInventory().setHelmet(
					new ItemStack(Material.IRON_HELMET, 1));

			player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED
					+ " You're a CHEATER");

			return true;
		} else if (arg[0].equalsIgnoreCase("diamond")) {
			player.getInventory().setBoots(
					new ItemStack(Material.DIAMOND_BOOTS, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.DIAMOND_LEGGINGS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
			player.getInventory().setHelmet(
					new ItemStack(Material.DIAMOND_HELMET, 1));

			player.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED
					+ " You're a CHEATER :P");

			return true;
		} else {
			player.sendMessage(ChatColor.RED + "No know armor type with this name !");

			return false;
		}
	}

}
