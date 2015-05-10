package buff;

public interface BuffElement {
    void accept(BuffVisitor visitor);
}
