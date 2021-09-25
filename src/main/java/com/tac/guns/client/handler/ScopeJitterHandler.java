package com.tac.guns.client.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Date;

import static java.lang.Math.PI;

public class ScopeJitterHandler {
    private static ScopeJitterHandler instance;

    private boolean isStabilizing = false;
    private boolean forceBreathing = false;
    private Long radix = 0L;
    private int respiratoryQuotient = 0;
    private final int component = 85;
    private double xTarget = Math.random()*2-1;
    private final double duration = 5.5;

    public static ScopeJitterHandler getInstance() {
        return instance == null ? instance = new ScopeJitterHandler() : instance;
    }

    private ScopeJitterHandler() {}

    public boolean isStabilizing() {
        return isStabilizing;
    }

    public void setStablizing(boolean value){
        isStabilizing = value;
    }

    public boolean isForceBreathing() {return forceBreathing;}

    public void setForceBreathing(boolean value){
        forceBreathing = value;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) { tick();}

    public void tick(){
        double suffocationPoint = 20.0;
        if(!isStabilizing()||forceBreathing){
            radix += respiratoryQuotient/(int)(suffocationPoint*component)*2 + 1;
            if(radix % (long)(duration *component / 2) <= 2) xTarget = Math.random()*2-1;
            if(respiratoryQuotient > 0) respiratoryQuotient-=5;
            if(respiratoryQuotient <= 0) forceBreathing = false;
        }else {
            respiratoryQuotient ++;
            if(respiratoryQuotient >= suffocationPoint*component) forceBreathing = true;
        }
    }

    public double getYOffsetRatio(){
        double degree = ((radix % (long)(duration *component)) / (duration *component)) * 2 * PI;
        return Math.sin(degree);
    }

    public double getXOffsetRatio(){
        double degree = ((radix % (long)(duration *component / 2)) / (duration *component / 2)) * PI;
        return Math.sin(degree)*xTarget;
    }
}