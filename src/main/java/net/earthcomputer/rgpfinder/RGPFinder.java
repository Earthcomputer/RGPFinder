package net.earthcomputer.rgpfinder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.FlowLayout;

@SuppressWarnings("all")
public class RGPFinder extends JFrame {

	private JPanel contentPane;
	private JTextField xTextField;
	private JTextField zTextField;
	private JTextField worldSeedTextField;
	private JLabel lblWorldSeedClarifier;
	private JTextField exactXTextField;
	private JTextField exactZTextField;
	private JPanel exactPosPanel;
	private JCheckBox exactPosChckbx;
	private long worldSeed = 1563854987194673247L;
	private JTextField radiusTextField;
	private JTextArea outputTextArea;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGPFinder frame = new RGPFinder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RGPFinder() {
		setTitle("RGP Finder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.1);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_2);
		
		JLabel lblChunkX = new JLabel("Chunk X");
		panel_2.add(lblChunkX);
		
		xTextField = new JTextField();
		panel_2.add(xTextField);
		xTextField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel.add(panel_3);
		
		JLabel lblChunkZ = new JLabel("Chunk Z");
		panel_3.add(lblChunkZ);
		
		zTextField = new JTextField();
		panel_3.add(zTextField);
		zTextField.setColumns(10);
		
		exactPosPanel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) exactPosPanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(exactPosPanel);
		
		exactPosChckbx = new JCheckBox("Exact Position");
		exactPosChckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setEnabled(exactPosPanel, exactPosChckbx.isSelected());
				exactPosPanel.setEnabled(true);
				exactPosChckbx.setEnabled(true);
			}
		});
		exactPosPanel.add(exactPosChckbx);
		
		JLabel label = new JLabel("(");
		exactPosPanel.add(label);
		
		exactXTextField = new JTextField();
		exactPosPanel.add(exactXTextField);
		exactXTextField.setColumns(2);
		
		JLabel label_1 = new JLabel(", ");
		exactPosPanel.add(label_1);
		
		exactZTextField = new JTextField();
		exactPosPanel.add(exactZTextField);
		exactZTextField.setColumns(2);
		
		JLabel label_2 = new JLabel(")");
		exactPosPanel.add(label_2);
		
		setEnabled(exactPosPanel, false);
		exactPosPanel.setEnabled(true);
		exactPosChckbx.setEnabled(true);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel_5.add(panel_4);
		
		JLabel lblWorldSeed = new JLabel("World seed");
		panel_4.add(lblWorldSeed);
		
		worldSeedTextField = new JTextField();
		panel_4.add(worldSeedTextField);
		worldSeedTextField.setColumns(10);
		worldSeedTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				changed();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				changed();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				changed();
			}
			
			private void changed() {
				try {
					worldSeed = Long.parseLong(worldSeedTextField.getText());
				} catch (NumberFormatException e) {
					worldSeed = worldSeedTextField.getText().hashCode();
				}
				lblWorldSeedClarifier.setText("World Seed: " + worldSeed);
			}
		});
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_6.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		panel_5.add(panel_6);
		
		lblWorldSeedClarifier = new JLabel("World Seed: 0");
		panel_6.add(lblWorldSeedClarifier);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_8.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		panel.add(panel_8);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int portalX, portalZ, maxRadius;
				try {
					portalX = Integer.parseInt(xTextField.getText());
					portalZ = Integer.parseInt(zTextField.getText());
					maxRadius = Integer.parseInt(radiusTextField.getText());
				} catch (NumberFormatException e) {
					return;
				}
				
				boolean exact = exactPosChckbx.isSelected();
				int exactX, exactZ;
				if (exact) {
					try {
						exactX = Integer.parseInt(exactXTextField.getText());
						exactZ = Integer.parseInt(exactZTextField.getText());
					} catch (NumberFormatException e) {
						return;
					}
				} else {
					exactX = exactZ = 0;
				}
				
				Thread thread = new Thread(() -> {
					outputTextArea.setText("");
					
					World portalArea = World.alloc(portalX * 16, 0, portalZ * 16, 32, 256, 32);
					ChunkGeneratorEnd chunkGen = new ChunkGeneratorEnd(worldSeed);
					chunkGen.generateChunk(portalArea, portalX, portalZ);
					chunkGen.generateChunk(portalArea, portalX + 1, portalZ);
					chunkGen.generateChunk(portalArea, portalX, portalZ + 1);
					chunkGen.generateChunk(portalArea, portalX + 1, portalZ + 1);
					
					for (int r = 0; r < maxRadius; r++) {
						for (int i = 0, e = r == 0 ? 1 : 4 * r; i < e; i++) {
							int loadX, loadZ;
							if (i <= r * 2) {
								loadX = portalX - r + i;
								loadZ = portalZ - (r - Math.abs(portalX - loadX));
							} else {
								loadX = portalX + r - (i - r * 2);
								loadZ = portalX + (r - Math.abs(portalX - loadX));
							}
							chunkGen.resetSeed(loadX, loadZ);
							chunkGen.hasGeneratedGateway = false;
							chunkGen.populate(portalArea.copy(), portalX, portalZ);
							if (chunkGen.hasGeneratedGateway) {
								if (!exact || (exactX == chunkGen.relGatewayX && exactZ == chunkGen.relGatewayZ)) {
									String result = "(" + loadX + ", " + loadZ + ")";
									result += " -> (" + chunkGen.relGatewayX + ", " + chunkGen.relGatewayY + ", " + chunkGen.relGatewayZ + ")";
									outputTextArea.append(result + "\n");
								}
							}
						}
					}
				});
				thread.setDaemon(true);
				thread.start();
			}
		});
		panel_8.add(btnSearch);
		
		JLabel lblRadius = new JLabel("Radius");
		panel_8.add(lblRadius);
		
		radiusTextField = new JTextField();
		radiusTextField.setText("100");
		panel_8.add(radiusTextField);
		radiusTextField.setColumns(5);
		
		JLabel lblChunks = new JLabel("chunks");
		panel_8.add(lblChunks);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		outputTextArea = new JTextArea();
		outputTextArea.setEditable(false);
		scrollPane.setViewportView(outputTextArea);
	}
	
	private static void setEnabled(Container container, boolean enabled) {
		container.setEnabled(enabled);
		for (Component child : container.getComponents()) {
			if (child instanceof Container) {
				setEnabled((Container) child, enabled);
			} else {
				child.setEnabled(enabled);
			}
		}
	}

}
