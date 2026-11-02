package ru.crystalbounty;
import java.util.*;
public final class AbilityRegistry {
 public interface Ability {String id();String title();int price();}
 private static final Map<String,Ability> ITEMS=new LinkedHashMap<>();
 static{register(new DashAbility());} private AbilityRegistry(){}
 public static void register(Ability a){if(ITEMS.putIfAbsent(a.id(),a)!=null)throw new IllegalArgumentException("Duplicate ability "+a.id());}
 public static Ability get(String id){return ITEMS.get(id);} public static Collection<Ability> all(){return Collections.unmodifiableCollection(ITEMS.values());}
}