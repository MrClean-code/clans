package com.clans.entity;

import com.clans.dto.ClanDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clan")
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gold")
    private AtomicInteger gold = new AtomicInteger();

    public static ClanDto toClanDto(Clan clan) {
        ClanDto clanDto = new ClanDto();

        clanDto.setId(clan.getId());
        clanDto.setName(clan.getName());
        clanDto.setGold(clan.getGold().get());

        return clanDto;
    }
}
