import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

 
class MyPanel extends JPanel implements MouseListener,KeyListener{
private Board board;
  public MyPanel () {  
    board = new Board();      	
    setPreferredSize(new Dimension(600, 600));
  }   
  
  JLabel finishGame;
  int topMenu = 30;
  int xMouse;
  int yMouse;
  int ballSize = 40;
  ImageIcon  icon =new ImageIcon("bckg4.jpg");
  private BufferedImage questionB;

  //here added planets instead colors
  List<BufferedImage> planetPlayList;
  List<BufferedImage> planetList;
  
  List<Color> colorChooseList;
  List<Color> colorPlayList= new ArrayList<>(Collections.nCopies(36, Color.red));
  List<Color> colorCheckList = new ArrayList<>(Collections.nCopies(36, Color.yellow));
  List<Color> colorResultList = new ArrayList<>(Collections.nCopies(4, Color.black));
  
  List<Boolean> chooseBooleanList = new ArrayList<>();
  Boolean isChoose = false;
  int aic=5;
  int clickCounter = 0; 
  int clickFirstTime = 0;
  int clickSecondTime = 0;
  int firstClick = 99;
  int counterMarkPlay = 0;
  boolean generatorResult = true;
  boolean game = true;
  List<Integer>listCheck;
  boolean loadListboard = true;
  int actuallLine = 0;

  File question = new File("question.png");
  List<BufferedImage> planetResultList = new ArrayList<>(Collections.nCopies(4, questionB));
  List<List<Integer>> topResult = new ArrayList<>();
  List<List<Integer>> sortedTopResult = sorterWinnerPlayers(topResult);

  boolean switchPlannetLoad = true;

  List<Integer>playNumberOfChoose = new ArrayList<>();
  List<Integer>checkNumberSaveList = new ArrayList<>();
  
  int timerTime = 1000;
  int clicknumberChoose;
  List<List<Integer>> playBoard;
  List<List<Integer>> chooseBoard;
  List<List<Integer>> checkBoard;
  List<List<Integer>> resultBoard;
  List<Integer> resultPlannetsNumber = new ArrayList<>();
  
  

  public void run() throws IOException {
   planetList = board.getPlannets();
   playBoard = board.getPlayList();
   chooseBoard = board.getChooseList();
   checkBoard = board.getCheckList();
   resultBoard = board.getResultList();
   colorChooseList = board.getColor();
   //timer 
  questionB = ImageIO.read(question);
  
    
    // listImg.addAll(Arrays.asList(imgOneB,imgTwoB,imgThreeB,imgFourB,imgFiveB,imgSixB,imgSevenB,imgEightB));
    //planetPlayList = new ArrayList<>(Collections.nCopies(36, questionB));
    if(switchPlannetLoad){
    planetPlayList = new ArrayList<>(Collections.nCopies(36, questionB));
      switchPlannetLoad = false;
      if(!timerStop){
      timer.start();}
  }
   if(generatorResult)
   {
    Random r = new Random();
    
    for(int i=0; i<colorResultList.size();i++)
    {
      int generatorNumber = r.nextInt(7)+1;
      System.out.println(generatorNumber);
      colorResultList.set(i, colorChooseList.get(generatorNumber));
      planetResultList.set(i, planetList.get(generatorNumber));
      resultPlannetsNumber.add(generatorNumber);
    }
    generatorResult = false;
   }
   clicknumberChoose = clickCircle(chooseBoard, xMouse, yMouse,topMenu,ballSize,playBoard);
   chooseBooleanList=markChoose(clicknumberChoose, chooseBoard);
   proccesor();
   repaint();
  }
  

