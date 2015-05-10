package buff;

public interface BuffVisitor {
    void visit(Oil element);
    void visit(Sticky element);
}
