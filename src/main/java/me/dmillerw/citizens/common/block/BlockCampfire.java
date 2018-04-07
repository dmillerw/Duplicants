package me.dmillerw.citizens.common.block;

import me.dmillerw.citizens.Citizens;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCampfire extends Block {

    public BlockCampfire() {
        super(Material.WOOD);

        setUnlocalizedName(Citizens.Info.MOD_ID + ":campfire");
        setRegistryName(Citizens.Info.MOD_ID + ":campfire");

        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.4, 0.9);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        final float x = pos.getX() + 0.5F;
        final float y = pos.getY() + 0.3F;
        final float z = pos.getZ() + 0.5F;
        final float mod = 0.25F;

        final int count = 30;

        if (rand.nextDouble() < 0.1D) {
            worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        for (int i = 0; i < count; i++) {
            final float xmod = x + (mod * rand.nextFloat()) - (mod * rand.nextFloat());
            final float zmod = z + (mod * rand.nextFloat()) - (mod * rand.nextFloat());

            if (rand.nextBoolean()) {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, xmod, y, zmod, 0.0D, 0.0D, 0.0D);
            } else {
                worldIn.spawnParticle(EnumParticleTypes.FLAME, xmod, y, zmod, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