  boolean change = false;
  private void proccesor() {
   change=false;
    if((clicknumberChoose>=0 && clicknumberChoose<=chooseBoard.size()-1) || clickCounter ==1)
    {
      if(clickCounter == 0){
      firstClick =clicknumberChoose;}
      clickCounter=1;
       if(clicknumberChoose>=0+8 && clicknumberChoose<playBoard.size()+8)
      {
        change = changePlayColor(clicknumberChoose,colorPlayList);
        clickCounter = 0;
      }
      if(clicknumberChoose == 100)
      {
        clickCounter = 0;
      }
    }
    else
    {
      clickCounter = 0;
    }
   if(change)
   {
    playNumberOfChoose.add(firstClick);
    colorPlayList.set(clicknumberChoose-8,colorChooseList.get(firstClick));
    planetPlayList.set(clicknumberChoose-8,planetList.get(firstClick));
    counterMarkPlay++;
    listCheck =  checkTheChoice(counterMarkPlay,colorPlayList,colorResultList);
    if(listCheck.size()!=0){
      actuallLine++;
      int firstElement = listCheck.get(2)*4;
      for (int i = firstElement; i < listCheck.get(0)+firstElement; i++) {
        colorCheckList.set(i, Color.BLACK);
      }
      for(int j = listCheck.get(0)+firstElement; j<listCheck.get(0)+firstElement+listCheck.get(1);j++)
      {
        colorCheckList.set(j, Color.WHITE);
      }
      if(listCheck.get(0) == 4 || colorPlayList.get(colorPlayList.size()-1) != colorResultList.get(colorResultList.size()-1) && colorPlayList.get(colorPlayList.size()-1) != Color.red)
      {
        game = false;
      }
    }
   }
  }
  
  String timeMinSec = "0";
  Integer passedTimeSec =0;
  Integer passedTimeMin = 0;
  Integer passedTime = 0;
  String passedTimeString = "0";
  ActionListener timerTask = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      //...Perform a task...
      // if(timerStop)
      // {
      //   timer.stop();
      //   timer.restart();
      // }
      // //System.out.println(passedTime);
      // if(passedTimeSec%20==0 && passedTimeSec!=0)
      // {
      //   passedTimeMin++;
      //   passedTimeSec=0;
      // }
      

      // String sec = Integer.toString(passedTimeSec);
      // String min = Integer.toString(passedTimeMin);
      // if(passedTimeMin==0)
      // {
      //   passedTimeString = sec;
      // }

