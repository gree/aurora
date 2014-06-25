package net.gree.aurora.domain;

import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.lifecycle.exception.EntityNotFoundRuntimeException;
import org.sisioh.dddbase.lifecycle.sync.*;
import org.sisioh.dddbase.model.Entity;
import org.sisioh.dddbase.model.Identity;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Try;

import java.util.*;

/**
 * メモリ型のリポジトリの骨格実装。
 *
 * @param <This> 派生型
 * @param <ID>   エンティティのID型
 * @param <E>    エンティティの型
 */
public class AbstractSyncRepositoryOnMemory<This extends SyncRepository<This, ID, E>, ID extends Identity<?>, E extends Entity<ID>>
        implements SyncRepository<This, ID, E>, SyncEntityIterableReader<ID, E> {

    private final Map<ID, E> entities = new HashMap<>();
    private final List<ID> identies = new ArrayList<>();

    @Override
    public Try<Boolean> contains(ID identity, SyncEntityIOContext ctx) {
        Validate.notNull(identity);
        Validate.notNull(ctx);
        boolean result = entities.containsKey(identity);
        return Try.ofSuccess(result);
    }

    @Override
    public Try<Boolean> contains(ID identity) {
        Validate.notNull(identity);
        return contains(identity, SyncEntityIOContextFactory.DEFAULT);
    }

    @Override
    public Try<Boolean> contains(E entity, SyncEntityIOContext ctx) {
        Validate.notNull(entity);
        Validate.notNull(ctx);
        return contains(entity.getIdentity());
    }

    @Override
    public Try<Boolean> contains(E entity) {
        Validate.notNull(entity);
        return contains(entity.getIdentity(), SyncEntityIOContextFactory.DEFAULT);
    }

    @Override
    public Try<E> resolve(final ID identity) {
        Validate.notNull(identity);
        return contains(identity).map(new Function1<Boolean, E>() {
            @Override
            public E apply(Boolean contains) {
                if (contains) {
                    return entities.get(identity);
                } else {
                    throw new EntityNotFoundRuntimeException("identity = " + identity);
                }
            }
        });
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> store(E entity, SyncEntityIOContext ctx) {
        Validate.notNull(entity);
        Validate.notNull(ctx);
        entities.put(entity.getIdentity(), entity);
        identies.add(entity.getIdentity());
        SyncResultWithEntity<This, ID, E> result = SyncResultWithEntityFactory.create((This) this, entity);
        return Try.ofSuccess(result);
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> store(E entity) {
        return store(entity, SyncEntityIOContextFactory.DEFAULT);
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> delete(final ID identity, SyncEntityIOContext ctx) {
        Validate.notNull(identity);
        Validate.notNull(ctx);
        return contains(identity).map(new Function1<Boolean, SyncResultWithEntity<This, ID, E>>() {
            @Override
            public SyncResultWithEntity<This, ID, E> apply(Boolean contains) {
                if (contains) {
                    E entity = entities.remove(identity);
                    identies.remove(entity.getIdentity());
                    SyncResultWithEntity<This, ID, E> result =
                            SyncResultWithEntityFactory.create((This) AbstractSyncRepositoryOnMemory.this, entity);
                    return result;
                } else {
                    throw new EntityNotFoundRuntimeException("identity = " + identity);
                }
            }
        });
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> delete(ID identity) {
        Validate.notNull(identity);
        return delete(identity, SyncEntityIOContextFactory.DEFAULT);
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> delete(E entity, SyncEntityIOContext ctx) {
        Validate.notNull(entity);
        Validate.notNull(ctx);
        return delete(entity.getIdentity(), ctx);
    }

    @Override
    public Try<SyncResultWithEntity<This, ID, E>> delete(E entity) {
        Validate.notNull(entity);
        return delete(entity, SyncEntityIOContextFactory.DEFAULT);
    }

    @Override
    public Map<ID, E> toMap() {
        return new HashMap<>(entities);
    }

    @Override
    public Set<E> toSet() {
        return new HashSet<>(entities.values());
    }

    public List<E> toList() {
        List<E> result = new ArrayList<>();
        for (ID identity : identies) {
            result.add(entities.get(identity));
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return toList().iterator();
    }

}
