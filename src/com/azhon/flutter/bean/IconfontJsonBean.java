package com.azhon.flutter.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * createDate: 2022/11/24 on 09:30
 * desc:
 *
 * @author azhon
 */


public class IconfontJsonBean {


    private String id;
    private String name;
    @SerializedName("font_family")
    private String fontFamily;
    @SerializedName("css_prefix_text")
    private String cssPrefixText;
    private String description;
    private List<GlyphsBean> glyphs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getCssPrefixText() {
        return cssPrefixText;
    }

    public void setCssPrefixText(String cssPrefixText) {
        this.cssPrefixText = cssPrefixText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GlyphsBean> getGlyphs() {
        return glyphs;
    }

    public void setGlyphs(List<GlyphsBean> glyphs) {
        this.glyphs = glyphs;
    }

    public static class GlyphsBean {

        @SerializedName("icon_id")
        private String iconId;
        private String name;
        @SerializedName("font_class")
        private String fontClass;
        private String unicode;
        @SerializedName("unicode_decimal")
        private int unicodeDecimal;

        public String getIconId() {
            return iconId;
        }

        public void setIconId(String iconId) {
            this.iconId = iconId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFontClass() {
            return fontClass;
        }

        public void setFontClass(String fontClass) {
            this.fontClass = fontClass;
        }

        public String getUnicode() {
            return unicode;
        }

        public void setUnicode(String unicode) {
            this.unicode = unicode;
        }

        public int getUnicodeDecimal() {
            return unicodeDecimal;
        }

        public void setUnicodeDecimal(int unicodeDecimal) {
            this.unicodeDecimal = unicodeDecimal;
        }
    }
}
