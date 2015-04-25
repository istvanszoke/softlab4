package buff;

/**
 * Created by nyari on 2015.04.25..
 */
public interface BuffVisitor
{
    public void visit(Oil element);
    public void visit(Sticky element);
}
