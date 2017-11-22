package com.tikal.cacao.util;

import com.itextpdf.text.pdf.ExtendedColor;

public class CustomColor extends ExtendedColor {

	public CustomColor(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public CustomColor(int type, float red, float green, float blue) {
		super(type, red, green, blue);
		// TODO Auto-generated constructor stub
	}

	public CustomColor(int type, int red, int green, int blue, int alpha) {
		super(type, red, green, blue, alpha);
		// TODO Auto-generated constructor stub
	}

}
