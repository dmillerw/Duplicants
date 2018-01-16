package me.dmillerw.duplicants.item;

import me.dmillerw.duplicants.network.PacketHandler;
import me.dmillerw.duplicants.network.packet.SSelectBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static me.dmillerw.duplicants.Duplicants.Info.MOD_NAME;

public class ItemSelector extends Item {

    public ItemSelector() {
        super();

        setMaxDamage(0);
        setMaxStackSize(1);

        setUnlocalizedName(MOD_NAME + ":selector");
        setRegistryName(MOD_NAME, "selector");
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        player.sendMessage(new TextComponentString("Selected block"));
        SSelectBlock packet = new SSelectBlock(SSelectBlock.Type.RIGHT_CLICK, pos);
        PacketHandler.INSTANCE.sendToServer(packet);
        return EnumActionResult.SUCCESS;
    }
}
