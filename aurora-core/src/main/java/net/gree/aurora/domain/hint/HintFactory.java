package net.gree.aurora.domain.hint;

/**
 * {@link Hint}のためのファクトリ。
 */
public final class HintFactory {

    private HintFactory() {
    }

    /**
     * {@link Hint}を生成する。
     *
     * @param value ヒントの値
     * @param tag ヒントにつけるタグ
     * @param <T> ヒントの値の型
     * @return {@link Hint}
     */
    public static <T> Hint<T> create(T value, Object tag) {
        return new HintImpl<>(value, tag);
    }

    /**
     * {@link Hint}を生成する。
     *
     * @param value ヒントの値
     * @param <T> ヒントの値の型
     * @return {@link Hint}
     */
    public static <T> Hint<T> create(T value) {
        return new HintImpl<>(value);
    }

}
