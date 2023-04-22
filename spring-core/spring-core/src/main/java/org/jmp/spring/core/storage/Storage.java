package org.jmp.spring.core.storage;

import static org.jmp.spring.core.dao.impl.EventDaoImpl.EVENT_PREFIX;
import static org.jmp.spring.core.dao.impl.TicketDaoImpl.TICKET_PREFIX;
import static org.jmp.spring.core.dao.impl.UserDaoImpl.USER_PREFIX;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.jmp.spring.core.model.impl.EventImpl;
import org.jmp.spring.core.model.impl.TicketImpl;
import org.jmp.spring.core.model.impl.UserImpl;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Setter
public class Storage
{

    private String filePath;
    private ObjectMapper objectMapper;
    @Getter
    private Map<String, Object> storageMap;

    private static final Map<String, Class<?>> PARSING_MAP = Map.of(
            USER_PREFIX, UserImpl.class,
            EVENT_PREFIX, EventImpl.class,
            TICKET_PREFIX, TicketImpl.class
    );


    public void init() throws IOException
    {
        ResourceLoader loader = new DefaultResourceLoader();
        File file = loader.getResource("classpath:" + filePath).getFile();
        JsonNode jsonNode = objectMapper.readTree(file);
        jsonNode.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            storageMap.put(key, parseModel(key, entry.getValue()));
        });
    }

    private Object parseModel(String key, JsonNode node) {
        Object modelObject;
        String keyPrefix = String.format("%s:", key.split(":")[0]);
        Class<?> modelClass = PARSING_MAP.get(keyPrefix);
        if (modelClass == null) {
            throw new IllegalStateException("Unknown Entity type. Check provided json");
        }
        try
        {
            modelObject = objectMapper.treeToValue(node, modelClass);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException("Error parsing json: " + e.getMessage());
        }

        return modelObject;
    }
}
