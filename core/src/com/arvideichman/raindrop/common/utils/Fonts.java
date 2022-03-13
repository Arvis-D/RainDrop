package com.arvideichman.raindrop.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {
    private static final String defaultFont = "SpecialElite";
    private static Map<String, BitmapFont> fontMap = null;

    private static void init() {
        fontMap = new HashMap<String, BitmapFont>();
    }

    public static BitmapFont getFont(String fontName) {
        if (fontMap == null) init();
        
        var font = fontMap.get(fontName);
        if (font == null) font = tryToGenerateFont(fontName);

        return font;
    }

    private static BitmapFont tryToGenerateFont(String fontName) {
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + fontName + ".ttf"));
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        parameter.size = 18;

        var font = generator.generateFont(parameter);
        fontMap.put(fontName, font);

        return font;
    }

    public static BitmapFont getDefault() {
        return getFont(defaultFont);
    }
}
