package net.gree.aurora.domain.hint;

final class HintImpl<T> implements Hint<T> {

    private final T value;
    private final Object tag;

    HintImpl(T value, Object tag) {
        this.value = value;
        this.tag = tag;
    }

    HintImpl(T value) {
        this.value = value;
        this.tag = null;
    }


    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HintImpl hint = (HintImpl) o;

        if (tag != null ? !tag.equals(hint.tag) : hint.tag != null) return false;
        if (!value.equals(hint.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hint{" +
                "value=" + value +
                ", tag=" + tag +
                '}';
    }

}
