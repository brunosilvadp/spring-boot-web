package deserialize;

import java.io.IOException;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.SaleItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class SaleItemDeserialize extends StdDeserializer<SaleItem>{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SaleItemDeserialize() {
		this(null);
	}
	
	public SaleItemDeserialize(Class<?> vc) {
		super(vc);
	}

	@Override
	public SaleItem deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = jp.getCodec().readTree(jp);
		Product product = objectMapper.readValue(node.get("product").toString(), Product.class);
		Double saleValue = (Double) node.get("saleValue").doubleValue();
		Integer saleQuantity = (Integer) node.get("saleQuantity").numberValue();
		SaleItem saleItem = new SaleItem(product, saleQuantity, saleValue);
		return saleItem;
	}

}
