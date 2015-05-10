package graphics;

import java.awt.image.BufferedImage;

import agents.Agent;
import agents.AgentVisitor;
import agents.Robot;
import agents.Vacuum;
import feedback.NoFeedbackException;
import feedback.Result;
import graphics.handles.RobotSprite;
import graphics.handles.VacuumSprite;

public class AgentElementSprite implements SpriteHandle, AgentVisitor {
    private SpriteHandle agentSprite;
    private Agent agent;

    public AgentElementSprite(Agent agent) {
        if (agent != null) {
            agent.accept(this);
            this.agent = agent;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public BufferedImage getItemImage() {
        return agentSprite.getItemImage();
    }

    @Override
    public void visit(Robot element) {
        agentSprite = new RobotSprite(element);
    }

    @Override
    public void visit(Vacuum element) {
        agentSprite = new VacuumSprite();
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public Result getResult() throws NoFeedbackException {
        throw new NoFeedbackException();
    }
}
