package graphics;

import java.awt.image.BufferedImage;

import buff.Buff;
import buff.BuffVisitor;
import buff.Oil;
import buff.Sticky;
import graphics.handles.OilSprite;
import graphics.handles.StickySprite;

public class BuffElementSprite implements SpriteHandle, BuffVisitor {
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
