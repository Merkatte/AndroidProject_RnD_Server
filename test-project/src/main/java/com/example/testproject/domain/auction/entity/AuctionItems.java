package com.example.testproject.domain.auction.entity;

import com.example.testproject.domain.user.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "item_rarity_tier", referencedColumnName = "tier")
    ItemRarity rarity;

    @Column(nullable = false)
    LocalDateTime registeredAt;

    @Column(nullable = false)
    Boolean completed;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "auctionItems")
    List<Bids> bids;

    @Column
    Integer highestBid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    AppUser appUser;

    @PrePersist
    private void setDefault(){
        this.registeredAt = LocalDateTime.now();
        this.completed = false;
    }

    // Default Options
    @Column
    Float defaultPhysicalWeaponDamage;
    @Column
    Float defaultMagicalWeaponDamage;
    @Column
    Float defaultArmorRating;

//    @Column
//    Float DefaultMoveSpeed;
//    @Column
//    Float DefaultStrength;
//    @Column
//    Float DefaultAgility;
//    @Column
//    Float DefaultKnowledge;
//    @Column
//    Float DefaultPrimitive;
//    @Column
//    Float DefaultWill;
//    @Column
//    Float DefaultResourcefulness;


    // Dynamic Options
    @Column
    Float actionSpeed;

    @Column
    Float agility;

    @Column
    Float armorPenetration;

    @Column
    Float armorRating;

    @Column
    Float armorRatingAdd;

    @Column
    Float buffDurationBonus;

    @Column
    Float debuffDurationBonus;

    @Column
    Float headshotReductionMod;

    @Column
    Float itemEquipSpeed;

    @Column
    Float knowledge;

    @Column
    Float magicalDamage;

    @Column
    Float magicalDamageAdd;

    @Column
    Float magicalDamageBonus;

    @Column
    Float magicalDamageReduction;

    @Column
    Float magicalDamageTrue;

    @Column
    Float magicalHealing;

    @Column
    Float magicalInteractionSpeed;

    @Column
    Float magicalPower;

    @Column
    Float magicalWeaponDamage;

    @Column
    Float magicPenetration;

    @Column
    Float magicResistance;

    @Column
    Float maxHealthAdd;

    @Column
    Float maxHealthBonus;

    @Column
    Float moveSpeed;

    @Column
    Float moveSpeedAdd;

    @Column
    Float moveSpeedBonus;

    @Column
    Float physicalDamageAdd;

    @Column
    Float physicalDamageBonus;

    @Column
    Float physicalDamageReduction;

    @Column
    Float physicalDamageTrue;

    @Column
    Float physicalHealing;

    @Column
    Float physicalPower;

    @Column
    Float physicalWeaponDamage;

    @Column
    Float physicalWeaponDamageAdd;

    @Column
    Float prestigeItemDrop;

    @Column
    Float primitive;

    @Column
    Float projectileReductionMod;

    @Column
    Float regularInteractionSpeed;

    @Column
    Float resourcefulness;

    @Column
    Float spellCapacityAdd;

    @Column
    Float spellCapacityBonus;

    @Column
    Float spellCastingSpeed;

    @Column
    Float strength;

    @Column
    Float utilityEffectivenessAdd;

    @Column
    Float utilityEffectivenessBonus;

    @Column
    Float weaponDamageAdd;

    @Column
    Float weightLimitAdd;

    @Column
    Float weightLimitBonus;

    @Column
    Float will;

    @Builder
    public AuctionItems(Long id, Item item, ItemRarity rarity, LocalDateTime registeredAt, Integer highestBid, Float defaultPhysicalWeaponDamage, Float defaultMagicalWeaponDamage, Float defaultArmorRating, Float actionSpeed, Float agility, Float armorPenetration, Float armorRating, Float armorRatingAdd, Float buffDurationBonus, Float debuffDurationBonus, Float headshotReductionMod, Float itemEquipSpeed, Float knowledge, Float magicalDamage, Float magicalDamageAdd, Float magicalDamageBonus, Float magicalDamageReduction, Float magicalDamageTrue, Float magicalHealing, Float magicalInteractionSpeed, Float magicalPower, Float magicalWeaponDamage, Float magicPenetration, Float magicResistance, Float maxHealthAdd, Float maxHealthBonus, Float moveSpeed, Float moveSpeedAdd, Float moveSpeedBonus, Float physicalDamageAdd, Float physicalDamageBonus, Float physicalDamageReduction, Float physicalDamageTrue, Float physicalHealing, Float physicalPower, Float physicalWeaponDamage, Float physicalWeaponDamageAdd, Float prestigeItemDrop, Float primitive, Float projectileReductionMod, Float regularInteractionSpeed, Float resourcefulness, Float spellCapacityAdd, Float spellCapacityBonus, Float spellCastingSpeed, Float strength, Float utilityEffectivenessAdd, Float utilityEffectivenessBonus, Float weaponDamageAdd, Float weightLimitAdd, Float weightLimitBonus, Float will) {
        this.id = id;
        this.item = item;
        this.rarity = rarity;
        this.registeredAt = registeredAt == null ? LocalDateTime.now() : registeredAt;
        this.highestBid = highestBid;
        this.defaultPhysicalWeaponDamage = defaultPhysicalWeaponDamage;
        this.defaultMagicalWeaponDamage = defaultMagicalWeaponDamage;
        this.defaultArmorRating = defaultArmorRating;
        this.actionSpeed = actionSpeed;
        this.agility = agility;
        this.armorPenetration = armorPenetration;
        this.armorRating = armorRating;
        this.armorRatingAdd = armorRatingAdd;
        this.buffDurationBonus = buffDurationBonus;
        this.debuffDurationBonus = debuffDurationBonus;
        this.headshotReductionMod = headshotReductionMod;
        this.itemEquipSpeed = itemEquipSpeed;
        this.knowledge = knowledge;
        this.magicalDamage = magicalDamage;
        this.magicalDamageAdd = magicalDamageAdd;
        this.magicalDamageBonus = magicalDamageBonus;
        this.magicalDamageReduction = magicalDamageReduction;
        this.magicalDamageTrue = magicalDamageTrue;
        this.magicalHealing = magicalHealing;
        this.magicalInteractionSpeed = magicalInteractionSpeed;
        this.magicalPower = magicalPower;
        this.magicalWeaponDamage = magicalWeaponDamage;
        this.magicPenetration = magicPenetration;
        this.magicResistance = magicResistance;
        this.maxHealthAdd = maxHealthAdd;
        this.maxHealthBonus = maxHealthBonus;
        this.moveSpeed = moveSpeed;
        this.moveSpeedAdd = moveSpeedAdd;
        this.moveSpeedBonus = moveSpeedBonus;
        this.physicalDamageAdd = physicalDamageAdd;
        this.physicalDamageBonus = physicalDamageBonus;
        this.physicalDamageReduction = physicalDamageReduction;
        this.physicalDamageTrue = physicalDamageTrue;
        this.physicalHealing = physicalHealing;
        this.physicalPower = physicalPower;
        this.physicalWeaponDamage = physicalWeaponDamage;
        this.physicalWeaponDamageAdd = physicalWeaponDamageAdd;
        this.prestigeItemDrop = prestigeItemDrop;
        this.primitive = primitive;
        this.projectileReductionMod = projectileReductionMod;
        this.regularInteractionSpeed = regularInteractionSpeed;
        this.resourcefulness = resourcefulness;
        this.spellCapacityAdd = spellCapacityAdd;
        this.spellCapacityBonus = spellCapacityBonus;
        this.spellCastingSpeed = spellCastingSpeed;
        this.strength = strength;
        this.utilityEffectivenessAdd = utilityEffectivenessAdd;
        this.utilityEffectivenessBonus = utilityEffectivenessBonus;
        this.weaponDamageAdd = weaponDamageAdd;
        this.weightLimitAdd = weightLimitAdd;
        this.weightLimitBonus = weightLimitBonus;
        this.will = will;
    }

}
