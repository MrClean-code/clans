package com.clans.service;

import com.clans.dto.ClanDto;
import com.clans.entity.Clan;
import com.clans.rep.ClanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ClanManager {

    private final ClanRepository clanRep;
    private final Lock lock = new ReentrantLock();

    @Transactional
    public ClanDto incrementGold(long clanId, int gold) {
        lock.lock();
        try {
            Clan clan = getClan(clanId);
            clan.getGold().addAndGet(gold);
            clanRep.save(clan);
            return Clan.toClanDto(clan);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public ClanDto decrementGold(long clanId, int gold) {
        lock.lock();
        try {
            Clan clan = getClan(clanId);
            int currentGold = clan.getGold().get();
            if (currentGold < Math.abs(gold)) {
                throw new IllegalArgumentException("Недостаточно золота");
            }
            clan.getGold().addAndGet(-gold);
            clanRep.save(clan);
            return Clan.toClanDto(clan);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public ClanDto createClan(Clan clan) {
        Clan savedClan = clanRep.save(clan);
        return Clan.toClanDto(savedClan);
    }

    public Clan getClan(long id) {
        return clanRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found with id = " + id));
    }

    @Transactional
    public ClanDto updateClan(Clan clan, long clanId) {
        Clan clanDB = clanRep.findById(clanId)
                .orElseThrow(() -> new NullPointerException("clan is null"));

        clanDB.setGold(clan.getGold());
        clanDB.setName(clan.getName());

        clanRep.save(clanDB);
        return Clan.toClanDto(clanDB);
    }


}
