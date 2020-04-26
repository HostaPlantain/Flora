package com.hosta.Flora.block;

import net.minecraft.client.renderer.RenderType;

public interface IRenderType {

	public IRenderType.Type getType();

	enum Type
	{
		SOLID(RenderType.func_228639_c_()),
		CUTOUT(RenderType.func_228643_e_());

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
