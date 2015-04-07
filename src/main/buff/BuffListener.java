package buff;

/**
 * Interfész a Buff-okat figyelő osztályoknak, hogy a
 * buff-ok jelezni tudjanak eseményeikről
 */
public interface BuffListener {
    void onRemove(Buff buff);
}
