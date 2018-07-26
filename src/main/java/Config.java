public class Config {
    public static final String ADDRESS = "localhost";
    public static final int PORT = 9042;
    public static final String BOOTSTRAP_SERVERS_CONFIG = "104.154.186.117:9092";
    public static final String GROUP_ID_CONFIG = "test-group";
    public static final String KEY_DESERIALIZER_CLASS_CONFIG = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_DESERIALIZER_CLASS_CONFIG = "io.confluent.kafka.serializers.KafkaAvroDeserializer";
    public static final String SCHEMA_URL = "http://35.184.181.97:8089";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";
    public static final String AUTO_COMMIT = "true";
    public static final String AUTO_COMMIT_INTERVAL = "1000";
    public static final String SESSION_TIME_OUT = "30000";
    public static final String TOPIC_LOGIN = "driver_login_status";
    public static final String TOPIC_SHIFT = "driver_shift_status";
    public static final String TOPIC_DRIVER ="driver_status";
}
