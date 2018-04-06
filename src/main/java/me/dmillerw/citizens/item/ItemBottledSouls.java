package me.dmillerw.citizens.item;

import me.dmillerw.citizens.entity.EntityBottledSouls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import static me.dmillerw.citizens.Citizens.Info.MOD_NAME;

public class ItemBottledSouls extends Item {

    public ItemBottledSouls() {
        super();

        setUnlocalizedName(MOD_NAME + ":bottledSouls");
        setRegistryName(MOD_NAME, "bottledSouls");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        itemstack = playerIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ,
                SoundEvents.ENTITY_SPLASH_POTION_THROW,
                SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityBottledSouls entity = new EntityBottledSouls(worldIn, playerIn);
            entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.spawnEntity(entity);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
