package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    @Test
    public void whenAddFind() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        var base = new Base(1,   "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void whenCheckingBaseVersionAfterUpdateTwoTime() {
        Base base = new Base(999, "Check base version", 1);
        Cache cache = new Cache();
        cache.add(base);

        cache.update(new Base(999, "Check base version", 1));
        Optional<Base> find = cache.findById(base.id());
        assertThat(find.get().version())
                .isEqualTo(base.version() + 1);

        cache.update(new Base(999, "Check base version", base.version() + 1));
        find = cache.findById(base.id());
        assertThat(find.get().version())
                .isEqualTo(base.version() + 2);
    }
}