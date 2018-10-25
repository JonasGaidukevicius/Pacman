package lt.gaidukevicius;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Labirintas extends JFrame {

	private Container pane;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem save;
	private String[] variantai = { ".", "#", "*", "~" };
	private JButton[][] mygtukai = new JButton[15][15];
	private short levelDataNew[][] = { { 19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{ 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20 },
			{25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28 } };

	public Labirintas() {
		super();
		pane = getContentPane();
		pane.setLayout(new GridLayout(15, 15, 1, 1));
		setTitle("Labirinto redagavimas");
		setSize(680, 680);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		initializeBoard();
		initializeMenuBar();
	}

	// Siuo metodu sukuriu lenta su mygtukais
	public void initializeBoard() {

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				JButton btn = new JButton();
				if(i == 11 && j == 7) {
					btn.setText("*");
					mygtukai[i][j] = btn;
				} else if(i == 4 && j == 4) {
					btn.setText("~");
					mygtukai[i][j] = btn;
				} else {
					btn.setText("."); 
					mygtukai[i][j] = btn;
					btn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if(".".equals(btn.getText())) {
								btn.setText("#");
							} /*else if("#".equals(btn.getText())) {
								btn.setText("*");
							} else if("*".equals(btn.getText())) {
								btn.setText("~");
							}*/ else {
								btn.setText(".");
							}
							
						}
						
					});

				}
				
				pane.add(btn);
			}
		}
	}

	// siuo metodu sukuriu meniu
	private void initializeMenuBar() {
		// meniu punktas
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				labirintoIssaugojimas();
			}
		});
		menu.add(save);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	// Siuo metodu pereinu per mygtukus, paimu ju reiksmes ir konvertuoju jas i
	// skaicius, kuriuos supranta pagrindine programa
	public void labirintoSuformavimas() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				//cia patikrina jeigu langelyje yra #
				if("#".equals(mygtukai[i][j].getText())) {
					levelDataNew[i][j] -= 16;
				} else {
					if(i != 0) {
						if("#".equals(mygtukai[i - 1][j].getText())) {
							levelDataNew[i][j] += 2;
						}
					}
					if(j != 0) {
						if("#".equals(mygtukai[i][j - 1].getText())) {
							levelDataNew[i][j] += 1;
						}
					}
				}
				//cia patikrina, jeigu langelyje yra .
				if(".".equals(mygtukai[i][j].getText()) || "~".equals(mygtukai[i][j].getText()) || "*".equals(mygtukai[i][j].getText())) {
					if(j != 14) {
						if("#".equals(mygtukai[i][j + 1].getText())) {
							levelDataNew[i][j] += 4;
						}
					}
					if(i != 14) {
						if("#".equals(mygtukai[i + 1][j].getText())) {
							levelDataNew[i][j] += 8;
						}
					}	
				}
				
				System.out.println(levelDataNew[i][j]);
			}
		}
	}

	public void labirintoIssaugojimas() {
		labirintoSuformavimas();
		
		BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter("maps/level_02.txt", false));
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					writer.append(Short.toString(levelDataNew[i][j]));
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
