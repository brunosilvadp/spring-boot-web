package deserialize;

import java.io.IOException;

import com.bruno.boticario.model.Product;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public class ProductDeserialize extends StdDeserializer<Product>{

	public ProductDeserialize() {
		this(null);
	}
	
	public ProductDeserialize(Class<?> vc) {
		super(vc);
	}

	@Override
	public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		String code = node.get("code").asText();
		String name = node.get("name").asText();
		Double unitPrice = (Double) node.get("unitPrice").doubleValue();
		Integer stock = (Integer) node.get("stock").numberValue();
		Integer minimumStock = (Integer)  node.get("minimumStock").numberValue();
		return new Product(code, name, unitPrice, stock, minimumStock);
	}

}
