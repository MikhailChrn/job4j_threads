package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean result = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        for (Entry<Integer, Account> entry : accounts.entrySet()) {
            if (entry.getValue().id() == account.id()) {
                result = true;
                accounts.put(entry.getKey(), account);
                break;
            }
        }
        return result;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).isEmpty()
                || getById(toId).isEmpty()
                || getById(fromId).get().amount() < amount) {
            return false;
        }
        Account fromAcc = getById(fromId).get();
        fromAcc = new Account(fromAcc.id(), fromAcc.amount() - amount);
        update(fromAcc);
        Account toAcc = getById(toId).get();
        toAcc = new Account(toAcc.id(), toAcc.amount() + amount);
        update(toAcc);
        return true;
    }
}