package me.dmillerw.citizens.worldgen;

import me.dmillerw.citizens.block.BlockStoneSeat;
import me.dmillerw.citizens.block.ModBlocks;
import me.dmillerw.citizens.entity.EntityCitizen;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

import static net.minecraft.world.gen.structure.MapGenVillage.VILLAGE_SPAWN_BIOMES;

public class GenerateCampingSpot implements IWorldGenerator {

    public static void register() {
        GameRegistry.registerWorldGenerator(new GenerateCampingSpot(), 100);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        final int scanRadius = 3;
        final int x = chunkX * 16 + random.nextInt(16);
        final int z = chunkZ * 16 + random.nextInt(16);

        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, 255, z);

        if (world.getBiomeProvider().areBiomesViable(x, z, 0, VILLAGE_SPAWN_BIOMES)) {
            if (random.nextFloat() <= 0.1) {
                for (int i = -scanRadius; i <= scanRadius; i++) {
                    for (int j = -scanRadius; j <= scanRadius; j++) {
                        pos.setPos(x + i, 255, z + j);
                        if (!world.isChunkGeneratedAt(pos.getX() >> 4, pos.getZ() >> 4))
                            continue;

                        BlockPos top = world.getTopSolidOrLiquidBlock(pos);
                        IBlockState block = world.getBlockState(top.down());

                        if (block.getMaterial() == Material.ROCK || block.getMaterial() == Material.GROUND || block.getMaterial() == Material.GRASS) {
                            if (!world.isRemote) {
                                EnumFacing facing = EnumFacing.HORIZONTALS[random.nextInt(4)];
                                IBlockState seat = ModBlocks.stone_seat.getDefaultState().withProperty(BlockStoneSeat.FACING, facing);
                                world.setBlockState(top, seat);

                                EntityCitizen entityCitizen = new EntityCitizen(world);
                                entityCitizen.setPosition(top.getX() + 0.5, top.getY() + 1, top.getZ() + 0.5);
                                entityCitizen.setName("CyanideX");
                                entityCitizen.setSkin("CyanideX");

                                pos.setPos(top);
                                pos.offset(facing, 2);

                                world.setBlockState(pos, ModBlocks.campfire.getDefaultState());
                            }

                            return;
                        }
                    }
                }
            }
        }
    }
}
