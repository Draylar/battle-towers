package draylar.battletowers.tower;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import draylar.battletowers.api.Tower;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class TowerLoader extends JsonDataLoader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<Identifier, Tower> towers = ImmutableMap.of();

    public TowerLoader(Gson gson) {
        super(gson, "towers");
    }

    @Override
    protected void apply(Map<Identifier, JsonObject> loader, ResourceManager manager, Profiler profiler) {

    }

    public Map<Identifier, Tower> getTowers() {
        return towers;
    }
}
