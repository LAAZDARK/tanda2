package com.laaz.tanda;

public class GrupoModelo {
    private String textViewNameGroup, textViewFecha;
    private String imageView;

    public GrupoModelo() {
    }

    public GrupoModelo(String textViewNameGroup, String textViewFecha, String imageView) {
        this.textViewNameGroup = textViewNameGroup;
        this.textViewFecha = textViewFecha;
        this.imageView = imageView;
    }

    public String getTextViewNameGroup() {
        return textViewNameGroup;
    }

    public void setTextViewNameGroup(String textViewNameGroup) {
        this.textViewNameGroup = textViewNameGroup;
    }

    public String getTextViewFecha() {
        return textViewFecha;
    }

    public void setTextViewFecha(String textViewFecha) {
        this.textViewFecha = textViewFecha;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
