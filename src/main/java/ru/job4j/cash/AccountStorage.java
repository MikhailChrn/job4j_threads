package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAcc = getById(fromId);
        Optional<Account> toAcc = getById(toId);

        if (fromAcc.isEmpty()
                || toAcc.isEmpty()
                || fromAcc.get().amount() < amount) {
            return false;
        }
        fromAcc = Optional.of(
                new Account(fromAcc.get().id(), fromAcc.get().amount() - amount)
        );
        toAcc = Optional.of(
                new Account(toAcc.get().id(), toAcc.get().amount() + amount)
        );
        update(fromAcc.get());
        update(toAcc.get());
        return true;
    }
}