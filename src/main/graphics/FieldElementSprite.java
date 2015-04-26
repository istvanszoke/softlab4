package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import buff.Buff;
import feedback.NoFeedbackException;
import feedback.Result;
import field.*;
import graphics.handles.EmptyFieldCellSprite;
import graphics.handles.FieldCellSprite;
import graphics.handles.FinishLineFieldCellSprite;

public class FieldElementSprite implements SpriteHandle, FieldVisitor {
    SpriteHandle fieldSprite;
    Field field;

    public FieldElementSprite(Field field) {
        if (field != null) {
            field.accept(this);
            this.field = field;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void visit(FieldCell element) {
        fieldSprite = new FieldCellSprite(element);
    }

    @Override
    public void visit(EmptyFieldCell element) {
        fieldSprite = new EmptyFieldCellSprite(element);
    }

    @Override
    public void visit(FinishLineFieldCell element) {
        fieldSprite = new FinishLineFieldCellSprite(element);
    }

    @Override
    public Result getResult() throws NoFeedbackException {
        throw new NoFeedbackException();
    }

    @Override
    public BufferedImage getItemImage() {
        List<BuffElementSprite> buffSprites = new ArrayList<BuffElementSprite>();
        for (Buff buff : field.getBuffs()) {
            buffSprites.add(new BuffElementSprite(buff));
        }

        BufferedImage fieldImage = GameGraphics.deepCopyBufferedImage(fieldSprite.getItemImage());

        fieldImage.getGraphics().drawImage(fieldImage, 0, 0, null);
        for (BuffElementSprite buffSprite : buffSprites) {
            fieldImage.getGraphics().drawImage(buffSprite.getItemImage(), 0, 0, null);
        }
        if (field.getAgent() != null) {
            AgentElementSprite agentSprite = new AgentElementSprite(field.getAgent());
            fieldImage.getGraphics().drawImage(agentSprite.getItemImage(), 0, 0, null);
        }
        return fieldImage;
    }

    public Field getField() {
        return field;
    }
}
