package com.hosta.Flora.block;

import net.minecraft.client.renderer.RenderType;

public interface IRenderType {

	public IRenderType.Type getType();

	enum Type
	{
		SOLID(RenderType.getSolid()),
		CUTOUT(RenderType.getCutout());

		private RenderType type;

		private Type(RenderType field)
		{
			this.type = field;
		}

		public RenderType getRenderType()
		{
			return type;
		}
	};
}
