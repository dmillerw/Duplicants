package me.dmillerw.citizens.common.worldgen;

import me.dmillerw.citizens.common.block.BlockStoneSeat;
import me.dmillerw.citizens.common.block.ModBlocks;
import me.dmillerw.citizens.common.entity.EntityCitizen;
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
        final int x = chunkX * 16 + random.nextInt(16);
        final int z = chunkZ * 16 + random.nextInt(16);

        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, 255, z);

        if (random.nextFloat() <= 0.01) {
            if (!VILLAGE_SPAWN_BIOMES.contains(world.getBiome(pos)))
                return;

            BlockPos spawnPoint = world.getTopSolidOrLiquidBlock(pos);
            IBlockState block = world.getBlockState(spawnPoint.down());

            if (block.getMaterial() == Material.ROCK || block.getMaterial() == Material.GROUND || block.getMaterial() == Material.GRASS) {
                if (!world.isRemote) {
                    EnumFacing facing = EnumFacing.HORIZONTALS[random.nextInt(4)];
                    IBlockState seat = ModBlocks.stone_seat.getDefaultState().withProperty(BlockStoneSeat.FACING, facing);
                    world.setBlockState(spawnPoint, seat);

                    System.out.println(spawnPoint);

                    for (EnumFacing f : EnumFacing.HORIZONTALS) {
                        final BlockPos point = spawnPoint.offset(f);
                        if (world.isBlockLoaded(pos) && world.isAirBlock(point)) {
                            world.setBlockState(point, ModBlocks.campfire.getDefaultState());
                            break;
                        }
                    }

                    for (EnumFacing f : EnumFacing.HORIZONTALS) {
                        final BlockPos point = spawnPoint.offset(f);
                        if (world.isBlockLoaded(pos) && world.isAirBlock(point)) {
                            EntityCitizen entityCitizen = new EntityCitizen(world);
                            entityCitizen.setPosition(point.getX() + 0.5, point.getY() + 1, point.getZ() + 0.5);

                            world.spawnEntity(entityCitizen);

                            break;
                        }
                    }
                }
            }
        }
    }
}
