package net.gree.aurora.domain.hint;

/**
 * ヒントを表す値オブジェクト。
 *
 * @param <T> ヒントの値の型
 */
public interface Hint<T> {

    /**
     * タグ値を取得する。
     *
     * @return タグ
     */
    Object getTag();

    /**
     * ヒントの値を取得する。
     *
     * @return ヒントの値
     */
    T getValue();

}
