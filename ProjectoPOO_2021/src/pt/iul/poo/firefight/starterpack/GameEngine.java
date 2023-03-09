package pt.iul.poo.firefight.starterpack;


import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Interfaces.Burnable;
import abstracts.GameObject;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.objects.Bulldozer;
import pt.iul.poo.firefight.objects.Fire;
import pt.iul.poo.firefight.objects.Fireman;
import pt.iul.poo.firefight.objects.Plane;
import pt.iul.poo.firefight.objects.Score;
import pt.iul.poo.firefight.objects.Water;
import pt.iul.poo.firefight.objects.eucaliptus;
import pt.iul.poo.firefight.objects.grass;
import pt.iul.poo.firefight.objects.land;
import pt.iul.poo.firefight.objects.pine;

public class GameEngine implements Observer {

	public static GameEngine INSTANCE;
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private int gameLevel = 1;


	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI
	private List<ImageTile> tileList; // Lista de imagens
	private Fireman fireman;
	private Plane plane;
	private Bulldozer bulldozer;




	private GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI
		tileList = new ArrayList<>();
	}


	//---------------- Funçoes para criar Firemans/Bulldozers/Planes || Para os meter null -------------------//
	public void setFireman(Fireman man) {
		fireman = man;
	}

	public void setBulldozer(Bulldozer a) {
		bulldozer = a;
	}
	public void setPlane(Plane a) {
		plane = a;
	}


	//---------------------- getters uteis para as outras classes para chamar algo -----------------//

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public List<ImageTile> getImageTile()  {
		return tileList;

	}

	public ImageMatrixGUI getGUI() {
		return gui;
	}

	@Override
	public void update(Observed source) {
		boolean hasMoved = false;
		int x = getPontuacao();
		if(checkCondition(obj -> obj instanceof Fire || obj instanceof Water)) {


			int keyPressed = ImageMatrixGUI.getInstance().keyPressed();
			if (keyPressed == KeyEvent.VK_UP|| keyPressed == KeyEvent.VK_DOWN 
					|| keyPressed == KeyEvent.VK_LEFT ||keyPressed == KeyEvent.VK_RIGHT 
					||keyPressed == KeyEvent.VK_ENTER){
			
				Direction d = Direction.directionFor(keyPressed);
				
				if(checkCondition(obj -> obj instanceof Water)) {
					Water water = (Water) getObject(obj -> obj instanceof Water);
					removeImage(water);
				}

				if(hasMoved == false) {
					fireman.move(d, getListaAtual());
					

					hasMoved = true;
				}
				bulldozer.move(d, getListaAtual());

				spread();

				removeHP(getListForCertainObject(obj -> obj instanceof Fire));

			}
						else if(keyPressed == KeyEvent.VK_P) {
							if(plane == null) {
							int coluna = getColWithMoreFire();
							Plane aviao = new Plane((new Point2D (coluna,9)));
							addImage(aviao);
							setPlane(aviao);
							
							}
						}
						if(plane !=null && hasMoved == true) {
			
							plane.move(getListaAtual());
							hasMoved = false;
					}
						
						
						
						ImageMatrixGUI.getInstance().setStatusMessage(Integer.toString(x));
			gui.update(); 

		}
		else {
			gameLevel++;
			ImageMatrixGUI.getInstance().setStatusMessage("GameOver! -- Final score : " + Integer.toString(x));
			WriteOnFileSimpleVersion();
			tileList.removeAll(tileList);
			gui.clearImages();
			start();
		}
	}
	
	public ArrayList<Score> ReadScoreFile(File file, Score novo) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		ArrayList<Score> scores = new ArrayList<Score>();

		while(scan.hasNextLine()) {
			scores.add(new Score(scan.next(),scan.nextInt()));
			scan.nextLine();
			Collections.sort(scores);
		}
		if(scores.size() == 0) {
			scores.add(novo);
		}
		
		
		ArrayList<Score> TemporaryList = new ArrayList<Score>();
		TemporaryList.addAll(scores);

		for(Score s : TemporaryList) {
			if(s.getScore() < novo.getScore() || TemporaryList.size()<5) {
				scores.add(novo);
				if(TemporaryList.size()>4) {
					scores.remove(4);
				}
				break;
			}
		}
		return scores;	
	}

	public void WriteOnFileSimpleVersion() {
		File file = new File("Records"+ gameLevel +".txt");
		Scanner scan = new Scanner (System.in);
		int score = getPontuacao();
		try {
			if(!file.exists()){
				file.createNewFile();
			} 
			System.out.println("each burnable object counts as 1 point, if it dies you lose a point until there's no more burnable object left!");
			System.out.println("Write your name to register your score!\n");
			String name = scan.nextLine();
			Score s = new Score(name,score);
			ArrayList<Score> FinalScores = ReadScoreFile(file, s);
			PrintWriter fileWrite = new PrintWriter(file);
			
			
			Collections.sort(FinalScores);
			 List<Score> newList = FinalScores.stream().distinct().collect(Collectors.toList());
			for(Score lista: newList) {
				fileWrite.println(lista);
			}
			System.out.println("Done!");
			fileWrite.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		scan.close();

	}



	public ArrayList<GameObject> getListaAtual() {

		ArrayList<GameObject> listaObjetos = new ArrayList<GameObject>();
		for (ImageTile b : tileList) {
			listaObjetos.add((GameObject) b);

		}
		return listaObjetos;
	}


	// ----------------Returna a coluna com mais fogos de forma a criar o avião-----------//


	public int getColWithMoreFire() {
		int [] count = new int [10];
		int max = 0;
		int maxIndex = 0;

		for (ImageTile fogo : tileList) {
			if(fogo instanceof Fire) {
				int l = fogo.getPosition().getX();
				count[l]++;
			}
		}

		for ( int i=0; i!= count.length; i++) {
			if(count[i]> max) {
				max = count[i];
				maxIndex = i;

			}
		}

		return maxIndex ;

	}

	//-------------- Funçao para retirar a vida dos objetos do tipo Burnable com fogo na mesma posiçao---------------------------//
	private void removeHP(List<ImageTile> listAllFires) {
		List<ImageTile> novaList = new ArrayList<ImageTile>();
		for(ImageTile lista : listAllFires) {
			for (ImageTile obj : tileList) {
				if (obj instanceof Burnable && lista.getPosition().equals(obj.getPosition())) {
					((Burnable) obj).updateHP();
					if(((Burnable) obj).getHP()== 0){
						novaList.add(lista);
					}
				}
			}
		}
		tileList.removeAll(novaList);
		gui.removeImages(novaList);
	}


	//----------------------------------Pontaçao--------------------------------------------------

	//Percorre o mapa para saber qual a pontuaçao final (retira 1 ponto por cada burnable queimado)
	//Ao Retirar terreno do mapa com bulldozer também descontará 1 ponto ou seja o objeto é salvar o maximo de terreno
	//possivel sem o destruir dentro do possivel a menos que seja mesmo necessário
	public int getPontuacao() {
		int sum = totalBurnableOnMap();
		for(ImageTile obj: tileList) {
			if (obj instanceof Burnable) {
				if(((Burnable) obj).getHP()==0) {
					sum--;

				}
			}
		}
		return sum;

	}
	//Percorre o mapa para saber qual a pontuaçao maxima possivel (numero de burnables)
	public int totalBurnableOnMap() {
		int sum = 0;
		for (ImageTile list: tileList) {
			if (list instanceof Burnable) {
				sum++;
			}
		}


		return sum;
	}



	//---------Funçoes para devolver listas de objetos, objetos, posiçoes objetos ... Com uso do predicado-------//


	public List<ImageTile> getListForCertainObject(Predicate<ImageTile> predicate) {
		List<ImageTile> novaLista = new ArrayList<ImageTile>();
		for(ImageTile obj : tileList){
			if(predicate.test(obj)) {
				novaLista.add(obj);
			}
		}
		return novaLista;

	}

	public void clearCertainTypeOfObjects(Predicate<ImageTile> predicate) {
		List<ImageTile> novaLista = new ArrayList<ImageTile>();
		for(ImageTile obj : tileList){
			if(predicate.test(obj)) {
				novaLista.add(obj);
			}
		}
		tileList.removeAll(novaLista);
		gui.removeImages(novaLista);

	}


	public GameObject getObject(Predicate<GameObject> predicate) {
		for(ImageTile obj : tileList){
			if(predicate.test((GameObject) obj)) {
				return (GameObject) obj;
			}
		}
		return null;

	}

	public ImageTile getCertainObjectOnCertainPosition(Point2D d , Predicate <ImageTile> predicate) {
		for( ImageTile obj : tileList) {
			if(predicate.test(obj)) {
				return obj;
			}
		}
		return null;
	}

	public boolean checkCondition(Predicate<ImageTile> predicate) {
		for(ImageTile obj : tileList){
			if(predicate.test(obj)) {
				return true;
			}
		}
		return false;

	}




	public Point2D getWaterPosition() {
		List <GameObject> lista = getListaAtual();
		for (GameObject object : lista)
			if (object instanceof Water) {
				return  object.getPosition();
			}
		return null;

	}

	//---------------- Funçao para returnar os objetos que se colide ao mover para determinado ponto---------------//


	public static GameObject getCollidingObject(Point2D nextMoveToVerify,
			ArrayList<GameObject> arrayListOfObjectsToCheckCollisions) {

		GameObject collidingObj = null;

		for (GameObject a : arrayListOfObjectsToCheckCollisions) {
			if (nextMoveToVerify.equals(a.getPosition())) {
				collidingObj = a;
			}
		}

		return collidingObj;
	}


	//-------------------------Funçoes para remover e adiciona imagens da tileList e gui-----------------//


	public void addImage(ImageTile img) {
		tileList.add(img);
		gui.addImage(img);
	}



	public void removeImage(ImageTile img) {
		tileList.remove(img);
		gui.removeImage(img);

	}

	//-------------------------------------SpreadFire------------------------------------------------//


	public void spread() {

		List<ImageTile> novaList = new ArrayList<ImageTile>(); 
		novaList.addAll(tileList);								
		for(ImageTile it : novaList) {
			if (it instanceof Fire) {
				((Fire)it).spreadFire();

			}

		}

	}

	public void MudaObjeto(GameObject a, GameObject b) {
		addImage(a);
		removeImage(b);


	}
	// CRIAÇÃO DOS OBJECTOS E ENVIAR PARA A GUI 

	public void start() {
		File novoFile = new File("levels\\example.txt");
		lerFicheiros(novoFile);
		sendImagesToGUI();
	}



	public void sendImagesToGUI() {
		gui.addImages(tileList);
	}




	// LER FICHEIRO + CRIAÇÃO DO TERRENO DO MAPA

	private void lerFicheiros(File file) {
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				for (int y = 0; y != GRID_WIDTH; y++) {
					String nomes = scan.nextLine();

					for (int x = 0; x != GRID_HEIGHT; x++) {

						Point2D p = new Point2D(x, y);
						char letra = nomes.charAt(x);

						switch (letra) {
						case 'p':
							tileList.add(new pine(p));
							break;
						case 'e':
							tileList.add(new eucaliptus(p));
							break;
						case '_':
							tileList.add(new land(p));
							break;
						case 'm':
							tileList.add(new grass(p));
							break;
						}
					}
				}

				// ADICIONAR MAIS ELEMENTOS AO JOGO

				for (int j = 0; j != 4; j++) {

					String s = scan.next();
					int x = scan.nextInt();
					int y = scan.nextInt();
					Point2D p = new Point2D(x, y);

					switch (s) {
					case "Fireman":
						fireman = new Fireman(p);
						tileList.add(fireman);
						break;
					case "Bulldozer":
						bulldozer = new Bulldozer(p);
						tileList.add(bulldozer);
						break;
					case "Fire":
						tileList.add(new Fire(p));
						break;
					}
				}
			}
			scan.close();
			gui.update();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



}