      // else
      // {
      //   passedTimeString = min + " :MIN"+" " + sec;
      // }
      passedTime=passedTime+1;
      timeMinSec = changeTimeToMinSec(passedTime);
      repaint();
    }
  };

  public static List<List<Integer>> sorterWinnerPlayers(List<List<Integer>> list)
  {
    List<List<Integer>> list2 = list ;
    for(int i=0; i<list2.size(); i++)
    {
      for(int j=i+1; j<list2.size();j++)
      {
        Integer el1Step = list2.get(i).get(0);
        Integer el2Step = list2.get(j).get(0);
        if(el1Step>el2Step)
        {
          List<Integer> el1Res = list2.get(i);
          List<Integer> el2Res = list2.get(j);
          list2.remove(i);
          list2.add(i, el2Res);
          list2.remove(j);
          list2.add(j,el1Res);
        }

      }
    }
    for(int i=0;i<list2.size();i++)
    {
      for(int j=i+1;j<list2.size();j++)
      {
        Integer el1Step = list2.get(i).get(0);
        Integer el2Step = list2.get(j).get(0);
        Integer el1Time = list2.get(i).get(1);
        Integer el2Time = list2.get(j).get(1);
        if(el1Step == el2Step && el1Time>el2Time)
        {
          List<Integer> el1Res = list2.get(i);
          List<Integer> el2Res = list2.get(j);
          list2.remove(i);
          list2.add(i, el2Res);
          list2.remove(j);
          list2.add(j,el1Res);
        }
      }
    }
    while(list2.size()>3)
    {
      list2.remove(list2.size()-1);
    }
    return list2;
  }
  

  public static String changeTimeToMinSec(Integer time)
  {
    Integer min = time/59;
    Integer sec =time -(min*60);
    String res="";
    if(min==0)
    {
      res = Integer.toString(sec)+" sec";
    }
    else
    {
      res = Integer.toString(min)+ " min " + sec+" sec";
    }
    return res;
  }
  Timer timer= new Timer(timerTime, timerTask);;


  private List<Integer> checkTheChoice(int counterMarkPlay2,List<Color> colorPlayList,List<Color> colorResultList) {
    List<Integer> result = new ArrayList<>();
    if(counterMarkPlay2%4==0)
    {
      int correctlyPlace = 0;
      int correctlyColor = 0;
      List<Boolean> list = new ArrayList<>(8);
      list.addAll(Arrays.asList(true,true,true,true,true,true,true,true));
      int whichRow = counterMarkPlay2/4-1;
      for(int i=counterMarkPlay2-4; i<counterMarkPlay2;i++)
      {
        int iCounter = i-(4*whichRow);
        for(int k = 0; k<colorResultList.size();k++){
        if(colorPlayList.get(i) == colorResultList.get(k) && list.get(iCounter)!=false && iCounter == k)
        {
          correctlyPlace++;
          list.set(iCounter, false);
          list.set(iCounter+4, false);
        }
        }
      }
      for(int i=counterMarkPlay2-4; i<counterMarkPlay2;i++)
      {
        int iCounter = i-(4*whichRow);
        for(int j = 0; j<colorResultList.size(); j++)
        {
          if(colorPlayList.get(i) == colorResultList.get(j) && list.get(j)!=false && list.get(iCounter+4) != false && i -(4*whichRow)!= j)
          {
              correctlyColor++;
              list.set(j, false);
              list.set(iCounter+4, false);
          }
        }
      }
      result.add(correctlyPlace);
      result.add(correctlyColor);
      result.add(whichRow);
    }
    return result;
  }

  public static boolean changePlayColor(int clickNumberChoose,List<Color> colorPlayList)
  {
    int firstColor = colorPlayList.size();
    int numberOfPlay = clickNumberChoose-8;
    int min = colorPlayList.size();
    while(firstColor>0)
    {
      if(colorPlayList.get(firstColor-1) == Color.red)
      {
        min = firstColor-1;
      }
      firstColor--;
    }
    if(min == numberOfPlay)
    {
      return true;
    }
    return false;
  }
  public static List<Boolean> markChoose(int clickNumberChoose,List<List<Integer>> listChooseCircle)
  {
    List<Boolean> markChoose = new ArrayList<>(Collections.nCopies(listChooseCircle.size(), false));
    if(clickNumberChoose>=0 && clickNumberChoose<=7)
    {
      markChoose.set(clickNumberChoose, true);
    }
    else
    {
      markChoose = new ArrayList<>(Collections.nCopies(listChooseCircle.size(), false));
      
    }
    return markChoose;
  }
  public static int clickCircle(List<List<Integer>> listChooseCircle, int xMouse, int yMouse, int topMenu, int ballSize,List<List<Integer>> playBoard)
  {
    int padding = 7;
    int ballSizePlayBoard = ballSize-10;
    for(int i = 0; i<listChooseCircle.size();i++)
    {
      int xCircle = listChooseCircle.get(i).get(0);
      int yCircle = listChooseCircle.get(i).get(1);
      if(xCircle+padding<xMouse && xCircle+ballSize+padding>xMouse && yCircle+topMenu<yMouse && yCircle+ballSize+topMenu>yMouse &&xMouse!=0)
      {
        return i;
      }
    }
    for(int i = 0; i< playBoard.size(); i++ )
    {
      int xCircle = playBoard.get(i).get(0);
      int yCircle = playBoard.get(i).get(1);

      if(xCircle+padding<xMouse && xCircle+ballSizePlayBoard+padding>xMouse && yCircle + topMenu< yMouse&&yCircle+ballSizePlayBoard+topMenu>yMouse && xMouse!=0 )
      {
        return i+8;
      }
     }
    return 100;
  }

  private void newGame() {
      actuallLine=0;
      colorPlayList= new ArrayList<>(Collections.nCopies(36, Color.red));
      colorCheckList = new ArrayList<>(Collections.nCopies(36, Color.yellow));
      colorResultList = new ArrayList<>(Collections.nCopies(4, Color.black));
      playNumberOfChoose = new ArrayList<>();
      playBoard = board.getPlayList();
      chooseBoard = board.getChooseList();
      checkBoard = board.getCheckList();
      resultBoard = board.getResultList();
      colorChooseList = board.getColor();
      isChoose = false;
      aic=5;
      clickCounter = 0; 
      clickFirstTime = 0;
      clickSecondTime = 0;
      firstClick = 99;
      counterMarkPlay = 0;
      generatorResult = true;
      game = true;
      passedTimeSec = 0;
      timer.stop();
      timer= new Timer(timerTime, timerTask);;
      timer.start();
      loadListboard = true;
      switchPlannetLoad = true;
      passedTime = 0;
      
      

      chooseBooleanList = new ArrayList<>();

      planetPlayList = new ArrayList<>(Collections.nCopies(36, questionB));
      generatorResult= true;
      repaint(); 
  }
  
  private void saveGame() throws IOException {
    timer.stop();
    JFileChooser fileChooser = new JFileChooser();
    int resultClick = fileChooser.showSaveDialog(null);
    if(resultClick == JFileChooser.CANCEL_OPTION)
    {
      timer.start();
    }
    else
    {
      File file = fileChooser.getSelectedFile();
      String fullPath = file + ".txt";
      FileWriter fileWriter = new FileWriter(fullPath);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.println(actuallLine);
      printWriter.println(playNumberOfChoose.size());
      for(int i = 0; i<playNumberOfChoose.size();i++)
      {
        printWriter.println(playNumberOfChoose.get(i));
      }
      for(Color color: colorCheckList)
      {
        printWriter.println(color.getRGB());
      }
      printWriter.println(counterMarkPlay);
      printWriter.println(resultPlannetsNumber.get(0));
      printWriter.println(resultPlannetsNumber.get(1));
      printWriter.println(resultPlannetsNumber.get(2));
      printWriter.println(resultPlannetsNumber.get(3));
      printWriter.println(passedTime);
      printWriter.close();
      timer.start();
    }
  }

  private void loadGame() 
  {
    timer.stop();
    int counter = 0;
    int colorCounter = 0;
    int counterResult = 0;
    int sizeOfPlay=0;
    JFileChooser chooser = new JFileChooser();
    int resultClick = chooser.showOpenDialog(null);
    if(resultClick == JFileChooser.CANCEL_OPTION)
    {
      timer.start();
    }
    else
    {
        if(chooser.getSelectedFile()!=null){
          Scanner sc;
          try {
            sc = new Scanner(chooser.getSelectedFile());
            while (sc.hasNextLine()) {
            String oneLine = sc.nextLine();
            if(counter == 0)
            {
              actuallLine = Integer.parseInt( oneLine);
              
            }
            else if(counter == 1)
            {
              sizeOfPlay =Integer.parseInt(oneLine);
              
            }
            else if(counter>1 && counter<sizeOfPlay+2)
            {
              Integer numberOfPlannet = Integer.parseInt(oneLine);
              planetPlayList.set(counter-2, planetList.get(numberOfPlannet));
              colorPlayList.set(counter-2, Color.black);
            }
            else if(counter>=sizeOfPlay+2 && counter<sizeOfPlay+2+colorCheckList.size())
            {
              int line = Integer.parseInt(oneLine);
              if(line == -256)
              {
                colorCheckList.set(colorCounter, Color.YELLOW);
              }
              else if(line == -1)
              {
                colorCheckList.set(colorCounter, Color.WHITE);
              }
              else
              {
                colorCheckList.set(colorCounter, Color.BLACK);
              }
              colorCounter++;
            }
            else if(counter==sizeOfPlay+2+colorCheckList.size())
            {
              counterMarkPlay = Integer.parseInt(oneLine);
            }
            else
            {
              int nb = Integer.parseInt(oneLine);
              if(counterResult == 0) planetResultList.set(counterResult, planetList.get(nb));
              if(counterResult == 1) planetResultList.set(counterResult, planetList.get(nb));
              if(counterResult == 2) planetResultList.set(counterResult, planetList.get(nb));
              if(counterResult == 3) planetResultList.set(counterResult, planetList.get(nb));
              if(counterResult == 4) passedTime=nb;
              counterResult++;
            }
            counter++;
            }
          } catch (FileNotFoundException e) {

            e.printStackTrace();
          }
        
      }
      timer.start();
    }
    repaint();
  }
  boolean timerStop = false;
  private void pause()
  {
    if(timer.isRunning())
    {
      timer.stop();
    }
    else
    {
      timer.start();
    }
    repaint();
  }
  
  public void paintComponent (Graphics g) {
    int myClickNumberChoose = clicknumberChoose;
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.drawImage(icon.getImage(), 0,0, null);

  if(playBoard !=null){
  
    if(myClickNumberChoose<8 && chooseBooleanList.size()!=0 && chooseBooleanList.get(myClickNumberChoose) == true && myClickNumberChoose!=100){
          g2.setColor(Color.BLACK);
          g2.fill(new Ellipse2D.Double(chooseBoard.get(clicknumberChoose).get(0)-10,chooseBoard.get(clicknumberChoose).get(1)-10, ballSize+20, ballSize+20));
    }
    for (int i = 0; i < playBoard.size(); i++) {
      int boardX = playBoard.get(i).get(0);
      int boardY = playBoard.get(i).get(1);
      if(planetPlayList!=null && this.playBoard !=null)
      g2.drawImage(planetPlayList.get(i), boardX-10, boardY-10,this);
    }


    for(int i=0; i< chooseBoard.size(); i++)
    {
      g2.setColor(colorChooseList.get(i));
      g2.fill(new Ellipse2D.Double(chooseBoard.get(i).get(0),chooseBoard.get(i).get(1), ballSize, ballSize));
      if(planetList.size()>0){
      g2.drawImage(planetList.get(i), chooseBoard.get(i).get(0), chooseBoard.get(i).get(1),this);} 
    }
    g2.setColor(Color.BLACK);

    int counterCheckBoard = 0;
    for(List<Integer> arr: checkBoard)
    {
      g2.setColor(colorCheckList.get(counterCheckBoard));
      g2.fill(new Ellipse2D.Double(arr.get(0)+20,arr.get(1)+5,ballSize-20,ballSize-20));
      counterCheckBoard++;
    }

    for(int i=0; i<resultBoard.size();i++)
    {
      g2.setColor(colorChooseList.get(3));
      g2.fill(new Ellipse2D.Double(resultBoard.get(i).get(0), resultBoard.get(i).get(1),ballSize,ballSize));
    }  
    if(game==false){
      List<Integer> result = List.of(actuallLine,passedTime);
      topResult.add(result);
      sorterWinnerPlayers(topResult);
      //topResult();
      
      timer.stop();
      if(((colorPlayList.get(colorPlayList.size()-1) == Color.red)|| listCheck.get(0) == 4))
      {
        g2.drawString("Congratulation, You win!, ", 70, 60);
        g2.drawString("you needed "+ actuallLine + " rounds for this ", 70, 80);
        g2.drawImage(planetResultList.get(0), resultBoard.get(0).get(0),resultBoard.get(0).get(1),this);
        g2.drawImage(planetResultList.get(1), resultBoard.get(1).get(0),resultBoard.get(1).get(1),this);
        g2.drawImage(planetResultList.get(2), resultBoard.get(2).get(0),resultBoard.get(2).get(1),this);
        g2.drawImage(planetResultList.get(3), resultBoard.get(3).get(0),resultBoard.get(3).get(1),this);
        
      } 
      else
      {
        g2.drawString("GAME OVER :( )", 80, 60);
      }
    }
}

  //info
  g2.drawString("HELP:", 55, 280);
  g2.drawString("A - NEW GAME", 55, 300);
  g2.drawString("S - SAVE GAME", 55, 320);
  g2.drawString("L - LOAD GAME", 55, 340);
  g2.drawString("P - PAUSE", 55, 360);
  g2.drawString("TOP RESULTS:", 55, 400);
  
  if(sortedTopResult.size()!=0)
  {
    for (int i = 0; i < sortedTopResult.size(); i++) {
      int nb = i+1;
      g2.drawString( nb+ ". "+ sortedTopResult.get(i).get(0), 55, (420 + (i+1)*20));
      g2.drawString( sortedTopResult.get(i).get(1)+"", 85, (420 + (i+1)*20));
    }
  }

  g2.drawString("TIME: " + timeMinSec, 55, 120);
  if(!timer.isRunning() && game)
  {
    g2.drawString(" PAUSE " , 50, 70);
  }
  
} 
  @Override
  public void mouseClicked(MouseEvent e) {

  }
  @Override
  public void mousePressed(MouseEvent e) {

  }
  @Override
  public void mouseReleased(MouseEvent e) {
    PointerInfo mouseInfo = MouseInfo.getPointerInfo();
    Point point = mouseInfo.getLocation();
     xMouse = (int)point.getX();
     yMouse = (int)point.getY();
   if(game && timer.isRunning())
   {
    try {
      run();
    } catch (IOException e1) {

    }
   }
   else
   {
    timer.stop();
   }
  }
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  @Override
  public void mouseExited(MouseEvent e) {

  }
  @Override
  public void keyTyped(KeyEvent e) {

    
  }
  @Override
  public void keyPressed(KeyEvent e) {
    
    switch(e.getKeyCode())
      {
        case 65:
          
          newGame();
        try {
          run();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
          break;
        case 76:
          loadGame();
          break;
        case 83:
        try {
          saveGame();
          break;
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        case 80:
          pause();
          break;
      
      }


    
  }


  @Override
  public void keyReleased(KeyEvent e) {

    
  }
}