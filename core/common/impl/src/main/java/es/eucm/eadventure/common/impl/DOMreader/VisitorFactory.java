package es.eucm.eadventure.common.impl.DOMreader;

import java.util.HashMap;
import java.util.Map;

public class VisitorFactory {

	private Map<String, NodeVisitor<?>> map;
	
	private static VisitorFactory instance;
	
	private VisitorFactory() {
		map = new HashMap<String, NodeVisitor<?>>();
		addVisitor(new ElementNodeVisitor());
		addVisitor(new ParamNodeVisitor());
		addVisitor(new AssetNodeVisitor());
		addVisitor(new ListNodeVisitor());
	}
	
	private void addVisitor(NodeVisitor<?> nodeVisitor) {
		map.put(nodeVisitor.getNodeType(), nodeVisitor);
	}
	
	static public NodeVisitor<?> getVisitor(String key) {
		if (instance == null)
			instance = new VisitorFactory();
		NodeVisitor<?> visitor = instance.map.get(key);
		if (visitor == null)
			throw new RuntimeException("missing visitor " + key);
		return visitor;
	}
}
