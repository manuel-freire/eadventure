/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.eucm.ead.editor.view.generics;

import javax.swing.JFrame;

public class ElementControllerGetPanelTest extends JFrame {
	//
	//	private static final long serialVersionUID = 1L;
	//
	//	private Scene scene, scene2;
	//
	//	private SwingProviderFactory swingProviderFactory;
	//
	//	private SceneController sceneController, sceneController2;
	//
	//	private SceneElementDefController sceneElementController;
	//
	//	private JComponent component;
	//
	//	public static void main(String[] args) {
	//		try {
	//			UIManager.setLookAndFeel(EAdGUILookAndFeel.getInstance());
	//		} catch (UnsupportedLookAndFeelException e) {
	//			e.printStackTrace();
	//		}
	//		new ElementControllerGetPanelTest();
	//	}
	//
	//
	//    public ElementControllerGetPanelTest() {
	//        setSize( 600,400 );
	//
	//        setLayout(new BorderLayout());
	//
	//        scene = new InitScene();
	//        scene2 = new ComplexElementScene();
	//
	//        CommandManager commandManager = mock(CommandManager.class);
	//
	//        sceneController = new SceneController();
	//        sceneController.setElement(scene);
	//
	//        sceneController2 = new SceneController();
	//        sceneController2.setElement(scene2);
	//
	//        SceneElementDef sceneElementDef = mock(SceneElementDef.class);
	//        when(sceneElementDef.getName()).thenReturn(new EAdString(("testName"));
	//        when(sceneElementDef.getDoc()).thenReturn(new EAdString(("testDocumentation"));
	//        when(sceneElementDef.getDesc()).thenReturn(new EAdString(("testDescription"));
	//        when(sceneElementDef.getDetailDesc()).thenReturn(new EAdString(("testDetailedDescription"));
	//
	//        sceneElementController = new SceneElementDefController();
	//        sceneElementController.setElement(sceneElementDef);
	//
	//        StringHandler stringHandler = new EditorStringHandler();
	//
	//        swingProviderFactory = new SwingProviderFactory(stringHandler, commandManager);
	//
	//        add(getButtons(), BorderLayout.NORTH);
	//
	//        setVisible( true );
	//        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	//        //pack();
	//    }
	//
	//    public JPanel getButtons() {
	//    	EAdBorderedPanel panel = new EAdBorderedPanel("buttons");
	//    	panel.setLayout(new GridLayout(1, 0));
	//
	//    	JButton button = new JButton("scene");
	//    	button.addActionListener(new ActionListener() {
	//
	//			@Override
	//			public void actionPerformed(ActionEvent arg0) {
	//		        Panel panel = sceneController.getPanel(ElementController.LevelOfDetail.EXPERT);
	//		        ComponentProvider<Panel, JComponent> componentProvider = swingProviderFactory.getProvider(panel);
	//		        if (component != null)
	//		        	remove(component);
	//		        component = componentProvider.getComponent(panel);
	//		        add(component, BorderLayout.CENTER);
	//		        validate();
	//			}
	//
	//    	});
	//    	panel.add(button);
	//
	//    	button = new JButton("scene2");
	//    	button.addActionListener(new ActionListener() {
	//
	//			@Override
	//			public void actionPerformed(ActionEvent arg0) {
	//		        Panel panel = sceneController2.getPanel(ElementController.LevelOfDetail.EXPERT);
	//		        ComponentProvider<Panel, JComponent> componentProvider = swingProviderFactory.getProvider(panel);
	//		        if (component != null)
	//		        	remove(component);
	//		        component = componentProvider.getComponent(panel);
	//		        add(component, BorderLayout.CENTER);
	//		        validate();
	//			}
	//
	//    	});
	//    	panel.add(button);
	//
	//    	button = new JButton("element");
	//    	button.addActionListener(new ActionListener() {
	//
	//			@Override
	//			public void actionPerformed(ActionEvent arg0) {
	//		        Panel panel = sceneElementController.getPanel(ElementController.LevelOfDetail.EXPERT);
	//		        ComponentProvider<Panel, JComponent> componentProvider = swingProviderFactory.getProvider(panel);
	//		        if (component != null)
	//		        	remove(component);
	//		        component = componentProvider.getComponent(panel);
	//		        add(component, BorderLayout.CENTER);
	//		        validate();
	//			}
	//
	//    	});
	//    	panel.add(button);
	//
	//    	panel.setVisible(true);
	//    	return panel;
	//    }

}
