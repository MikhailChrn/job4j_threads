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
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) == null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account fromAcc = getById(fromId).orElse(null);
        Account toAcc = getById(toId).orElse(null);
        if (fromAcc == null
                || toAcc == null
                || fromAcc.amount() < amount) {
            return false;
        }

        fromAcc = new Account(fromAcc.id(), fromAcc.amount() - amount);
        toAcc = new Account(toAcc.id(), toAcc.amount() + amount);
        update(fromAcc);
        update(toAcc);
        return true;
    }
}