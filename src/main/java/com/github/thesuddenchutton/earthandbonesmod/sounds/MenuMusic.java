package com.github.thesuddenchutton.earthandbonesmod.sounds;

import com.github.thesuddenchutton.earthandbonesmod.setup.Registration;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.data.SoundDefinition.SoundType;

public class MenuMusic implements SoundInstance{

	@Override
	public ResourceLocation getLocation() {
		// TODO Auto-generated method stub
		return Registration.MENUMUSIC.getId();
	}

	@Override
	public WeighedSoundEvents resolve(SoundManager p_119841_) {
		// TODO Auto-generated method stub
		return new WeighedSoundEvents(Registration.MENUMUSIC.getId(), p_119841_.getName());
	}

	@Override
	public Sound getSound() {
		// TODO Auto-generated method stub
		return new Sound(Registration.MENUMUSIC.getId().toString(), getVolume(), getPitch(), 1, Sound.Type.SOUND_EVENT, true, true, 10);
	}
	
	@Override
	public SoundSource getSource() {
		// TODO Auto-generated method stub
		return SoundSource.MUSIC;
	}

	@Override
	public boolean isLooping() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRelative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVolume() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public float getPitch() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Attenuation getAttenuation() {
		// TODO Auto-generated method stub
		return Attenuation.NONE;
	}
	
}
