package draylar.battletowers.api.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.*;
import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class TowerDataLoader extends JsonDataLoader {

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Identifier.class, new IdentifierTypeAdapter()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<Identifier, Tower> towers = ImmutableMap.of();
    private Tower defaultTower =  null;

    public TowerDataLoader() {
        super(GSON, "towers");
    }

    @Override
    public void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
        Map<Identifier, Tower> loadedTowers = Maps.newHashMap();

        for(Map.Entry<Identifier, JsonElement> entry : loader.entrySet()) {
            Identifier id = entry.getKey();

            try {
                Tower tower = GSON.fromJson(entry.getValue(), Tower.class);
                tower.setName(id.getPath());
                loadedTowers.put(id, tower);

                // hacky way to store stone as the default tower
                if(defaultTower == null && id.equals(BattleTowers.id("stone"))) {
                    defaultTower = tower;
                }
            } catch (IllegalArgumentException | JsonParseException exception) {
                LOGGER.error("Parsing error loading tower {}", id, exception);
            }
        }

        towers = loadedTowers;
        LOGGER.info("Loaded {} towers", loadedTowers.size());
        Towers.init();
    }

    public Map<Identifier, Tower> getTowers() {
        return towers;
    }

    public Tower getDefaultTower() {
        return defaultTower;
    }
}
