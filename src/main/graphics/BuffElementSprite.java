package graphics;

import java.awt.image.BufferedImage;

import buff.*;
import graphics.handles.OilSprite;
import graphics.handles.StickySprite;

/**
 * Created by nyari on 2015.04.25..
 */
public class BuffElementSprite implements SpriteHandle, BuffVisitor
{
    SpriteHandle buffSprite;
    Buff buff;

    public BuffElementSprite(Buff buff) {
        if (buff != null) {
            buff.accept(this);
            this.buff = buff;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void visit(Oil element) {
        buffSprite = new OilSprite(element);
    }

    @Override
    public void visit(Sticky element) {
        buffSprite = new StickySprite(element);
    }

    @Override
    public BufferedImage getItemImage() {
        return buffSprite.getItemImage();
    }

    public Buff getBuff() {
        return buff;
    }
}
