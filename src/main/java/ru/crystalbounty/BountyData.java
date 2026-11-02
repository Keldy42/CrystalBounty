package ru.crystalbounty;
public final class BountyData {
 public int points=0; public String target=""; public boolean orderActive=false, targetKilled=false, dashPurchased=false, dashEnabled=false; public int dashKeyCode=86;
 public void normalize(){if(target==null)target="";if(target.isBlank()){orderActive=false;targetKilled=false;}if(!dashPurchased)dashEnabled=false;if(dashKeyCode<32||dashKeyCode>348)dashKeyCode=86;}
}