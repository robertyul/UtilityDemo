package ylc.tool;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by robertpicyu on 2017/2/21.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**  structure：
     *   objectMapper   <-->   paraser/generator  <-->  JsonNode/ TargetClass
     */
    static {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public JsonUtil() {
    }

    public static void main(String[] args) throws IOException {
        class TargetBean{
            /** annotation
             * 1、@JsonProperty("name")
             * 2、@JsonIgnore
             */
            public String aa;

        }

        /**
         *  1、(Json string)  <-->  (TargetClassObject)
         */
        String jsonString = "";
        TargetBean targetBean = objectMapper.readValue(jsonString,TargetBean.class);
        objectMapper.writeValueAsString(targetBean);


        /** 2、复杂类型转换( 推荐 )
         *     (Json string)  <-->  (TargetClassObject List)
         *     (Json string)  <-->  (TargetClassObject Map)
         */
        String jsonStrings = "";
        JsonParser parser = objectMapper.getFactory().createParser(jsonStrings);
        List<TargetBean> beanList = objectMapper.readValue(jsonString, new TypeReference<List<TargetBean>>() {});
        objectMapper.writeValueAsString(beanList);
        Map<String,TargetBean> beanMap = objectMapper.readValue(jsonString, new TypeReference<Map<String,TargetBean>>() {});
        objectMapper.writeValueAsString(beanMap);

        /**
         * 3、复杂类型转换：利用JsonNode
         */
        parser = objectMapper.getFactory().createParser(jsonString);
        JsonNode nodes = parser.readValueAsTree();
        List<TargetBean> list = new LinkedList<TargetBean>();
        for (JsonNode node : nodes) {
            list.add(objectMapper.readValue(node.asText(), TargetBean.class));
        }
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(new ByteArrayOutputStream());
        jsonGenerator.writeTree(nodes);
    }
}

