package es.eucm.eadventure.common.impl.reader.visitors;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Node;

import es.eucm.eadventure.common.model.DOMTags;
import es.eucm.eadventure.common.impl.reader.extra.ObjectFactory;
import es.eucm.eadventure.common.resources.assets.AssetDescriptor;

/**
 * <p>
 * Visitor for the asset element of resources.
 * </p>
 * <p>
 * The asset element should be<br>
 * {@code <asset id="ASSET_ID" class="ASSETDESCRIPTOR_CLASS">ASSET_VALUE</asset>}
 * <br>
 * </p>
 */
public class AssetNodeVisitor extends NodeVisitor<AssetDescriptor> {

	protected static final Logger logger = Logger.getLogger("AssetNodeVisitor");

	@Override
	public AssetDescriptor visit(Node node, Field field, Object parent, Class<?> listClass) {
		AssetDescriptor element =  (AssetDescriptor) ObjectFactory.getObject(node.getTextContent(), AssetDescriptor.class);
		if (element != null) {
			setValue(field, parent, element);
			return element;
		}
			
		String uniqueId = node.getAttributes().getNamedItem(DOMTags.UNIQUE_ID_AT).getNodeValue();

		String clazz = node.getAttributes().getNamedItem(DOMTags.CLASS_AT).getNodeValue();
		clazz = translateClass(clazz);
		
		Class<?> c = null;
		try {
			c = ClassLoader.getSystemClassLoader().loadClass(clazz);
			element = (AssetDescriptor) c.newInstance();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		if (element != null)
			ObjectFactory.addAsset(uniqueId, element);
		setValue(field, parent, element);

		readFields(element, node);
		
		return element;
	}
	
	


	@Override
	public String getNodeType() {
		return DOMTags.ASSET_AT;
	}

}
