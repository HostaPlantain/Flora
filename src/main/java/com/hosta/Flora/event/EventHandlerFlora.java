package com.hosta.Flora.event;

import java.util.List;
import java.util.Set;

import com.hosta.Flora.Flora;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlerFlora {

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (Flora.CONFIG_CLIENT.showItemTags.get())
		{
			List<ITextComponent> tooltips = event.getToolTip();
			Set<ResourceLocation> tags = event.getItemStack().getItem().getTags();

			if (tags.iterator().hasNext())
			{
				tooltips.add(new StringTextComponent(I18n.format("gui.tag")).applyTextStyle(TextFormatting.GRAY));
			}

			for (ResourceLocation resourceLocation : tags)
			{
				Tag<Item> tag = ItemTags.getCollection().get(resourceLocation);
				tooltips.add(new StringTextComponent(tag.getId().toString()).applyTextStyle(TextFormatting.DARK_GRAY));
			}
		}
	}
}
