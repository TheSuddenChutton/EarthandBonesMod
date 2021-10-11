package com.github.thesuddenchutton.earthandbonesmod.blocks.blockentities;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants.NBT;

public class SpiderNestBEntity extends BlockEntity {
		
	public SpiderNestBEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_) {
		super(p_155228_, p_155229_, Registration.SPIDERNEST.get().defaultBlockState());
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
	}
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("spiders", tag.getInt("spiders"));
		return super.serializeNBT();
	}
	@Override
	public void deserializeNBT(CompoundTag nbt) {
		// TODO Auto-generated method stub
		super.deserializeNBT(nbt);
	}
}
